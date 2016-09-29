package me.jiangcai.chanpay.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * 业务异常,通常指报表内部的不同响应码
 *
 * @author CJ
 */
@Getter
@ToString
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
