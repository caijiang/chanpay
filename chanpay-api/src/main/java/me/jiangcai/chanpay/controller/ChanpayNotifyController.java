package me.jiangcai.chanpay.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author CJ
 */
@Controller
public class ChanpayNotifyController {

    private static final Log log = LogFactory.getLog(ChanpayNotifyController.class);

    @ResponseStatus(HttpStatus.OK)
    public void notify(@RequestParam("notify_time") String notify_time) {
        System.out.println(notify_time);
    }

}
