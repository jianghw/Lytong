package com.zantong.mobilecttx.contract.browser;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.weizhang.bean.ViolationNum;

import java.util.List;

import com.tzly.ctcyh.router.bean.BaseResponse;

/**
 * 支付页面
 */

public interface IPayHtmlContract {

    interface IPayHtmlView extends IMvpView<IPayHtmlPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        String getViolationNum();

        void numberedQueryError(String s);

        void updateStateError(String s);

        void updateStateSucceed(BaseResponse result);
    }

    interface IPayHtmlPresenter extends IMvpPresenter {
        void updateState(List<ViolationNum> violationUpdateDTO);

        void numberedQuery();
    }

}
