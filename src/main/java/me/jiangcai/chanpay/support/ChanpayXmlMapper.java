package me.jiangcai.chanpay.support;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author CJ
 */
public class ChanpayXmlMapper extends XmlMapper {

    {
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

}
