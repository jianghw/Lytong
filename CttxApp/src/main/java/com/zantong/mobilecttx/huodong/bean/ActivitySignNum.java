package com.zantong.mobilecttx.huodong.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhengyingbing on 17/2/23.
 */

public class ActivitySignNum extends BaseResponse {

    private ActivitySignNumBean data;

    public void setData(ActivitySignNumBean data) {
        this.data = data;
    }

    public ActivitySignNumBean getData() {
        return data;
    }

    public class ActivitySignNumBean{

        private String activityCount;//报名数量
        private String notPeccancy;//无违章人数

        public void setActivityCount(String activityCount) {
            this.activityCount = activityCount;
        }

        public String getActivityCount() {
            return activityCount;
        }

        public void setNotPeccancy(String notPeccancy) {
            this.notPeccancy = notPeccancy;
        }

        public String getNotPeccancy() {
            return notPeccancy;
        }
    }


}
