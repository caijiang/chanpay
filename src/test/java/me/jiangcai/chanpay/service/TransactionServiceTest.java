package me.jiangcai.chanpay.service;

import me.jiangcai.chanpay.AbstractTestBase;
import me.jiangcai.chanpay.data.pay.CreateInstantTrade;
import me.jiangcai.chanpay.data.pay.GetPayChannel;
import me.jiangcai.chanpay.data.pay.PayRequest;
import me.jiangcai.chanpay.data.pay.QueryTrade;
import me.jiangcai.chanpay.data.pay.QueryTradeResult;
import me.jiangcai.chanpay.data.pay.support.PayChannel;
import me.jiangcai.chanpay.exception.ServiceException;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.CardType;
import me.jiangcai.chanpay.model.PayType;
import me.jiangcai.chanpay.model.TradeType;
import me.jiangcai.chanpay.service.impl.GetPayChannelHandler;
import me.jiangcai.chanpay.service.impl.InstantTradeHandler;
import me.jiangcai.chanpay.service.impl.QueryTradeHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
public class TransactionServiceTest extends AbstractTestBase {
    @Autowired
    private TransactionService transactionService;

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
        // https://tpay.chanpay.com/cashier/index.htm?token=C456656&memberType=1

        System.out.println("扫码支付");

        // 试下扫码支付
        request = new CreateInstantTrade();
        initRequest(request);
        request.setBankCode("WXPAY");
        request.setPayMethod("1");
        request.setPayType(new PayType(CardType.GC, CardAttribute.C));
        request.setReturnPayUrl(true);
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
        transactionService.execute(request, new InstantTradeHandler());
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


    private void initRequest(PayRequest request) {
        request.setPartner("200000400007");

        if (request instanceof CreateInstantTrade) {
            ((CreateInstantTrade) request).setAmount(BigDecimal.valueOf(0.01));
//            request.setN
        }
    }

}