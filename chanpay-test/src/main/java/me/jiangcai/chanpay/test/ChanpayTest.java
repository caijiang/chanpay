package me.jiangcai.chanpay.test;

import me.jiangcai.chanpay.config.ChanpayConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

//@ContextConfiguration(value = {"/spring-web.xml"})
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ChanpayTestSpringConfig.class, ChanpayConfig.class})
@WebAppConfiguration
public abstract class ChanpayTest {


}
