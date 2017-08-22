package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.map.bean.GasStationDetailResult;
import com.zantong.mobilecttx.map.bean.GasStationResult;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResult;
import com.zantong.mobilecttx.map.bean.YearCheckResult;
import com.zantong.mobilecttx.map.dto.AnnualDTO;

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
