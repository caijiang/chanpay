package me.jiangcai.chanpay._bean.dto;

public abstract class Gw01MsgBase {
    /**
     * 报文交易代码 标识此请求为“单笔垫资付款业务”
     */
    private String trxCode;
    /**
     * 商户代码 标识商户的唯一ID，15位
     */
    private String mertid;
    /**
     * 交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号；
     */
    private String reqSn;
    /**
     * 受理时间 代支付系统接收到交易请求时服务器时间； 对于交易的发起时间以此时间为准
     */
    private String timestamp;
    /**
     * CJ返回代码
     */
    private String retCode;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 二级商户号
     */
    private String subMertid;

    public String getTrxCode() {
        return trxCode;
    }

    public void setTrxCode(String trxCode) {
        this.trxCode = trxCode;
    }

    public String getMertid() {
        return mertid;
    }

    public void setMertid(String mertid) {
        this.mertid = mertid;
    }

    public String getReqSn() {
        return reqSn;
    }

    public void setReqSn(String reqSn) {
        this.reqSn = reqSn;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getSubMertid() {
        return subMertid;
    }

    public void setSubMertid(String subMertid) {
        this.subMertid = subMertid;
    }

}// class
