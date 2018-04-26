package com.zantong.mobilecttx.home_p;


import com.tzly.ctcyh.java.response.news.IconsResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:资讯
 * Update by:
 * Update day:
 */

public interface IHomeInfomationContract {

    interface IHomeInfomationView extends IBaseView<IHomeInfomationPresenter>, IResponseView {


        void iconsError(String message);

        void iconsSucceed(IconsResponse result);
    }

    interface IHomeInfomationPresenter extends IBasePresenter {

        void getIcons();

        void getNavigations();
    }

}
