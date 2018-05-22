package com.tzly.ctcyh.cargo.refuel_p;

import android.graphics.Bitmap;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;

/**
 * Created by jianghw on 2017/10/12.
 */

public interface IShareWechatContract {

    interface IShareWechatView extends IBaseView<IShareWechatPresenter> {

        void mergeSucceed(Bitmap bitmap);
    }

    interface IShareWechatPresenter extends IBasePresenter {

        void mergeBitmap(Bitmap bitmap, String codeUrl, Bitmap logio);
    }

}
