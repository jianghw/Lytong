package com.tzly.ctcyh.router.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;

/**
 * Fragment 下拉刷新基类
 */
public abstract class JxBaseFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    /**
     * 页面控制方法
     */
    protected void showActivityContent() {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity).showContentView();
        else
            toastShort("current fragment" + this.getClass().getSimpleName() + "is not extends JxBaseActivity");
    }

    protected void showActivityEmpty() {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity).showEmptyView();
        else
            toastShort("current fragment" + this.getClass().getSimpleName() + "is not extends JxBaseActivity");
    }

    protected void showActivityError() {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity).showErrorView();
        else
            toastShort("current fragment" + this.getClass().getSimpleName() + "is not extends JxBaseActivity");
    }

    protected void showActivityNetError() {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity).showNetErrorView();
        else
            toastShort("current fragment" + this.getClass().getSimpleName() + "is not extends JxBaseActivity");
    }

    /**
     * 加载提示框
     */
    public void showLoading() {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity).showLoading();
    }

    public void dismissLoading() {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity).dismissLoading();
    }

    /**
     * 统一封装
     */
    public void toastShore(String message) {
        FragmentActivity activity = getActivity();
        if (activity instanceof JxBaseActivity)
            ((JxBaseActivity) activity). toastShort(message);
    }

}
