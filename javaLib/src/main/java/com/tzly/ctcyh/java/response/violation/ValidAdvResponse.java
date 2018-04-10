package com.tzly.ctcyh.java.response.violation;


import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 广告
 */

public class ValidAdvResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : [{"id":1,"type":1,"image":"https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=138126325,1485620701&fm=85&s=7FAB2EC3909A35D01E299C1A030010D2","url":"native_app_97recharge","isValid":1},{"id":2,"type":2,"image":"https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=138126325,1485620701&fm=85&s=7FAB2EC3909A35D01E299C1A030010D2","url":"http://www.baidu.com","isValid":1}]
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
         * id : 1
         * type : 1
         * image : https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=138126325,1485620701&fm=85&s=7FAB2EC3909A35D01E299C1A030010D2
         * url : native_app_97recharge
         * isValid : 1
         */

        private int id;
        private int type;
        private String image;
        private String url;
        private int isValid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }
    }
}
