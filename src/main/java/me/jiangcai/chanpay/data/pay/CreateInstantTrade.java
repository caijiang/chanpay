package me.jiangcai.chanpay.data.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.converter.PayTypeSerializer;
import me.jiangcai.chanpay.converter.YNSerializer;
import me.jiangcai.chanpay.model.PayType;

import java.math.BigDecimal;

/**
 * 2.3 产生支付
 * 以下返回参数只在扫码支付时返回，即时到账支付业务畅捷方控制页面跳转，就没有同步返回参数了；扫码支付则不控制页面跳转了。
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateInstantTrade extends PayRequest implements BusinessSerial, AsynchronousNotifiable {
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

    /**
     * 务器异步通知页面路径	String(200)	支付服务器主动通知商户网站里
     * 指定的页面http路径。
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    /**
     * 商品名称	String(256)		可空
     */
    @JsonProperty("product_name")
    private String productName;

    /**
     * 可空	1:直连(合作方自己有收银台，选择银行时候，
     * 调用该接口直接跳转到选中的银行网银)；
     * 2：收银台（合作方没有收银台，订单支付时候，
     * 调用该接口到畅捷支付收银台）；
     * 3：余额支付（合作方选择畅捷支付余额付款时候，
     * 到畅捷支付账户余额付款页面）；
     */
    @JsonProperty("pay_method")
    private String payMethod;

    /**
     * pay_method为1时候传递，否则为空；
     */
    @JsonProperty("pay_type")
    @JsonSerialize(using = PayTypeSerializer.class)
    private PayType payType;
    /**
     * 是否同步返回支付URL	String(1)	目前只支持扫码支付同步返回付款二维码URL地址	扫码支付必传	Y：需要返回扫码支付付款二维码URL地址；
     * N：不同步返回（默认）
     */
    @JsonProperty("is_returnpayurl")
    private boolean returnPayUrl;

    @Override
    public String serviceName() {
        return "cjt_create_instant_trade";
    }

}
