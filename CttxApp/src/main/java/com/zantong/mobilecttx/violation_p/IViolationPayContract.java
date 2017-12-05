package com.zantong.mobilecttx.violation_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IViolationPayContract {

    interface IViolationPayView extends IBaseView<IViolationPayPresenter>, IResponseView {

    }

    interface IViolationPayPresenter extends IBasePresenter {
        public void getPayCars();

        public String initHomeDataDTO();
    }

}
