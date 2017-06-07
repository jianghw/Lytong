package com.zantong.mobilecttx.daijia.bean;

import com.zantong.mobilecttx.base.bean.BaseOcrResult;

/**
 * Created by zhengyingbing on 16/9/13.
 * 拍照扫描  駕駛證返回实体类
 */
public class DriverOcrResult extends BaseOcrResult {

    DriverOcrBean content;


    public void setContent(DriverOcrBean content) {
        this.content = content;
    }

    public DriverOcrBean getContent() {
        return content;
    }
}
