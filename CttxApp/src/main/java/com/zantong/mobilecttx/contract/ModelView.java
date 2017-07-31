package com.zantong.mobilecttx.contract;

/**
 * Created by Administrator on 2016/4/22.
 */
public interface ModelView {

    void showProgress();
    void updateView(Object object, int index);
    void hideProgress();
}
