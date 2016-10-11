package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jiangcai.chanpay.model.Bank;

import java.io.IOException;

/**
 * @author CJ
 */
public class BankSerializer extends JsonSerializer<Bank> {
    @Override
    public void serialize(Bank value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getId());
            gen.writeStringField("bank_name", value.getName());
        }
    }
}
