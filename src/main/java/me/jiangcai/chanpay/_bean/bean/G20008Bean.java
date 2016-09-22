package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G20008接口 Bean
 *
 * @author Thinker
 */
public class G20008Bean extends Gw01MsgBase {
    private String qryReqSn;//要查询批量交易的请求号
    private String qryPage;//查询页号
    private String sn;//明细号
    private String batRetCode;//批次业务返回码
    private String batErrMsg;//批次业务返回码描述
    private String ttlPage;//总页数
    private String ttlCnt;//记录总条数
    private String currPage;//当前页号
    private String currCnt;//当前页记录条数
    private String retDate;//明细结果记录

    public String getQryReqSn() {
        return qryReqSn;
    }

    public void setQryReqSn(String qryReqSn) {
        this.qryReqSn = qryReqSn;
    }

    public String getQryPage() {
        return qryPage;
    }

    public void setQryPage(String qryPage) {
        this.qryPage = qryPage;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getBatRetCode() {
        return batRetCode;
    }

    public void setBatRetCode(String batRetCode) {
        this.batRetCode = batRetCode;
    }

    public String getBatErrMsg() {
        return batErrMsg;
    }

    public void setBatErrMsg(String batErrMsg) {
        this.batErrMsg = batErrMsg;
    }

    public String getTtlPage() {
        return ttlPage;
    }

    public void setTtlPage(String ttlPage) {
        this.ttlPage = ttlPage;
    }

    public String getTtlCnt() {
        return ttlCnt;
    }

    public void setTtlCnt(String ttlCnt) {
        this.ttlCnt = ttlCnt;
    }

    public String getCurrPage() {
        return currPage;
    }

    public void setCurrPage(String currPage) {
        this.currPage = currPage;
    }

    public String getCurrCnt() {
        return currCnt;
    }

    public void setCurrCnt(String currCnt) {
        this.currCnt = currCnt;
    }

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }


}
