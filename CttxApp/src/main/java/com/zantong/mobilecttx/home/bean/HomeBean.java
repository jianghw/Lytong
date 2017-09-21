package com.zantong.mobilecttx.home.bean;

import com.tzly.annual.base.bean.HomeNotice;

import java.util.List;

/**
 * Created by zhoujie on 2016/9/19.
 * 首页返回实体
 */
public class HomeBean {
    private List<HomeNotice> notices;//通知
    private HomeWeather weatherResponse;//天气
    private List<HomeAdvertisement> advertisementResponse;//底部广告
    private int msgNum;//未读消息数量
    private String isExistMessage;

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public String getIsExistMessage() {
        return isExistMessage;
    }

    public void setIsExistMessage(String isExistMessage) {
        this.isExistMessage = isExistMessage;
    }

    public List<HomeNotice> getNotices() {
        return notices;
    }

    public void setNotices(List<HomeNotice> notices) {
        this.notices = notices;
    }

    public HomeWeather getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(HomeWeather weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public List<HomeAdvertisement> getAdvertisementResponse() {
        return advertisementResponse;
    }

    public void setAdvertisementResponse(List<HomeAdvertisement> advertisementResponse) {
        this.advertisementResponse = advertisementResponse;
    }
}
