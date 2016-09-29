package me.jiangcai.chanpay.data.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import me.jiangcai.chanpay.converter.LocalDateTimeDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提现结果
 *
 * @author CJ
 */
@Data
public class OrderWithdrawResult {
    /**
     * 支付平台交易订单号	String(32)	统一凭证交易订单号。
     */
    @JsonProperty("trades_src_voucher_number")
    private String orderNo;
    /**
     * 原始提现凭证号	String(32)	提现订单号
     */
    @JsonProperty("order_withdraw_number")
    private String withdrawNo;
    /**
     * 提现金额	Number	交易金额
     */
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("accept_status")
    private boolean accepted;
    @JsonProperty("accept_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime acceptedTime;
    /**
     * 交易失败原因	String(200)	订单提现受理失败原因
     */
    @JsonProperty("err_msg")
    private String reason;

}
