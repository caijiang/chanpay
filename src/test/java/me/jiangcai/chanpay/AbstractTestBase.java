/**
 * 文件名： AbstractTestSpring.java
 * 建立时间： 2012-2-20 上午09:11:47
 * 创建人： SongCheng
 */
package me.jiangcai.chanpay;

import me.jiangcai.chanpay.config.ChanpayConfig;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 本项目spring测试基类，所有需要使用spring功能的测试类，均继承本类。
 * 此基类无事务管理
 *
 * @author SongCheng
 */
//@ContextConfiguration(value = {"/spring-web.xml"})
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ChanpayConfig.class, AbstractTestBase.Config.class})
public abstract class AbstractTestBase {

    static {
        //重新定位Log4j日志配置
        /*
        URL url = AbstractTestSpring.class.getResource("/log4j.properties");
		PropertyConfigurator.configure(url);
		 */
    }//static

    @PropertySource("classpath:/cj.properties")
    @Configuration
    static class Config {

    }


}//class
