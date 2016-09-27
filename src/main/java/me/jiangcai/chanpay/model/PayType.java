package me.jiangcai.chanpay.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author CJ
 */
@Data
@AllArgsConstructor
public class PayType {
    private CardType type;
    private CardAttribute attribute;
}
