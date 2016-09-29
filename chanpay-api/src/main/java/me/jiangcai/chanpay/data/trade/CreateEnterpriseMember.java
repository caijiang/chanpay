package me.jiangcai.chanpay.data.trade;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CreateEnterpriseMember extends TradeRequest {
    private String uid;

    @Override
    public String serviceName() {
        return "create_enterprise_member";
    }


}
