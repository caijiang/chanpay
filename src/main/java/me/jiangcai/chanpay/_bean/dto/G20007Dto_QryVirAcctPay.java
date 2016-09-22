package me.jiangcai.chanpay._bean.dto;

public class G20007Dto_QryVirAcctPay extends Gw01MsgBase {

    /**
     * 要查询交易的请求号：
     * 原单笔垫资付款交易请求中代码REQ_SN号
     */
    public String qryReqSn;
    /**
     * CJ业务返回码，
     * 0000：处理成功
     * 0001：代理系统受理成功
     * 。。。 。。。
     */
    public String bizRetcode;
    /**
     * 业务返回码描述
     */
    public String bizErrmsg;
    /**
     * 付款账号
     */
    public String payAcctNo;
    /**
     * 付款账户余额
     */
    public long payAcctBalance;
    /**
     * 收款账号
     */
    public String recAcctNo;
    /**
     * 收款账户名称
     */
    public String recAcctName;
    /**
     * 金额
     */
    public long amount;
    /**
     * 垫资利息
     * 注：对于非次日还款的交易，利息费用计算需要等到还款后计算，因此此处金额可能为“0”或“空”
     */
    public long charge;
    /**
     * 备注
     */
    public String summary;
    /**
     * 用途
     */
    public String postscript;
    /**
     * 外部企业流水号
     */
    public String corpFlowNo;
    /**
     * 外部企业订单号,要求企业内唯一
     */
    public String corpOrderNo;
    /**
     * 备用字段1
     */
    public String remark1 = "";
    /**
     * 备用字段2
     */
    public String remark2 = "";
    /**
     * 备用字段3
     */
    public String remark3 = "";
    /**
     * 备用字段4
     */
    public String remark4 = "";
    /**
     * 备用字段5
     */
    public String remark5 = "";
}//class
