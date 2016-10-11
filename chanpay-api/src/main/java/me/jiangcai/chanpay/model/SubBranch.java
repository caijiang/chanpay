package me.jiangcai.chanpay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.jiangcai.chanpay.model.support.AbstractGeographyModel;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SubBranch extends AbstractGeographyModel {
    private String cityId;
    private String bankId;
//    private City city;
//    private Bank bank;
}
