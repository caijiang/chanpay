package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.jiangcai.chanpay.security.Signable;

/**
 * @author CJ
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JacksonXmlRootElement(localName = "MESSAGE")
class AbstractMessage implements Signable {

    //    @JacksonXmlElementWrapper(useWrapping = true)
    @JsonProperty("INFO")
    private final Header info;

    AbstractMessage(Header info) {
        this.info = info;
    }

    @JsonIgnore
    @Override
    public String getSignedMessage() {
        return info.getSignedMessage();
    }

    @JsonIgnore
    @Override
    public void setSignedMessage(String message) {
        info.setSignedMessage(message);
    }
}
