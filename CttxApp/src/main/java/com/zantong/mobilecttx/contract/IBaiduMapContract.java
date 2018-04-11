package com.zantong.mobilecttx.contract;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.map.bean.GasStation;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.GasStationResponse;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;

import java.util.List;

/**
 * 地图页面
 */

public interface IBaiduMapContract {

    interface IBaiduMapView extends IBaseView<IBaiduMapPresenter> {

        AnnualDTO getAnnualDTO();

        void annualInspectionListSucceed(YearCheckResponse result);

        void annualInspectionListError(String s);

        void annualInspectionSucceed(YearCheckDetailResponse result);

        void annualInspectionError(String message);

        void gasStationError(String message);

        void gasStationSucceed(GasStationDetailResponse result);

        void gasStationListError(String message);

        void gasStationListSucceed(List<GasStation> result);

        boolean isCheckNinetyFour();
    }

    interface IBaiduMapPresenter extends IBasePresenter {

        void annualInspectionList();

        AnnualDTO getAnnualDTO();

        void annualInspection(int id);

        void gasStation(int id);

        void gasStationList();

    }
}
