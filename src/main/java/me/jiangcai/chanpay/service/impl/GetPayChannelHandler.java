package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.data.pay.support.PayChannel;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CJ
 */
public class GetPayChannelHandler extends PayHandler<List<PayChannel>> {
    @Override
    protected List<PayChannel> handleNode(HttpResponse response, JsonNode node) throws IOException {
        JsonNode jsonList = node.get("pay_inst_list");
        ArrayList<PayChannel> list = new ArrayList<>();

        if (!jsonList.isArray()) {
            jsonList = objectMapper.readTree(jsonList.asText());
        }

        for (JsonNode data : jsonList) {
            list.add(objectMapper.readValue(objectMapper.treeAsTokens(data), PayChannel.class));
        }
        return list;
    }
}
