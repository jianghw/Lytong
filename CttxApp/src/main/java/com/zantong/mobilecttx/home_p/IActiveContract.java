package com.zantong.mobilecttx.home_p;


import com.tzly.ctcyh.java.response.active.ActiveConfigResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IActiveContract {

    interface IActiveView extends IBaseView<IActivePresenter> {

        String getChannel();

        void responseError(String message);

        String getResisterDate();

        void configError(String message);

        void configSucceed(ActiveConfigResponse response);
    }

    interface IActivePresenter extends IBasePresenter {

        void receiveCoupon(String couponId);

        void getConfig(String channel, String date);
    }

}
