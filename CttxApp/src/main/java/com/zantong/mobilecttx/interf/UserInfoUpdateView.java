package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IBaseView;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public interface UserInfoUpdateView extends IBaseView{

    String getOrderType();
    void showProgress();
    void updateView(Object object, int index);
    void hideProgress();

}
