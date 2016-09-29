package me.jiangcai.chanpay.model;

/**
 * @author CJ
 */
public enum TradeType {
    /**
     * 即时到账
     */
    INSTANT,
    /**
     * 担保交易
     */
    ENSURE,
    /**
     * 退款
     */
    REFUND,
    /**
     * 提现
     */
    WITHDRAWAL,
    /**
     * 定金下定
     */
    PREPAY
}
