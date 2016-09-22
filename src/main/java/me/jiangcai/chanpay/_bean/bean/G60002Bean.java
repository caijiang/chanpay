package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G60002接口  Bean
 *
 * @author Thinker
 */
public class G60002Bean extends Gw01MsgBase {
    private String qryReqSn;//要查询交易的原请求号

    private String batchRryReqSn;//要查询交易的原请求号
    private String batchRetCode;//批次的业务返回码
    private String batchErrMsg;//批次的业务返回码描述
    private String dtlsn;//记录序号
    private String dtlRetCode;//协议的业务返回码
    private String dtlErrMsg;//协议的业务返回码描述
    private String dtlaccountNo;//账号
    private String dtlaccountName;//账户名称

    public String getQryReqSn() {
        return qryReqSn;
    }

    public void setQryReqSn(String qryReqSn) {
        this.qryReqSn = qryReqSn;
    }

    public String getBatchRryReqSn() {
        return batchRryReqSn;
    }

    public void setBatchRryReqSn(String batchRryReqSn) {
        this.batchRryReqSn = batchRryReqSn;
    }

    public String getBatchRetCode() {
        return batchRetCode;
    }

    public void setBatchRetCode(String batchRetCode) {
        this.batchRetCode = batchRetCode;
    }

    public String getBatchErrMsg() {
        return batchErrMsg;
    }

    public void setBatchErrMsg(String batchErrMsg) {
        this.batchErrMsg = batchErrMsg;
    }

    public String getDtlsn() {
        return dtlsn;
    }

    public void setDtlsn(String dtlsn) {
        this.dtlsn = dtlsn;
    }

    public String getDtlRetCode() {
        return dtlRetCode;
    }

    public void setDtlRetCode(String dtlRetCode) {
        this.dtlRetCode = dtlRetCode;
    }

    public String getDtlErrMsg() {
        return dtlErrMsg;
    }

    public void setDtlErrMsg(String dtlErrMsg) {
        this.dtlErrMsg = dtlErrMsg;
    }

    public String getDtlaccountNo() {
        return dtlaccountNo;
    }

    public void setDtlaccountNo(String dtlaccountNo) {
        this.dtlaccountNo = dtlaccountNo;
    }

    public String getDtlaccountName() {
        return dtlaccountName;
    }

    public void setDtlaccountName(String dtlaccountName) {
        this.dtlaccountName = dtlaccountName;
    }


}
