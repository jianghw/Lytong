package com.zantong.mobilecttx.share_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.fahrschule.bean.RecordCountResponse;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountResponse;

/**
 * 驾校报名分享页面
 */

public interface IFahrschuleShareFtyContract {

    interface IFahrschuleShareFtyView extends IBaseView<IFahrschuleShareFtyPresenter> {
        String getType();

        void recordCountError(String message);

        void recordCountSucceed(Object result);
    }

    interface IFahrschuleShareFtyPresenter extends IBasePresenter {

        void getRecordCount();

        void getStatisticsCount();

        String getType();

        String getPhone();
    }

}
