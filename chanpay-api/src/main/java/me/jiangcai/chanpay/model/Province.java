package me.jiangcai.chanpay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.jiangcai.chanpay.model.support.AbstractGeographyModel;

import java.util.List;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Province extends AbstractGeographyModel {

    private List<City> cityList;

}
