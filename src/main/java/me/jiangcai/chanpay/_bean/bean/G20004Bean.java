package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

/**
 * G20004接口 Bean
 *
 * @author Thinker
 */
public class G20004Bean extends Gw01MsgBase {

    private String accountNo;//待查询账号
    private String startDay;//开始时间
    private String endDay;//结束时间
    private String status;//状态
    private long qryPage;//查询页号
    private String ttlPage;//总页数
    private String ttlCnt;//记录总条数
    private String currPage;//当前页号
    private String currCnt;//当前页记录条数
    private String retDate;//明细结果记录

    public String getRetDate() {
        return retDate;
    }

    public void setRetDate(String retDate) {
        this.retDate = retDate;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getQryPage() {
        return qryPage;
    }

    public void setQryPage(long qryPage) {
        this.qryPage = qryPage;
    }


}
