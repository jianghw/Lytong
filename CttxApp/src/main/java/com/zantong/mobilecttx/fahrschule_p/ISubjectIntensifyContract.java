package com.zantong.mobilecttx.fahrschule_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

import com.tzly.ctcyh.router.bean.response.SubjectGoodsResponse;

/**
 * 畅通页面
 */

public interface ISubjectIntensifyContract {

    interface ISubjectIntensifyView extends IBaseView<ISubjectIntensifyPresenter> {

        void getGoodsError(String message);

        void getGoodsSucceed(SubjectGoodsResponse result);
    }

    interface ISubjectIntensifyPresenter extends IBasePresenter {

        void getGoods();
    }

}
