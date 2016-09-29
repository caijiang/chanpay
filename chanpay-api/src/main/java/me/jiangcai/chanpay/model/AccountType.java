package me.jiangcai.chanpay.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.jiangcai.chanpay.StringExpression;
import me.jiangcai.chanpay.converter.EnumSerializer;

/**
 * 帐号类型
 *
 * @author CJ
 */
@JsonSerialize(using = EnumSerializer.class)
public enum AccountType implements StringExpression {
    /**
     * 借记卡
     */
    debit("00"),
    /**
     * 存折
     */
    bankbook("01"),
    /**
     * 信用卡
     */
    credit("02");

    private final String expression;

    AccountType(String expression) {
        this.expression = expression;
    }

    public String toStringExpression() {
        return expression;
    }
}
