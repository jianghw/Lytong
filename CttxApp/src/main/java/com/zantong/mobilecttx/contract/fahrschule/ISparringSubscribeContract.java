package com.zantong.mobilecttx.contract.fahrschule;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResult;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.SubjectGoodsResult;

/**
 * 驾校陪练订单页面
 */

public interface ISparringSubscribeContract {

    interface ISparringSubscribeView extends IMvpView<ISparringSubscribePresenter> {

        void showLoadingDialog();

        void dismissLoadingDialog();

        void serviceAreaError(String message);

        void serviceAreaSucceed(SparringAreaResult result);

        void getGoodsError(String message);

        void getGoodsSucceed(SubjectGoodsResult result);

        void goodsSucceed(SparringGoodsResult result);

        void goodsError(String s);
    }

    interface ISparringSubscribePresenter extends IMvpPresenter {

        void getServiceArea();

        void getGoods();

    }

}
