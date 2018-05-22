package com.tzly.ctcyh.java.response.oil;


import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 油卡 分享
 */

public class OilShareInfoResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : {"coupons":{"eventTarget":0,"shareCounts":0,"phone":null,"configId":0,"eventStage":0,"acceptCounts":0,"id":0,"shareGiftCounts":0,"acceptGiftCounts":0},"accepters":{"eventTarget":0,"shareCounts":0,"phone":null,"configId":0,"eventStage":0,"acceptCounts":0,"id":0,"shareGiftCounts":0,"acceptGiftCounts":0}}
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
         * coupons : {"eventTarget":0,"shareCounts":0,"phone":null,"configId":0,"eventStage":0,"acceptCounts":0,"id":0,"shareGiftCounts":0,"acceptGiftCounts":0}
         * accepters : {"eventTarget":0,"shareCounts":0,"phone":null,"configId":0,"eventStage":0,"acceptCounts":0,"id":0,"shareGiftCounts":0,"acceptGiftCounts":0}
         */

        private CouponsBean coupons;
        private AcceptersBean accepters;

        public CouponsBean getCoupons() {
            return coupons;
        }

        public void setCoupons(CouponsBean coupons) {
            this.coupons = coupons;
        }

        public AcceptersBean getAccepters() {
            return accepters;
        }

        public void setAccepters(AcceptersBean accepters) {
            this.accepters = accepters;
        }

        public static class CouponsBean {
            /**
             * eventTarget : 0
             * shareCounts : 0
             * phone : null
             * configId : 0
             * eventStage : 0
             * acceptCounts : 0
             * id : 0
             * shareGiftCounts : 0
             * acceptGiftCounts : 0
             */

            private int eventTarget;
            private int shareCounts;
            private Object phone;
            private int configId;
            private int eventStage;
            private int acceptCounts;
            private int id;
            private int shareGiftCounts;
            private int acceptGiftCounts;

            public int getEventTarget() {
                return eventTarget;
            }

            public void setEventTarget(int eventTarget) {
                this.eventTarget = eventTarget;
            }

            public int getShareCounts() {
                return shareCounts;
            }

            public void setShareCounts(int shareCounts) {
                this.shareCounts = shareCounts;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public int getConfigId() {
                return configId;
            }

            public void setConfigId(int configId) {
                this.configId = configId;
            }

            public int getEventStage() {
                return eventStage;
            }

            public void setEventStage(int eventStage) {
                this.eventStage = eventStage;
            }

            public int getAcceptCounts() {
                return acceptCounts;
            }

            public void setAcceptCounts(int acceptCounts) {
                this.acceptCounts = acceptCounts;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShareGiftCounts() {
                return shareGiftCounts;
            }

            public void setShareGiftCounts(int shareGiftCounts) {
                this.shareGiftCounts = shareGiftCounts;
            }

            public int getAcceptGiftCounts() {
                return acceptGiftCounts;
            }

            public void setAcceptGiftCounts(int acceptGiftCounts) {
                this.acceptGiftCounts = acceptGiftCounts;
            }
        }

        public static class AcceptersBean {
            /**
             * eventTarget : 0
             * shareCounts : 0
             * phone : null
             * configId : 0
             * eventStage : 0
             * acceptCounts : 0
             * id : 0
             * shareGiftCounts : 0
             * acceptGiftCounts : 0
             */

            private int eventTarget;
            private int shareCounts;
            private Object phone;
            private int configId;
            private int eventStage;
            private int acceptCounts;
            private int id;
            private int shareGiftCounts;
            private int acceptGiftCounts;

            public int getEventTarget() {
                return eventTarget;
            }

            public void setEventTarget(int eventTarget) {
                this.eventTarget = eventTarget;
            }

            public int getShareCounts() {
                return shareCounts;
            }

            public void setShareCounts(int shareCounts) {
                this.shareCounts = shareCounts;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public int getConfigId() {
                return configId;
            }

            public void setConfigId(int configId) {
                this.configId = configId;
            }

            public int getEventStage() {
                return eventStage;
            }

            public void setEventStage(int eventStage) {
                this.eventStage = eventStage;
            }

            public int getAcceptCounts() {
                return acceptCounts;
            }

            public void setAcceptCounts(int acceptCounts) {
                this.acceptCounts = acceptCounts;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShareGiftCounts() {
                return shareGiftCounts;
            }

            public void setShareGiftCounts(int shareGiftCounts) {
                this.shareGiftCounts = shareGiftCounts;
            }

            public int getAcceptGiftCounts() {
                return acceptGiftCounts;
            }

            public void setAcceptGiftCounts(int acceptGiftCounts) {
                this.acceptGiftCounts = acceptGiftCounts;
            }
        }
    }
}
