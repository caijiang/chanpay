package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

public class G20001Bean extends Gw01MsgBase {

    /**
     * 原交易请求号
     */
    private String qryReqSn;

    /** 返回结果信息 */
    /**
     * 手续费
     */
    private long charge;
    /**
     * 企业账号
     */
    private String corpAcctNo;
    /**
     * 企业名称
     */
    private String corpAcctName;
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 金额
     */
    private long amount;
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

    public String getQryReqSn() {
        return qryReqSn;
    }

    public void setQryReqSn(String qryReqSn) {
        this.qryReqSn = qryReqSn;
    }

    public long getCharge() {
        return charge;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public String getCorpAcctNo() {
        return corpAcctNo;
    }

    public void setCorpAcctNo(String corpAcctNo) {
        this.corpAcctNo = corpAcctNo;
    }

    public String getCorpAcctName() {
        return corpAcctName;
    }

    public void setCorpAcctName(String corpAcctName) {
        this.corpAcctName = corpAcctName;
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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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
