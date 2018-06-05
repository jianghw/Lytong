package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.java.response.card.CancelCardResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.base.bean.ValidCountResponse;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;

/**
 * 畅通页面
 */

public interface IHomeMeFtyContract {

    interface IHomeMeFtyView extends IBaseView<IHomeMeFtyPresenter> {

        void countMessageDetailSucceed(MessageCountResponse result);

        void countMessageDetailError(String responseDesc);

        void driverCoachError(String message);

        void driverCoachSucceed(DriverCoachResponse result);

        void validCountError(String message);

        void validCountSucceed(ValidCountResponse result);

        void cancelCardSucceed(CancelCardResponse result);

        void cancelCardError(String responseDesc);
    }

    interface IHomeMeFtyPresenter extends IBasePresenter {

        void getCouponCount();

        void getUnReadMsgCount();

        BaseDTO initBaseDTO();

        String initUserPhone();

        void getDriverCoach();

        void getValidCount();

        void cancelCard();

    }

}
