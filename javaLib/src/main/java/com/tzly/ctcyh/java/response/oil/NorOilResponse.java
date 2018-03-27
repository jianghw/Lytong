package com.tzly.ctcyh.java.response.oil;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 */

public class NorOilResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : {"SINOPEC":[{"id":43,"merchantId":15,"name":"中石化1000元在线充值","type":14,"price":0.01,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.97,"settlementPrice":970},{"id":44,"merchantId":15,"name":"中石化2000元在线充值","type":14,"price":2000,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.97,"settlementPrice":0},{"id":45,"merchantId":15,"name":"中石化5000元在线充值","type":14,"price":5000,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.97,"settlementPrice":0}],"oilCard":"1000111100018483282","img":"static-resources/images/oil.png"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * SINOPEC : [{"id":43,"merchantId":15,"name":"中石化1000元在线充值","type":14,"price":0.01,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.97,"settlementPrice":970},{"id":44,"merchantId":15,"name":"中石化2000元在线充值","type":14,"price":2000,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.97,"settlementPrice":0},{"id":45,"merchantId":15,"name":"中石化5000元在线充值","type":14,"price":5000,"description":"本服务为全国加油卡代充服务","status":1,"createTime":0,"expiredTime":86400,"discount":0.97,"settlementPrice":0}]
         * oilCard : 1000111100018483282
         * img : static-resources/images/oil.png
         */

        private String oilCard;
        private String img;
        private List<SINOPECBean> SINOPEC;

        public String getOilCard() {
            return oilCard;
        }

        public void setOilCard(String oilCard) {
            this.oilCard = oilCard;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<SINOPECBean> getSINOPEC() {
            return SINOPEC;
        }

        public void setSINOPEC(List<SINOPECBean> SINOPEC) {
            this.SINOPEC = SINOPEC;
        }

    }
}
