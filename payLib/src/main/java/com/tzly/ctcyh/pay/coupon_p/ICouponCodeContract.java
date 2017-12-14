package com.tzly.ctcyh.pay.coupon_p;

import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface ICouponCodeContract {

    interface ICouponCodeView extends IBaseView<ICouponCodePresenter>, IResponseView {

        void deleteCodeError(String message);

        void deleteCodeSucceed(BaseResponse response, int position);
    }

    interface ICouponCodePresenter extends IBasePresenter {
        void getCodeList();

        void deleteCode(String codeId, int position);
    }

}
