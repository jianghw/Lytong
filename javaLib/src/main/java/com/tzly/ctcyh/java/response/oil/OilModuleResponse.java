package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 油卡
 */

public class OilModuleResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : [{"id":111,"parentId":104,"title":"banner1","subTitle":"","showType":0,"moduleType":1,"img":"http://h5.liyingtong.com/img/second_car/banner1.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction1.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":1,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"},{"id":112,"parentId":104,"title":"banner2","subTitle":"","showType":0,"moduleType":1,"img":"http://h5.liyingtong.com/img/second_car/banner2.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction2.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":2,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|","children":[{"id":115,"parentId":112,"title":"banner2","subTitle":"","showType":0,"moduleType":1,"img":"","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction4.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":2,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"}]},{"id":113,"parentId":104,"title":"banner3","subTitle":"","showType":0,"moduleType":1,"img":"http://h5.liyingtong.com/img/second_car/banner3.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction3.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":3,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|","children":[{"id":116,"parentId":113,"title":"banner3","subTitle":"","showType":0,"moduleType":1,"img":"","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction4.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":3,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"}]},{"id":114,"parentId":104,"title":"banner4","subTitle":"","showType":0,"moduleType":1,"img":"http://h5.liyingtong.com/img/second_car/banner4.jpg","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction4.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":4,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"}]
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
         * id : 111
         * parentId : 104
         * title : banner1
         * subTitle :
         * showType : 0
         * moduleType : 1
         * img : http://h5.liyingtong.com/img/second_car/banner1.jpg
         * targetType : 1
         * targetPath : http://h5.liyingtong.com/img/second_car/introduction1.jpg
         * effectiveFrom : 2018-04-26 00:00:00
         * effectiveTo : 2018-04-30 00:00:00
         * state : 1
         * sort : 1
         * clickVolume : 0
         * statisticsId : 0
         * versionIOS : |999|
         * versionAndroid : |999|
         * children : [{"id":115,"parentId":112,"title":"banner2","subTitle":"","showType":0,"moduleType":1,"img":"","targetType":1,"targetPath":"http://h5.liyingtong.com/img/second_car/introduction4.jpg","effectiveFrom":"2018-04-26 00:00:00","effectiveTo":"2018-04-30 00:00:00","state":1,"sort":2,"clickVolume":0,"statisticsId":0,"versionIOS":"|999|","versionAndroid":"|999|"}]
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
        private String effectiveFrom;
        private String effectiveTo;
        private int state;
        private int sort;
        private int clickVolume;
        private int statisticsId;
        private String versionIOS;
        private String versionAndroid;
        private List<ChildrenBean> children;

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

        public String getEffectiveFrom() {
            return effectiveFrom;
        }

        public void setEffectiveFrom(String effectiveFrom) {
            this.effectiveFrom = effectiveFrom;
        }

        public String getEffectiveTo() {
            return effectiveTo;
        }

        public void setEffectiveTo(String effectiveTo) {
            this.effectiveTo = effectiveTo;
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

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 115
             * parentId : 112
             * title : banner2
             * subTitle :
             * showType : 0
             * moduleType : 1
             * img :
             * targetType : 1
             * targetPath : http://h5.liyingtong.com/img/second_car/introduction4.jpg
             * effectiveFrom : 2018-04-26 00:00:00
             * effectiveTo : 2018-04-30 00:00:00
             * state : 1
             * sort : 2
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
            private String effectiveFrom;
            private String effectiveTo;
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

            public String getEffectiveFrom() {
                return effectiveFrom;
            }

            public void setEffectiveFrom(String effectiveFrom) {
                this.effectiveFrom = effectiveFrom;
            }

            public String getEffectiveTo() {
                return effectiveTo;
            }

            public void setEffectiveTo(String effectiveTo) {
                this.effectiveTo = effectiveTo;
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
}
