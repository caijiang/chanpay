package me.jiangcai.chanpay.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.model.TradeStatus;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TradeEvent extends AbstractTradeEvent {

    private final TradeStatus tradeStatus;

    public TradeEvent(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Override
    public boolean isRefund() {
        return false;
    }
}
