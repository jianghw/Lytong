package com.zantong.mobilecttx.daijia.bean;

import com.tzly.ctcyh.java.response.orc.DrivingOcrBean;
import com.zantong.mobilecttx.base.bean.BaseOcrResult;

/**
 * Created by zhengyingbing on 16/9/13.
 * 拍照扫描  行驶证返回实体类
 */
public class DrivingOcrResult extends BaseOcrResult {

    DrivingOcrBean content;

    public void setContent(DrivingOcrBean content) {
        this.content = content;
    }

    public DrivingOcrBean getContent() {
        return content;
    }

}
