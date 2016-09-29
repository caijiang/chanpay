package me.jiangcai.chanpay.data.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.data.trade.support.AbstractPayTrade;
import me.jiangcai.chanpay.data.trade.support.EncryptString;
import me.jiangcai.chanpay.model.CardAttribute;
import me.jiangcai.chanpay.model.CardType;

/**
 * 2.18单笔订单快捷支付API接口
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuickPayment extends AbstractPayTrade implements BusinessSerial, AsynchronousNotifiable {
    /**
     * 卡类型	Str ing(10)	借记：DC；贷记：CC
     */
    @JsonProperty("card_type")
    private CardType cardType;
    /**
     * 对公对私	Str ing(10)	对公：B；对私：C
     */
    @JsonProperty("pay_type")
    private CardAttribute cardAttribute;
    /**
     * 付款方名称	String	密文，使用RSA 加密。明文长度：90	不可空
     */
    @JsonProperty("payer_name")
    private EncryptString payerNameData;
    /**
     * 付款方银行卡号	String	密文，使用RSA 加密。明文长度：50	不可空
     */
    @JsonProperty("payer_card_no")
    private EncryptString payerBankAccountData;
    /**
     * 身份证号	String(20)	密文，使用RSA 加密。明文长度：30	不可空
     */
    @JsonProperty("id_number")
    private EncryptString idNumber;
    /**
     * 手机号	String(20)	密文，使用RSA 加密。明文长度：30	不可空
     */
    @JsonProperty("phone_number")
    private EncryptString mobilePhone;

    @Override
    public String serviceName() {
        return "cjt_quick_payment";
    }

    @Override
    @JsonIgnore
    public String getPayerName() {
        return payerNameData.getOrigin();
    }

    @Override
    @JsonIgnore
    public void setPayerName(String name) {
        if (name == null)
            payerNameData = null;
        else
            payerNameData = new EncryptString(name);
    }

    @Override
    @JsonIgnore
    public String getPayerBankName() {
        return null;
    }

    @Override
    @JsonIgnore
    public void setPayerBankName(String name) {

    }

    @Override
    @JsonIgnore
    public String getPayerBankAccount() {
        return payerBankAccountData.getOrigin();
    }

    @Override
    @JsonIgnore
    public void setPayerBankAccount(String account) {
        if (account == null)
            payerBankAccountData = null;
        else
            payerBankAccountData = new EncryptString(account);
    }
}
