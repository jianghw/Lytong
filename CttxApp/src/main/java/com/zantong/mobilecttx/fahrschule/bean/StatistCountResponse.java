package com.zantong.mobilecttx.fahrschule.bean;

import com.tzly.ctcyh.java.response.BaseResponse;

import java.util.List;
import java.util.Map;

/**
 * 7.获取用户指定活动的统计总数
 */
public class StatistCountResponse extends BaseResponse {

    /**
     * responseCode : 2000
     * data : {"flag":false,"list":[{"分享数":2},{"注册数":2},{"coupon":"加油50元优惠券","年检数":0},{"加油数":0,"coupon":"加油50元优惠券"}]}
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
         * list : [{"分享数":2},{"注册数":2},{"coupon":"加油50元优惠券","年检数":0},{"加油数":0,"coupon":"加油50元优惠券"}]
         */

        private boolean flag;
        private List<Map<String, String>> list;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public List<Map<String, String>> getList() {
            return list;
        }

        public void setList(List<Map<String, String>> list) {
            this.list = list;
        }
    }
}
