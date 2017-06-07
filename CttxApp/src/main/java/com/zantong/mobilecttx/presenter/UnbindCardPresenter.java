package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.model.UnbindCardModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.card.fragment.UnbindCardFragment;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class UnbindCardPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    UnbindCardFragment mUnbindCardFragment;
    UnbindCardModelImp mUnbindCardModelImp;
    public UnbindCardPresenter(UnbindCardFragment mUnbindCardFragment) {
        this.mUnbindCardFragment = mUnbindCardFragment;
        mUnbindCardModelImp = new UnbindCardModelImp();


    }


    @Override
    public void loadView(int index) {
        switch (index){
            case 1:
                break;
        }
//        mAddVehicleModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {

    }

    @Override
    public void onFailed() {

    }
}
