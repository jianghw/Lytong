package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 油卡 分享
 */

public class OilShareModuleResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : {"id":1,"businessType":13,"topImg":"http://n.sinaimg.cn/sports/transform/283/w650h433/20180522/mwmC-hawmaua6700627.jpg","banner":"http://n.sinaimg.cn/sports/transform/283/w650h433/20180522/mwmC-hawmaua6700627.jpg","img":"http://n.sinaimg.cn/sports/transform/283/w650h433/20180522/mwmC-hawmaua6700627.jpg","extraParam":"{configId:1}"}
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
         * id : 1
         * businessType : 13
         * topImg : http://n.sinaimg.cn/sports/transform/283/w650h433/20180522/mwmC-hawmaua6700627.jpg
         * banner : http://n.sinaimg.cn/sports/transform/283/w650h433/20180522/mwmC-hawmaua6700627.jpg
         * img : http://n.sinaimg.cn/sports/transform/283/w650h433/20180522/mwmC-hawmaua6700627.jpg
         * extraParam : {configId:1}
         */

        private int id;
        private int businessType;
        private String topImg;
        private String banner;
        private String img;
        private String extraParam;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBusinessType() {
            return businessType;
        }

        public void setBusinessType(int businessType) {
            this.businessType = businessType;
        }

        public String getTopImg() {
            return topImg;
        }

        public void setTopImg(String topImg) {
            this.topImg = topImg;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getExtraParam() {
            return extraParam;
        }

        public void setExtraParam(String extraParam) {
            this.extraParam = extraParam;
        }
    }
}
