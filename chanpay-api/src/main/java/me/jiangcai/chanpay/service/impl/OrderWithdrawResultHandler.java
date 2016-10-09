package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.data.trade.OrderWithdrawResult;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * @author CJ
 */
public class OrderWithdrawResultHandler extends PayHandler<OrderWithdrawResult> {
    @Override
    public OrderWithdrawResult handleNode(HttpResponse response, JsonNode node) throws IOException {
        return classicsResult(node);
    }
}
