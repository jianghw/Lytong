package com.tzly.ctcyh.pay.response;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class CouponDetailBean {


    /**
     * id : 15
     * goodsId : 32
     * orderId : 17121913015860
     * code : 1966
     * totalCount : 1
     * usedCount : 0
     * bindTime : 2017-12-19 13:02:13
     * createTime : 2017-10-15 20:45:33
     * startTime : 2017.12.04
     * endTime : 2018.12.12
     * channel : 电影券
     * uid : 00090018811095999
     * status : 1
     * regulation : <p>这是一个段落</p><img src="https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=138126325,1485620701&fm=85&s=7FAB2EC3909A35D01E299C1A030010D2"/>
     */

    private int id;
    private int goodsId;
    private long orderId;
    private String code;
    private int totalCount;
    private int usedCount;
    private String bindTime;
    private String createTime;
    private String startTime;
    private String endTime;
    private String channel;
    private String uid;
    private int status;
    private String regulation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }
}
