package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jiangcai.chanpay.model.PayType;

import java.io.IOException;

/**
 * @author CJ
 */
public class PayTypeSerializer extends JsonSerializer<PayType> {
    @Override
    public void serialize(PayType value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        //
        if (value == null)
            gen.writeNull();
        else {
            gen.writeString(value.getAttribute().name() + "," + value.getType().name());
        }
    }
}
