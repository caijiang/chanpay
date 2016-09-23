package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.jiangcai.chanpay.data.Request;
import me.jiangcai.chanpay.data.Response;
import me.jiangcai.chanpay.security.Sign;
import me.jiangcai.chanpay.service.TransactionService;
import me.jiangcai.chanpay.support.ChanpayXmlMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.SignatureException;

/**
 * @author CJ
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Log log = LogFactory.getLog(TransactionServiceImpl.class);
    private final Sign sign;
    private final XmlMapper xmlMapper = new ChanpayXmlMapper();
    @Autowired
    private Environment environment;

    @Autowired
    public TransactionServiceImpl(Environment environment, ApplicationContext context) throws Exception {
        String keyLocation = environment.getRequiredProperty("chanpay.keyStore");
        String keyPass = environment.getRequiredProperty("chanpay.keyPass");
        String certificate = environment.getRequiredProperty("chanpay.certificate");
        try (InputStream inputStream = context.getResource(keyLocation).getInputStream()) {
            try (InputStream certificateInputStream = context.getResource(certificate).getInputStream()) {
                sign = new Sign(inputStream, keyPass, certificateInputStream);
            }
        }
    }

    @Override
    public Response execute(Request request) throws IOException, SignatureException {

        sign.sign(request);
        if (log.isDebugEnabled()) {
            log.debug(xmlMapper.writeValueAsString(request));
        }

        try (CloseableHttpClient client = newClient()) {

            HttpPost post = new HttpPost(environment.getRequiredProperty("chanpay.gateWayUrl"));
            log.debug("prepare url:" + post);

            HttpEntity entity = EntityBuilder.create()
                    .setBinary(xmlMapper.writeValueAsBytes(request))
                    .build();

            post.setEntity(entity);

//            String code =
            return client.execute(post, new ResponseHandler());
//            if (log.isDebugEnabled())
//                log.debug("result:" + code);
        }
    }

    private CloseableHttpClient newClient() {

        HttpClientBuilder builder = HttpClientBuilder.create();

        if (environment.acceptsProfiles("test")) {
            builder.setSSLHostnameVerifier(new NoopHostnameVerifier());
        }

        return builder
                .build();
    }
}
