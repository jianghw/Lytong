package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.cargo.bean.PHPResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class ScoreResponse extends PHPResponse {

    /**
     * data : {"ljjf":"驾驶证已扣分数"}
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
         * ljjf : 驾驶证已扣分数
         */

        private String ljjf;

        public String getLjjf() {
            return ljjf;
        }

        public void setLjjf(String ljjf) {
            this.ljjf = ljjf;
        }
    }
}
