package com.zantong.mobilecttx.home_v;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

/**
 * 富文本页面
 */

public class RichTextActivity extends AbstractBaseActivity {
    private TextView mTvHtml;
    private String mCurRich;

    @Override
    protected int initContentView() {
        return R.layout.main_activity_rich_text;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.hasExtra(MainGlobal.putExtra.home_rich_extra))
                    mCurRich = bundle.getString(MainGlobal.putExtra.home_rich_extra);
            }
        }
    }

    @Override
    protected void bindFragment() {
        titleContent("广播详情");

        initView();
    }

    @Override
    protected void initContentData() {
        RichText.initCacheDir(this);

        RichText.fromHtml(mCurRich) // 数据源
                .autoFix(true) // 是否自动修复，默认true
                .autoPlay(true) // gif图片是否自动播放
                .showBorder(false) // 是否显示图片边框
                .scaleType(ImageHolder.ScaleType.fit_xy) // 图片缩放方式
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
                .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
                .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
                .urlClick(new OnUrlClickListener() {// 设置链接点击回调
                    @Override
                    public boolean urlClicked(String url) {
                        gotoHtml(url);
                        return false;
                    }
                })
                .bind(this) // 绑定richText对象到某个object上，方便后面的清理
                .into(mTvHtml); // 设置目标TextView
    }

    public void initView() {
        mTvHtml = (TextView) findViewById(R.id.tv_html);
    }

    private void gotoHtml(String tableHtml) {
        MainRouter.gotoWebHtmlActivity(getApplicationContext(), "Html页面", tableHtml);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // activity onDestory时
        RichText.clear(this);
    }
}
