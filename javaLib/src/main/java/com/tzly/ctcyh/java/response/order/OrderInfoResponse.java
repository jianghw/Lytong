package com.tzly.ctcyh.java.response.order;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 反显用户信息
 */

public class OrderInfoResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : {"addressDetail":"1","phone":"1","shi":"1","xian":"1","supplement":"","name":"1","sheng":"1"}
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
         * addressDetail : 1
         * phone : 1
         * shi : 1
         * xian : 1
         * supplement :
         * name : 1
         * sheng : 1
         */

        private String addressDetail;
        private String phone;
        private String shi;
        private String xian;
        private String supplement;
        private String name;
        private String sheng;

        private String bespeakDate;
        private String expressTime;

        public String getBespeakDate() {
            return bespeakDate;
        }

        public String getExpressTime() {
            return expressTime;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShi() {
            return shi;
        }

        public void setShi(String shi) {
            this.shi = shi;
        }

        public String getXian() {
            return xian;
        }

        public void setXian(String xian) {
            this.xian = xian;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSheng() {
            return sheng;
        }

        public void setSheng(String sheng) {
            this.sheng = sheng;
        }
    }
}
