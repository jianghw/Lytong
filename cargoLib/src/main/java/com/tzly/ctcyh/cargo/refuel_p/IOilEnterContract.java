package com.tzly.ctcyh.cargo.refuel_p;

import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IOilEnterContract {

    interface IOilEnterView extends IBaseView<IOilEnterPresenter> {

        void countError(String message);

        void countSucceed(OilEnterResponse response);
    }

    interface IOilEnterPresenter extends IBasePresenter {
        void getCounts();
    }

}
