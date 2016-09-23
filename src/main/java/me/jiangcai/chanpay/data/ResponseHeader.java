package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
