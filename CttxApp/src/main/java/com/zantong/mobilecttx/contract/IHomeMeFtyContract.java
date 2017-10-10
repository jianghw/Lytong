package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.order.bean.CouponFragmentResponse;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;

/**
 * 畅通页面
 */

public interface IHomeMeFtyContract {

    interface IHomeMeFtyView extends IMvpView<IHomeMeFtyPresenter> {

        void getCouponCountSucceed(CouponFragmentResponse result);

        void getCouponCountError(String responseDesc);

        void countMessageDetailSucceed(MessageCountResponse result);

        void countMessageDetailError(String responseDesc);

        void driverCoachError(String message);

        void driverCoachSucceed(DriverCoachResponse result);
    }

    interface IHomeMeFtyPresenter extends IMvpPresenter {

        void getCouponCount();

        void getUnReadMsgCount();

        BaseDTO initBaseDTO();

        String initUserPhone();

        void getDriverCoach();
    }

}
