package com.zantong.mobile.share_p;

import com.tzly.annual.base.bean.response.StatistCountResult;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.fahrschule.bean.RecordCountResult;

/**
 * 驾校报名分享页面
 */

public interface IShareFtyContract {

    interface IShareFtyView extends IMvpView<IShareFtyPresenter> {

        String getPhone();

        void statisticsCountError(String message);

        void statisticsCountSucceed(StatistCountResult result);
    }

    interface IShareFtyPresenter extends IMvpPresenter {
        void getStatisticsCount();
    }

}
