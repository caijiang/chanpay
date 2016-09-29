package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author CJ
 */
public class EncryptSerializer extends JsonSerializer<Object> {
    public static Function<String, String> encryptors = Function.identity();

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(encryptors.apply(value.toString()));
        }
    }
}
