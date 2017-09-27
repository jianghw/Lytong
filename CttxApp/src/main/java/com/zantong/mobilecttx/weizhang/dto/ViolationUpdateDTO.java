package com.zantong.mobilecttx.weizhang.dto;

import com.zantong.mobilecttx.weizhang.bean.ViolationNum;

import java.util.List;

/**
 * Created by zhoujie on 2017/2/15.
 */

public class ViolationUpdateDTO {

    private List<ViolationNum> jsonStr;

    public List<ViolationNum> getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(List<ViolationNum> jsonStr) {
        this.jsonStr = jsonStr;
    }
}
