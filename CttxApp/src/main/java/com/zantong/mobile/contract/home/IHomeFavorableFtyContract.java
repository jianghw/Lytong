package com.zantong.mobile.contract.home;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.home.bean.BannerBean;
import com.zantong.mobile.home.bean.BannerResult;
import com.zantong.mobile.home.bean.ModuleResult;

/**
 * 畅通页面
 */

public interface IHomeFavorableFtyContract {

    interface IHomeFavorableFtyView extends IMvpView<IHomeFavorableFtyPresenter> {

        void getBannerSucceed(BannerBean bannerBean);

        void getBannerError(String responseDesc);

        void getRewardSucceed(BannerBean bannerBean);

        void moduleTreeError(String message);

        void moduleTreeSucceed(ModuleResult result);
    }

    interface IHomeFavorableFtyPresenter extends IMvpPresenter {

        void getBanner();

        void distributeByType(BannerResult result, final String type);

        void moduleTree();

    }

}
