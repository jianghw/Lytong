package com.zantong.mobile.base.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.tzly.annual.base.custom.dialog.LoadingDialog;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.api.BaseApiClient;
import com.zantong.mobile.contract.IBaseActivity;
import com.zantong.mobile.eventbus.ErrorEvent;
import com.zantong.mobile.eventbus.ExitAppEvent;
import com.zantong.mobile.utils.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * 基类
 *
 * @author Sandy
 *         create at 16/6/2 下午2:14
 */
public abstract class BaseActivity extends FragmentActivity implements OnClickListener, IBaseActivity {
    private Dialog mLoadingDialog;
    protected SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        onBeforeSetContentLayout();

        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
        onAfterSetContentLayout();
        ButterKnife.bind(this);
        initView();
        initData();
        registerEventBus();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.red));
        }
    }

    /**
     * 注册EventBus通信组件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 取消注册EventBus通信组件
     */
    protected void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置布局前要进行的操作
     */
    protected void onBeforeSetContentLayout() {
    }

    /**
     * 设置布局后要进行的操作
     */
    protected void onAfterSetContentLayout() {

    }

    /**
     * 资源文件Layout ID 友盟统计
     */
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 加载中效果
     */
    public synchronized void showDialogLoading() {
        hideDialogLoading();
        showDialogLoading("加载中...");
    }

    /**
     * @param msg 提示信息
     */
    public synchronized void showDialogLoading(String msg) {
        mLoadingDialog = LoadingDialog.createLoading(this, msg);
    }

    /**
     * 隐藏遮罩的dialog
     */
    public synchronized void hideDialogLoading() {
        LoadingDialog.closeDialog(mLoadingDialog);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 退出应用
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ExitAppEvent exitApp) {
        if (exitApp.isExit()) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ErrorEvent event) {
        String status = event.getStatus();
        String message = event.getMsg();
        if (event.getContext().equals(this)) {
            if (event.getStatus().equals("505"))
                return;
            onFailure(status, message);
        }
    }

    /**
     * 网络请求或请求数据失败的信息提示
     */
    protected void onFailure(String status, String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        BaseApiClient.cancelCall(this);
        super.onDestroy();
    }
}