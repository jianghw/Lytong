package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.base.bean.UnimpededBannerResponse;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;
import com.zantong.mobilecttx.home.bean.HomeResponse;
import com.zantong.mobilecttx.home.bean.IndexLayerResponse;
import com.zantong.mobilecttx.home.dto.HomeDataDTO;
import com.zantong.mobilecttx.user.bean.UserCarsResult;

public interface IUnimpededBannerContract {

    interface IUnimpededBannerView extends IBaseView<IUnimpededBannerPresenter> {

    }

    interface IUnimpededBannerPresenter extends IBasePresenter {
        void saveStatisticsCount(String contentId);
    }

}
