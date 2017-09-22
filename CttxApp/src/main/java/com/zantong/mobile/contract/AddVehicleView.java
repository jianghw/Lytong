package com.zantong.mobile.contract;

import com.zantong.mobile.weizhang.bean.AddVehicleBean;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface AddVehicleView {
    void showProgress();
    void updateView(AddVehicleBean mAddVehicleBean);
    void hideProgress();

}
