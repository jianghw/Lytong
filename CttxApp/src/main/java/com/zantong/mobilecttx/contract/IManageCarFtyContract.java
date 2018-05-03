package com.zantong.mobilecttx.contract;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.card.dto.BindCarDTO;

import java.util.List;

/**
 * 车辆管理
 */

public interface IManageCarFtyContract {

    interface IManageCarFtyView extends IBaseView<IManageCarFtyPresenter> {

        void addVehicleLicenseSucceed(List<VehicleLicenseBean> data);

        void addVehicleLicenseError(String msg);

        void allVehiclesError(String message);
    }

    interface IManageCarFtyPresenter extends IBasePresenter {
        /**
         * 获取所有车辆信息
         */
        void getTextNoticeInfo();

        void getRemoteCarInfo();

        String initUserCarsDTO();

        void getPayCars();

        String initHomeDataDTO();

        void getAllVehicles();

        void addOrUpdateVehicleLicense(List<BindCarDTO> result);

    }

}
