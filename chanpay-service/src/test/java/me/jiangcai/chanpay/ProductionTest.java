package me.jiangcai.chanpay;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.config.ChanpayConfig;
import me.jiangcai.chanpay.data.trade.GetPayChannel;
import me.jiangcai.chanpay.data.trade.OrderWithdraw;
import me.jiangcai.chanpay.data.trade.PaymentToCard;
import me.jiangcai.chanpay.data.trade.QueryTrade;
import me.jiangcai.chanpay.data.trade.support.EncryptString;
import me.jiangcai.chanpay.data.trade.support.PayChannel;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.TradeType;
import me.jiangcai.chanpay.service.TransactionService;
import me.jiangcai.chanpay.service.impl.GetPayChannelHandler;
import me.jiangcai.chanpay.service.impl.PayHandler;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProductionTest.Config.class, ChanpayConfig.class})
@WebAppConfiguration
public class ProductionTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void d() throws IOException, SignatureException {
        // queryTrade
//        me.jiangcai.chanpay.data.trade.OrderWithdraw
        QueryTrade trade = new QueryTrade();
        trade.setSerialNumber("57838ff8d4004f029d1f069d404f2a4a");
        trade.setType(TradeType.INSTANT);

//        System.out.println(transactionService.execute(trade, new QueryTradeHandler()));

        GetPayChannel request = new GetPayChannel();
//        initRequest(request);
        List<PayChannel> list = transactionService.execute(request, new GetPayChannelHandler());
        assertThat(list)
                .isNotEmpty();
        list.forEach(System.out::println);

        OrderWithdraw orderWithdraw = new OrderWithdraw();
        orderWithdraw.setOrderNo("57838ff8d4004f029d1f069d404f2a4a");
        orderWithdraw.setCardNo(new EncryptString("6222081202008477323"));
        orderWithdraw.setName(new EncryptString("徐春锋"));

//        System.out.println(transactionService.execute(orderWithdraw,new OrderWithdrawResultHandler()));

        PaymentToCard paymentToCard = new PaymentToCard();
        paymentToCard.setAmount(BigDecimal.valueOf(0.1));
        paymentToCard.setBankBranch("中国工商银行德胜支行");
        paymentToCard.setBankCode("ICBC");
        paymentToCard.setBankName("中国工商银行");
        paymentToCard.setCardAttribute(CardAttribute.C);
        paymentToCard.setCity("杭州市");
        paymentToCard.setProvince("浙江省");
        paymentToCard.setCardName(new EncryptString("徐春锋"));
        paymentToCard.setCardNumber(new EncryptString("6222081202008477323"));

        transactionService.execute(paymentToCard, new PayHandler<Object>() {
            @Override
            protected Object handleNode(HttpResponse response, JsonNode node) throws IOException {
                System.out.println(node);
                return null;
            }
        });
    }

    @Configuration
    @PropertySource("classpath:/production.properties")
    static class Config {

    }

}
