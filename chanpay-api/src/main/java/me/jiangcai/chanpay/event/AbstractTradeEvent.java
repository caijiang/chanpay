package me.jiangcai.chanpay.event;

import lombok.Data;

/**
 * @author CJ
 */
@Data
public abstract class AbstractTradeEvent {

    private String serialNumber;
    private String extension;
    private Number amount;

    /**
     * @return 是否退款交易
     */
    public abstract boolean isRefund();
}
