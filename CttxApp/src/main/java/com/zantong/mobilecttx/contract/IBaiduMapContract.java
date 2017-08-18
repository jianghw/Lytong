package com.zantong.mobilecttx.contract;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
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
    }

    interface IBaiduMapPresenter extends IMvpPresenter {

        void annualInspectionList();

        AnnualDTO getAnnualDTO();

    }
}
