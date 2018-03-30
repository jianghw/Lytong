package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;

/**
 * 7.获取用户指定活动的统计总数
 */
public class StatistCountResponse extends BaseResponse {


    /**
     * responseCode : 2000
     * data : {"flag":false,"list":[{"name":"分享数","count":6},{"name":"注册数","count":0},{"coupon":"加油50元优惠券","name":"年检数","count":0},{"coupon":"加油50元优惠券","name":"加油数","count":0}]}
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
         * flag : false
         * list : [{"name":"分享数","count":6},{"name":"注册数","count":0},{"coupon":"加油50元优惠券","name":"年检数","count":0},{"coupon":"加油50元优惠券","name":"加油数","count":0}]
         */

        private boolean flag;
        private List<ListBean> list;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : 分享数
             * count : 6
             * coupon : 加油50元优惠券
             */

            private String name;
            private int count;
            private String coupon;
            private int type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }

            public ListBean(String name, int count, String coupon, int type) {
                this.name = name;
                this.count = count;
                this.coupon = coupon;
                this.type = type;
            }
        }
    }
}
