package com.zantong.mobilecttx.contract.browser;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;

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
    }

    interface IHtmlBrowserPresenter extends IMvpPresenter {
        File getImageFile();

        void uploadDrivingImg();
    }

}
