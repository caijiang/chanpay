package me.jiangcai.chanpay.model;

/**
 * @author CJ
 */
public enum TradeStatus {
    WAIT_PAY,
    WAIT_BUYER_PAY,
    PAY_FINISHED,
    TRADE_SUCCESS,
    TRADE_FINISHED,
    TRADE_CLOSED,
    //    出款查询返回状态
    submitted,
    failed,
    success
}
