package com.zantong.mobile.contract;

import com.tzly.annual.base.bean.response.AnnouncementResult;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.home.bean.HomeResult;
import com.zantong.mobile.home.dto.HomeDataDTO;

/**
 * 畅通页面
 */

public interface IUnimpededFtyContract {

    interface IUnimpededFtyView extends IMvpView<IUnimpededFtyPresenter> {

        void announcementsError(String message);

        void announcementsSucceed(AnnouncementResult result);
    }

    interface IUnimpededFtyPresenter extends IMvpPresenter {
        /**
         * 首页接口请求
         */
        void homePage();

        HomeDataDTO initHomeDataDTO();

        void findAnnouncements();
    }

}
