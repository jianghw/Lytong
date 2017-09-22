package com.zantong.mobile.home.bean;

import java.util.List;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public class BannerBean {

    /**
     * type : 1
     * banners : [{"id":3,"url":"http://139.196.183.121:8081/h5/banner/happysend.png","advertisementSkipUrl":"http://139.196.183.121:8081/h5/bannerSkip/happysend.html"},{"id":2,"url":"http://139.196.183.121:8081/h5/banner/discount_travel.png","advertisementSkipUrl":"https://sale.jd.com/m/act/AkGyJL7gi5NY.html"}]
     */

    private String type;
    private List<BannersBean> banners;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

}
