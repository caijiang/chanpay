package me.jiangcai.chanpay.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 载入该配置即可获得可随时使用的{@link me.jiangcai.chanpay.service.TransactionService},
 * 一个用于状态通知的回调URI也已被注册,它可以通过修改环境属性chanpay.notify.uri进行调整
 *
 * @author CJ
 */
@Configuration
@EnableWebMvc
@ComponentScan({"me.jiangcai.chanpay.service", "me.jiangcai.chanpay.controller"})
public class ChanpayConfig extends WebMvcConfigurerAdapter {

}

