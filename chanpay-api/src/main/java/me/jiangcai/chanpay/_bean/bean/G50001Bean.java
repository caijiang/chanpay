package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G50001接口 Bean
 *
 * @author Thinker
 */
public class G50001Bean extends Gw01MsgBase {
    private String accountNo;//待查账号
    private String accountName;//待查户名
    private String balance;//余额
    private String usableBalance;//可用余额
    private String bodyErrMsg;
    private String bodyRetCode;

    public String getBodyErrMsg() {
        return bodyErrMsg;
    }

    public void setBodyErrMsg(String bodyErrMsg) {
        this.bodyErrMsg = bodyErrMsg;
    }

    public String getBodyRetCode() {
        return bodyRetCode;
    }

    public void setBodyRetCode(String bodyRetCode) {
        this.bodyRetCode = bodyRetCode;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUsableBalance() {
        return usableBalance;
    }

    public void setUsableBalance(String usableBalance) {
        this.usableBalance = usableBalance;
    }


}
