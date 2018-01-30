package com.zantong.mobile.code_v;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;

/**
 * 黄牛订单列表页面
 */
public class WeixinCodeActivity extends BaseJxActivity {


    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_weixin_code;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("微信违章缴费");
        //设置屏幕亮度最大
        setWindowBrightness(WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL);
    }

    @Override
    protected void DestroyViewAndThing() {
        //取消屏幕最亮
        setWindowBrightness(WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE);
    }

    /**
     * 设置当前窗口亮度
     *
     * @param brightness
     */
    private void setWindowBrightness(float brightness) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness;
        window.setAttributes(lp);
    }
}
