package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jiangcai.chanpay.model.City;

import java.io.IOException;

/**
 * @author CJ
 */
public class CitySerializer extends JsonSerializer<City> {
    @Override
    public void serialize(City value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getName());
        }
    }
}
