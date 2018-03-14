package com.zantong.mobilecttx.push_v;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.Map;

/**
 * 辅助推送
 */

public class PopupPushActivity extends AndroidPopupActivity {

    /**
     * 实现通知打开回调方法，获取通知相关信息
     */
    @Override
    protected void onSysNoticeOpened(String title, String s1, Map<String, String> extraMap) {
        AliPushExtBean pushExtBean= new AliPushExtBean();

        for (Map.Entry<String, String> entry : extraMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("id")) {
                pushExtBean.setId(value);
            } else if (key.equals("type")) {
                pushExtBean.setType(value);
            }
        }
        String type = pushExtBean.getType();

        //前台工作
        if (type.equals("1"))//主页
            MainRouter.gotoMainActivity(this, 0);
        else if (type.equals("2"))//消息详情
            MainRouter.gotoMegDetailActivity(this, title, pushExtBean.getId());
        else if (type.equals("3"))//优惠详情
            MainRouter.gotoCouponStatusActivity(this);
        else if (type.equals("4"))//html详情
            MainRouter.gotoWebHtmlActivity(this, title, pushExtBean.getUrl());
        else if (type.equals("5"))//违章查询
            MainRouter.gotoViolationActivity(this);
        else
            gotoAppLauncher(this);
    }

    private void gotoAppLauncher(Context context) {
        try {
            Intent LaunchIntent = context.getPackageManager()
                    .getLaunchIntentForPackage("com.zantong.mobilecttx");
            startActivity(LaunchIntent);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("geo1://......"));
            startActivity(intent);
        }
    }
}
