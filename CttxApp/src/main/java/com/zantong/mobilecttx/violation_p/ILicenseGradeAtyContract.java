package com.zantong.mobilecttx.violation_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.weizhang.bean.LicenseResponseBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ILicenseGradeAtyContract {

    interface ILicenseGradeAtyView extends IBaseView<ILicenseGradeAtyPresenter> {

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

    interface ILicenseGradeAtyPresenter extends IBasePresenter {

        void driverLicenseCheckGrade();

        String initLicenseFileNumDTO();

        void saveLicenseFileNumDTO();
    }

}
