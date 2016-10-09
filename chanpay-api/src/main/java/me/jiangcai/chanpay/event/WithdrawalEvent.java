package me.jiangcai.chanpay.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.jiangcai.chanpay.model.WithdrawalStatus;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class WithdrawalEvent extends AbstractTradeEvent {

    private final WithdrawalStatus status;

    @Override
    public boolean isRefund() {
        return false;
    }
}
