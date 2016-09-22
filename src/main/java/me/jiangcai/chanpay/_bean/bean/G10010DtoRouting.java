package me.jiangcai.chanpay._bean.bean;

import java.io.Serializable;

/**
 * G10010=消费贷付款; 报文中的分账节点
 *
 * @author Thinker
 */
public class G10010DtoRouting implements Serializable {

    private static final long serialVersionUID = -5154127005614916508L;

    public String recAcctNo = "";
    public String amount;
    public String summary = "";

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


}//class
