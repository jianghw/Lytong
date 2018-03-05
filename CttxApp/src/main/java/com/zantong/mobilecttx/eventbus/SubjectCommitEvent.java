package com.zantong.mobilecttx.eventbus;

import com.tzly.ctcyh.java.response.SubjectGoodsBean;
import com.tzly.ctcyh.java.response.SubjectGoodsData;

public class SubjectCommitEvent {

    private final SubjectGoodsBean mSubjectGoodsBean;
    private final SubjectGoodsData.RemarkBean mRemarkBean;

    public SubjectCommitEvent(SubjectGoodsBean bean, SubjectGoodsData.RemarkBean remarkBean) {
        mSubjectGoodsBean = bean;
        mRemarkBean = remarkBean;
    }

    public SubjectGoodsBean getSubjectGoodsBean() {
        return mSubjectGoodsBean;
    }

    public SubjectGoodsData.RemarkBean getRemarkBean() {
        return mRemarkBean;
    }
}
