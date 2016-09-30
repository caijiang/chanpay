package me.jiangcai.chanpay.test.mock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import me.jiangcai.chanpay.event.TradeEvent;
import me.jiangcai.chanpay.model.TradeStatus;
import me.jiangcai.chanpay.support.ChanpayObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * 模拟支付,模拟之后 还可以检查服务器的通知记录
 * 好吧,太理想了,模拟支付还是存在问题的。
 *
 * @author CJ
 */
@Service
public class MockPay {

    private static final Log log = LogFactory.getLog(MockPay.class);
    private final ObjectMapper objectMapper = new ChanpayObjectMapper();
    private final FileObject logsRoot;
    private final String mockNotifyUri;
    private final MockMvc mockMvc;

    private final List<String> successTrades = new ArrayList<>();

    @Autowired
    public MockPay(Environment environment, WebApplicationContext context, FilterChainProxy springSecurityFilter
            , MockMvcConfigurer mockMvcConfigurer) throws Exception {

        MockitoAnnotations.initMocks(this);
        // ignore it, so it works in no-web fine.
        if (context != null) {
            DefaultMockMvcBuilder builder = webAppContextSetup(context);
//            builder = buildMockMVC(builder);
            if (springSecurityFilter != null) {
                builder = builder.addFilters(springSecurityFilter);
            }

            if (mockMvcConfigurer != null) {
                builder = builder.apply(mockMvcConfigurer);
            }
            mockMvc = builder.build();

            mockNotifyUri = environment.getRequiredProperty("chanpay.notify.uri");
            String logsUri = environment.getRequiredProperty("chanpay.notify.logs");

            FileSystemOptions options = new FileSystemOptions();
            SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, false);
            logsRoot = VFS.getManager().resolveFile(logsUri, options);
            System.out.println(logsRoot);
        } else {
            mockMvc = null;
            mockNotifyUri = null;
            logsRoot = null;
        }

//        checkFor(null);

    }

    @EventListener
    public void trade(TradeEvent event) {
        if (!successTrades.contains(event.getSerialNumber()))
            log.debug(event.getSerialNumber() + " turn to " + event.getTradeStatus());
        if (event.getTradeStatus() == TradeStatus.TRADE_SUCCESS) {
            // 支付成功
            successTrades.add(event.getSerialNumber());
        }
    }

    /**
     * 模拟支付
     *
     * @param serialNumber 我方系统订单号
     * @param url          畅捷支付提供的支付地址
     */
    public void pay(String serialNumber, String url) throws Exception {

        WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME, true)
//        {
//            @Override
//            protected WebClient modifyWebClient(WebClient client) {
//                //currently does nothing, but may be changed in future versions
//                WebClient modifiedClient = super.modifyWebClient(client);
//
//                modifiedClient.getOptions().setThrowExceptionOnScriptError(false);
//                return modifiedClient;
//            }
//        }
                ;
        try {
            driver.get(url);
//            WebDriverEventListener listener;
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("$.ajaxSetup({async:false});");
            WebElement targetBank = driver.findElement(By.id("bank_list"))
                    .findElements(By.className("card-wrap"))
                    .stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst().orElseThrow(IllegalStateException::new);

            WebElement nextButton = driver.findElement(By.id("nextStep"));

            new Actions(driver)
                    .click(targetBank)
                    .click(nextButton)
                    .perform();

            executor.executeScript("$.ajaxSetup({async:false});");
            // 1 寻找错误字段 label class=gTips-error
            // <input type="text" name="idName" value="" id="idName" placeholder="请输入姓名" class="pop-inputText w200" data-required="true" data-describedby="idName-description" data-pattern="^[\u4E00-\u9FA5]{2,10}$" data-description="idName">
            // <input id="agreeto1" type="checkbox" class="marr10" value="on">
            WebElement nameInput = findInput(driver, element -> {
                String str = element.getAttribute("placeholder");
                return str != null && str.contains("输入姓名");
            });
            WebElement idInput = findInput(driver, element -> {
                String str = element.getAttribute("placeholder");
                return str != null && str.contains("输入身份证");
            });
            WebElement accountInput = findInput(driver, element -> {
                String str = element.getAttribute("placeholder");
                return str != null && str.contains("银行卡");
            });
            WebElement mobileInput = findInput(driver, element -> {
                String str = element.getAttribute("placeholder");
                return str != null && str.contains("手机");
            });
            WebElement codeInput = findInput(driver, element -> {
                String str = element.getAttribute("placeholder");
                return str != null && str.contains("输入验证码");
            });
            WebElement agreeCheck = findInput(driver, element -> {
                String str = element.getAttribute("type");
                return str.equals("checkbox");
            });

            WebElement sendMsg = driver.findElement(By.id("send_msg"));
            WebElement submit = driver.findElement(By.id("waitsubmit"));

            new Actions(driver)
                    .click(nameInput).sendKeys("蒋才")
                    .click(idInput).sendKeys("999999999999999999")
                    .click(accountInput).sendKeys("9999999999")
                    .click(mobileInput).sendKeys("13999999999")
                    .click(sendMsg)
                    .perform();

            executor.executeScript("$.ajaxSetup({async:false});");
            WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
            webDriverWait.withTimeout(2, TimeUnit.SECONDS);
            webDriverWait.until(new com.google.common.base.Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver input) {
                    return input.findElement(By.id("send_msg")).getText().contains("重新");
                }
            });

            new Actions(driver)
                    .click(codeInput).sendKeys("123456")
                    .click(agreeCheck)
                    .click(submit)
                    .perform();

            assertErrors(driver);
            try {
                Alert alert = driver.switchTo().alert();
                throw new AssertionError(alert.getText());
            } catch (NoAlertPresentException ignored) {
                //nothing
            }

            // 这个时候就认为是支付成功么? 还是等几秒中 是否看到了支付成功几个字?
            // class = main-txt

            System.out.println(driver.getPageSource());

            webDriverWait = new WebDriverWait(driver, 5);
            webDriverWait.until(new com.google.common.base.Predicate<WebDriver>() {
                @Override
                public boolean apply(WebDriver input) {
                    JavascriptExecutor executor = (JavascriptExecutor) input;
                    executor.executeScript("$.ajaxSetup({async:false});");

                    System.out.println(input.getCurrentUrl());

                    WebElement txt = input.findElements(By.className("main-txt"))
                            .stream()
                            .filter(WebElement::isDisplayed)
                            .findFirst().orElse(null);
                    System.out.println(txt);
                    return txt != null && txt.getText().contains("支付成功");
                }
            });

            System.out.println(driver.getPageSource());

        } finally {
            driver.close();
        }

        log.debug("driver done");

        checkFor(serialNumber);
    }

    private void checkFor(String serialNumber) throws Exception {
        if (logsRoot == null) {
            log.debug("can not work in no-web.");
            return;
        }
        int nextWait = 1;

        // 1 2 4
        while (true) {
            tryMVC();
            if (successTrades.contains(serialNumber))
                return;
            if (nextWait > 10) {
                throw new AssertionError("没有收到成功支付的接口调用");
            }
            Thread.sleep(nextWait * 1000);
            nextWait = nextWait << 1;
        }

    }

    private void tryMVC() throws Exception {
        String folderName = "wx-" + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());

        FileObject[] files = logsRoot.findFiles(new FileSelector() {
            @Override
            public boolean includeFile(FileSelectInfo fileInfo) throws Exception {
                FileName name = fileInfo.getFile().getName();
                return name.getType() == FileType.FILE && name.getBaseName().endsWith(".json");
            }

            @Override
            public boolean traverseDescendents(FileSelectInfo fileInfo) throws Exception {
                FileObject name = fileInfo.getFile();
                if (name.getName().getBaseName().equalsIgnoreCase("logs"))
                    return true;
                //
                return name.getName().getBaseName().equals(folderName);
            }
        });

        // 都走下MVC流程
        for (FileObject json : files) {
            try (InputStream inputStream = json.getContent().getInputStream()) {
                JsonNode node = objectMapper.readTree(inputStream);
                // 过滤掉uri不符合的 因为我们发起的uri仅限于此
                JsonNode request = node.get("request");
                String uri = request.get("uri").asText();
                if (!uri.equals(mockNotifyUri))
                    continue;
                if (request.get("method").asText().equalsIgnoreCase("post")) {
                    MockHttpServletRequestBuilder builder = post(uri);
                    JsonNode headers = request.get("headers");
                    String contentType = null;
                    for (JsonNode header : headers) {
                        String name = header.get("name").asText();
                        if (name.equals("content-length"))
                            continue;
                        if (name.equals("host"))
                            continue;
                        String value = header.get("value").asText();
                        builder = builder.header(name, value);
                        if (name.equalsIgnoreCase("Content-Type")) {
                            contentType = value;
                        }
                    }
                    String content = request.get("content").asText();
                    builder = builder.content(content);
                    // 这个算是MVC Mock 不足的地方 参数不正确!
                    if (content != null && contentType != null && MediaType.parseMediaType(contentType).isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED)) {
                        String[] paramStrings = content.split("&");
                        for (String paramString : paramStrings) {
                            int index = paramString.indexOf("=");
                            String name = paramString.substring(0, index);
                            String value = paramString.substring(index + 1);
                            builder = builder.param(name, URLDecoder.decode(value, "UTF-8"));
                        }
                    }
                    mockMvc.perform(builder)
//                            .andDo(print())
                            .andExpect(status().isOk());
                } else
                    throw new IllegalArgumentException(request.get("method").asText() + " is not supported.");
            }
        }
    }

    private void assertErrors(WebDriver driver) {
        driver.findElements(By.className("gTips-error"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findAny()
                .ifPresent(element -> {
                    throw new AssertionError(element.getText());
                });
    }


    private WebElement findInput(WebDriver driver, Predicate<WebElement> predicate) {
        return driver.findElements(By.tagName("input")).stream()
                .filter(WebElement::isDisplayed)
                .filter(predicate)
                .findAny().orElse(null);
    }


}
