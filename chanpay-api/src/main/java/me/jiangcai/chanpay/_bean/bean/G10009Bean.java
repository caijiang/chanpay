package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G10009接口  Bean
 *
 * @author Thinker
 */
public class G10009Bean extends Gw01MsgBase {
    /**
     * 业务代码, 接入生产前，业务人员会提供
     */
    private String businessCode;
    /**
     * 产品编码, 接入生产前，业务人员会提供
     */
    private String productCode;
    /**
     * 付款方账号
     */
    private String payAcctNo;
    /**
     * 付款方账号名称
     */
    private String payAcctName;
    /**
     * 收款方账号
     */
    private String recAcctNo;
    /**
     * 收款方账户名称
     */
    private String recAcctName;
    /**
     * 交易时间
     */
    private String bankTrxTime;
    /**
     * 货币类型
     */
    private String currency;
    /**
     * 金额
     */
    private long amount;
    /**
     * 备注
     */
    private String summary;
    /**
     * 银行流水号
     */
    private String bankFlowNo;
    /**
     * 外部企业流水号
     */
    private String corpFlowNo;
    /**
     * 外部企业订单号
     */
    private String corpOrderNo;

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

    public String getBankTrxTime() {
        return bankTrxTime;
    }

    public void setBankTrxTime(String bankTrxTime) {
        this.bankTrxTime = bankTrxTime;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBankFlowNo() {
        return bankFlowNo;
    }

    public void setBankFlowNo(String bankFlowNo) {
        this.bankFlowNo = bankFlowNo;
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
