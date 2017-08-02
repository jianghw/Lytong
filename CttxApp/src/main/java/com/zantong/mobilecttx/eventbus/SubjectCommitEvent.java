package com.zantong.mobilecttx.eventbus;

import com.zantong.mobilecttx.fahrschule.bean.SubjectGoodsBean;

public class SubjectCommitEvent {

    private final SubjectGoodsBean mSubjectGoodsBean;

    public SubjectCommitEvent(SubjectGoodsBean bean) {
        mSubjectGoodsBean = bean;
    }

    public SubjectGoodsBean getSubjectGoodsBean() {
        return mSubjectGoodsBean;
    }
}
