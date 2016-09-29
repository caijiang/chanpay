package me.jiangcai.chanpay._bean.bean;

public class G20002SubBean {

    /**
     * 记录序号
     */
    private String sn;
    /**
     * 手续费
     */
    private long charge;
    /**
     * 付款方账号
     */
    private String payAcctNo;
    /**
     * 付款方名称
     */
    private String payAcctName;
    /**
     * 收款方账号
     */
    private String retAcctNo;
    /**
     * 收款方名称
     */
    private String retAcctName;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getCharge() {
        return charge;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

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

    public String getRetAcctNo() {
        return retAcctNo;
    }

    public void setRetAcctNo(String retAcctNo) {
        this.retAcctNo = retAcctNo;
    }

    public String getRetAcctName() {
        return retAcctName;
    }

    public void setRetAcctName(String retAcctName) {
        this.retAcctName = retAcctName;
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
