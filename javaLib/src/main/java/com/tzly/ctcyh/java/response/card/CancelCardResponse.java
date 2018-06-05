package com.tzly.ctcyh.java.response.card;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 解绑
 */

public class CancelCardResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : {"id":3,"userId":775}
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
         * id : 3
         * userId : 775
         */

        private int id;
        private int userId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
