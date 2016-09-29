package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G30001接口  Bean
 *
 * @author Thinker
 */
public class G30001Bean extends Gw01MsgBase {
    private String accountNo;//待查询虚拟账号
    private String balance;//当前余额

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


}
