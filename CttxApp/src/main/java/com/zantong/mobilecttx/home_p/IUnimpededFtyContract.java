package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.base.bean.UnimpededBannerResponse;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.user.bean.UserCarsResult;

/**
 * 畅通页面
 */

public interface IUnimpededFtyContract {

    interface IUnimpededFtyView extends IBaseView<IUnimpededFtyPresenter> {

        void homePageSucceed(HomeResponse result);

        void remoteCarInfoError(String message);

        void getTextNoticeInfo(HomeCarResponse result);

        void dataError(String message);

        void indexLayerSucceed(IndexLayerResponse result);

        void countDownTextView(long l);

        void countDownCompleted();

        void bannerSucceed(UnimpededBannerResponse result);
    }

    interface IUnimpededFtyPresenter extends IBasePresenter {
        /**
         * 首页接口请求
         */
        void homePage();

        HomeDataDTO initHomeDataDTO();

        void getTextNoticeInfo();

        void getIndexLayer();

        void startCountDown();

        void saveStatisticsCount(String contentId);

        void getBanner();

    }

}
