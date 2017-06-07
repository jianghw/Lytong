package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.model.SetPayCarModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.car.activity.SetPayCarActivity;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class SetPayCarPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    SetPayCarActivity mSetPayCarActivity;
    SetPayCarModelImp mSetPayCarModelImp;
    public SetPayCarPresenter(SetPayCarActivity mSetPayCarActivity) {
        this.mSetPayCarActivity = mSetPayCarActivity;
        mSetPayCarModelImp = new SetPayCarModelImp();


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
