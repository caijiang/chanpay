package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

public class G10005Bean extends Gw01MsgBase {

    /**
     * 业务代码, 接入生产前，业务人员会提供
     */
    private String businessCode;
    /**
     * 产品编码, 接入生产前，业务人员会提供
     */
    private String productCode;
    /**
     * 企业账号
     */
    private String corpAccNo;
    /**
     * 对公对私
     */
    private String accountProp;
    /**
     * 支付时效性
     */
    private String timeliness;
    /**
     * 预约清算日期
     */
    private String appointmentTime;
    /**
     * 文件路径
     */
    private String ftpPath;
    /**
     * MD5数值
     */
    private String fileMd5;

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

    public String getCorpAccNo() {
        return corpAccNo;
    }

    public void setCorpAccNo(String corpAccNo) {
        this.corpAccNo = corpAccNo;
    }

    public String getAccountProp() {
        return accountProp;
    }

    public void setAccountProp(String accountProp) {
        this.accountProp = accountProp;
    }

    public String getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
}// class
