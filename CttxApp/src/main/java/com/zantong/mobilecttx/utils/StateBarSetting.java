package com.zantong.mobilecttx.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home.activity.HomeMainActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

/**
 * Created by 王海洋 on 2016/5/9.
 * 沉浸式状态栏设置
 */
public class StateBarSetting {

    public static void settingBar(Activity mContex){
        settingBar(mContex, 0, null, false);
    }
    public static void settingBar(Activity mContext, int res){
        settingBar(mContext, res, null, false);
    }
    public static void settingBar(Activity mContext, Class<?> clazz, boolean popFlag){
        settingBar(mContext, 0, clazz, popFlag);
    }
    public static void settingBar(final Activity mContext, int res, final Class<?> clazz, final boolean popFlag){
        if(!(mContext instanceof HomeMainActivity)){
            ScreenManager.pushActivity(mContext);
        }
        View view = mContext.findViewById(R.id.tv_back);
        View text_right = mContext.findViewById(R.id.text_right);
//        View viewTitle = mContext.findViewById(R.id.title);
        if(view != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive() && mContext.getCurrentFocus() != null) {
                        if (mContext.getCurrentFocus().getWindowToken() != null) {
                            imm.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    ScreenManager.popActivity();
                }
            });
        }
        if(text_right != null && null != clazz){
            text_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Act.getInstance().lauchIntent(mContext, clazz);
                    if(popFlag){
                        ScreenManager.popActivity();
                    }
                }
            });
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, mContext);

//            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(mContext);
        tintManager.setStatusBarTintEnabled(true);
        if(res == 0){
            tintManager.setStatusBarTintResource(R.color.appmain);//通知栏所需颜色
//            viewTitle.setBackgroundColor(mContext.getResources().getColor(R.color.appmain));
        }else{
            tintManager.setStatusBarTintResource(res);//通知栏所需颜色
//            viewTitle.setBackgroundColor(mContext.getResources().getColor(res));

        }
    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on, Activity mContext) {
        Window win = mContext.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
