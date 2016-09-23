package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.jiangcai.chanpay.data.Response;
import me.jiangcai.chanpay.data.ResponseHeader;
import me.jiangcai.chanpay.exception.SystemException;
import me.jiangcai.chanpay.support.ChanpayXmlMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author CJ
 */
class ResponseHandler extends AbstractResponseHandler<Response> {

    private static final Log log = LogFactory.getLog(ResponseHandler.class);
    private static final XmlMapper xmlMapper = new ChanpayXmlMapper();

    @Override
    public Response handleEntity(HttpEntity entity) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        StreamUtils.copy(entity.getContent(), buffer);
        if (log.isDebugEnabled()) {
            log.debug(StreamUtils.copyToString(new ByteArrayInputStream(buffer.toByteArray()), Charset.forName("UTF-8")));
        }

        JsonNode tree = xmlMapper.readTree(new ByteArrayInputStream(buffer.toByteArray()));

        JsonNode info = tree.get("INFO");
        ResponseHeader header = xmlMapper.readValue(xmlMapper.treeAsTokens(info), ResponseHeader.class);

        // 安全校验
        header.setSignedMessage(null);

        if (!header.isSuccess()) {
            throw new SystemException(header);
        }
        // 根据不同的业务组装不同的响应内容


        return new Response(header);
    }
}
