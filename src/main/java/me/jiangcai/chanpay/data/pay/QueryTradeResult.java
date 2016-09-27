package me.jiangcai.chanpay.data.pay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import me.jiangcai.chanpay.converter.LocalDateTimeDeserializer;
import me.jiangcai.chanpay.model.TradeStatus;

import java.time.LocalDateTime;

/**
 * @author CJ
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryTradeResult {
    /**
     * 外部企业流水号
     * 对应电子回单中“付款单号”字段；
     */
    @JsonProperty("outer_trade_no")
    private String serialNumber;
    @JsonProperty("inner_trade_no")
    private String chanPayNumber;
    @JsonProperty("trade_amount")
    private Number amount;
    @JsonProperty("trade_status")
    private TradeStatus status;
    @JsonProperty("trade_date")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;

}
