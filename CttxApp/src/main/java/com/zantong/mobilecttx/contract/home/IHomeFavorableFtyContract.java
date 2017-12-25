package com.zantong.mobilecttx.contract.home;

import com.tzly.ctcyh.router.base.IResponseView;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannerResponse;

/**
 * 畅通页面
 */

public interface IHomeFavorableFtyContract {

    interface IHomeFavorableFtyView extends IMvpView<IHomeFavorableFtyPresenter>,IResponseView {

        void getBannerSucceed(BannerBean bannerBean);

        void getBannerError(String responseDesc);

        void getRewardSucceed(BannerBean bannerBean);
    }

    interface IHomeFavorableFtyPresenter extends IMvpPresenter {

        void getBanner();

        void distributeByType(BannerResponse result, final String type);

        void moduleTree();

        void saveStatisticsCount(String contentId);

    }

}
