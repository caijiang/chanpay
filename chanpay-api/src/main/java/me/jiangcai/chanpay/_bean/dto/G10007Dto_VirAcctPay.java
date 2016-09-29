package me.jiangcai.chanpay._bean.dto;

public class G10007Dto_VirAcctPay extends Gw01MsgBase {

    /**
     * 业务代码, 接入生产前，业务人员会提供
     */
    public String businessCode;
    /**
     * 产品编码, 接入生产前，业务人员会提供
     */
    public String productCode;
    /**
     * 账号
     */
    public String recAcctNo;
    /**
     * 账户名称
     */
    public String recAcctName;
    /**
     * 开户行名称
     * 如：中国建设银行广州东山广场分理处
     */
    public String bankName;
    /**
     * 开户行号， 对方账号对应的开户行支行行号
     */
    public String bankCode;
    /**
     * 货币类型, 人民币
     */
    public String currency;
    /**
     * 金额, 垫资付款金额
     */
    public long amount;
    /**
     * 外部企业流水号
     */
    public String corpFlowNo;
    /**
     * 备注
     */
    public String summary;
    /**
     * 外部企业订单号
     * 要求企业内唯一
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
