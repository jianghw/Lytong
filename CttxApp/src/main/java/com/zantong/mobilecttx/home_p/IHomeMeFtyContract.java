package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.order.bean.CouponFragmentResponse;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;

/**
 * 畅通页面
 */

public interface IHomeMeFtyContract {

    interface IHomeMeFtyView extends IBaseView<IHomeMeFtyPresenter> {

        void getCouponCountSucceed(CouponFragmentResponse result);

        void getCouponCountError(String responseDesc);

        void countMessageDetailSucceed(MessageCountResponse result);

        void countMessageDetailError(String responseDesc);

        void driverCoachError(String message);

        void driverCoachSucceed(DriverCoachResponse result);
    }

    interface IHomeMeFtyPresenter extends IBasePresenter {

        void getCouponCount();

        void getUnReadMsgCount();

        BaseDTO initBaseDTO();

        String initUserPhone();

        void getDriverCoach();
    }

}
