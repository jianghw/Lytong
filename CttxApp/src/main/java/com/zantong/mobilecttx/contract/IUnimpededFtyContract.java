package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.HomeResult;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;

/**
 * 畅通页面
 */

public interface IUnimpededFtyContract {

    interface IUnimpededFtyView extends IMvpView<IUnimpededFtyPresenter> {
        /**
         * 显示loading框
         */
        void loadingProgress();

        /**
         * 隐藏loading框
         */
        void hideLoadingProgress();

        void homePageError(String message);

        void homePageSucceed(HomeResult result);

    }

    interface IUnimpededFtyPresenter extends IMvpPresenter {
        /**
         * 首页接口请求
         */
        void homePage();

        HomeDataDTO initHomeDataDTO();
    }

}
