package com.zantong.mobilecttx.base.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.DialogUtils;

import butterknife.ButterKnife;
import cn.qqtheme.framework.util.ui.StatusBarUtils;

/**
 * activity 基类
 */
public abstract class BaseJxActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog mLoadingDialog;
    private ImageView mImgBack;
    /**
     * 驾校报名
     */
    private TextView mTvTitle;
    private ImageView mImgHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        bundleIntent(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_base_jx, null);

        setContentView(contentView);
        initStatusBarColor();

        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.lay_base_content);
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
            initTitleView(contentView);
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
            initFragmentView(contentView);
        }

        if (isNeedKnife()) ButterKnife.bind(this);
    }

    protected abstract void bundleIntent(Bundle savedInstanceState);

    /**
     * 状态栏颜色
     */

    protected void initStatusBarColor() {
        StatusBarUtils.setColor(this, iniStatusColor());
    }

    /**
     * 状态栏颜色
     */
    protected int iniStatusColor() {
        return getResources().getColor(R.color.colorTvRed_f33);
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
        mImgBack = (ImageView) view.findViewById(R.id.img_back);
        mImgBack.setOnClickListener(this);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgHome = (ImageView) view.findViewById(R.id.img_home);
        mImgHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                break;
            case R.id.img_home:
                break;
        }
    }

    /**
     * 标题栏
     */
    protected void initTitleContent(String title) {
        if (mTvTitle != null) mTvTitle.setText(title);
    }

    /**
     * 是否使用黄油刀，小心有坑
     */
    protected boolean isNeedKnife() {
        return false;
    }

    /**
     * 子布局文件
     */
    protected abstract int getContentResId();

    /**
     * 子布局初始化信息
     */
    protected abstract void initFragmentView(View view);

    /**
     * 左侧的按钮事件
     */
    protected void baseGoBack() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive() &&
                getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        finish();
    }


    /**
     * 加载中效果
     *
     * @param msg 提示信息
     */
    public void showDialogLoading(String msg) {
        if (mLoadingDialog == null || !mLoadingDialog.isShowing())
            mLoadingDialog = TextUtils.isEmpty(msg)
                    ? DialogUtils.showLoading(this) : DialogUtils.showLoading(this, msg);
    }

    /**
     * 隐藏遮罩的dialog
     */
    public void hideDialogLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    @Override
    protected void onDestroy() {
        hideDialogLoading();

        if (isNeedKnife()) ButterKnife.unbind(this);
        DestroyViewAndThing();

        super.onDestroy();
    }

    /**
     * 收尾工作要做好
     */
    protected abstract void DestroyViewAndThing();
}
