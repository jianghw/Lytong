package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.model.CarPayCarModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.car.activity.CanPayCar;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class CanPayCarAPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    CanPayCar mCanPayCar;
    CarPayCarModelImp mCarPayCarModelImp;
    public CanPayCarAPresenter(CanPayCar mCanPayCar) {
        this.mCanPayCar = mCanPayCar;
        mCarPayCarModelImp = new CarPayCarModelImp();


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
