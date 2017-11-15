package com.zantong.mobilecttx.card.bean;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * Created by zhoujie on 2017/2/14.
 */

public class YingXiaoResponse extends BaseResponse {

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
