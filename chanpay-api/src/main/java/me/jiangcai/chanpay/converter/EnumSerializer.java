package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jiangcai.chanpay.StringExpression;

import java.io.IOException;

/**
 * @author CJ
 */
public class EnumSerializer extends JsonSerializer<Enum<?>> {

    @Override
    public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        if (value instanceof StringExpression) {
            gen.writeString(((StringExpression) value).toStringExpression());
            return;
        }

        gen.writeString(String.valueOf(value.ordinal()));
    }
}
