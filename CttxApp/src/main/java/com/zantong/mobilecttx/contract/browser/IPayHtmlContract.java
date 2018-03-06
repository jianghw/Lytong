package com.zantong.mobilecttx.contract.browser;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;

import java.util.List;

/**
 * 支付页面
 */

public interface IPayHtmlContract {

    interface IPayHtmlView extends IBaseView<IPayHtmlPresenter> {

        String getViolationNum();

        void numberedQueryError(String s);

        void updateStateError(String s);

        void updateStateSucceed(BaseResponse result);
    }

    interface IPayHtmlPresenter extends IBasePresenter {
        void updateState(List<ViolationNum> violationUpdateDTO);

        void numberedQuery();
    }

}
