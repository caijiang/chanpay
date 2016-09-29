package me.jiangcai.chanpay.controller;

import me.jiangcai.chanpay.data.pay.PayRequest;
import me.jiangcai.chanpay.event.AbstractTradeEvent;
import me.jiangcai.chanpay.event.RefundEvent;
import me.jiangcai.chanpay.event.TradeEvent;
import me.jiangcai.chanpay.model.RefundStatus;
import me.jiangcai.chanpay.model.TradeStatus;
import me.jiangcai.chanpay.util.RSA;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CJ
 */
@Controller
public class ChanpayNotifyController {

    private static final Log log = LogFactory.getLog(ChanpayNotifyController.class);
    private final String key;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ChanpayNotifyController(Environment environment) {
        key = environment.getRequiredProperty("chanpay.key.platform.public");
    }

    @ResponseStatus(HttpStatus.OK)
    public void payNotify(@RequestParam("sign") String sign, HttpServletRequest request
            , @RequestParam("outer_trade_no") String serilNumber
            , @RequestParam(required = false) String extension
                          // 只存在2.8
            , @RequestParam(value = "trade_status", required = false) TradeStatus tradeStatus
            , @RequestParam(value = "trade_amount", required = false) Number tradeAmount
                          // 只存在2.9
            , @RequestParam(value = "refund_status", required = false) RefundStatus refundStatus
            , @RequestParam(value = "refund_amount", required = false) Number refundAmount
    ) {
        //转存为1:1
        Map<String, String> requestParameters = converter(request.getParameterMap());
        requestParameters.remove("sign");
        requestParameters.remove("sign_type");
        //再
        String preString = PayRequest.preString(requestParameters);
        try {
            if (RSA.verify(preString, sign, key, "UTF-8")) {
                // 继续
                System.out.println("work!!!!");
                // 判断下是 2.8 还是 2.9
                AbstractTradeEvent event;
                if (tradeStatus != null) {
                    event = new TradeEvent(tradeStatus);
                    event.setAmount(tradeAmount);
                } else {
                    event = new RefundEvent(refundStatus);
                    event.setAmount(refundAmount);
                }

                event.setSerialNumber(serilNumber);
                event.setExtension(extension);

                applicationEventPublisher.publishEvent(event);

            } else
                throw new IllegalStateException("unknown");
        } catch (SignatureException e) {
            // 不行!
            log.warn("Sign Verify Failed", e);
            throw new IllegalStateException("unknown");
        }
    }

    private Map<String, String> converter(Map<String, String[]> parameterMap) {
        HashMap<String, String> result = new HashMap<>();
        parameterMap.entrySet().forEach(stringEntry -> {
            String[] value = stringEntry.getValue();
            if (value.length > 1)
                throw new IllegalArgumentException(stringEntry.getKey() + " has too many values.");
            if (value.length == 1) {
                result.put(stringEntry.getKey(), value[0]);
            } else {
                result.put(stringEntry.getKey(), null);
            }
        });
        return result;
    }

}
