package me.jiangcai.chanpay.data.trade.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.function.Function;

/**
 * 很像{@link String},而且可以用一个{@link CharSequence}初始化
 *
 * @author CJ
 */
@JsonSerialize(using = EncryptString.EncryptStringSerializer.class)
public class EncryptString implements java.io.Serializable, Comparable<String>, CharSequence {

    private static final long serialVersionUID = 1759511151732845879L;
    public static Function<String, String> encryptors = Function.identity();
    /**
     * 原始值
     */
    private final String origin;
    private final String encrypt;

    public EncryptString(CharSequence origin) {
        this.origin = origin.toString();
        this.encrypt = encryptors.apply(this.origin);
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return encrypt;
    }

    @Override
    public int length() {
        return origin.length();
    }

    @Override
    public char charAt(int index) {
        return origin.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return origin.subSequence(start, end);
    }

    @Override
    public int compareTo(String o) {
        return origin.compareTo(o);
    }

    static class EncryptStringSerializer extends JsonSerializer<Object> {

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            if (value == null)
                gen.writeNull();
            else
                gen.writeString(value.toString());
        }
    }
}
