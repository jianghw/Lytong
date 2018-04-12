package com.tzly.ctcyh.cargo.refuel_p;

import com.tzly.ctcyh.cargo.bean.response.BidOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IBidOilContract {

    interface IBidOilView extends IBaseView<IBidOilPresenter>, IResponseView {

        BidOilBean getSubmitBean();

        String getStrEdtName();

        String getStrEditPhone();

        String getTvArea();

        String getEditDetailedAddress();

        void createOrderError(String message);

        void createOrderSucceed(RefuelOrderResponse response);

        void allAreasError(String message);

        void allAreasSucceed(Object[] objects);

        void isNeedCreate(OilRemainderResponse response);
    }

    interface IBidOilPresenter extends IBasePresenter {
        void handleOilCard();

        void createOrder();

        void getRemainder();

        void getAllAreas();
    }

}
