package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.home.bean.HomeCarResponse;

import java.util.List;

/**
 * 车辆管理
 */

public interface IManageCarFtyContract {

    interface IManageCarFtyView extends IMvpView<IManageCarFtyPresenter> {

        /**
         * 获取车俩
         */
        void textNoticeInfoError(String message);

        void textNoticeInfoSucceed(HomeCarResponse result);

        void addVehicleLicenseError(String message);

        void addVehicleLicenseSucceed(List<VehicleLicenseBean> data);

        /**
         * 显示loading框
         */
        void showLoadingDialog();

        /**
         * 隐藏loading框
         */
        void dismissLoadingDialog();

        void allVehiclesError(String message);
    }

    interface IManageCarFtyPresenter extends IMvpPresenter {
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