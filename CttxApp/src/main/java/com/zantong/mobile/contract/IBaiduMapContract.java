package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.map.bean.GasStationDetailResult;
import com.zantong.mobile.map.bean.GasStationResult;
import com.zantong.mobile.map.bean.YearCheckDetailResult;
import com.zantong.mobile.map.bean.YearCheckResult;
import com.zantong.mobile.map.dto.AnnualDTO;

/**
 * 地图页面
 */

public interface IBaiduMapContract {

    interface IBaiduMapView extends IMvpView<IBaiduMapPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        AnnualDTO getAnnualDTO();

        void annualInspectionListSucceed(YearCheckResult result);

        void annualInspectionListError(String s);

        void annualInspectionSucceed(YearCheckDetailResult result);

        void annualInspectionError(String message);

        void gasStationError(String message);

        void gasStationSucceed(GasStationDetailResult result);

        void gasStationListError(String message);

        void gasStationListSucceed(GasStationResult result);
    }

    interface IBaiduMapPresenter extends IMvpPresenter {

        void annualInspectionList();

        AnnualDTO getAnnualDTO();

        void annualInspection(int id);

        void gasStation(int id);

        void gasStationList();

    }
}
