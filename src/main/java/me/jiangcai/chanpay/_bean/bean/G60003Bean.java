package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G60003接口  Bean
 *
 * @author Thinker
 */
public class G60003Bean extends Gw01MsgBase {
    private String corpAccountNo;//企业账号
    private String businessCode;//业务代码
    private String alterType;//变更类型
    private String protocolType;//协议类型收款标志
    private String sn;//明细号
    private String protocolNo;//协议号
    private String bankName;//开户行名称
    private String bankCode;//开户行号
    private String accountType;//账号类型
    private String accountNo;//账号
    private String accountName;//账户名称
    private String idType;//开户证件类型
    private String id;//证件号
    private String beginDate;//协议开始时间
    private String endDate;//协议结束时间
    private String tel;//预留手机号

    public String getCorpAccountNo() {
        return corpAccountNo;
    }

    public void setCorpAccountNo(String corpAccountNo) {
        this.corpAccountNo = corpAccountNo;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getAlterType() {
        return alterType;
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
