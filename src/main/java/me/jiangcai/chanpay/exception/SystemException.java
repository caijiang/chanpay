package me.jiangcai.chanpay.exception;

import lombok.Getter;
import me.jiangcai.chanpay.data.ResponseHeader;

/**
 * 系统错误,一般指报头报错
 *
 * @author CJ
 */
@Getter
public class SystemException extends RuntimeException {

    private final ResponseHeader header;

    public SystemException(ResponseHeader header) {
        super(header.getResponseMessage());
        this.header = header;
    }

}
