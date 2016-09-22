package me.jiangcai.chanpay.service;

import me.jiangcai.chanpay.data.Request;
import me.jiangcai.chanpay.exception.ServiceException;
import me.jiangcai.chanpay.exception.SystemException;

import java.io.IOException;
import java.security.SignatureException;

/**
 * @author CJ
 */
public interface TransactionService {

    /**
     * @param request 请求包
     * @throws IOException        本地网络
     * @throws SignatureException 签名异常,这个一般不会发生
     * @throws SystemException    系统级别异常
     * @throws ServiceException   业务级别异常
     */
    void execute(Request request) throws IOException, SignatureException, SystemException, ServiceException;

}
