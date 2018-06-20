package com.tzly.ctcyh.cargo.refuel_p;

import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareInfoResponse;
import com.tzly.ctcyh.java.response.oil.OilShareModuleResponse;
import com.tzly.ctcyh.java.response.oil.OilShareResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 */

public interface IOilShareContract {

    interface IOilShareView extends IBaseView<IOilSharePresenter> {
        String getConfigId();

        void shareError(String message);

        void shareSucceed(OilShareResponse response);

        void shareInfoError(String message);

        void shareInfoSucceed(OilShareInfoResponse response);

        void accepterInfoError(String message);

        void accepterInfoSucceed(OilAccepterInfoResponse response);

        String getBusinessType();

        void shareModuleInfoError(String message);

        void shareModuleInfoSucceed(OilShareModuleResponse response);
    }

    interface IOilSharePresenter extends IBasePresenter {
        void shareModuleInfo();

        void getShareInfo();

        void shareInfo();

        void getAccepterInfoList(int position);
    }

}
