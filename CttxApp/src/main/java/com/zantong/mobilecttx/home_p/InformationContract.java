package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * 畅通页面
 */

public interface InformationContract {

    interface InformationView extends IBaseView<InformationPresenter>,IResponseView {

        int getTypeItem();
    }

    interface InformationPresenter extends IBasePresenter {


        void findByType();
    }

}
