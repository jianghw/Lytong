package com.zantong.mobilecttx.user.activity;

import android.content.Intent;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.AppUtils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;

public class AboutActivity extends AbstractBaseActivity {

    /**
     * Version 1.0
     */
    TextView mVersion;


    @Override
    protected void bundleIntent(Intent intent) {}

    @Override
    protected int initContentView() {
        return R.layout.mine_about_activity;
    }

    @Override
    protected void bindFragment() {
        titleContent("关于我们");

        mVersion = (TextView) findViewById(R.id.mine_about_version);
    }

    @Override
    protected void initContentData() {
        mVersion.setText(getVersion());
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    private String getVersion() {
        String version = AppUtils.getAppVersionName()
                + "_" + AppUtils.getAppVersionCode();
        String channel = AppUtils.getAppMetaData(getApplicationContext(), "UMENG_CHANNEL");
        String config = BuildConfig.App_Url ? "debug" : "release";
        return version + "/" + channel + "/" + config;
    }

}
