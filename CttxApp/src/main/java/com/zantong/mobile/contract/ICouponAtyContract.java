package com.zantong.mobile.contract;

import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.order.bean.CouponFragmentBean;
import com.zantong.mobile.order.bean.CouponFragmentResult;
import com.zantong.mobile.order.bean.MessageResult;

import java.util.List;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ICouponAtyContract {

    interface ICouponAtyView extends IMvpView<ICouponAtyPresenter> {

        /**
         * 请求响应成功
         */
        void usrCouponInfoSucceed(CouponFragmentResult result);

        void usrCouponInfoError(String message);

        void delUsrCouponSucceed(MessageResult result, int position);

        void delUsrCouponError(String message);

        String getCouponStatus();

        void setListDataResult(List<CouponFragmentBean> o);

        void showLoadingDialog();

        void dismissLoadingDialog();
    }

    interface ICouponAtyPresenter extends IMvpPresenter {

        void usrCouponInfo();

        /**
         * 是否有效数据处理
         *
         * @param megList
         */
        void processingDataFiltrate(List<CouponFragmentBean> megList);

        /**
         * 2.4.27删除用户优惠券
         *
         * @param meg
         * @param position
         */
        void delUsrCoupon(CouponFragmentBean meg, int position);
    }

}
