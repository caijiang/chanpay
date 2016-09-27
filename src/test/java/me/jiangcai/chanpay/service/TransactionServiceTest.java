package me.jiangcai.chanpay.service;

import me.jiangcai.chanpay.AbstractTestBase;
import me.jiangcai.chanpay.data.pay.CreateInstantTrade;
import me.jiangcai.chanpay.data.pay.GetPayChannel;
import me.jiangcai.chanpay.data.pay.PayRequest;
import me.jiangcai.chanpay.data.pay.QueryTrade;
import me.jiangcai.chanpay.data.pay.QueryTradeResult;
import me.jiangcai.chanpay.data.pay.support.PayChannel;
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
//        CreateEnterpriseMember createEnterpriseMember = new CreateEnterpriseMember();
        CreateInstantTrade request = new CreateInstantTrade();
        initRequest(request);
        request.setBankCode("WXPAY");

        String url = transactionService.execute(request, new InstantTradeHandler());
        System.out.println(url);
        assertThat(url)
                .isNotEmpty();
        // https://tpay.chanpay.com/cashier/index.htm?token=C456656&memberType=1
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
            ((CreateInstantTrade) request).setAmount(BigDecimal.valueOf(100.1));
        }
    }

}