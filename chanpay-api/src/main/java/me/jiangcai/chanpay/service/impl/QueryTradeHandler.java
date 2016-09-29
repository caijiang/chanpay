package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.data.trade.QueryTradeResult;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * @author CJ
 */
public class QueryTradeHandler extends PayHandler<QueryTradeResult> {
    @Override
    protected QueryTradeResult handleNode(HttpResponse response, JsonNode node) throws IOException {
        return classicsResult(node);
    }

}
