package me.jiangcai.chanpay.test;

import me.jiangcai.chanpay.data.SingleRealTransaction;

/**
 * @author CJ
 */
public class TestTransaction extends SingleRealTransaction {
    @Override
    protected String getTransactionCode() {
        return "G10001";
    }
}
