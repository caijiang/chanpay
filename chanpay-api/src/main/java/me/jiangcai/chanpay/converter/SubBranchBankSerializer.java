package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import me.jiangcai.chanpay.model.SubBranch;

import java.io.IOException;

/**
 * @author CJ
 */
public class SubBranchBankSerializer extends JsonSerializer<SubBranch> {
    @Override
    public void serialize(SubBranch value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.getName());
            if (value.getId() != null)
                gen.writeStringField("bank_line_no", value.getId());

        }
    }
}
