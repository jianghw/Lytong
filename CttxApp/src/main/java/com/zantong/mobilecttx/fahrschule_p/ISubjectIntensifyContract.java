package com.zantong.mobilecttx.fahrschule_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

import cn.qqtheme.framework.bean.response.SubjectGoodsResponse;

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
