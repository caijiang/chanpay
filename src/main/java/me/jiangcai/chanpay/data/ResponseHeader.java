package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseHeader extends Header {

    @JsonProperty("RET_CODE")
    private String responseCode;
    @JsonProperty("ERR_MSG")
    private String responseMessage;


    public boolean isSuccess() {
        return "0000".equals(responseCode);
    }
}
