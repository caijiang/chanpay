package me.jiangcai.chanpay;

/**
 * 异步可通知的
 *
 * @author CJ
 */
public interface AsynchronousNotifiable {
    String getNotifyUrl();

    void setNotifyUrl(String notifyUrl);
}
