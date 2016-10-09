package me.jiangcai.chanpay.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import me.jiangcai.chanpay.model.CardType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author CJ
 */
public class CardTypeDeserializer extends JsonDeserializer<CardType> {

    private static final Log log = LogFactory.getLog(CardTypeDeserializer.class);

    @Override
    public CardType deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String value = p.readValueAs(String.class);
        if (StringUtils.isEmpty(value))
            return null;
        try {
            return CardType.valueOf(value);
        } catch (IllegalArgumentException ex) {
            log.warn("unknown data", ex);
            return CardType.other;
        }
    }
}
