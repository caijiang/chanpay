package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

public class G30003Bean extends Gw01MsgBase {

    /**
     * 账号
     */
    private String payAcctNo;
    /**
     * 账户名称
     */
    private String payAcctName;
    /**
     * POS付款时间
     */
    private String posTime;
    /**
     * POS机流水号
     */
    private String posFlowNo;
    /**
     * 金额
     */
    private long amount;
    /**
     * 备注
     */
    private String summary;
    /**
     * 外部企业流水号
     */
    private String corpFlowNo;
    /**
     * 外部企业流水号
     */
    private String corpOrderNo;

    public String getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(String payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public String getPayAcctName() {
        return payAcctName;
    }

    public void setPayAcctName(String payAcctName) {
        this.payAcctName = payAcctName;
    }

    public String getPosTime() {
        return posTime;
    }

    public void setPosTime(String posTime) {
        this.posTime = posTime;
    }

    public String getPosFlowNo() {
        return posFlowNo;
    }

    public void setPosFlowNo(String posFlowNo) {
        this.posFlowNo = posFlowNo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCorpFlowNo() {
        return corpFlowNo;
    }

    public void setCorpFlowNo(String corpFlowNo) {
        this.corpFlowNo = corpFlowNo;
    }

    public String getCorpOrderNo() {
        return corpOrderNo;
    }

    public void setCorpOrderNo(String corpOrderNo) {
        this.corpOrderNo = corpOrderNo;
    }

}// class
