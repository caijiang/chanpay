package me.jiangcai.chanpay;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jiangcai.chanpay.config.ChanpayConfig;
import me.jiangcai.chanpay.data.trade.GetPayChannel;
import me.jiangcai.chanpay.data.trade.OrderWithdraw;
import me.jiangcai.chanpay.data.trade.PaymentToCard;
import me.jiangcai.chanpay.data.trade.QueryTrade;
import me.jiangcai.chanpay.data.trade.support.EncryptString;
import me.jiangcai.chanpay.event.WithdrawalEvent;
import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.Province;
import me.jiangcai.chanpay.model.SubBranch;
import me.jiangcai.chanpay.model.TradeType;
import me.jiangcai.chanpay.service.TransactionService;
import me.jiangcai.chanpay.test.ChanpayTest;
import me.jiangcai.chanpay.util.RSA;
import me.jiangcai.lib.test.SpringWebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.SignatureException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @author CJ
 */
//@Ignore
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProductionTest.Config.class, ChanpayConfig.class})
@WebAppConfiguration
public class ProductionTest extends SpringWebTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private Environment environment;
    @Autowired
    private ABean aBean;

    @Test
    public void x214Notify() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream json = new ClassPathResource("/notify/PaymentToCard.json").getInputStream()) {
            JsonNode root = objectMapper.readTree(json);
            JsonNode request = root.get("request");
            ChanpayTest.MockRequest(mockMvc, request, environment.getRequiredProperty("chanpay.notify.uri"))
                    .andDo(print());
            assertThat(aBean.lastEvent)
                    .isNotNull();
            System.out.println(aBean.lastEvent);
        }

    }


    @Test
    public void verify() throws SignatureException {
        boolean x;
        x = RSA.verify("_input_charset=UTF-8&extension={}&gmt_create=20161008093750&gmt_payment=20161008093750&inner_trade_no=101147589065668057411&notify_id=0b278b96a198429d8a7507f86d9ff347&notify_time=20161008094002&notify_type=trade_status_sync&outer_trade_no=7a30314dee394b829c69df2cbf88a742&trade_amount=0.01&trade_status=TRADE_SUCCESS&version=1.0"
                , "qZ3GRkC4o2Mc2GORIywr0SW00VmU0f6dgOSX6QaH5hdswSclD7Y5ZuGlvj95Hh8NbMz7fZi6fKD8LK++xJAMHmiO1EWGKaZZ5ZRXvQWjfUnQo9RNd0DwGuvatIega2jO40Fqo6ValOLP4k4rcHSdVTxmx6y6lAKdeu9QqgMB6XQ="
                , environment.getRequiredProperty("chanpay.key.platform.public"), "UTF-8");
        assertThat(x)
                .isTrue();

        x = RSA.verify("_input_charset=UTF-8&extension={}&gmt_create=20161008093750&gmt_payment=20161008093750&inner_trade_no=101147589065668057411&notify_id=56fbb7912aee4dbebd03d153439e42bf&notify_time=20161008094002&notify_type=trade_status_sync&outer_trade_no=7a30314dee394b829c69df2cbf88a742&trade_amount=0.01&trade_status=TRADE_FINISHED&version=1.0"
                , "T7WIXLM8bCF08PgPr5IaaMOsV/Ty9PUUvpW1lT9MqoiuwQxG7N/VkAU7C1G3JfmOiSrTB7jTQbQ3sgLIIeHaxcRUZxnmVT/ASfXHmIqB0+XyGsWJDYQdQu+BZE2mGypcBs8yjXuNhVINHM8wzZNvNupnu527Tt0Shb+oNxtwiIc="
                , environment.getRequiredProperty("chanpay.key.platform.public"), "UTF-8");
        assertThat(x)
                .isTrue();
    }

    @Test
    public void otherCard() throws IOException, SignatureException {
        PaymentToCard paymentToCard = new PaymentToCard();
        paymentToCard.setAmount(BigDecimal.valueOf(0.1));

//        paymentToCard.setCity("杭州市");
//        paymentToCard.setProvince("浙江省");
//        paymentToCard.setBankBranch("中国建设银行兴安支行");
//        paymentToCard.setBankCode("CCB");
//        paymentToCard.setBankName("中国建设银行");

        paymentToCard.setProvince(Dictionary.findByName(Province.class, "浙江省"));
        paymentToCard.setCity(paymentToCard.getProvince().getCityList().stream()
                .filter(city -> city.getName().equals("杭州市"))
                .findAny()
                .orElse(null));
        paymentToCard.setBank(Dictionary.findByName(Bank.class, "中国建设银行"));
        paymentToCard.setSubBranch(Dictionary.findByName(SubBranch.class, "中国建设银行兴安支行"));


        paymentToCard.setCardAttribute(CardAttribute.C);
        paymentToCard.setCardName(new EncryptString("蒋才"));
        paymentToCard.setCardNumber(new EncryptString("6236681540007951184"));

        boolean x = transactionService.execute(paymentToCard, null);
        System.out.println(x);
    }

    @Test
    public void d() throws IOException, SignatureException {
        // queryTrade
//        me.jiangcai.chanpay.data.trade.OrderWithdraw
        QueryTrade trade = new QueryTrade();
        trade.setSerialNumber("57838ff8d4004f029d1f069d404f2a4a");
        trade.setType(TradeType.INSTANT);

//        System.out.println(transactionService.execute(trade, new QueryTradeHandler()));

        GetPayChannel request = new GetPayChannel();
//        List<PayChannel> list = transactionService.execute(request, new GetPayChannelHandler());
//        list.forEach(System.out::println);

        OrderWithdraw orderWithdraw = new OrderWithdraw();
        orderWithdraw.setOrderNo("57838ff8d4004f029d1f069d404f2a4a");
        orderWithdraw.setCardNo(new EncryptString("6222081202008477323"));
        orderWithdraw.setName(new EncryptString("徐春锋"));

//        System.out.println(transactionService.execute(orderWithdraw,new OrderWithdrawResultHandler()));

        PaymentToCard paymentToCard = new PaymentToCard();
        paymentToCard.setAmount(BigDecimal.valueOf(20));

        paymentToCard.setProvince(Dictionary.findByName(Province.class, "浙江省"));
        paymentToCard.setCity(paymentToCard.getProvince().getCityList().stream()
                .filter(city -> city.getName().equals("杭州市"))
                .findAny()
                .orElse(null));
        paymentToCard.setBank(Dictionary.findByName(Bank.class, "中国工商银行"));
        paymentToCard.setSubBranch(Dictionary.findByName(SubBranch.class, "中国工商银行德胜支行"));

//        paymentToCard.setCity("杭州市");
//        paymentToCard.setProvince("浙江省");
//        paymentToCard.setBankBranch("中国工商银行德胜支行");
//        paymentToCard.setBankCode("ICBC");
//        paymentToCard.setBankName("中国工商银行");

        paymentToCard.setCardAttribute(CardAttribute.C);

        paymentToCard.setCardName(new EncryptString("徐春锋"));
        paymentToCard.setCardNumber(new EncryptString("6222081202008477323"));

        transactionService.execute(paymentToCard, null);
    }


    @Configuration
    @PropertySource(value = "classpath:/production.properties", ignoreResourceNotFound = true)
    static class Config {
        @Bean
        public ABean aBean() {
            return new ABean();
        }
    }

    static class ABean {
        private WithdrawalEvent lastEvent;

        @EventListener
        public void get(WithdrawalEvent event) {
            this.lastEvent = event;
        }
    }

}
