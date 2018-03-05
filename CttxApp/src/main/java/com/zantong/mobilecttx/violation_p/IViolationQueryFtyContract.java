package com.zantong.mobilecttx.violation_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;

import java.io.File;

import com.tzly.ctcyh.java.response.BankResponse;
import com.tzly.ctcyh.java.response.BaseResponse;

/**
 * 违章查询
 */

public interface IViolationQueryFtyContract {

    interface IViolationQueryFtyView extends IBaseView<IViolationQueryFtyPresenter> {

        void uploadDrivingImgSucceed(DrivingOcrResult result);

        void uploadDrivingImgError(String s);

        CarInfoDTO getCarInfoDTO();

        void commitCarInfoToOldServerError(String message);

        BindCarDTO getBindCarDTO();

        void commitCarInfoToNewServerError(String message);

        void commitCarInfoToOldServerSucceed(BankResponse responseBean);

        void removeVehicleLicenseSucceed(BaseResponse responseBean);

        void removeVehicleLicenseError(String message);
    }

    interface IViolationQueryFtyPresenter extends IBasePresenter {
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
