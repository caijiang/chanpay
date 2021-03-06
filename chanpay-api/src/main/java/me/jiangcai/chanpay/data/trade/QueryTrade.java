package me.jiangcai.chanpay.data.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.model.TradeType;

/**
 * 2.6 查询交易
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryTrade extends TradeRequest implements BusinessSerial {

    /**
     * 外部企业流水号
     * 对应电子回单中“付款单号”字段；
     */
    @JsonProperty("outer_trade_no")
    private String serialNumber;
    @JsonProperty("trade_type")
    private TradeType type;
    @JsonProperty("inner_trade_no")
    private String chanPayNumber;

    @Override
    public String serviceName() {
        return "cjt_query_trade";
    }

}
