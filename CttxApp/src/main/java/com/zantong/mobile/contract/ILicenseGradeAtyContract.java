package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.weizhang.bean.LicenseResponseBean;
import com.zantong.mobile.weizhang.dto.LicenseFileNumDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ILicenseGradeAtyContract {

    interface ILicenseGradeAtyView extends IMvpView<ILicenseGradeAtyPresenter> {

        void setLayoutVisibilityByRefresh(boolean isRefresh);

        /**
         * 请求响应成功
         *
         * @param result
         */
        void driverLicenseCheckGradeSucceed(LicenseResponseBean result);

        void driverLicenseCheckGradeError(String message);

        LicenseFileNumDTO initLicenseFileNumDTO();

        void onShowDefaultData();

        void showErrorCryingFace(boolean isCrying);
    }

    interface ILicenseGradeAtyPresenter extends IMvpPresenter {

        void driverLicenseCheckGrade();

        String initLicenseFileNumDTO();

        void saveLicenseFileNumDTO();
    }

}
