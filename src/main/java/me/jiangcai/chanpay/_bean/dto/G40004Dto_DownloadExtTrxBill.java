package me.jiangcai.chanpay._bean.dto;

public class G40004Dto_DownloadExtTrxBill extends Gw01MsgBase {

    /**
     * 对账文件类型
     * 标识此请求为“下载交易对账文件”
     */
    public int billType;
    /**
     * “按日”下载对账文件
     * 6位时间戳格式：yyyyMMdd；
     * 注：只能下载当日之前的数据（交易数据时间以付款成功时间计算）
     */
    public String billDay;
    /**
     * 对账文件字节流
     */
    public byte[] billData;
    public String contentType = "";
    public String contentDisposition = "";

}//class
