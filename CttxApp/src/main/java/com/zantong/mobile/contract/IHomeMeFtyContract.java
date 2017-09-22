package com.zantong.mobile.contract;

import com.zantong.mobile.base.dto.BaseDTO;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.home.bean.DriverCoachResult;
import com.zantong.mobile.order.bean.CouponFragmentResult;
import com.zantong.mobile.user.bean.MessageCountResult;

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
