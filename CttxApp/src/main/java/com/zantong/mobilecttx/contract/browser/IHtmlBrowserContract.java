package com.zantong.mobilecttx.contract.browser;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

import java.io.File;

/**
 * Html页面
 */

public interface IHtmlBrowserContract {

    interface IHtmlBrowserView extends IBaseView<IHtmlBrowserPresenter> {

        void uploadDrivingImgError(String message);

        void uploadDrivingImgSucceed(DrivingOcrResult result);

        void getBankPayHtmlError(String message);

        void getBankPayHtmlSucceed(PayOrderResponse result, String orderId);
    }

    interface IHtmlBrowserPresenter extends IBasePresenter {
        File getImageFile();

        void uploadDrivingImg();

        void getBankPayHtml(String coupon, String orderId, String s);
    }

}
