package com.zantong.mobilecttx.violation_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ILicenseGradeAtyContract {

    interface ILicenseGradeAtyView extends IBaseView<ILicenseGradeAtyPresenter>,IResponseView {

        void animationRefresh(boolean isRefresh);

        /**
         * 请求响应成功
         */
        void driverLicenseCheckGradeError(String message);

        LicenseFileNumDTO initLicenseFileNumDTO();

        void onShowDefaultData();

        void errorCryingFace(boolean isCrying);
    }

    interface ILicenseGradeAtyPresenter extends IBasePresenter {

        void driverLicenseCheckGrade();

        String initLicenseFileNumDTO();
    }

}
