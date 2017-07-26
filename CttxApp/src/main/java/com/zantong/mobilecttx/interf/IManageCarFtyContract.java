package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.HomeCarResult;

/**
 * 车辆管理
 */

public interface IManageCarFtyContract {

    interface IManageCarFtyView extends IMvpView<IManageCarFtyPresenter> {
        /**
         * 显示loading框
         */
        void loadingProgress();

        /**
         * 隐藏loading框
         */
        void hideLoadingProgress();

        /**
         * 获取车俩
         */
        void textNoticeInfoError(String message);

        void textNoticeInfoSucceed(HomeCarResult result);
    }

    interface IManageCarFtyPresenter extends IMvpPresenter {
        /**
         * 获取所有车辆信息
         */
        void getTextNoticeInfo();
    }

}
