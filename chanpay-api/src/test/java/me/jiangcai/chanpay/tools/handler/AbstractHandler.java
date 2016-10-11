package me.jiangcai.chanpay.tools.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author CJ
 */
public abstract class AbstractHandler<T> extends AbstractResponseHandler<T> {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public T handleEntity(HttpEntity entity) throws IOException {
        try (InputStream inputStream = entity.getContent()) {
            JsonNode node = objectMapper.readTree(inputStream);
            JsonNode data = node.get("data");

            return handleData(data);
        }
    }

    protected abstract T handleData(JsonNode data);

}
