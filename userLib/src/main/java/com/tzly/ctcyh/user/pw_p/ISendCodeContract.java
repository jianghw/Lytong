package com.tzly.ctcyh.user.pw_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.response.VCodeResponse;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public interface ISendCodeContract {

    interface ISendCodeView extends IBaseView<ISendCodePresenter> {
        void codeBtnEnable(boolean b);

        String getPhone();

        void verificationCodeError(String message);

        void verificationCodeSucceed(VCodeResponse response);

        void countDownTextView(long l);

        String getCode();

        String getFlag();

        void v_p002_01Succeed(BankResponse response);

        void v_p002_01Error(String s);
    }

    interface ISendCodePresenter extends IBasePresenter {

        void sendVerificationCode();

        void startCountDown();

        void v_p002_01();
    }

}
