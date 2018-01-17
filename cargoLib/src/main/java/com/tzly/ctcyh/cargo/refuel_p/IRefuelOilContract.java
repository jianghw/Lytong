package com.tzly.ctcyh.cargo.refuel_p;

import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IRefuelOilContract {

    interface IRefuelOilView extends IBaseView<IRefuelOilPresenter>, IResponseView {

        RefuelOilBean.CardInfoBean getCardInfo();

        String getOilCard();

        void createOrderError(String message);

        void createOrderSucceed(RefuelOrderResponse response);
    }

    interface IRefuelOilPresenter extends IBasePresenter {
        void getGoods();

        void createOrder();

        void findAndSaveCards();
    }

}
