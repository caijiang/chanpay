package me.jiangcai.chanpay.data.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 指跟支付系统交互的请求
 * http://dev.chanpay.com/doku.php/sdwg:%E4%BC%9A%E5%91%98%E7%BD%91%E5%85%B3%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3
 *
 * @author CJ
 */
@Data
public abstract class PayRequest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 签名方式	String(10)	签名方式只支持RSA、MD5	不可空	MD5
     */
    @JsonProperty("sign_type")
    private String signType;

//    private String version = "1.0";
//    @JsonProperty("_input_charset")
//    private String charset = "UTF-8";
    /**
     * 合作者身份ID
     * 签约合作方的唯一用户号	不可空	2088001159940003
     */
    @JsonProperty("partner_id")
    private String partner;
    private String sign;
    /**
     * 页面跳转同步返回页面路径	String(1000)	处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。
     * 空则不会进行该操作。	可空
     */
    @JsonProperty("return_url")
    private String returnUrl;
    /**
     * 备注	String(1000)	说明信息
     */
    private String memo;

    /**
     * @return 接口名称
     */
    public abstract String serviceName();

    /**
     * @return 组装成字符串
     */
    public String preString() throws IOException {

        Map<String, String> stringStringMap = toMap();

        List<String> names = stringStringMap.keySet().stream().collect(Collectors.toList());
        Collections.sort(names);

        StringBuilder builder = new StringBuilder();
        names.forEach(name -> {
            if (builder.length() > 0)
                builder.append("&");
            builder.append(name).append("=");
            //待签名数据应为参数原始值而非URL Encoding之后的值
            builder.append(stringStringMap.get(name));
        });

        return builder.toString();
    }

    private Map<String, String> toMap() throws IOException {
        JsonNode node = objectMapper.readTree(objectMapper.writeValueAsString(this));

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("service", serviceName());
        stringStringMap.put("version", "1.0");
        stringStringMap.put("_input_charset", "UTF-8");

        node.fields().forEachRemaining(stringJsonNodeEntry -> {
            JsonNode value = stringJsonNodeEntry.getValue();
            if (value.isNull())
                return;
            String str = value.asText();
            if (StringUtils.isEmpty(str))
                return;
            stringStringMap.put(stringJsonNodeEntry.getKey(), str);
        });

        return stringStringMap;
    }

    public HttpEntity toEntity() throws IOException {

        Map<String, String> stringStringMap = toMap();

        return EntityBuilder.create()
                .setContentEncoding("UTF-8")
                .setParameters(stringStringMap.keySet().stream()
                        .map(name -> (NameValuePair) new BasicNameValuePair(name, stringStringMap.get(name)))
                        .collect(Collectors.toList()))
                .build();
    }
}
