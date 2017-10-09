package com.zantong.mobile.contract;

import com.tzly.annual.base.bean.BaseResult;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.tzly.annual.base.bean.response.OrderListBean;

import java.util.List;

/**
 * 订单页面
 */

public interface ICattleOrderContract {

    interface ICattleOrderView extends IMvpView<ICattleOrderPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void annualInspectionOrdersError(String message);

        void allStatusData(List<OrderListBean> data);

        void dataDistribution(String message, int position);

        void haveStatusData(List<OrderListBean> orderList);

        void auditStatusData(List<OrderListBean> orderList);

        void processStatusData(List<OrderListBean> orderList);

        void completedStatusData(List<OrderListBean> orderList);

        void annualOrderTargetStateError(String message);

        void annualOrderTargetStateSucceed(BaseResult result);
    }

    interface ICattleOrderPresenter extends IMvpPresenter {

        void getAnnualInspectionOrders();

        String initUserId();

        void annualOrderTargetState(String id, String orderId, String s);

        void addBackExpressInfo(String orderId, String trim);
    }
}
