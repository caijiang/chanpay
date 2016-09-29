package me.jiangcai.chanpay.mock;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * 模拟支付,模拟之后 还可以检查服务器的通知记录
 *
 * @author CJ
 */
@Service
public class MockPay {

    /**
     * 模拟支付
     *
     * @param serialNumber 我方系统订单号
     * @param url          畅捷支付提供的支付地址
     */
    public void pay(String serialNumber, String url) {

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

            // 这个时候就认为是支付成功么? 还是等几秒中 是否看到了支付成功几个字?
            // class = main-txt

//            webDriverWait = new WebDriverWait(driver, 5);
//            webDriverWait.until(new com.google.common.base.Predicate<WebDriver>() {
//                @Override
//                public boolean apply(WebDriver input) {
//                    input.getWindowHandles()
//                            .forEach(str -> System.out.println("handle:" + str));
//                    WebElement txt = input.findElements(By.className("main-txt"))
//                            .stream()
//                            .filter(WebElement::isDisplayed)
//                            .findFirst().orElse(null);
//                    System.out.println(txt);
//                    return txt != null && txt.getText().contains("支付成功");
//                }
//            });

//            System.out.println(driver.getPageSource());
        } finally {
            driver.close();
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
