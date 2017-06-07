package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.model.ManageVehiclesModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.car.activity.CarManageActivity;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class ManageVehiclesPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    CarManageActivity mCarManageActivity;
    ManageVehiclesModelImp mManageVehiclesModelImp;
    public ManageVehiclesPresenter(CarManageActivity mCarManageActivity) {
        this.mCarManageActivity = mCarManageActivity;
        mManageVehiclesModelImp = new ManageVehiclesModelImp();


    }


    @Override
    public void loadView(int index) {

    }

    @Override
    public void onSuccess(Object clazz, int index) {

    }

    @Override
    public void onFailed() {

    }
}
