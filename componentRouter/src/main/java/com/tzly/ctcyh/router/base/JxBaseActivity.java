package com.tzly.ctcyh.router.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.jianghw.multi.state.layout.MultiStateLayout;
import com.jianghw.multi.state.layout.OnStateViewCreatedListener;
import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.dialog.LoadingDialog;
import com.tzly.ctcyh.router.util.StatusBarUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by jianghw on 2017/9/12.
 * Description: 基类activity
 * Update by:
 * Update day:
 */

public abstract class JxBaseActivity extends AppCompatActivity {

    private RelativeLayout mBackgroundLay;
    private ImageView mImgBack;
    private TextView mTvClose;
    private TextView mTvTitle;
    private ImageView mImgHome;
    private TextView mTvRight;
    private TextView mTvLine;
    private MultiStateLayout multiStateLayout;
    /**
     * 加载等待
     */
    private Dialog mLoadingDialog;

    /**
     * 1、当root不为null，attachToRoot为true时，
     * 表示将resource布局添加到root中，resource布局的根节点各个属性都有效,将这个root作为根对象返回
     * <p>
     * 2、如果root不为null，而attachToRoot为false的话，表示不将第一个参数所指定的View添加到root中
     * <p>
     * 3、一个顶级View叫做DecorView，DecorView中包含一个竖直方向的LinearLayout，
     * LinearLayout由两部分组成，第一部分是标题栏，第二部分是内容栏，内容栏是一个FrameLayout
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleIntent(savedInstanceState);

        setContentView(getRootView());
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.lay_linear);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Title
        if (!isCustomTitle()) {
            View titleView = inflater.inflate(initTitleView(), parentLayout, true);
            bindTitleView(titleView);
        }
        //multi
        View multiStateView = inflater.inflate(R.layout.activity_jx_content, parentLayout, true);
        multiStateLayout = (MultiStateLayout) multiStateView.findViewById(R.id.lay_state);
        multiStateLayout.setOnStateViewCreatedListener(new OnStateViewCreatedListener() {
            @Override
            public void onViewCreated(View view, int state) {
                if (state == MultiState.CONTENT) {
                    enhanceContentView(view, state);
                } else if (state == MultiState.LOADING) {
                    enhanceLoadingView(view, state);
                } else if (state == MultiState.EMPTY) {
                    enhanceEmptyView(view, state);
                } else if (state == MultiState.ERROR) {
                    enhanceErrorView(view, state);
                } else if (state == MultiState.NET_ERROR) {
                    enhanceNetErrorView(view, state);
                }
            }
        });
        //注入控件布局
        multiStateLayout.customChildAt();
        multiStateLayout.setState(initMultiState());

        //content
        if (initContentView() != 0) {
            View contentView = inflater.inflate(initContentView(), multiStateLayout, true);
            bindContentView(contentView);
        }
        initContentData();
    }

    /**
     * 数据传递过来
     *
     * @param savedInstanceState
     */
    protected abstract void bundleIntent(Bundle savedInstanceState);

    /**
     * 页面布局 增强控制
     */
    private void enhanceEmptyView(View view, int state) {
        doClickRefreshView(view, state);
    }

    private void doClickRefreshView(View view, int state) {
        View tvEmpty = view.findViewById(R.id.tv_empty);
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickRefreshData();
            }
        });
    }

    private void enhanceErrorView(View view, int state) {
        doClickRefreshView(view, state);
    }

    private void enhanceNetErrorView(View view, int state) {
        doClickRefreshView(view, state);
    }

    private void enhanceLoadingView(View view, int state) {
    }

    private void enhanceContentView(View view, int state) {
    }

    /**
     * 手动控制布局显示效果
     */
    protected void showContentView() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.CONTENT);
    }

    protected void showLoadingView() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.LOADING);
    }

    protected void showEmptyView() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.EMPTY);
    }

    protected void showNetErrorView() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.NET_ERROR);
    }

    protected void showErrorView() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.ERROR);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计开始
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计结束
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dismissLoading();
    }

    protected int getRootView() {
        return R.layout.activity_jx_base;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (isStatusBar()) initStatusBarColor();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        if (isStatusBar()) initStatusBarColor();
    }

    /**
     * 是否自定义状态栏效果
     */
    protected boolean isStatusBar() {
        return true;
    }

    /**
     * 状态栏颜色
     */
    protected void initStatusBarColor() {
        StatusBarUtils.setColor(this, iniStatusColor(), StatusBarUtils.DEFAULT_BAR_ALPHA);
    }

    /**
     * 状态栏颜色
     */
    protected int iniStatusColor() {
        return getResources().getColor(R.color.res_color_white);
    }

    /**
     * 是否要子布局定义title
     */
    protected boolean isCustomTitle() {
        return false;
    }

    /**
     * Title
     */
    protected int initTitleView() {
        return R.layout.custom_base_title;
    }

    protected void bindTitleView(View view) {
        mBackgroundLay = (RelativeLayout) view.findViewById(R.id.lay_re_bg);
        mImgBack = (ImageView) view.findViewById(R.id.img_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClickListener();
            }
        });
        mTvClose = (TextView) view.findViewById(R.id.tv_close);
        mTvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeClickListener();
            }
        });
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgHome = (ImageView) view.findViewById(R.id.img_home);
        mImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClickListener();
            }
        });
        mTvRight = (TextView) view.findViewById(R.id.tv_right);
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClickListener();
            }
        });
        mTvLine = (TextView) view.findViewById(R.id.tv_line);
    }

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        closeFragment();
    }

    /**
     * 页面关闭
     */
    protected void closeClickListener() {
        closeFragment();
    }

    /**
     * 底部回退按键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
        }
        return true;
    }

    /**
     * 关闭Fragment 默认最后只有一个fragment时 关闭页面
     */
    public void closeFragment() {
        closeFragment(1);
    }

    public void closeFragment(int count) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > count) {
            fragmentManager.popBackStackImmediate();
        } else {
            finish();
        }
    }

    /**
     * 右边图片点击
     */
    protected void imageClickListener() {}

    /**
     * 右边文字点击
     */
    protected void rightClickListener() {}

    protected void titleLeftImage(@DrawableRes int resId) {
        if (mImgBack != null) mImgBack.setImageResource(resId);
    }

    /**
     * 标题栏
     */
    protected void titleContent(String title) {
        if (mTvTitle != null) mTvTitle.setText(title);
    }

    protected void titleClose() {
        mTvClose.setVisibility(View.VISIBLE);
        if (mTvClose != null) mTvClose.setText("关闭");
    }

    protected void titleClose(String close) {
        mTvClose.setVisibility(View.VISIBLE);
        if (mTvClose != null) mTvClose.setText(close);
    }

    protected void titleMore(String right) {
        mTvRight.setVisibility(View.VISIBLE);
        if (mTvRight != null) mTvRight.setText(right);
    }

    protected void rightImage(int imgRed) {
        if (imgRed < 0 && mImgHome != null) {
            mImgHome.setVisibility(View.GONE);
            return;
        }

        if (mImgHome != null && mImgHome.getVisibility() != View.VISIBLE) {
            mImgHome.setVisibility(View.VISIBLE);
            mImgHome.setImageResource(imgRed != 0 ? imgRed : R.mipmap.ic_title_home);
        }

        if (mTvRight != null && mTvRight.getVisibility() != View.GONE) {
            mTvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 定义渐变色红色
     */
    protected void titleBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mBackgroundLay != null)
                mBackgroundLay.setBackground(getResources().getDrawable(R.drawable.shape_pay_pressed_false));
            if (mImgBack != null)
                mImgBack.setImageResource(R.mipmap.ic_arrow_left);
            if (mTvTitle != null)
                mTvTitle.setTextColor(getResources().getColor(R.color.res_color_white));
            if (mTvRight != null)
                mTvRight.setTextColor(getResources().getColor(R.color.res_color_white));
            if (mTvLine != null)
                mTvLine.setVisibility(View.GONE);
        }
    }

    /**
     * 默认第一次显示状态
     */
    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    /**
     * 显示内容
     */
    protected abstract int initContentView();

    /**
     * 绑定子控件
     */
    protected abstract void bindContentView(View childView);

    protected abstract void initContentData();

    /**
     * 用户手动刷新数据
     */
    protected void userClickRefreshData() {}

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 加载中效果
     */
    public synchronized void showLoading() {
        dismissLoading();
        showLoading("加载中...");
    }

    /**
     * @param msg 提示信息
     */
    public synchronized void showLoading(String msg) {
        mLoadingDialog = LoadingDialog.createLoading(this, msg);
    }

    /**
     * 隐藏遮罩的dialog
     */
    public synchronized void dismissLoading() {
        LoadingDialog.closeDialog(mLoadingDialog);
    }

    /**
     * 统一封装
     */
    public void toastShore(String msg) {
        ToastUtils.toastShort(msg);
    }
}
