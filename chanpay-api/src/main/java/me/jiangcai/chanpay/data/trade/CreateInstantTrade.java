package me.jiangcai.chanpay.data.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.converter.PayTypeSerializer;
import me.jiangcai.chanpay.converter.YNSerializer;
import me.jiangcai.chanpay.data.trade.support.AbstractPayTrade;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.CardType;
import me.jiangcai.chanpay.model.PayType;

/**
 * 2.3 产生支付
 * 以下返回参数只在扫码支付时返回，即时到账支付业务畅捷方控制页面跳转，就没有同步返回参数了；扫码支付则不控制页面跳转了。
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateInstantTrade extends AbstractPayTrade implements BusinessSerial, AsynchronousNotifiable {

    /**
     * payer_truename 付款方姓名 使用提现请求必传
     */
    @JsonProperty("payer_truename")
    private String payerName;
    /**
     * payer_bankname	付款方银行名称（详细到支行）	String(30_0)		可空
     */
    @JsonProperty("payer_bankname")
    private String payerBankName;
    /**
     * payer_bankaccountNo	付款方银行账号	String(300)		可空
     */
    @JsonProperty("payer_bankaccountNo")
    private String payerBankAccount;

    /**
     * pay_method为1时候传递，否则为空；
     */
    @JsonProperty("pay_type")
    @JsonSerialize(using = PayTypeSerializer.class)
    private PayType payType;

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
     * 是否匿名支付（跳转收银台的场景使用）	String（1）	若该值为Y，表示该笔订单的用户不需要是畅捷支付的用户。
     */
    @JsonProperty("is_anonymous")
    @JsonSerialize(using = YNSerializer.class)
    private boolean anonymous = true;

    /**
     * 是否同步返回支付URL	String(1)	目前只支持扫码支付同步返回付款二维码URL地址	扫码支付必传	Y：需要返回扫码支付付款二维码URL地址；
     * N：不同步返回（默认）
     */
    @JsonProperty("is_returnpayurl")
    @JsonSerialize(using = YNSerializer.class)
    private boolean returnPayUrl;

    @Override
    public String serviceName() {
        return "cjt_create_instant_trade";
    }

    public void scanPay() {
        setAnonymous(true);
        setPayMethod("1");
        setPayType(new PayType(CardType.GC, CardAttribute.C));
        setReturnPayUrl(true);
    }

    @Override
    @JsonIgnore
    public CardType getCardType() {
        return getPayType().getType();
    }

    @Override
    @JsonIgnore
    public void setCardType(CardType cardType) {
        if (getPayType() != null) {
            getPayType().setType(cardType);
        } else {
            setPayType(new PayType(cardType, null));
        }
    }

    @Override
    @JsonIgnore
    public CardAttribute getCardAttribute() {
        return getPayType().getAttribute();
    }

    @Override
    @JsonIgnore
    public void setCardAttribute(CardAttribute cardAttribute) {
        if (getPayType() != null) {
            getPayType().setAttribute(cardAttribute);
        } else {
            setPayType(new PayType(null, cardAttribute));
        }
    }
}
