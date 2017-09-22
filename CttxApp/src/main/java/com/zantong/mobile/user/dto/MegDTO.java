package com.zantong.mobile.user.dto;

import com.zantong.mobile.base.dto.BaseDTO;

/**
 * 消息列表请求实体类
 * @author Sandy
 * create at 16/6/8 下午11:48
 */
public class MegDTO extends BaseDTO {

    private String id; //用户id

    @Override
    public void setUsrId(String usrId) {
        super.setUsrId(usrId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsrId() {
        return super.getUsrId();
    }
}
