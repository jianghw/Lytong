package com.zantong.mobilecttx.violation_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IViolationDetailsContract {

    interface IViolationDetailsView extends IBaseView<IViolationDetailsPresenter>, IResponseView {

        String getViolationNum();

        void responseCustomError(ViolationDetailsBean response);
    }

    interface IViolationDetailsPresenter extends IBasePresenter {
        public void violationDetails_v003();

        public String initV003DTO();
    }

}
