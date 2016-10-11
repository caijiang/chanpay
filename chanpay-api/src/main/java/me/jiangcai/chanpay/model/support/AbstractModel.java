package me.jiangcai.chanpay.model.support;

import lombok.Data;

/**
 * @author CJ
 */
@Data
public class AbstractModel {

    private String id;
    private String name;

    public String toQuery() {
        return id + "@" + name;
    }
}
