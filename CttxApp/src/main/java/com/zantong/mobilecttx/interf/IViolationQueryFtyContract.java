package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;

import java.io.File;

/**
 * 违章查询
 */

public interface IViolationQueryFtyContract {

    interface IViolationQueryFtyView extends IMvpView<IViolationQueryFtyPresenter> {
        /**
         * 显示loading框
         */
        void loadingProgress();

        /**
         * 隐藏loading框
         */
        void hideLoadingProgress();

        void uploadDrivingImgSucceed(DrivingOcrResult result);

        void uploadDrivingImgError(String s);

        CarInfoDTO getCarInfoDTO();

        void commitCarInfoToOldServerError(String message);

        BindCarDTO getBindCarDTO();

        void commitCarInfoToNewServerError(String message);

        void commitCarInfoToOldServerSucceed(Result responseBean);
    }

    interface IViolationQueryFtyPresenter extends IMvpPresenter {
        /**
         * 上传图片
         */
        void uploadDrivingImg();

        File getImageFile();

        void commitCarInfoToOldServer();

        void commitCarInfoToNewServer();

        String initCarInfoDTO();

        BindCarDTO initBindCarDTO();
    }

}
