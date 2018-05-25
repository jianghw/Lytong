package com.tzly.ctcyh.java.response.violation;


import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 广告
 */

public class AdvModuleResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : [{"id":155,"parentId":155,"title":"分享","subTitle":"分享","showType":1,"moduleType":1,"img":"http://h5.liyingtong.com/alipay/img/pic_2.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/alipay/img/pic_2.jpg?configId=1&stage=0","state":1,"sort":1,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"},{"id":156,"parentId":155,"title":"测试1","subTitle":"副标题1","showType":1,"moduleType":1,"img":"http://h5.liyingtong.com/alipay/img/pic_1.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/alipay/img/pic_1.jpg","state":1,"sort":1,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"},{"id":157,"parentId":155,"title":"测试2","subTitle":"副标题2","showType":2,"moduleType":1,"img":"http://h5.liyingtong.com/alipay/img/pic_2.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/alipay/img/pic_2.jpg","state":1,"sort":2,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"},{"id":158,"parentId":155,"title":"测试3","subTitle":"副标题3","showType":3,"moduleType":1,"img":"http://h5.liyingtong.com/alipay/img/pic_3.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/alipay/img/pic_3.jpg","state":1,"sort":3,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 155
         * parentId : 155
         * title : 分享
         * subTitle : 分享
         * showType : 1
         * moduleType : 1
         * img : http://h5.liyingtong.com/alipay/img/pic_2.jpg
         * targetType : 1
         * targetPath : http://h5.liyingtong.com/alipay/img/pic_2.jpg?configId=1&stage=0
         * state : 1
         * sort : 1
         * clickVolume : 0
         * statisticsId : 0
         * versionIOS : |999|
         * versionAndroid : |999|
         */

        private int id;
        private int parentId;
        private String title;
        private String subTitle;
        private int showType;
        private int moduleType;
        private String img;
        private int targetType;
        private String targetPath;
        private int state;
        private int sort;
        private int clickVolume;
        private int statisticsId;
        private String versionIOS;
        private String versionAndroid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public int getModuleType() {
            return moduleType;
        }

        public void setModuleType(int moduleType) {
            this.moduleType = moduleType;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getTargetType() {
            return targetType;
        }

        public void setTargetType(int targetType) {
            this.targetType = targetType;
        }

        public String getTargetPath() {
            return targetPath;
        }

        public void setTargetPath(String targetPath) {
            this.targetPath = targetPath;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getClickVolume() {
            return clickVolume;
        }

        public void setClickVolume(int clickVolume) {
            this.clickVolume = clickVolume;
        }

        public int getStatisticsId() {
            return statisticsId;
        }

        public void setStatisticsId(int statisticsId) {
            this.statisticsId = statisticsId;
        }

        public String getVersionIOS() {
            return versionIOS;
        }

        public void setVersionIOS(String versionIOS) {
            this.versionIOS = versionIOS;
        }

        public String getVersionAndroid() {
            return versionAndroid;
        }

        public void setVersionAndroid(String versionAndroid) {
            this.versionAndroid = versionAndroid;
        }
    }
}
