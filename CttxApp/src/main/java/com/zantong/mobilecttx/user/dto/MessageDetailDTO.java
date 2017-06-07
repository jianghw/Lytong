package com.zantong.mobilecttx.user.dto;

import com.zantong.mobilecttx.base.dto.BaseDTO;

/**
 * 消息详情请求实体类
 */
public class MessageDetailDTO extends BaseDTO {

    private int id; //用户id

    @Override
    public void setUsrId(String usrId) {
        super.setUsrId(usrId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getUsrId() {
        return super.getUsrId();
    }
}
