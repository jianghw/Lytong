package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;

/**
 * 地图页面
 */

public interface IBaiduMapContract {

    interface IBaiduMapView extends IMvpView<IBaiduMapPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        AnnualDTO getAnnualDTO();

        void annualInspectionListSucceed(YearCheckResponse result);

        void annualInspectionListError(String s);

        void annualInspectionSucceed(YearCheckDetailResponse result);

        void annualInspectionError(String message);

        void gasStationError(String message);

        void gasStationSucceed(GasStationDetailResponse result);

        void gasStationListError(String message);

        void gasStationListSucceed(GasStationResponse result);
    }

    interface IBaiduMapPresenter extends IMvpPresenter {

        void annualInspectionList();

        AnnualDTO getAnnualDTO();

        void annualInspection(int id);

        void gasStation(int id);

        void gasStationList();

    }
}
