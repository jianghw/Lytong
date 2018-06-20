package com.tzly.ctcyh.router.custom.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luozm.captcha.Captcha;
import com.tzly.ctcyh.router.R;


/**
 * Created by jianghw on 2017/12/6.
 * Description: 滑动选择区
 * Update by:
 * Update day:
 */

public class CaptchaDialogFragment extends DialogFragment {

    private static final String STATUS_NAME = "STATUS_NAME";
    private IOnCouponSubmitListener submitListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 设置主题需要在 onCreate() 方法中调用 setStyle() 方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      /*  Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }*/

        View view = inflater.inflate(R.layout.custom_dialog_captcha, container, true);

        Captcha captcha = (Captcha) view.findViewById(R.id.captCha);
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public void onAccess(long time) {
                if (submitListener != null) submitListener.submit(null);
                dismiss();
            }

            @Override
            public void onFailed() {
                if (submitListener != null) submitListener.cancel();
                dismiss();
            }
        });

        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static CaptchaDialogFragment newInstance() {
        CaptchaDialogFragment fragment = new CaptchaDialogFragment();
        Bundle bundle = new Bundle();
//        bundle.putString(STATUS_NAME, couponName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public void setClickListener(IOnCouponSubmitListener iOnDateSetListener) {
        submitListener = iOnDateSetListener;
    }
}
