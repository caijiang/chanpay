package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.jiangcai.chanpay.AsynchronousNotifiable;
import me.jiangcai.chanpay.BusinessSerial;
import me.jiangcai.chanpay.data.Request;
import me.jiangcai.chanpay.data.Response;
import me.jiangcai.chanpay.data.pay.PayRequest;
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
    private static String MERCHANT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDv0rdsn5FYPn0EjsCPqDyIsYRawNWGJDRHJBcdCldodjM5bpve+XYb4Rgm36F6iDjxDbEQbp/HhVPj0XgGlCRKpbluyJJt8ga5qkqIhWoOd/Cma1fCtviMUep21hIlg1ZFcWKgHQoGoNX7xMT8/0bEsldaKdwxOlv3qGxWfqNV5QIDAQAB";
    private static String MERCHANT_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";
    private final Sign sign;
    private final XmlMapper xmlMapper = new ChanpayXmlMapper();
    /**
     * 本服务提供出来的异步通知接口URL
     */
    private final String notifyUrl;
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

        //
        notifyUrl = environment.getRequiredProperty("chanpay.notify.url");
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
    public <T> T execute(PayRequest request, PayHandler<T> handler) throws IOException, SignatureException {

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
            HttpPost post = new HttpPost("https://tpay.chanpay.com/mag/gateway/receiveOrder.do");

            post.setEntity(request.toEntity());

            if (handler == null) {
                String response = client.execute(post, new BasicResponseHandler());
                log.debug("response:" + response);
                return null;
            }
            return client.execute(post, handler);
        }
    }

    private void signRequest(PayRequest request) throws IOException, SignatureException {
        request.setSign(null);
        request.setSignType(null);
        String code = request.preString();

        String sign = RSA.sign(code, MERCHANT_PRIVATE_KEY, "UTF-8");
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
