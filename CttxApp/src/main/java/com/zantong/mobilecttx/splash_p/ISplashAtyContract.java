package com.zantong.mobilecttx.splash_p;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.home.bean.StartPicResponse;

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
         */
        void displayAdsImage(StartPicResponse result);

        void displayGuideImage(StartPicResponse result);

        void displayAdsImageError(String message);
    }

    interface ISplashAtyPresenter extends IMvpPresenter {


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
