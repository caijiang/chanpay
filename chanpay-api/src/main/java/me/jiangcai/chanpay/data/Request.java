package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求
 * MESSAGE/BODY
 * MESSAGE/BODY/BATCH/
 * MESSAGE/BODY/TRANS_DETAILS 多个
 * MESSAGE/BODY/TRANS_DETAILS/DTL/ 多个
 *
 * @author CJ
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Request extends AbstractMessage {

    @JsonProperty("BODY")
    private final Transaction transaction;

    public Request(String merchantId, Transaction transaction) {
        super(new Header(merchantId, transaction));
        this.transaction = transaction;
    }

}
