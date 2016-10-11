package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jiangcai.chanpay.model.Province;

import java.io.IOException;

/**
 * @author CJ
 */
public class ProvinceSerializer extends JsonSerializer<Province> {
    @Override
    public void serialize(Province value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getName());
        }
    }
}
