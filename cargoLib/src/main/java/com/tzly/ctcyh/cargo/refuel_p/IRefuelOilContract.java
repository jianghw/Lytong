package com.tzly.ctcyh.cargo.refuel_p;

import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.SINOPECBean;
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

        SINOPECBean getCardInfo();

        String getStrEditOil();

        void createOrderError(String message);

        void createOrderSucceed(RefuelOrderResponse response);

        String getBusinessType();

        void shareModuleInfoError(String message);

        void shareModuleInfoSucceed(OilShareModuleResponse response);
    }

    interface IRefuelOilPresenter extends IBasePresenter {
        void getGoods();

        void createOrder();

        void findAndSaveCards();

        void findOilCards();

        void findCaiNiaoCard();

        void findOilCardsAll();

        void getRemainder();

        void shareModuleInfo();

    }

}
