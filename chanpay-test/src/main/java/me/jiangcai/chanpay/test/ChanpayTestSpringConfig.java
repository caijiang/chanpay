package me.jiangcai.chanpay.test;

import me.jiangcai.chanpay.test.mock.MockPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author CJ
 */
@PropertySource("classpath:/cj.properties")
@Configuration
class ChanpayTestSpringConfig {
    @Autowired
    private Environment environment;
    @Autowired(required = false)
    private WebApplicationContext context;
    @Autowired(required = false)
    private FilterChainProxy springSecurityFilter;
    @Autowired(required = false)
    private MockMvcConfigurer mockMvcConfigurer;

    @Bean
    public MockPay mockPay() throws Exception {
        return new MockPay(environment, context, springSecurityFilter, mockMvcConfigurer);
    }
}
