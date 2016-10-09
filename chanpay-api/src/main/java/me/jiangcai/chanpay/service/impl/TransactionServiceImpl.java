package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.data.Request;
import me.jiangcai.chanpay.data.Response;
import me.jiangcai.chanpay.data.trade.TradeRequest;
import me.jiangcai.chanpay.data.trade.support.EncryptString;
import me.jiangcai.chanpay.security.Sign;
import me.jiangcai.chanpay.service.TransactionService;
import me.jiangcai.chanpay.support.ChanpayXmlMapper;
import me.jiangcai.chanpay.util.RSA;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.SignatureException;
import java.util.UUID;

/**
 * @author CJ
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Log log = LogFactory.getLog(TransactionServiceImpl.class);

    private final Sign sign;
    private final XmlMapper xmlMapper = new ChanpayXmlMapper();
    /**
     * 本服务提供出来的异步通知接口URL
     */
    private final String notifyUrl;
    private final String privateKey;
    private final String publicKey;
    @Autowired
    private Environment environment;

    @Autowired
    public TransactionServiceImpl(Environment environment, ApplicationContext context) throws Exception {
        privateKey = environment.getRequiredProperty("chanpay.key.self.private");
        publicKey = environment.getRequiredProperty("chanpay.key.platform.public");
        EncryptString.encryptors = this::encrypt;

        String keyLocation = environment.getRequiredProperty("chanpay.keyStore");
        String keyPass = environment.getRequiredProperty("chanpay.keyPass");
        String certificate = environment.getRequiredProperty("chanpay.certificate");
        try (InputStream inputStream = context.getResource(keyLocation).getInputStream()) {
            try (InputStream certificateInputStream = context.getResource(certificate).getInputStream()) {
                sign = new Sign(inputStream, keyPass, certificateInputStream);
            }
        }

        //
        notifyUrl = environment.getRequiredProperty("chanpay.notify.url");
    }

    /**
     * 加密
     *
     * @param src 原值
     * @return RSA加密后的密文
     */
    private String encrypt(String src) {
        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes("UTF-8"), publicKey);
            return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
//            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
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
            return client.execute(post, new ChanResponseHandler());
//            if (log.isDebugEnabled())
//                log.debug("result:" + code);
        }
    }

    @Override
    public void createMember(String loginName, String name, String id, String mobile) throws IOException {
        // 建立请求
        String uid = UUID.randomUUID().toString().replace("-", "");

    }

    @Override
    public <T> T execute(TradeRequest request, PayHandler<T> handler) throws IOException, SignatureException {

        request.setPartner(environment.getRequiredProperty("chanpay.partner"));

        if (request instanceof BusinessSerial) {
            if (((BusinessSerial) request).getSerialNumber() == null)
                ((BusinessSerial) request).setSerialNumber(UUID.randomUUID().toString().replace("-", ""));
        }

        if (request instanceof AsynchronousNotifiable) {
            if (((AsynchronousNotifiable) request).getNotifyUrl() == null) {
                ((AsynchronousNotifiable) request).setNotifyUrl(notifyUrl);
            }
        }

        signRequest(request);
        try (CloseableHttpClient client = newClient()) {
            // https://tpay.chanpay.com/mag/gateway/receiveOrder.do
            HttpPost post = new HttpPost(environment.getRequiredProperty("chanpay.cjt.gateWayUrl"));

            post.setEntity(request.toEntity());

            if (handler == null) {
                String response = client.execute(post, new BasicResponseHandler());
                log.debug("response:" + response);
                return null;
            }
            return client.execute(post, handler);
        }
    }

    private void signRequest(TradeRequest request) throws IOException, SignatureException {
        request.setSign(null);
        request.setSignType(null);
        String code = request.preString();

        String sign = RSA.sign(code, privateKey, "UTF-8");
        request.setSign(sign);
        request.setSignType("RSA");
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
