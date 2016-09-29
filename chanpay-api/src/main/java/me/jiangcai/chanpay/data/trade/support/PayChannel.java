package me.jiangcai.chanpay.data.trade.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.CardType;
import me.jiangcai.chanpay.model.PayMode;

/**
 * 支付渠道
 *
 * @author CJ
 */
@Data
public class PayChannel {
    @JsonProperty("instId")
    private String id;
    @JsonProperty("payMode")
    private PayMode mode;
    @JsonProperty("instCode")
    private String code;
    @JsonProperty("instName")
    private String name;
    @JsonProperty("cardType")
    private CardType type;
    @JsonProperty("cardAttribute")
    private CardAttribute attribute;
    private String ext;
}
