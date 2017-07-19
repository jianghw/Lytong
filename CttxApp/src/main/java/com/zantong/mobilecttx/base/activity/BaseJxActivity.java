package com.zantong.mobilecttx.base.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.DialogUtils;

import butterknife.ButterKnife;
import cn.qqtheme.framework.util.ui.StatusBarUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_jx);

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

        bundleIntent(savedInstanceState);
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
        StatusBarUtils.setColor(this, iniStatusColor(), 38);
    }

    /**
     * 状态栏颜色
     */
    protected int iniStatusColor() {
        return getResources().getColor(R.color.colorTvRed_f33);
    }

    /**
     * 数据传递口及保存口
     */
    protected abstract void bundleIntent(Bundle savedInstanceState);

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
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClickListener();
            }
        });
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgHome = (ImageView) view.findViewById(R.id.img_home);
        mImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
     * 加载中效果
     */
    public void showDialogLoading() {
        mLoadingDialog = DialogUtils.showLoading(this);
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

    /**
     * 关闭Fragment
     */
    public void closeFragment() {
        closeFragment(1);
    }

    public void closeFragment(int count) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > count) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }
}
