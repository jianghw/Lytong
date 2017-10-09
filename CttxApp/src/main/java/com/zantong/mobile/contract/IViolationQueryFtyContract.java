package com.zantong.mobile.contract;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.Result;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.car.dto.CarInfoDTO;
import com.zantong.mobile.card.dto.BindCarDTO;
import com.zantong.mobile.daijia.bean.DrivingOcrResult;

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

        void removeVehicleLicenseSucceed(BaseResult responseBean);

        void removeVehicleLicenseError(String message);
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

        void addVehicleLicense();

        void removeVehicleLicense();

        void updateVehicleLicense();
    }

}
