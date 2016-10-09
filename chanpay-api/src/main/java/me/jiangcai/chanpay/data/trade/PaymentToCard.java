package me.jiangcai.chanpay.data.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.TradeResponseHandler;
import me.jiangcai.chanpay.data.trade.support.EncryptString;
import me.jiangcai.chanpay.model.CardAttribute;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 2.14 付款到卡
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentToCard extends TradeRequest implements BusinessSerial, AsynchronousNotifiable
        , TradeResponseHandler<Boolean> {

    /**
     * outer_trade_no	商户网站唯一订单号	String(32)	畅捷支付合作商户网站唯一订单号
     * （确保在商户系统中唯一）。
     */
    @JsonProperty("outer_trade_no")
    private String serialNumber;
    /**
     * bank_account_no	银行卡号	String	密文，使用RSA 加密。明文长度：50
     */
    @JsonProperty("bank_account_no")
    private EncryptString cardNumber;
    /**
     * 户名	String	密文，使用RSA 加密。明文长度：90
     */
    @JsonProperty("account_name")
    private EncryptString cardName;
    /**
     * 银行编号	String(10)	银行编号 见附录	不可空
     */
    @JsonProperty("bank_code")
    private String bankCode;
    /**
     * 银行名称	String(50)	银行全称 见附录	不可空
     */
    @JsonProperty("bank_name")
    private String bankName;
    /**
     * 支行名称	String(255)		不可空	中国农业银行深圳南山支行
     */
    @JsonProperty("bank_branch")
    private String bankBranch;
    private String province;
    private String city;
    @JsonProperty("card_type")
    private String type = "DEBIT";
    @JsonProperty("card_attribute")
//    @JsonSerialize(using = EnumSerializer.class)
    private CardAttribute cardAttribute;
    private BigDecimal amount;
    @JsonProperty("notify_url")
    private String notifyUrl;

//    bank_line_no	银行分支行号	String(12)	银行分支行号	可空
//member_id	出款账户MEMBER_ID	String(32)	商户可请求从其子账户出款	可空


    @Override
    public String serviceName() {
        return "cjt_payment_to_card";
    }

    @Override
    public Boolean handleNode(HttpResponse response, JsonNode node) throws IOException {
        return "T".equalsIgnoreCase(node.get("is_success").asText());
    }
}
