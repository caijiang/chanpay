package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.model.AccountProperty;
import me.jiangcai.chanpay.model.AccountType;
import me.jiangcai.chanpay.model.IdType;

import java.util.Currency;
import java.util.Locale;

/**
 * 单笔实时交易
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class SingleRealTransaction extends Transaction implements BusinessSerial {
    /**
     * 业务代码, 接入生产前，业务人员会提供
     * 代收付系统分配
     */
    @JsonProperty("BUSINESS_CODE")
    private String businessCode;
    /**
     * 产品编码, 接入生产前，业务人员会提供
     */
    @JsonProperty("PRODUCT_CODE")
    private String productCode;
    /**
     * 企业账号
     * 发起方；收款交易为收款方；
     * 付款交易为付款方
     */
    @JsonProperty("CORP_ACCT_NO")
    private String corporateAccountNo;
    /**
     * 对公对私
     * 指接收方的属性
     */
    @JsonProperty("ACCOUNT_PROP")
    private AccountProperty accountProperty;
    /**
     * 二级商户代码	C(40)	商户自己的二级商户代码，可以上送到代收付，
     * 为后续预留处理使用；（暂无业务逻辑）
     */
    @JsonProperty("SUB_MERCHANT_ID")
    private String subMerchantId;
    /**
     * 银行通用名称	C(1,60)	表示一家商业银行的全称，如：中国工商银行；	必填	“CJ系统”需要根据此信息确定银行
     */
    @JsonProperty("BANK_GENERAL_NAME")
    private String bankGeneralName;
    /**
     * 账号类型
     */
    @JsonProperty("ACCOUNT_TYPE")
    private AccountType accountType;
    /**
     * 账号
     * 银行卡或存折号码
     */
    @JsonProperty("ACCOUNT_NO")
    private String accountNo;
    /**
     * 账户名称
     * 银行卡或存折上的所有人姓名。
     */
    @JsonProperty("ACCOUNT_NAME")
    private String accountName;
    /**
     * 开户行所在省
     * 不带“省”或“自治区”，如 广东，广西，内蒙古等。
     */
    @JsonProperty("PROVINCE")
    private String province;
    /**
     * 开户行所在市
     * 不带“市”，如 广州，南宁等。 如果是直辖市，则填直辖市名称；如：北京
     */
    @JsonProperty("CITY")
    private String city;
    /**
     * 开户行名称 如：中国建设银行广州东山广场分理处
     * 开户行详细名称，也叫网点，如 中国建设银行广州东山广场分理处。
     * 必选!
     */
    @JsonProperty("BANK_NAME")
    private String bankName;
    /**
     * 开户行号， 对方账号对应的开户行支行行号
     * 必选!
     */
    @JsonProperty("BANK_CODE")
    private String bankCode;
    /**
     * 清算行号
     * 必选!
     * 对方开户行对应的清算行总行行号
     */
    @JsonProperty("DRCT_BANK_CODE")
    private String drctBankCode;
    /**
     * 协议号
     * 企业客户与付款人签署的收款协议
     * 收款交易必填!
     */
    @JsonProperty("PROTOCOL_NO")
    private String protocolNo;
    /**
     * 货币类型, 人民币
     */
    @JsonProperty("CURRENCY")
    private Currency currency = Currency.getInstance(Locale.CHINA);
    /**
     * 金额
     * 整数，单位：分
     */
    @JsonProperty("AMOUNT")
    private long amount;
    /**
     * 开户证件类型
     * 可选
     */
    @JsonProperty("ID_TYPE")
    private IdType idType;
    /**
     * 证件号
     * 可选
     */
    @JsonProperty("ID")
    private String id;
    /**
     * 手机号
     * 不带括号，减号
     */
    @JsonProperty("TEL")
    private String tel;

    /**
     * 外部企业流水号
     * 对应电子回单中“付款单号”字段；
     */
    @JsonProperty("CORP_FLOW_NO")
    private String serialNumber;
    /**
     * 备注
     * 填入网银的交易备注,可以在网银明细中查询到该字段信息，但部分银行可能不支持
     */
    @JsonProperty("SUMMARY")
    private String summary;
    /**
     * 用途
     * 用来注明该笔款项的用途或其它内容等
     */
    @JsonProperty("POSTSCRIPT")
    private String postscript;
}
