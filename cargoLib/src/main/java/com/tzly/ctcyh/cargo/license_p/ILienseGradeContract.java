package com.tzly.ctcyh.cargo.license_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface ILienseGradeContract {

    interface ILienseGradeView extends IBaseView<ILienseGradePresenter>, IResponseView {

        String getmEditLicense();

        String getmEditSerial();

        String getmEditVerification();

        String getCookie();
    }

    interface ILienseGradePresenter extends IBasePresenter {
        public void scoresCaptcha();

        void apiScores();
    }

}
