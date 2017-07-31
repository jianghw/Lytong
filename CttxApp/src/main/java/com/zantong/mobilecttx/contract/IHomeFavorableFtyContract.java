package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannerResult;

/**
 * 畅通页面
 */

public interface IHomeFavorableFtyContract {

    interface IHomeFavorableFtyView extends IMvpView<IHomeFavorableFtyPresenter> {

        void getBannerSucceed(BannerBean bannerBean);

        void getBannerError(String responseDesc);

        void getRewardSucceed(BannerBean bannerBean);
    }

    interface IHomeFavorableFtyPresenter extends IMvpPresenter {

        void getBanner();

        void distributeByType(BannerResult result, final String type);
    }

}
