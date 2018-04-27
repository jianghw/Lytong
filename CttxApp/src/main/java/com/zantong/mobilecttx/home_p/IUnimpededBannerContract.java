package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

public interface IUnimpededBannerContract {

    interface IUnimpededBannerView extends IBaseView<IUnimpededBannerPresenter> {

    }

    interface IUnimpededBannerPresenter extends IBasePresenter {
        void saveStatisticsCount(String contentId);
    }

}
