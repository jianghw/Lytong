package com.zantong.mobilecttx.home.bean;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class HomeAdvertisement {
    private int id;	                //id
    private String url;	                //广告图片地址
    private String advertisementSkipUrl;//链接
    private int cityId;
    private String validPeriodStart;	//广告开始时间
    private String validPeriodEnd;	    //广告截止时间
    private String  displayTime;
    private String clickVolume;

    private int statisticsId;

    public int getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(int statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdvertisementSkipUrl() {
        return advertisementSkipUrl;
    }

    public void setAdvertisementSkipUrl(String advertisementSkipUrl) {
        this.advertisementSkipUrl = advertisementSkipUrl;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getValidPeriodStart() {
        return validPeriodStart;
    }

    public void setValidPeriodStart(String validPeriodStart) {
        this.validPeriodStart = validPeriodStart;
    }

    public String getValidPeriodEnd() {
        return validPeriodEnd;
    }

    public void setValidPeriodEnd(String validPeriodEnd) {
        this.validPeriodEnd = validPeriodEnd;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getClickVolume() {
        return clickVolume;
    }

    public void setClickVolume(String clickVolume) {
        this.clickVolume = clickVolume;
    }
}
