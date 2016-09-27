package me.jiangcai.chanpay.data.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.model.TradeType;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryTrade extends PayRequest implements BusinessSerial {

    /**
     * 外部企业流水号
     * 对应电子回单中“付款单号”字段；
     */
    @JsonProperty("out_trade_no")
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
