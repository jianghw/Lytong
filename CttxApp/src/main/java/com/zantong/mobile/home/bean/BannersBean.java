package com.zantong.mobile.home.bean;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */
public class BannersBean {
    /**
     * id : 3
     * url : http://139.196.183.121:8081/h5/banner/happysend.png
     * advertisementSkipUrl : http://139.196.183.121:8081/h5/bannerSkip/happysend.html
     */

    private int id;
    private String url;
    private String advertisementSkipUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdvertisementSkipUrl() {
        return advertisementSkipUrl;
    }

    public void setAdvertisementSkipUrl(String advertisementSkipUrl) {
        this.advertisementSkipUrl = advertisementSkipUrl;
    }
}
