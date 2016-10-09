package me.jiangcai.chanpay.event;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author CJ
 */
@Data
public abstract class AbstractTradeEvent {

    /**
     * 平台唯一订单号
     */
    private String platformOrderNo;

    /**
     * 我方订单号
     */
    private String serialNumber;
    /**
     * 额外数据
     */
    private String extension;
    /**
     * 涉案金额
     */
    private Number amount;
    /**
     * 可选 code
     */
    private String code;
    /**
     * 可选 message
     */
    private String message;

    /**
     * 交易时间,一般指完成的时刻
     */
    private LocalDateTime tradeTime;

    /**
     * @return 是否退款交易
     */
    public abstract boolean isRefund();
}
