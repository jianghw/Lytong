package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.StartPicResult;

/**
 * Created by jianghw on 2017/4/26.
 * Description: 启动页面p
 * Update by:
 * Update day:
 */

public interface ISplashAtyContract {

    interface ISplashAtyView extends IMvpView<ISplashAtyPresenter> {

        /**
         * 倒计时完成
         */
        void countDownOver();

        /**
         * 显示倒计时
         *
         * @param l
         */
        void countDownTextView(long l);

        /**
         * 显示广告页面
         *
         * @param result
         */
        void displayAdsImage(StartPicResult result);

        void displayGuideImage(StartPicResult result);

        void displayAdsImageError(String message);
    }

    interface ISplashAtyPresenter extends IMvpPresenter {
        /**
         * 登录数据读取
         */
        void readObjectLoginInfoBean();

        /**
         * 提交安盛服务器登录数据
         */
        String initLoginMessage();

        /**
         * 提交安盛服务器
         */
        void loadLoginPost();

        /**
         * app启动图片获取
         */
        void startGetPic();

        /**
         * 倒计时
         */
        void startCountDown();

        /**
         * app启动图片获取
         */
        void startGuidePic();
    }

}
