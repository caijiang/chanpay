package me.jiangcai.chanpay.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jiangcai.chanpay.exception.ServiceException;
import me.jiangcai.chanpay.support.ChanpayObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * @author CJ
 */
public abstract class PayHandler<T> implements ResponseHandler<T> {

    protected static final ObjectMapper objectMapper = new ChanpayObjectMapper();
    // {"_input_charset":"UTF-8","error_code":"REQUIRED_FIELD_NOT_EXIST","error_message":"必填字段未填写","is_success":"F","memo":"买家ID类型不能为空"}

    protected T classicsResult(JsonNode node) throws IOException {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        @SuppressWarnings("unchecked") Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
        return objectMapper.readValue(objectMapper.treeAsTokens(node), clazz);
    }

    @Override
    public T handleResponse(HttpResponse response) throws IOException {
        return handleEntity(response, response.getEntity());
    }

    private T handleEntity(HttpResponse response, HttpEntity entity) throws IOException {
        if (entity.getContentLength() == 0) {
            return handleNode(response, null);
        }
        JsonNode node = objectMapper.readTree(entity.getContent());
        JsonNode code = node.get("error_code");
        if (code != null && !code.isNull()) {
            JsonNode message = node.get("error_message");
            JsonNode memo = node.get("memo");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(message.asText());

            if (memo != null && !memo.isNull()) {
                stringBuilder.append(":").append(memo.asText());
            }
            throw new ServiceException(code.asText(), stringBuilder.toString());
        }

        return handleNode(response, node);
    }

    protected abstract T handleNode(HttpResponse response, JsonNode node) throws IOException;
}
