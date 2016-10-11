package me.jiangcai.chanpay.model.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author CJ
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AbstractGeographyModel extends AbstractModel {
    private String shortName;
}
