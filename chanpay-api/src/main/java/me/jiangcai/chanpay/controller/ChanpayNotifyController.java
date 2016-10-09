package me.jiangcai.chanpay.controller;

import me.jiangcai.chanpay.data.trade.TradeRequest;
import me.jiangcai.chanpay.event.AbstractTradeEvent;
import me.jiangcai.chanpay.event.RefundEvent;
import me.jiangcai.chanpay.event.TradeEvent;
import me.jiangcai.chanpay.event.WithdrawalEvent;
import me.jiangcai.chanpay.model.RefundStatus;
import me.jiangcai.chanpay.model.TradeStatus;
import me.jiangcai.chanpay.model.WithdrawalStatus;
import me.jiangcai.chanpay.util.RSA;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 接受异步通知的控制器,这个方法并没声明{@link org.springframework.web.bind.annotation.RequestMapping},
 * 它依赖{@link ChanpayHandlerMapping}帮助它通过查找系统属性映射绑定的URI
 *
 * @author CJ
 */
@Controller
public class ChanpayNotifyController {

    private static final Log log = LogFactory.getLog(ChanpayNotifyController.class);
    private final String key;
    private final boolean verify;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ChanpayNotifyController(Environment environment) {
        key = environment.getRequiredProperty("chanpay.key.platform.public");
        verify = environment.getProperty("chanpay.notify.verify", Boolean.class, true);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String payNotify(@RequestParam("sign") String sign, HttpServletRequest request
            , @RequestParam("outer_trade_no") String serialNumber
            , @RequestParam(required = false) String extension
                            // 只存在2.8
            , @RequestParam(value = "trade_status", required = false) TradeStatus tradeStatus
            , @RequestParam(value = "trade_amount", required = false) Number tradeAmount
//                            gmt_create	交易创建时间	Date	交易创建时间，格式：
//                                        yyyyMMddHHmmss	不可空	20131101102030
//                                        gmt_payment	交易支付时间	Date	交易支付时间，格式：
//                                        yyyyMMddHHmmss	可空	20131101102030
//                                        gmt_close	交易关闭时间	Date	交易关闭时间，格式：
//                                        yyyyMMddHHmmss	可空	20131101102030
                            // 只存在2.9
            , @RequestParam(value = "refund_status", required = false) RefundStatus refundStatus
            , @RequestParam(value = "refund_amount", required = false) Number refundAmount
//                            gmt_refund	交易退款时间	Date	交易退款时间，格式：
//                                        yyyyMMddHHmmss	可空
                            // 只存在2.15
            , @RequestParam(value = "withdrawal_status", required = false) WithdrawalStatus withdrawalStatus
            , @RequestParam(value = "withdrawal_amount", required = false) Number withdrawalAmount
                            // uid	用户ID	String	用户唯一标识	不可空
            , @RequestParam(value = "uid", required = false) String uid
            , @RequestParam(value = "return_code", required = false) String notifyCode
            , @RequestParam(value = "fail_reason", required = false) String failedReson
            , @RequestParam(value = "gmt_withdrawal", required = false)
                            @DateTimeFormat(pattern = "yyyyMMddHHmmss")
                                    LocalDateTime withdrawalTime
    ) {
        //转存为1:1
        Map<String, String> requestParameters = converter(request.getParameterMap());
        requestParameters.remove("sign");
        requestParameters.remove("sign_type");
        //再
        String preString = TradeRequest.preString(requestParameters);
        try {

            log.debug("[CHANPAY][NOTIFY]" + preString);
            log.debug("[CHANPAY][NOTIFY]" + sign);

            if (!verify || RSA.verify(preString, sign, key, "UTF-8")) {
                // 继续
                // 判断下是 2.8 还是 2.9
                AbstractTradeEvent event;
                if (tradeStatus != null) {
                    event = new TradeEvent(tradeStatus);
                    event.setAmount(tradeAmount);
                } else if (refundStatus != null) {
                    event = new RefundEvent(refundStatus);
                    event.setAmount(refundAmount);
                } else {
                    event = new WithdrawalEvent(withdrawalStatus);
                    event.setAmount(withdrawalAmount);
                }

                event.setSerialNumber(serialNumber);
                event.setExtension(extension);

                applicationEventPublisher.publishEvent(event);
                return "success";
            } else
                throw new IllegalStateException("bad request from Chanpay(sign)");
        } catch (SignatureException e) {
            // 不行!
            log.warn("Sign Verify Failed", e);
            throw new IllegalStateException("bad request from Chanpay(sign)");
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
