package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author CJ
 */
public class SuccessDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String time = p.readValueAs(String.class);
        return time.equalsIgnoreCase("success");
    }
}
