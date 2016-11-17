package me.jiangcai.chanpay.exception;

import lombok.Getter;

/**
 * 业务异常,通常指报表内部的不同响应码
 *
 * @author CJ
 */
@Getter
public class ServiceException extends RuntimeException {

    private final String code;

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + code + '\'' +
                "} " + super.toString();
    }
}
