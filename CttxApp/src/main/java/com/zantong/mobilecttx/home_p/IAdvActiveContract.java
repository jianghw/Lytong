package com.zantong.mobilecttx.home_p;


import com.tzly.ctcyh.java.response.violation.AdvModuleResponse;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:广告
 */

public interface IAdvActiveContract {

    interface IAdvActiveView extends IBaseView<IAdvActivePresenter> {
        void validAdvertError(String message);

        void validAdvertSucceed(ValidAdvResponse result);

        void moduleListError(String message);

        void moduleListSucceed(AdvModuleResponse result);
    }

    interface IAdvActivePresenter extends IBasePresenter {
        void findIsValidAdvert();

        void moduleList();
    }

}
