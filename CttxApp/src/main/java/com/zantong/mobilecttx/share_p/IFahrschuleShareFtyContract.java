package com.zantong.mobilecttx.share_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;

/**
 * 驾校报名分享页面
 */

public interface IFahrschuleShareFtyContract {

    interface IFahrschuleShareFtyView extends IBaseView<IFahrschuleShareFtyPresenter> {
        String getType();

        void getRecordCountError(String message);

        void getRecordCountSucceed(RecordCountResponse result);
    }

    interface IFahrschuleShareFtyPresenter extends IBasePresenter {

        void getRecordCount();

        String getType();

        String getPhone();
    }

}
