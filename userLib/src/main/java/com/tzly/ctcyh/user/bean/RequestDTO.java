package com.tzly.ctcyh.user.bean;

/**
 * 请求实体封装
 */
public class RequestDTO {

    Object ReqInfo;
    RequestHeadDTO SYS_HEAD;

    public Object getReqInfo() {
        return ReqInfo;
    }

    public void setReqInfo(Object reqInfo) {
        ReqInfo = reqInfo;
    }

    public RequestHeadDTO getSYS_HEAD() {
        return SYS_HEAD;
    }

    public void setSYS_HEAD(RequestHeadDTO SYS_HEAD) {
        this.SYS_HEAD = SYS_HEAD;
    }
}
