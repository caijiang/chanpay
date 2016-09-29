package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G20007接口  Bean
 *
 * @author Thinker
 */
public class G20007Bean extends Gw01MsgBase {
    private String qryReqSn;//要查询交易的请求号
    private String payAcctNo;//付款方账号
    private String payAcctBalance;//付款方账户余额
    private String recAcctNo;//收款方账号
    private String recAcctName;//收款方账户名称
    private String currency;//货币类型
    private String amount;//金额
    private String charge;//手续费
    private String summary;//备注
    private String postscript;//用途
    private String corpFlowNo;//外部企业流水号
    private String corpOrderNo;//外部企业订单号

    public String getQryReqSn() {
        return qryReqSn;
    }

    public void setQryReqSn(String qryReqSn) {
        this.qryReqSn = qryReqSn;
    }

    public String getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(String payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public String getPayAcctBalance() {
        return payAcctBalance;
    }

    public void setPayAcctBalance(String payAcctBalance) {
        this.payAcctBalance = payAcctBalance;
    }

    public String getRecAcctNo() {
        return recAcctNo;
    }

    public void setRecAcctNo(String recAcctNo) {
        this.recAcctNo = recAcctNo;
    }

    public String getRecAcctName() {
        return recAcctName;
    }

    public void setRecAcctName(String recAcctName) {
        this.recAcctName = recAcctName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
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


}
