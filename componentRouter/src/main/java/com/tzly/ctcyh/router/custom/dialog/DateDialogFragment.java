package com.tzly.ctcyh.router.custom.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by jianghw on 2017/12/6.
 * Description: 日期选择器
 * Update by:
 * Update day:
 */

public class DateDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private IOnDateSetListener mIOnDateSetListener;

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

        //        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog_Popup);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static DateDialogFragment newInstance() {
        return new DateDialogFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //       return super.onCreateDialog(savedInstanceState);

        //得到Calendar类实例，用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //因为实现了OnDateSetListener接口，所以第二个参数直接传入this
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * 选择回调
     * 1、日期判别 今天以前
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //        String date = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
        Calendar c = Calendar.getInstance();
        long today = c.getTime().getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        long date = calendar.getTime().getTime();
        boolean usable = date <= today;

        if (mIOnDateSetListener != null) {
            if (!usable) Toast.makeText(getContext(), "选择日期不可超过今日", Toast.LENGTH_SHORT).show();
            mIOnDateSetListener.onDateSet(view, year, month, dayOfMonth, usable);
        }
    }

    public void setClickListener(IOnDateSetListener iOnDateSetListener) {
        mIOnDateSetListener = iOnDateSetListener;
    }
}
