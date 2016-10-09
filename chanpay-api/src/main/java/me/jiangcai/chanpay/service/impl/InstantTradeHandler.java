package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.HttpResponse;

/**
 * @author CJ
 */
public class InstantTradeHandler extends PayHandler<String> {

    @Override
    public String handleNode(HttpResponse response, JsonNode node) {

        // 以下返回参数只在扫码支付时返回，即时到账支付业务畅捷方控制页面跳转，就没有同步返回参数了；扫码支付则不控制页面跳转了。
        if (node != null)
            return node.get("pay_url").asText();
        return response.getFirstHeader("Location").getValue();
    }
}
