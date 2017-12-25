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
import android.widget.TextView;

import com.tzly.ctcyh.router.R;

import java.text.DecimalFormat;


/**
 * Created by jianghw on 2017/12/6.
 * Description: 日期选择器
 * Update by:
 * Update day:
 */

public class MessageDialogFragment extends DialogFragment {

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

        View view = inflater.inflate(R.layout.custom_dialog_message, container, true);

        TextView tvContent = (TextView) view.findViewById(R.id.tv_msg);
        tvContent.setText(getArguments().getString(STATUS_NAME));


        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (submitListener != null)
                    submitListener.submit(getArguments().getString(STATUS_NAME));
                dismiss();
            }
        });

        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static MessageDialogFragment newInstance(String couponName) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STATUS_NAME, couponName);
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
