package com.zantong.mobile.card.bean;

import com.tzly.annual.base.bean.BaseResult;

/**
 * Created by zhoujie on 2017/2/14.
 */

public class YingXiaoResult extends BaseResult {

    private YingXiaoBean data;

    public YingXiaoBean getData() {
        return data;
    }

    public void setData(YingXiaoBean data) {
        this.data = data;
    }

    public class YingXiaoBean{
        private String empNum;

        public String getEmpNum() {
            return empNum;
        }

        public void setEmpNum(String empNum) {
            this.empNum = empNum;
        }
    }
}


