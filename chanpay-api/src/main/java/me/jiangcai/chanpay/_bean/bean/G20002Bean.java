package me.jiangcai.chanpay._bean.bean;

import me.jiangcai.chanpay._bean.dto.Gw01MsgBase;

import java.util.List;

public class G20002Bean extends Gw01MsgBase {

    /**
     * 原交易请求号
     */
    private String qryReqSn;
    /**
     * 原交易明细号
     */
    private String sn;
    /** 返回结果信息 */
    /**
     * 交易明细集合
     */
    private List<G20002SubBean> list;

    public String getQryReqSn() {
        return qryReqSn;
    }

    public void setQryReqSn(String qryReqSn) {
        this.qryReqSn = qryReqSn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<G20002SubBean> getList() {
        return list;
    }

    public void setList(List<G20002SubBean> list) {
        this.list = list;
    }

}// class
