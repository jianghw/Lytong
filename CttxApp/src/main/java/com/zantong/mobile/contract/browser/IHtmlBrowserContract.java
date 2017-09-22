package com.zantong.mobile.contract.browser;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.daijia.bean.DrivingOcrResult;
import com.zantong.mobile.weizhang.bean.PayOrderResult;

import java.io.File;

/**
 * Html页面
 */

public interface IHtmlBrowserContract {

    interface IHtmlBrowserView extends IMvpView<IHtmlBrowserPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void uploadDrivingImgError(String message);

        void uploadDrivingImgSucceed(DrivingOcrResult result);

        void getBankPayHtmlError(String message);

        void getBankPayHtmlSucceed(PayOrderResult result, String orderId);
    }

    interface IHtmlBrowserPresenter extends IMvpPresenter {
        File getImageFile();

        void uploadDrivingImg();

        void getBankPayHtml(String orderId, String s);
    }

}
