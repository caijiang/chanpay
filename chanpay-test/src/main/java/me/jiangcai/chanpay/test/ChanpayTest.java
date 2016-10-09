package me.jiangcai.chanpay.test;

import com.fasterxml.jackson.databind.JsonNode;
import me.jiangcai.chanpay.config.ChanpayConfig;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URLDecoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(value = {"/spring-web.xml"})
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ChanpayTestSpringConfig.class, ChanpayConfig.class})
@WebAppConfiguration
public abstract class ChanpayTest {

    public static ResultActions MockRequest(MockMvc mockMvc, JsonNode request, String uri) throws Exception {
        if (request.get("method").asText().equalsIgnoreCase("post")) {
            MockHttpServletRequestBuilder builder = post(uri);
            JsonNode headers = request.get("headers");
            String contentType = null;
            for (JsonNode header : headers) {
                String name = header.get("name").asText();
                if (name.equals("content-length"))
                    continue;
                if (name.equals("host"))
                    continue;
                String value = header.get("value").asText();
                builder = builder.header(name, value);
                if (name.equalsIgnoreCase("Content-Type")) {
                    contentType = value;
                }
            }
            String content = request.get("content").asText();
            builder = builder.content(content);
            // 这个算是MVC Mock 不足的地方 参数不正确!
            if (content != null && contentType != null && MediaType.parseMediaType(contentType).isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED)) {
                String[] paramStrings = content.split("&");
                for (String paramString : paramStrings) {
                    int index = paramString.indexOf("=");
                    String name = paramString.substring(0, index);
                    String value = paramString.substring(index + 1);
                    builder = builder.param(name, URLDecoder.decode(value, "UTF-8"));
                }
            }
            return mockMvc.perform(builder)
//                            .andDo(print())
                    .andExpect(status().isOk());
        } else
            throw new IllegalArgumentException(request.get("method").asText() + " is not supported.");
    }

}
