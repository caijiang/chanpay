package me.jiangcai.chanpay.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.jiangcai.chanpay.converter.EnumSerializer;

/**
 * @author CJ
 */
@JsonSerialize(using = EnumSerializer.class)
public enum IdType {


    身份证, 户口簿, 护照, 军官证, 士兵证, 港澳居民来往内地通行证, 台湾同胞来往内地通行证, 临时身份证, 外国人居留证, 警官证,
    /**
     * X.
     */
    其他证件
}
