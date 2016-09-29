package me.jiangcai.chanpay.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.model.RefundStatus;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RefundEvent extends AbstractTradeEvent {
    private final RefundStatus refundStatus;

    public RefundEvent(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Override
    public boolean isRefund() {
        return true;
    }
}
