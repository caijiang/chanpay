package me.jiangcai.chanpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author CJ
 */
@Component
public class ChanpayHandlerMapping implements HandlerMapping {

    private final HandlerMethod handler;
    @Autowired
    private Environment environment;

    @Autowired
    public ChanpayHandlerMapping(ChanpayNotifyController chanpayNotifyController) {
        HandlerMethod _handler = null;
        for (Method method : chanpayNotifyController.getClass().getMethods()) {
            if (method.getName().equalsIgnoreCase("notify")) {
                _handler = new HandlerMethod(chanpayNotifyController, method);
            }
        }

        handler = _handler;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (request.getRequestURI().equalsIgnoreCase(environment.getRequiredProperty("chanpay.notify.uri"))) {
            return new HandlerExecutionChain(handler);
        }
        return null;
    }
}
