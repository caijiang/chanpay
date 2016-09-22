package me.jiangcai.chanpay.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 一个业务,业务在一个请求中会有不同的写入方式
 * BATCH/
 * TRANS_DETAILS 多个
 * TRANS_DETAILS/DTL/ 多个
 *
 * @author CJ
 */
public abstract class Transaction {

    @JsonIgnore
    protected abstract String getTransactionCode();

}
