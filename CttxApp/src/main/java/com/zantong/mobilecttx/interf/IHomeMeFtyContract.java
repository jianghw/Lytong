package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;

/**
 * 畅通页面
 */

public interface IHomeMeFtyContract {

    interface IHomeMeFtyView extends IMvpView<IHomeMeFtyPresenter> {

    }

    interface IHomeMeFtyPresenter extends IMvpPresenter {

        void getCouponCount();

        void getUnReadMsgCount();
    }

}
