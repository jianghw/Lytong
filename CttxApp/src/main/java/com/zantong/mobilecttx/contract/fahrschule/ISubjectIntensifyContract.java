package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import cn.qqtheme.framework.bean.response.SubjectGoodsResponse;

/**
 * 畅通页面
 */

public interface ISubjectIntensifyContract {

    interface ISubjectIntensifyView extends IMvpView<ISubjectIntensifyPresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void getGoodsError(String message);

        void getGoodsSucceed(SubjectGoodsResponse result);
    }

    interface ISubjectIntensifyPresenter extends IMvpPresenter {

        void getGoods();
    }

}
