package com.zantong.mobile.contract.browser;

import com.tzly.annual.base.bean.BaseResult;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.weizhang.bean.ViolationNum;

import java.util.List;

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

        void updateStateSucceed(BaseResult result);
    }

    interface IPayHtmlPresenter extends IMvpPresenter {
        void updateState(List<ViolationNum> violationUpdateDTO);

        void numberedQuery();
    }

}
