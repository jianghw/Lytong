package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.contract.IPayHistoryView;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class PayHistoryPresenter extends BasePresenter<IPayHistoryView>{

    public String getStartTm(){
        return mView.getStartTm();
    }
    public String getEndTm(){
        return mView.getEndTm();
    }

}
