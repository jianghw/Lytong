package com.tzly.ctcyh.router.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.dialog.LoadingDialog;
import com.tzly.ctcyh.router.util.StatusBarUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;


/**
 * Created by jianghw on 2017/9/12.
 * Description: 基类activity
 * Update by:
 * Update day:
 */

public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected final String BASE_POSITION = "base_position";
    private int mBasePosition = -1;
    protected final String BASE_TITLE = "base_title";
    private String mBaseTitle = null;

    private RelativeLayout mBackgroundLay;
    private ImageView mImgBack;
    private TextView mTvClose;
    private TextView mTvTitle;
    private ImageView mImgHome;
    private TextView mTvRight;
    private TextView mTvLine;
    /**
     * 提示框
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

        if (null != savedInstanceState) {
            mBasePosition = savedInstanceState.getInt(BASE_POSITION);
            mBaseTitle = savedInstanceState.getString(BASE_TITLE);
        }

        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        content.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(linearLayout);

        if (!isCustomTitle()) {
            View titleView = LayoutInflater.from(this).inflate(initTitleView(), linearLayout, false);
            linearLayout.addView(titleView);
            bindTitleView(titleView);
        }
        LayoutInflater.from(this).inflate(initContentView(), linearLayout, true);
        if (isStatusBar()) initStatusBarColor();

        bundleIntent(getIntent());
        bindFragment();
        initContentData();
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
        super.onDestroy();
        dismissLoading();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        bundleIntent(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(BASE_POSITION, mBasePosition);
        savedInstanceState.putString(BASE_TITLE, mBaseTitle);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mBasePosition = savedInstanceState.getInt(BASE_POSITION);
        mBaseTitle = savedInstanceState.getString(BASE_TITLE);
    }

    /**
     * 底部回退按键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected abstract int initContentView();

    protected abstract void bundleIntent(Intent intent);

    protected abstract void bindFragment();

    protected abstract void initContentData();

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

    /**
     * 定义渐变色红色
     */
    protected void titleBackground() {
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
     * 右边图片点击
     */
    protected void imageClickListener() {}

    /**
     * 右边文字点击
     */
    protected void rightClickListener() {}

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
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
    public void shortToast(String msg) {
        ToastUtils.toastShort(msg);
    }
}
