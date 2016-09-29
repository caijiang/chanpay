package me.jiangcai.chanpay.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.jiangcai.chanpay.converter.EnumSerializer;

/**
 * 帐号属性
 *
 * @author CJ
 */
@JsonSerialize(using = EnumSerializer.class)
public enum AccountProperty {

    /**
     * 对私
     */
    personal,
    /**
     * 对公
     */
    corporate

}
