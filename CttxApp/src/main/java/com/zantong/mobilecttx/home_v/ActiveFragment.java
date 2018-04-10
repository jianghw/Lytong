package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.CarBeautyActivity;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

/**
 * Fragment 活动
 */
public class ActiveFragment extends Fragment {

    private static final String BANK_NAME = "bank_name";
    private static final String BANK_MOBILE = "bank_mobile";
    private static final String BANK_CERTNUM = "bank_certnum";
    private static final String BANK_CARDNAME = "bank_cardname";

    public static ActiveFragment newInstance() {
        ActiveFragment f = new ActiveFragment();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    /**
     * 当该fragment被添加到Activity时回调，该方法只会被调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 创建fragment时被回调。该方法只会调用一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 每次创建、绘制该fragment的View组件时回调该方法，fragment将会显示该方法的View组件
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 当fragment所在的Activity被创建完成后调用该方法。
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 销毁该fragment所包含的View组件时调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 销毁fragment时被回调，该方法只会被调用一次。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当该fragment从Activity中被删除、被替换完成时回调该方法，
     * onDestory()方法后一定会回调onDetach()方法。该方法只会被调用一次。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Context getContext() {
        if (super.getContext() != null && super.getContext().getApplicationContext() != null) {
            return super.getContext().getApplicationContext();
        }
        return super.getContext();
    }

}