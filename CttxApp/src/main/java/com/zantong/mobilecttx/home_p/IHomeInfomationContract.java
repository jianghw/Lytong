package com.zantong.mobilecttx.home_p;


import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.base.bean.ModuleBannerResponse;

/**
 * Created by jianghw on 2017/10/12.
 * Description:资讯
 * Update by:
 * Update day:
 */

public interface IHomeInfomationContract {

    interface IHomeInfomationView extends IBaseView<IHomeInfomationPresenter>{


        void iconsError(String message);

        void iconsSucceed(ModuleBannerResponse result);

        void navigationError(String message);

        void navigationSucceed(ModuleBannerResponse result);
    }

    interface IHomeInfomationPresenter extends IBasePresenter {

        void getIcons();

        void getNavigations();
    }

}
