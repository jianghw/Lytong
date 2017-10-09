package com.tzly.annual.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.jianghw.multi.state.layout.MultiStateLayout;
import com.jianghw.multi.state.layout.OnStateViewCreatedListener;
import com.tzly.annual.base.custom.dialog.LoadingDialog;
import com.tzly.annual.base.util.StatusBarUtils;


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

        setContentView(getRootView());
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.lay_linear);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Title
        if (!isCustomTitle()) {
            View titleView = inflater.inflate(initTitleView(), parentLayout, true);
            bindTitleView(titleView);
        }
        //multi
        View multiStateView = inflater.inflate(R.layout.base_activity_jx_content, parentLayout, true);
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
        //content
        if (initContentView() != 0) {
            View contentView = inflater.inflate(initContentView(), multiStateLayout, true);
            bindContentView(contentView);
        }
        //注入控件布局
        multiStateLayout.customChildAt();
        multiStateLayout.setState(initMultiState());

        initContentData();
        userRefreshData();
    }

    /**
     * 页面布局 增强控制
     */
    private void enhanceEmptyView(View view, int state) {
        View tvEmpty = view.findViewById(R.id.tv_empty);
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRefreshData();
            }
        });
    }

    private void enhanceErrorView(View view, int state) {
    }

    private void enhanceNetErrorView(View view, int state) {
    }

    private void enhanceLoadingView(View view, int state) {
    }

    private void enhanceContentView(View view, int state) {
    }

    protected void showContentView() {
        if (multiStateLayout != null) multiStateLayout.setState(MultiState.CONTENT);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hideDialogLoading();
    }

    protected int getRootView() {
        return R.layout.base_activity_jx_base;
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
        return getResources().getColor(R.color.base_colorWhite);
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
        return R.layout.base_custom_base_title;
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
    protected void backClickListener() {}

    /**
     * 页面关闭
     */
    protected void closeClickListener() {
        finish();
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

    protected void titleContent(String title, String right) {
        if (mTvTitle != null) mTvTitle.setText(title);
        mTvRight.setVisibility(View.VISIBLE);
        if (mTvRight != null) mTvRight.setText(right);
    }

    /**
     * 默认第一次显示状态
     */
    @MultiState
    protected int initMultiState() {
        return MultiState.LOADING;
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
    protected void userRefreshData() {}

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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

}
