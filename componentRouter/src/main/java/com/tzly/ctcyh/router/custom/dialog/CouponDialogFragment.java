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

public class CouponDialogFragment extends DialogFragment {

    private static final String STATUS_ID = "STATUS_ID";
    private static final String STATUS_NAME = "STATUS_NAME";
    private static final String STATUS_BUSINESS = "STATUS_BUSINESS";
    private static final String STATUS_TYPE = "STATUS_TYPE";
    private static final String STATUS_VALUE = "STATUS_VALUE";

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

        View view = inflater.inflate(R.layout.custom_dialog_coupon, container,true);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        TextView tvUnit = (TextView) view.findViewById(R.id.tv_unit);

        tvTitle.setText(getArguments().getString(STATUS_NAME));
        tvContent.setText(getArguments().getString(STATUS_BUSINESS));
        String type = getArguments().getString(STATUS_TYPE);
        String value =  getArguments().getString(STATUS_VALUE);

        if (type.equals("2")) {
            tvPrice.setText(new DecimalFormat("#0.#").format(Float.valueOf(value) / 10));
            tvUnit.setText("折");
        } else if (type.equals("3")) {
            tvPrice.setText(value);
            tvUnit.setText("元");
        } else if (type.equals("1")) {
            tvPrice.setText("兑换码");
            tvUnit.setVisibility(View.INVISIBLE);
        } else {
            tvPrice.setText("未知状态");
            tvUnit.setVisibility(View.INVISIBLE);
        }

        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (submitListener != null)
                    submitListener.submit(getArguments().getString(STATUS_ID));
                dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static CouponDialogFragment newInstance(String couponId, String couponName,
                                                   String couponBusiness,
                                                   String couponType, String couponValue) {
        CouponDialogFragment fragment = new CouponDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STATUS_ID, couponId);
        bundle.putString(STATUS_NAME, couponName);
        bundle.putString(STATUS_BUSINESS, couponBusiness);
        bundle.putString(STATUS_TYPE, couponType);
        bundle.putString(STATUS_VALUE, couponValue);
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
