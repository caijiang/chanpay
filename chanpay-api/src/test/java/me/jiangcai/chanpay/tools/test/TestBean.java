package me.jiangcai.chanpay.tools.test;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author CJ
 */
@Data
public class TestBean {

    @JsonSerialize(using = FunSer.class)
    private String name;

}
