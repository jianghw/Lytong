package com.zantong.mobilecttx.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.base.activity.MvpBaseActivity;
import com.zantong.mobilecttx.eventbus.ErrorEvent;
import com.zantong.mobilecttx.interf.IBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * Fragment基类
 * @author Sandy
 * create at 16/6/2 下午2:14
 */
public abstract class BaseFragment extends Fragment implements OnClickListener, IBaseFragment {

    protected boolean isPrepared = false;

    /**
     * 加载中
     */
    public static final int LOADING = 1;
    /**
     * 加载失败
     */
    public static final int LOADING_FAILED = 2;
    /**
     * 加载成功
     */
    public static final int LOADING_SUCCESS = 3;
    /**
     * 加载成功,无数据
     */
    public static final int LOADING_SUCCESS_NULL = 4;
    
    /**
     * 加载中布局，加载失败布局，加载成功显示数据布局
     */
    private View mLoadingLayout, mLoadingFailedLayout, mBaseContent;

    /**
     * 加载失败后的刷新数据
     */
    private View mLoadingFailedRefresh;
    /**
     * 遮罩层显示加载中的Dialog
     */
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.base_loading_content, container, false);
        LinearLayout linearLayout = (LinearLayout) mView.findViewById(R.id.base_loading_content);
        if (rootView == null) {
            registerEventBus();
            rootView = inflater.inflate(getLayoutResId(), null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        linearLayout.addView(rootView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return mView;
    }

    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    protected void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        unRegisterEventBus();
        super.onDestroy();
    }

    /**
     * 显示加载中的Dialog
     */
    public void showDialogLoading() {
        showDialogLoading(null);
    }

    /**
     * 显示加载中遮罩层Dialog，带消息提示
     *
     * @param msg
     */
    public void showDialogLoading(String msg) {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).showDialogLoading(msg);
        } else if (activity != null && activity instanceof MvpBaseActivity) {
            ((MvpBaseActivity) activity).showDialogLoading(msg);
        }
    }

    /**
     * 隐藏遮罩层
     */
    public void hideDialogLoading() {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof BaseActivity) {
            ((BaseActivity) activity).hideDialogLoading();
        } else if (activity != null && activity instanceof MvpBaseActivity) {
            ((MvpBaseActivity) activity).hideDialogLoading();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(ErrorEvent event) {
        String status = event.getStatus();
        String message = event.getMsg();
        if (event.getContext().equals(getActivity())) {
//            showView(LOADING);
//            LogUtils.i("Fragment_error_status:" + event.getStatus());
//            LogUtils.i("Fragment_error_msg:" + event.getMsg());
            onErrorMsg(status, message);

        }
    }

    /**
     * 网络请求或请求数据失败的信息提示
     *
     * @param status
     * @param message
     */
    protected void onErrorMsg(String status, String message) {
//        if (status.equals(Config.ERROR_IO) || status.equals(Config.ERROR_NET)
//                || status.equals(Config.ERROR_PARSER)) {
//        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // 统计页面
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 布局资源文件
     *
     * @return
     */
    protected abstract int getLayoutResId();



    /**
     * 刷新数据
     */
    protected void onForceRefresh() {
    }

    /**
     * 判断显示的View
     *
     * @param index
     */
    private void showView(int index) {
        mLoadingLayout.setVisibility(index == LOADING ? View.VISIBLE : View.GONE);
        mLoadingFailedLayout.setVisibility(index == LOADING_FAILED ? View.VISIBLE : View.GONE);
        mBaseContent.setVisibility(index == LOADING_SUCCESS ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示加载失败布局
     */
    public void onShowFailed() {
        showView(LOADING_FAILED);
    }
    /**
     * 显示加载失败布局
     */
    public void onShowError() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    /**
     * 显示加载成功布局
     */
    public void onShowContent() {
        showView(LOADING_SUCCESS);
    }

    /**
     * 显示加载中布局
     */
    public void onShowLoading() {
        showView(LOADING);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mLoadingLayout = view.findViewById(R.id.base_loading_bg);
        mLoadingFailedLayout = view.findViewById(R.id.base_loading_failed_layout);
        mBaseContent = view.findViewById(R.id.base_loading_content);
        mLoadingFailedRefresh = (View) view.findViewById(R.id.base_loading_failed_refresh);
        mLoadingFailedRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onForceRefresh();
            }
        });
        isPrepared = true;
        initView(view);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }
    /**
     * Toast提示信息
     * @param message
     */
    public void showMsg(String message){
        ToastUtils.showShort(getActivity(), message);
    }

    /**
     * Toast提示信息
     * @param resId
     */
    public void showMsg(int resId){
        ToastUtils.showShort(getActivity(), resId);
    }
}
