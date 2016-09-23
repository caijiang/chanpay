package me.jiangcai.chanpay.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author CJ
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Response extends AbstractMessage {

    public Response(ResponseHeader header) {
        super(header);
    }

}
