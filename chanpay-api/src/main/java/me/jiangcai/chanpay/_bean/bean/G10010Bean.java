package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

import java.util.LinkedList;
import java.util.List;

/**
 * G10010接口  Bean
 *
 * @author Thinker
 */
public class G10010Bean extends Gw01MsgBase {

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
    //分账信息
    public List<G10010DtoRouting> routingList = new LinkedList<G10010DtoRouting>();
    /**
     * 业务代码, 接入生产前，业务人员会提供
     */
    private String businessCode;
    /**
     * 产品编码, 接入生产前，业务人员会提供
     */
    private String productCode;
    /**
     * 对公对私
     */
    private String publicPersonal;
    /**
     * 收款方账号
     */
    private String recAcctNo;
    /**
     * 收款方账户名称
     */
    private String recAcctName;
    /**
     * 开户行名称 如：中国建设银行广州东山广场分理处
     */
    private String bankName;
    /**
     * 开户行号， 对方账号对应的开户行支行行号
     */
    private String bankCode;
    /**
     * 开户行所在省
     */
    private String province;
    /**
     * 开户行所在市
     */
    private String city;
    /**
     * 金额
     */
    private long amount;
    /**
     * 开户证件类型
     */
    private String idType;
    /**
     * 证件号
     */
    private String id;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 备注
     */
    private String summary;
    /**
     * 用途
     */
    private String postscript;
    /**
     * 外部企业流水号
     */
    private String corpFlowNo;
    /**
     * 外部企业订单号
     */
    private String corpOrderNo;

    public List<G10010DtoRouting> getRoutingList() {
        return routingList;
    }

    public void setRoutingList(List<G10010DtoRouting> routingList) {
        this.routingList = routingList;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPublicPersonal() {
        return publicPersonal;
    }

    public void setPublicPersonal(String publicPersonal) {
        this.publicPersonal = publicPersonal;
    }

    public String getRecAcctNo() {
        return recAcctNo;
    }

    public void setRecAcctNo(String recAcctNo) {
        this.recAcctNo = recAcctNo;
    }

    public String getRecAcctName() {
        return recAcctName;
    }

    public void setRecAcctName(String recAcctName) {
        this.recAcctName = recAcctName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public String getCorpFlowNo() {
        return corpFlowNo;
    }

    public void setCorpFlowNo(String corpFlowNo) {
        this.corpFlowNo = corpFlowNo;
    }

    public String getCorpOrderNo() {
        return corpOrderNo;
    }

    public void setCorpOrderNo(String corpOrderNo) {
        this.corpOrderNo = corpOrderNo;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }


}
