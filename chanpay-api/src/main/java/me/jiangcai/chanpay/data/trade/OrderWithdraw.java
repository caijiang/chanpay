package me.jiangcai.chanpay.data.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.data.trade.support.EncryptString;

/**
 * 2.20 提现
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderWithdraw extends TradeRequest implements BusinessSerial, AsynchronousNotifiable {
    /**
     * 原交易订单号	String(32)	原交易订单号（单笔订单支付中的订单号）	不可空
     */
    @JsonProperty("trade_src_voucher_no")
    private String orderNo;
    /**
     * 商户网站唯一提现订单号	String(32)	畅捷支付合作商户网站唯一提现订单号（确保在商户系统中唯一）。
     */
    @JsonProperty("order_withdraw_no")
    private String serialNumber;
    /**
     * 银行卡号	String	密文，使用RSA 加密。明文长度：50
     */
    @JsonProperty("bank_card_no")
    private EncryptString cardNo;
    /**
     * 户名	String	密文，使用RSA 加密。明文长度：90
     */
    @JsonProperty("account_name")
    private EncryptString name;
    /**
     * 服务器异步通知页面路径	String(200)	支付平台服务器主动通知商户网站里指定的页面http路径。
     */
    @JsonProperty("notify_url")
    private String notifyUrl;

    @Override
    public String serviceName() {
        return "cjt_order_withdraw";
    }

}
