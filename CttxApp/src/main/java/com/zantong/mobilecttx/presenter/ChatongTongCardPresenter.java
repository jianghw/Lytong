package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.model.ChangTongCardModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.card.activity.ChangTongCard;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class ChatongTongCardPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    ChangTongCard mChangTongCard;
    ChangTongCardModelImp mChangTongCardModelImp;
    public ChatongTongCardPresenter(ChangTongCard mChangTongCard) {
        this.mChangTongCard = mChangTongCard;
        mChangTongCardModelImp = new ChangTongCardModelImp();


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
