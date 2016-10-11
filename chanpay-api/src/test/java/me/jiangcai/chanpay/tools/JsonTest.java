package me.jiangcai.chanpay.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jiangcai.chanpay.tools.test.TestBean;
import org.junit.Test;

/**
 * @author CJ
 */
public class JsonTest {

    @Test
    public void seTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TestBean bean = new TestBean();
        bean.setName("JC");

        System.out.println(objectMapper.writeValueAsString(bean));
    }

}
