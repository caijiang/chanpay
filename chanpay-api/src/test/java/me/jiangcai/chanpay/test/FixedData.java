package me.jiangcai.chanpay.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import me.jiangcai.chanpay.security.Signable;

/**
 * @author CJ
 */
@Data
public class FixedData implements Signable {

    @JsonProperty("SIGNED_MSG")
    private String signedMessage;
    private String data;

}
