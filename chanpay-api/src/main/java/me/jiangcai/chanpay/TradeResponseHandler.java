package me.jiangcai.chanpay;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * @author CJ
 */
public interface TradeResponseHandler<T> {
    T handleNode(HttpResponse response, JsonNode node) throws IOException;
}
