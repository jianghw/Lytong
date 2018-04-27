package com.zantong.mobilecttx.home_p;

import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * 畅通页面
 */

public interface InformationContract {

    interface InformationView extends IBaseView<InformationPresenter> {

        void findByTypeSucceed(NewsInfoResponse result);

        void findByTypeError(String message);

        int getTypeItem();
    }

    interface InformationPresenter extends IBasePresenter {


        void findByType();
    }

}
