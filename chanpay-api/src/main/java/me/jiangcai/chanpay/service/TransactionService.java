package me.jiangcai.chanpay.service;

import me.jiangcai.chanpay.data.Request;
import me.jiangcai.chanpay.data.Response;
import me.jiangcai.chanpay.data.trade.TradeRequest;
import me.jiangcai.chanpay.exception.ServiceException;
import me.jiangcai.chanpay.exception.SystemException;
import me.jiangcai.chanpay.service.impl.PayHandler;

import java.io.IOException;
import java.security.SignatureException;

/**
 * @author CJ
 */
public interface TransactionService {

    /**
     * 这里走的是CJ
     * http://dev.chanpay.com/doku.php/dsf:%E4%BB%A3%E6%94%B6%E4%BB%98%E7%B3%BB%E7%BB%9F%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3
     * 代收付
     *
     * @param request 请求包
     * @return 响应
     * @throws IOException        本地网络
     * @throws SignatureException 签名异常,这个一般不会发生
     * @throws SystemException    系统级别异常
     * @throws ServiceException   业务级别异常
     */
    Response execute(Request request) throws IOException, SignatureException, SystemException, ServiceException;

    /**
     * 执行CJT交易
     *
     * @param request 交易信息
     * @param handler 响应处理器
     * @param <T>     返回值的类型
     * @return 交易成功的返回值
     * @throws ServiceException   业务级别错误
     * @throws IOException        网络交互错误
     * @throws SignatureException 签名错误
     */
    <T> T execute(TradeRequest request, PayHandler<T> handler) throws ServiceException, IOException, SignatureException;


}
