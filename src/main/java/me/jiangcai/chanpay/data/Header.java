package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jiangcai.chanpay.converter.LocalDateTimeDeserializer;
import me.jiangcai.chanpay.converter.TemporalAccessorSerializer;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author CJ
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
class Header {

    /**
     * 报文交易代码
     * C(1, 20)
     */
    @JsonProperty("TRX_CODE")
    private String code;
    /**
     * 版本C(2)
     */
    @JsonProperty("VERSION")
    private String version = "01";
    /**
     * 签名信息
     * C
     * 待签名的原文为含有SIGNED_MSG空标签的报文，签名时对XML全文进行签名；
     */
    @JsonProperty("SIGNED_MSG")
    private String signedMessage;
    /**
     * 商户代码	C(15)
     * 标识商户的唯一ID，15位
     */
    @JsonProperty("MERCHANT_ID")
    private String merchant;
    /**
     * 交易请求号
     * C(1,32)	数据格式：
     * (15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号；
     * 批量交易时，作为“批次流水号”使用；
     * 单笔交易时，作为“交易流水号”使用；
     */
    @JsonProperty("REQ_SN")
    private String id;
    /**
     * 提交时间	C(14)	yyyyMMddHHmmss
     * 提交时间不能超过畅捷支付服务器时间[-24,+24]小时
     */
    @JsonProperty("TIMESTAMP")
    @JsonSerialize(using = TemporalAccessorSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;

    public Header() {
    }

    Header(String merchantId, Transaction transaction) {
        code = transaction.getTransactionCode();
        id = UUID.randomUUID().toString().replace("-", "");
        merchant = merchantId;
        time = LocalDateTime.now();
    }
}
