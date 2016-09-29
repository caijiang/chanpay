package me.jiangcai.chanpay;

import me.jiangcai.chanpay.model.RefundStatus;
import me.jiangcai.chanpay.model.TradeStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * 异步可通知的
 *
 * @author CJ
 * @see me.jiangcai.chanpay.controller.ChanpayNotifyController#payNotify(String, HttpServletRequest, String, String
 *, TradeStatus, Number, RefundStatus, Number)
 */
public interface AsynchronousNotifiable {
    String getNotifyUrl();

    void setNotifyUrl(String notifyUrl);
}
