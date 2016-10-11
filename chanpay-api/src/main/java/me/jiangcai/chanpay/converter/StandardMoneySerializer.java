package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @author CJ
 */
public class StandardMoneySerializer extends JsonSerializer<Number> {
    private static final NumberFormat format;

    static {
        format = NumberFormat.getInstance();
//        format.setCurrency();/
        format.setGroupingUsed(false);
        format.setRoundingMode(RoundingMode.HALF_UP);//设置四舍五入
        format.setMinimumFractionDigits(2);//设置最小保留几位小数
        format.setMaximumFractionDigits(2);//设置最大保留几位小数
//        format.setGroupingUsed();
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(format.format(value));
        }
    }
}
