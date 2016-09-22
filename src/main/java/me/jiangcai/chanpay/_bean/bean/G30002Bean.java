package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G30002接口  Bean
 *
 * @author Thinker
 */
public class G30002Bean extends Gw01MsgBase {
    private String payAcctNo;//付款账号
    private String recAcctNo;//收款账号
    private String amount;//金额
    private String summary;//备注

    public String getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(String payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public String getRecAcctNo() {
        return recAcctNo;
    }

    public void setRecAcctNo(String recAcctNo) {
        this.recAcctNo = recAcctNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
