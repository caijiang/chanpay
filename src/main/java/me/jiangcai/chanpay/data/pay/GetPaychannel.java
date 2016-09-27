package me.jiangcai.chanpay.data.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 2.7查询银行列表接口
 *
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GetPayChannel extends PayRequest {

    /**
     * 产品码	String(32)	商户对接支付系统时
     * 约定支持的产品服务比如充值，
     * 交易等对应的代码。
     * 以商户签约为准	不可空	20201
     */
    @JsonProperty("product_code")
    private String productCode = "20201";

    @Override
    public String serviceName() {
        return "cjt_get_paychannel";
    }
}
