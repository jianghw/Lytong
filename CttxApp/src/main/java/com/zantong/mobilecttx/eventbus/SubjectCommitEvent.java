package com.zantong.mobilecttx.eventbus;

import cn.qqtheme.framework.contract.bean.SubjectGoodsBean;
import cn.qqtheme.framework.contract.bean.SubjectGoodsData;

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
