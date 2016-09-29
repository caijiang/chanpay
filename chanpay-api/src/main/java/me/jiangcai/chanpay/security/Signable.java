package me.jiangcai.chanpay.security;

/**
 * 可签名的
 *
 * @author CJ
 */
public interface Signable {

    String getSignedMessage();

    void setSignedMessage(String message);

}
