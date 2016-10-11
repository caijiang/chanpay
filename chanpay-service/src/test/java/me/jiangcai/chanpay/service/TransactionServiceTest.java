package me.jiangcai.chanpay.service;

import me.jiangcai.chanpay.Dictionary;
import me.jiangcai.chanpay.data.trade.CreateInstantTrade;
import me.jiangcai.chanpay.data.trade.GetPayChannel;
import me.jiangcai.chanpay.data.trade.OrderWithdraw;
import me.jiangcai.chanpay.data.trade.OrderWithdrawResult;
import me.jiangcai.chanpay.data.trade.PaymentToCard;
import me.jiangcai.chanpay.data.trade.QueryTrade;
import me.jiangcai.chanpay.data.trade.QueryTradeResult;
import me.jiangcai.chanpay.data.trade.QuickPayment;
import me.jiangcai.chanpay.data.trade.TradeRequest;
import me.jiangcai.chanpay.data.trade.support.EncryptString;
import me.jiangcai.chanpay.data.trade.support.PayChannel;
import me.jiangcai.chanpay.exception.ServiceException;
import me.jiangcai.chanpay.model.Bank;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.CardType;
import me.jiangcai.chanpay.model.Province;
import me.jiangcai.chanpay.model.SubBranch;
import me.jiangcai.chanpay.model.TradeType;
import me.jiangcai.chanpay.service.impl.GetPayChannelHandler;
import me.jiangcai.chanpay.service.impl.InstantTradeHandler;
import me.jiangcai.chanpay.service.impl.OrderWithdrawResultHandler;
import me.jiangcai.chanpay.service.impl.QueryTradeHandler;
import me.jiangcai.chanpay.test.ChanpayTest;
import me.jiangcai.chanpay.test.mock.MockPay;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
@WebAppConfiguration
public class TransactionServiceTest extends ChanpayTest {
    private static final Log log = LogFactory.getLog(TransactionServiceTest.class);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private MockPay mockPay;

    /**
     * 一个特有的标记 表示这会儿是真的在跑!!
     */
    private boolean online = false;

    @Test
    public void x214() throws IOException, SignatureException {
        PaymentToCard paymentToCard = new PaymentToCard();
        paymentToCard.setAmount(new BigDecimal("440495908.972"));

        paymentToCard.setCardName(new EncryptString("测试01"));
        paymentToCard.setCardNumber(new EncryptString("6214830215878947"));

        paymentToCard.setProvince(Dictionary.findByName(Province.class, "上海市"));
        paymentToCard.setCity(paymentToCard.getProvince().getCityList().stream()
                .filter(city -> city.getName().equals("上海市"))
                .findAny()
                .orElse(null));
        paymentToCard.setBank(Dictionary.findByName(Bank.class, "招商银行"));


        SubBranch subBranch = new SubBranch();
        subBranch.setName("中国招商银行上海市浦建路支行");
        paymentToCard.setSubBranch(subBranch);

//        paymentToCard.setBankBranch("中国招商银行上海市浦建路支行");
//        paymentToCard.setBankCode("CMB");
//        paymentToCard.setBankName("招商银行");
//        paymentToCard.setCity("上海市");
//        paymentToCard.setProvince("上海市");
        paymentToCard.setCardAttribute(CardAttribute.C);


        assertThat((boolean) transactionService.execute(paymentToCard, null))
                .isTrue();
    }

    @Test
    public void x218() throws Exception {
        QuickPayment quickPayment = new QuickPayment();
        initRequest(quickPayment);

        quickPayment.setSerialNumber("123243322");
        quickPayment.setAmount(BigDecimal.valueOf(2.50));
        quickPayment.setBuyerIp("10.20.31.88");
        quickPayment.setCardType(CardType.DC);
        quickPayment.setCardAttribute(CardAttribute.C);
        quickPayment.setBankCode("TESTBANK");
        quickPayment.setPayerName("测试01");
        quickPayment.setPayerBankAccount("6214830215878947");
        quickPayment.setIdNumber(new EncryptString("152801111111111111"));
        quickPayment.setMobilePhone(new EncryptString("13511111111"));
        quickPayment.setNotifyUrl("http://dev.chanpay.com/receive.php");

        transactionService.execute(quickPayment, null);
        // 没有商用计划 所有响应不管了
    }

    @Test
    public void x220() throws Exception {
        CreateInstantTrade request = new CreateInstantTrade();
        initRequest(request);
        request.setBankCode("WXPAY");
        if (online) {
            request.setPayerName("徐春锋");
            request.scanPay();
        } else {
            request.setPayerName("测试01");
        }


        //        request.setSerialNumber("111111111111");
//        request.scanPay();
//        request.setAmount(BigDecimal.valueOf(10.0D));
//        request.setNotifyUrl("http://dev.chanpay.com/receive.php");

        String url = transactionService.execute(request, new InstantTradeHandler());
        if (!online)
            mockPay.pay(request.getSerialNumber(), url);
        System.out.println(url);
        System.out.println("订单:" + request.getSerialNumber());

        OrderWithdraw orderWithdraw = new OrderWithdraw();
        initRequest(orderWithdraw);
        orderWithdraw.setOrderNo(request.getSerialNumber());
        orderWithdraw.setCardNo(new EncryptString("6214830215878947"));
        orderWithdraw.setName(new EncryptString("测试01"));
        // online test
        if (online) {
            orderWithdraw.setCardNo(new EncryptString("6222081202008477323"));
            orderWithdraw.setName(new EncryptString("徐春锋"));
        }

        OrderWithdrawResult result = transactionService.execute(orderWithdraw, new OrderWithdrawResultHandler());
        System.out.println(result);
        // TODO  提现银行卡未绑定，请先在商户自助平台绑定该卡信息 cao
        assertThat(result).isNotNull();
//        assertThat(result.isAccepted())
//                .isTrue();
    }

    @Test
    public void x23() throws Exception {
        // http://dev.chanpay.com/doku.php/sdwg:%E6%94%B6%E5%8D%95%E7%BD%91%E5%85%B3%E6%8E%A5%E5%85%A5faq_%E5%86%85%E9%83%A8%E7%89%88
//        demo中2.3接口需要修改如下参数，即可进行扫码支付
//        支付方式: 1
//        借记贷记,对公/对私: C,GC
//        银行简码:WXPAY
//        是否同步否返回支付URL:Y
//        ext1字段，json串增加一个参数[{'subMerchantNo':'畅捷开通好的代理商子商户商户号'}],

//        {"_input_charset":"UTF-8","error_code":"ILLEGAL_PAY_ERROR","error_message":"支付方式错误","is_success":"F","memo":"支付备注错误:WXPAY,C,GC"}
//        CreateEnterpriseMember createEnterpriseMember = new CreateEnterpriseMember();
        CreateInstantTrade request = new CreateInstantTrade();
        initRequest(request);
        request.setBankCode("WXPAY");

        String url = transactionService.execute(request, new InstantTradeHandler());
        System.out.println(url);
        assertThat(url)
                .isNotEmpty();

        mockPay.pay(request.getSerialNumber(), url);

        System.out.println("扫码支付");

        // 试下扫码支付
        request = new CreateInstantTrade();
        initRequest(request);
        request.scanPay();
        try {
            url = transactionService.execute(request, new InstantTradeHandler());
            throw new AssertionError("应当跑错");
        } catch (ServiceException ex) {
            assertThat(ex.getMessage())
                    .isEqualTo("支付方式错误:支付备注错误:WXPAY,C,GC");
            assertThat(ex.getCode())
                    .isEqualTo("ILLEGAL_PAY_ERROR");
        }
//        System.out.println(url);
//        assertThat(url)
//                .isNotEmpty();
    }

    @Test
    public void x26() throws IOException, SignatureException {
        CreateInstantTrade request = new CreateInstantTrade();
        initRequest(request);
        String url = transactionService.execute(request, new InstantTradeHandler());
        System.out.println(url);
        String tradeNo = request.getSerialNumber();

        QueryTrade queryTrade = new QueryTrade();
        initRequest(queryTrade);
        queryTrade.setSerialNumber(tradeNo);
        queryTrade.setType(TradeType.INSTANT);

        QueryTradeResult result = transactionService.execute(queryTrade, new QueryTradeHandler());
        System.out.println(result);
        assertThat(result)
                .isNotNull();

        assertThat(result.getSerialNumber())
                .isNotEmpty();
        assertThat(result.getChanPayNumber())
                .isNotEmpty();
        assertThat(result.getAmount().doubleValue())
                .isGreaterThan(0);
        assertThat(result.getStatus())
                .isNotNull();
        assertThat(result.getTime())
                .isNotNull();
    }


    @Test
    public void x27() throws IOException, SignatureException {
        GetPayChannel request = new GetPayChannel();
        initRequest(request);
        List<PayChannel> list = transactionService.execute(request, new GetPayChannelHandler());
        assertThat(list)
                .isNotEmpty();
        list.forEach(System.out::println);
    }
    // 27 28
    // 218 219


    private void initRequest(TradeRequest request) {
//        request.setPartner("200000400007");

        if (request instanceof CreateInstantTrade) {
            if (((CreateInstantTrade) request).getAmount() == null)
                ((CreateInstantTrade) request).setAmount(BigDecimal.valueOf(0.01));
//            request.setN
        }
    }

}