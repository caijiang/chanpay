package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G60001接口  Bean
 *
 * @author Thinker
 */
public class G60001Bean extends Gw01MsgBase {
    private String sn;//明细号
    private String bankGeneralName;//银行通用名称
    private String bankName;//开户行名称
    private String bankCode;//开户行号
    private String accountType;//账号类型
    private String accountNo;//账号
    private String accountName;//账户名称
    private String idType;//开户证件类型
    private String id;//证件号
    private String tel;//手机号

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBankGeneralName() {
        return bankGeneralName;
    }

    public void setBankGeneralName(String bankGeneralName) {
        this.bankGeneralName = bankGeneralName;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
