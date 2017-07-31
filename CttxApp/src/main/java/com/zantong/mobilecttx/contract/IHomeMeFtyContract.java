package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.DriverCoachResult;
import com.zantong.mobilecttx.order.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.MessageCountResult;

/**
 * 畅通页面
 */

public interface IHomeMeFtyContract {

    interface IHomeMeFtyView extends IMvpView<IHomeMeFtyPresenter> {

        void getCouponCountSucceed(CouponFragmentResult result);

        void getCouponCountError(String responseDesc);

        void countMessageDetailSucceed(MessageCountResult result);

        void countMessageDetailError(String responseDesc);

        void driverCoachError(String message);

        void driverCoachSucceed(DriverCoachResult result);
    }

    interface IHomeMeFtyPresenter extends IMvpPresenter {

        void getCouponCount();

        void getUnReadMsgCount();

        BaseDTO initBaseDTO();

        String initUserPhone();

        void getDriverCoach();
    }

}
