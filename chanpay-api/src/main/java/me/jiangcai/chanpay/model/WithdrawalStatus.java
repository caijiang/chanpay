package me.jiangcai.chanpay.model;

/**
 * @author CJ
 */
public enum WithdrawalStatus {
    /**
     * 已提交银行
     */
    WITHDRAWAL_SUBMITTED,
    /**
     * 提现成功
     */
    WITHDRAWAL_SUCCESS,
    /**
     * 提现失败
     */
    WITHDRAWAL_FAIL,
    /**
     * 提现退票
     */
    RETURN_TICKET
}
