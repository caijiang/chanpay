package me.jiangcai.chanpay.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log log = LogFactory.getLog(ChanpayHandlerMapping.class);

    private final HandlerMethod handler;
    @Autowired
    private Environment environment;

    @Autowired
    public ChanpayHandlerMapping(ChanpayNotifyController chanpayNotifyController) {
        HandlerMethod _handler = null;
        for (Method method : chanpayNotifyController.getClass().getMethods()) {
            if (method.getName().equalsIgnoreCase("payNotify")) {
                _handler = new HandlerMethod(chanpayNotifyController, method);
            }
        }

        handler = _handler;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("incoming:" + request.getRequestURI() + ", require:" + environment.getRequiredProperty("chanpay.notify.uri"));
        }

        if (request.getRequestURI().equalsIgnoreCase(environment.getRequiredProperty("chanpay.notify.uri"))) {
            return new HandlerExecutionChain(handler);
        }
        return null;
    }
}
