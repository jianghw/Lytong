package com.zantong.mobile.base.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.annual.base.custom.dialog.LoadingDialog;
import com.tzly.annual.base.util.StatusBarUtils;
import com.zantong.mobile.R;

import butterknife.ButterKnife;

/**
 * activity 基类
 */
public abstract class BaseJxActivity extends AppCompatActivity {

    private Dialog mLoadingDialog;
    private ImageView mImgBack;
    /**
     * 驾校报名
     */
    private TextView mTvTitle;
    private ImageView mImgHome;
    private TextView mTvClose;
    private TextView mTvRight;
    private TextView mTvLine;
    private RelativeLayout mBackgroundLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_jx);

        bundleIntent(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lay_base_content);
        //Title
        if (!isNeedCustomTitle()) {
            View rootView = inflater.inflate(getTitleLayoutResID(), null);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            linearLayout.addView(rootView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            initTitleView(linearLayout);
        }

        //Body
        if (getContentResId() != 0) {
            View rootView = inflater.inflate(getContentResId(), null);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            linearLayout.addView(rootView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            initFragmentView(linearLayout);
        }

        if (isNeedKnife()) ButterKnife.bind(this);

        initViewStatus();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (isNeedStatus()) initStatusBarColor();
    }

    /**
     * 是否需要修改状态栏
     */
    protected boolean isNeedStatus() {
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
        return getResources().getColor(R.color.colorWhite);
    }

    /**
     * 子布局可以继承实现
     */
    protected int getTitleLayoutResID() {
        return R.layout.include_title_base;
    }

    /**
     * 是否要自定义title
     */
    protected boolean isNeedCustomTitle() {
        return false;
    }

    /**
     * 只有当title布局不为0时，子类可以继承
     */
    protected void initTitleView(View view) {
        mBackgroundLay = (RelativeLayout) view.findViewById(R.id.lay_background);
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
     * 最多只能有一个可见
     */
    protected void setTvRightVisible(String msg) {
        if (mImgHome != null && mImgHome.getVisibility() != View.GONE)
            mImgHome.setVisibility(View.GONE);
        if (mTvRight != null && mTvRight.getVisibility() != View.VISIBLE) {
            mTvRight.setVisibility(View.VISIBLE);
        }
        if (mTvRight != null) mTvRight.setText(msg);
    }

    /**
     * 关闭控件
     */
    protected void setTvCloseVisible() {
        setTvCloseVisible("关闭");
    }

    protected void setTvCloseVisible(String msg) {
        if (mTvClose != null && mTvClose.getVisibility() != View.VISIBLE) {
            mTvClose.setVisibility(View.VISIBLE);
        }
        if (mTvRight != null) mTvRight.setText(msg);
    }

    protected void setImageRightVisible(int imgRed) {
        if (imgRed < 0 && mImgHome != null) {
            mImgHome.setVisibility(View.GONE);
            return;
        }

        if (mImgHome != null && mImgHome.getVisibility() != View.VISIBLE) {
            mImgHome.setVisibility(View.VISIBLE);
            mImgHome.setImageResource(imgRed != 0 ? imgRed : R.mipmap.btn_homepage);
        }

        if (mTvRight != null && mTvRight.getVisibility() != View.GONE) {
            mTvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 定义渐变色红色
     */
    protected void setTitleBackgroundRed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mBackgroundLay != null)
                mBackgroundLay.setBackground(getResources().getDrawable(R.drawable.bg_title_shade));
            if (mImgBack != null)
                mImgBack.setImageResource(R.mipmap.back_white);
            if (mTvTitle != null)
                mTvTitle.setTextColor(getResources().getColor(R.color.colorWhite));
            if (mTvRight != null)
                mTvRight.setTextColor(getResources().getColor(R.color.colorWhite));
            if (mTvLine != null)
                mTvLine.setVisibility(View.GONE);
        }
    }

    /**
     * 标题栏
     */
    protected void initTitleContent(String title) {
        if (mTvTitle != null) mTvTitle.setText(title);
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
        finish();
    }

    protected void imageClickListener() {
    }

    protected void rightClickListener() {
    }

    /**
     * 是否使用黄油刀，小心有坑
     */
    protected boolean isNeedKnife() {
        return false;
    }

    /**
     * 有些用Knife 的页面也许会用到
     * 注册控件之后
     */
    protected void initViewStatus() {
    }

    /**
     * 数据传递口及保存口
     */
    protected abstract void bundleIntent(Bundle savedInstanceState);

    /**
     * 子布局文件
     */
    protected abstract int getContentResId();

    /**
     * 子布局初始化信息
     */
    protected abstract void initFragmentView(View view);

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        hideDialogLoading();
        DestroyViewAndThing();

        if (isNeedKnife()) ButterKnife.unbind(this);
        super.onDestroy();
    }

    /**
     * 收尾工作要做好
     */
    protected abstract void DestroyViewAndThing();

    /**
     * 关闭Fragment
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
}
