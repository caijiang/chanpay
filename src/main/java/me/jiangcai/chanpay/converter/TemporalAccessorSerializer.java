package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.temporal.TemporalAccessor;

/**
 * @author CJ
 */
public class TemporalAccessorSerializer extends JsonSerializer<TemporalAccessor> {
    @Override
    public void serialize(TemporalAccessor value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value == null)
            gen.writeNull();
        else {
            gen.writeString(LocalDateTimeDeserializer.format.format(value));
        }
    }
}
