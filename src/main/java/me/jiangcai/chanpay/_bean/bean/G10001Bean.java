package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

public class G10001Bean extends Gw01MsgBase {

    /**
     * 业务代码, 接入生产前，业务人员会提供
     */
    private String businessCode;
    /**
     * 产品编码, 接入生产前，业务人员会提供
     */
    private String productCode;
    /**
     * 企业账号
     */
    private String corpAccNo;
    /**
     * 对公对私
     */
    private String accountProp;
    /**
     * 银行通用名称
     */
    private String bankGeneralName;
    /**
     * 账号类型
     */
    private String accountType;
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 开户行所在省
     */
    private String province;
    /**
     * 开户行所在市
     */
    private String city;
    /**
     * 开户行名称 如：中国建设银行广州东山广场分理处
     */
    private String bankName;
    /**
     * 开户行号， 对方账号对应的开户行支行行号
     */
    private String bankCode;
    /**
     * 清算行号
     */
    private String drctBankCode;
    /**
     * 协议号
     */
    private String protocolNo;
    /**
     * 货币类型, 人民币
     */
    private String currency;
    /**
     * 金额
     */
    private long amount;
    /**
     * 开户证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String id;
    /**
     * 手机号
     */
    private String tel;

    /**
     * 外部企业流水号
     */
    private String corpFlowNo;
    /**
     * 备注
     */
    private String summary;
    /**
     * 用途
     */
    private String postscript;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCorpAccNo() {
        return corpAccNo;
    }

    public void setCorpAccNo(String corpAccNo) {
        this.corpAccNo = corpAccNo;
    }

    public String getAccountProp() {
        return accountProp;
    }

    public void setAccountProp(String accountProp) {
        this.accountProp = accountProp;
    }

    public String getBankGeneralName() {
        return bankGeneralName;
    }

    public void setBankGeneralName(String bankGeneralName) {
        this.bankGeneralName = bankGeneralName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getDrctBankCode() {
        return drctBankCode;
    }

    public void setDrctBankCode(String drctBankCode) {
        this.drctBankCode = drctBankCode;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCorpFlowNo() {
        return corpFlowNo;
    }

    public void setCorpFlowNo(String corpFlowNo) {
        this.corpFlowNo = corpFlowNo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

}// class
