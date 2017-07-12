package com.zantong.mobilecttx.base.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SystemBarTintManager;
import com.zantong.mobilecttx.utils.UiHelpers;

import butterknife.ButterKnife;

/**
 * prestener绑定到activity,View的绑定和解绑操作每个activity都会去做
 * 故抽出以上操作在基类中完成
 * Created by zhengyingbing on 16/6/2.
 */
public abstract class MvpBaseActivity extends AppCompatActivity {

    private TextView mBaseTitle, mBaseTitleLeft, mBaseTitleRight, mBaseEnsure, mBaseBack;
    private View mTitleLayout, mTitleSelLayout;
    RelativeLayout mRelativeLayout;
    private Dialog mLoadingDialog;
    public boolean mStatusBarHeight = true;
    protected SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarColor();
        if (getLayoutResId() != 0) setContentView(getLayoutResId());

        ButterKnife.bind(this);
        onAfterSetContentLayout();

        setStatusBarSpace();
        setTitleView();
        initMvPresenter();
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

    private void setStatusBarSpace() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mStatusBarHeight) {
                mRelativeLayout.setPadding(0, tintManager.getConfig().getStatusBarHeight(), 0, 0);
            }
        }
    }

    protected int getLayoutResId() {
        return R.layout.base_titlebar_activity;
    }

    protected void onAfterSetContentLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.base_titlebar_content);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(getContentResId(), null);
        if (linearLayout != null)
            linearLayout.addView(view, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        baseInitView();
    }

    private void baseInitView() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.base_activity_layout);
        mTitleLayout = findViewById(R.id.base_titlebar_layout);
        mTitleSelLayout = findViewById(R.id.base_titlebar_sel_layout);

        mBaseTitle = (TextView) findViewById(R.id.base_titlebar_text);
        mBaseTitleLeft = (TextView) findViewById(R.id.base_titlebar_left);
        mBaseTitleRight = (TextView) findViewById(R.id.base_titlebar_right);
        mBaseEnsure = (TextView) findViewById(R.id.base_titlebar_ensure);
        mBaseBack = (TextView) findViewById(R.id.base_mvptitle_back);

        if (mBaseBack != null && mBaseBack.getText().toString().equals("返回")) {
            // 初始化返回按钮图片大小
            UiHelpers.setTextViewIcon(this, mBaseBack, R.mipmap.back_btn_image,
                    R.dimen.ds_24,
                    R.dimen.ds_51, UiHelpers.DRAWABLE_LEFT);
        }

        mBaseTitleRight.setTextColor(getResources().getColor(R.color.white));
        mBaseTitleRight.setBackgroundResource(R.drawable.query_history_shap);
        mBaseTitleLeft.setTextColor(getResources().getColor(R.color.appmain));
        mBaseTitleLeft.setBackgroundResource(R.drawable.illegal_query_shap_pre);
    }

    /**
     * 子布局文件
     */
    protected abstract int getContentResId();

    protected abstract void setTitleView();

    protected abstract void initMvPresenter();

    public void onBack(View v) {
        baseGoBack();
    }

    public void onRight(View v) {
        baseGoEnsure();
    }

    public void onLeftTitle(View v) {
        baseLeftTitleEnsure();
    }

    public void onRightTitle(View v) {
        baseRightTitleEnsure();
    }

    /**
     * 设置标题文字
     *
     * @param title
     */
    public void setTitleText(String title) {
        mTitleSelLayout.setVisibility(View.GONE);
        mBaseTitle.setVisibility(View.VISIBLE);
        mBaseTitle.setText(title);
    }

    /**
     * 设置选项格式的title标题
     *
     * @param title1
     * @param title2
     */
    public void setTitleSelText(String title1, String title2) {
        mTitleSelLayout.setVisibility(View.VISIBLE);
        mBaseTitle.setVisibility(View.GONE);
        mBaseTitleLeft.setText(title1);
        mBaseTitleRight.setText(title2);
    }

    /**
     * 获取父布局
     *
     * @return
     */
    public RelativeLayout getRelativeLayout() {
        return mRelativeLayout;
    }

    /**
     * 获取左侧标题
     *
     * @return
     */
    public TextView getTitleLeft() {
        return mBaseTitleLeft;
    }

    /**
     * 获取右侧标题
     *
     * @return
     */
    public TextView getTitleRight() {
        return mBaseTitleRight;
    }

    /**
     * 设置标题文字
     *
     * @param resid
     */
    public void setTitleText(int resid) {
        mBaseTitle.setText(resid);
    }

    /**
     * 获取标题控件
     *
     * @return
     */
    public TextView getTitleTextView() {
        return mBaseTitle;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     */
    public void setEnsureText(String text) {
        mBaseEnsure.setText(text);
    }

    public TextView getBaseBack() {
        return mBaseBack;
    }

    public void setBaseBack(String str) {
        mBaseBack.setText(str);
    }

    public void setEnsureText(int resid) {
        mBaseEnsure.setText(resid);
    }

    public TextView getEnsureView() {
        return mBaseEnsure;
    }

    public void setEnsureEnable(boolean flag) {
        mBaseEnsure.setTextColor(flag ? getResources().getColor(R.color.civory) : getResources().getColor(R.color.colorGray));
        mBaseEnsure.setClickable(flag);
        mBaseEnsure.setEnabled(flag);
    }

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
     * 右侧的按钮事件
     */
    protected void baseGoEnsure() {

    }

    /**
     * 左侧标题按钮事件
     */
    protected void baseLeftTitleEnsure() {
        mBaseTitleRight.setTextColor(getResources().getColor(R.color.white));
        mBaseTitleRight.setBackgroundResource(R.drawable.query_history_shap);
        mBaseTitleLeft.setTextColor(getResources().getColor(R.color.appmain));
        mBaseTitleLeft.setBackgroundResource(R.drawable.illegal_query_shap_pre);
    }

    /**
     * 右侧标题的按钮事件
     */
    protected void baseRightTitleEnsure() {
        mBaseTitleLeft.setTextColor(getResources().getColor(R.color.white));
        mBaseTitleLeft.setBackgroundResource(R.drawable.illegal_query_shap);
        mBaseTitleRight.setTextColor(getResources().getColor(R.color.appmain));
        mBaseTitleRight.setBackgroundResource(R.drawable.query_history_shap_pre);
    }

    protected View getTitleSelLayout() {
        return mTitleSelLayout;
    }

    /**
     * 加载中效果
     */
    public void showDialogLoading() {
        if (mLoadingDialog == null || !mLoadingDialog.isShowing())
            mLoadingDialog = DialogUtils.showLoading(this);
    }

    /**
     * 加载中效果
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

}
