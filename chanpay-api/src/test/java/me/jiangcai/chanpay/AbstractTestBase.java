package me.jiangcai.chanpay;

import me.jiangcai.chanpay.config.ChanpayConfig;
import me.jiangcai.chanpay.test.ChanpayTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author CJ
 */
@ContextConfiguration(classes = {ChanpayConfig.class})
public abstract class AbstractTestBase extends ChanpayTest {
}
