package me.jiangcai.chanpay.data.trade.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.converter.StandardMoneySerializer;
import me.jiangcai.chanpay.data.trade.TradeRequest;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.CardType;

import java.math.BigDecimal;

/**
 * 支付业务
 * 比如
 * 2.3 单笔支付
 * 2.18 单笔快捷支付
 * 2.21 单笔订单快捷WAP支付接口
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractPayTrade extends TradeRequest {

    /**
     * 外部企业流水号
     * 对应电子回单中“付款单号”字段；
     */
    @JsonProperty("out_trade_no")
    private String serialNumber;
    @JsonProperty("trade_amount")
    @JsonSerialize(using = StandardMoneySerializer.class)
    private BigDecimal amount;
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
     * 用户在商户平台下单时候的ip地址	String(32)	用户在商户平台下单的时候的ip地址，
     * 公网IP，不是内网IP
     * 用于风控校验
     */
    @JsonProperty("buyer_ip")
    private String buyerIp;

    /**
     * @return 付款方姓名
     */
    public abstract String getPayerName();

    /**
     * 在需提现的业务中必须
     *
     * @param name 付款方姓名
     */
    public abstract void setPayerName(String name);

    /**
     * 在快捷交易中必须
     *
     * @return 付款方银行
     */
    public abstract String getPayerBankName();

    public abstract void setPayerBankName(String name);

    /**
     * 在快捷交易中必须
     *
     * @return 付款方银行帐号
     */
    public abstract String getPayerBankAccount();

    public abstract void setPayerBankAccount(String account);

    /**
     * @return 卡种类
     */
    public abstract CardType getCardType();

    public abstract void setCardType(CardType cardType);

    /**
     * 快捷交易仅限对私
     *
     * @return 对公 or 对私
     */
    public abstract CardAttribute getCardAttribute();

    public abstract void setCardAttribute(CardAttribute cardAttribute);

}
