package me.jiangcai.chanpay.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.jiangcai.chanpay.AbstractTestBase;
import me.jiangcai.chanpay.test.FixedData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.InputStream;
import java.security.SignatureException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author CJ
 */
public class SignTest extends AbstractTestBase {

    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationContext context;
    private Sign sign;

    @Before
    public void init() throws Exception {
        String keyLocation = environment.getRequiredProperty("chanpay.keyStore");
        String keyPass = environment.getRequiredProperty("chanpay.keyPass");
        String certificate = environment.getRequiredProperty("chanpay.certificate");
        try (InputStream inputStream = context.getResource(keyLocation).getInputStream()) {
            try (InputStream certificateInputStream = context.getResource(certificate).getInputStream()) {
                sign = new Sign(inputStream, keyPass, certificateInputStream);
            }
        }
    }

    @Test
    public void runIt() throws JsonProcessingException, SignatureException {
        FixedData fixedData = new FixedData();
        fixedData.setData("悟空");
        sign.sign(fixedData);
        System.out.println(fixedData.getSignedMessage());
        // <FixedData xmlns=""><data>悟空</data><SIGNED_MSG></SIGNED_MSG></FixedData>

        assertThat(
                sign.verify(fixedData))
                .isTrue();
        // 用新API加密 老API检查
        fixedData = new FixedData();
        fixedData.setData(UUID.randomUUID().toString());
        sign.sign(fixedData);
    }

}