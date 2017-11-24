package com.tzly.ctcyh.cargo.refuel_p;

import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

import java.util.List;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IRefuelOilContract {

    interface IRefuelOilView extends IBaseView<IRefuelOilPresenter>, IResponseView {
        void dataDistributionSucceed(List<RefuelOilBean> beanList);

        void dataDistributionError(Throwable throwable);

        String getRechargeMoney();

        String getGoodsId();

        String getOilCardNum();

        void createOrderError(String message);

        void createOrderSucceed(RefuelOrderResponse response);
    }

    interface IRefuelOilPresenter extends IBasePresenter {
        void getGoods();

        void dataDistribution(int oilType, List<RefuelOilBean> data);

        void createOrder();
    }

}
