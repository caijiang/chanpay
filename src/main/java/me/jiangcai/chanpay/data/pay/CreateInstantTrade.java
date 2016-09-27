package me.jiangcai.chanpay.data.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.converter.YNSerializer;

import java.math.BigDecimal;

/**
 * 2.3
 * 以下返回参数只在扫码支付时返回，即时到账支付业务畅捷方控制页面跳转，就没有同步返回参数了；扫码支付则不控制页面跳转了。
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateInstantTrade extends PayRequest implements BusinessSerial {
    /**
     * 外部企业流水号
     * 对应电子回单中“付款单号”字段；
     */
    @JsonProperty("out_trade_no")
    private String serialNumber;
    @JsonProperty("trade_amount")
    private BigDecimal amount;
    /**
     * 是否匿名支付（跳转收银台的场景使用）	String（1）	若该值为Y，表示该笔订单的用户不需要是畅捷支付的用户。
     */
    @JsonProperty("is_anonymous")
    @JsonSerialize(using = YNSerializer.class)
    private boolean anonymous = true;
    /**
     * 银行简码	String(20)		可空	pay_method为1时候，传递银行简码，否则为空；
     * 例如，工行：ICBC；微信扫码支付：WXPAY
     */
    @JsonProperty("bank_code")
    private String bankCode;

    @Override
    public String serviceName() {
        return "cjt_create_instant_trade";
    }

}
