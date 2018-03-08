package com.zantong.mobilecttx.home_v;

import android.content.Intent;
import android.os.Bundle;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.htmltxt.HtmlHttpImageGetter;
import com.tzly.ctcyh.router.custom.htmltxt.HtmlTextView;
import com.tzly.ctcyh.router.custom.htmltxt.IHtmlTextClick;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.router.MainRouter;

/**
 * 富文本页面
 */

public class RichTextActivity extends AbstractBaseActivity {
    private HtmlTextView mTvHtml;
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

        mTvHtml.setClickHtml(mCurRich,
                new HtmlHttpImageGetter(mTvHtml),
                new IHtmlTextClick() {
                    @Override
                    public void clickLine(String url) {
                        gotoHtml(url);
                    }
                });
    }

    public void initView() {
        mTvHtml = (HtmlTextView) findViewById(R.id.tv_html);
    }

    private void gotoHtml(String tableHtml) {
        MainRouter.gotoWebHtmlActivity(getApplicationContext(), "Web页面", tableHtml);
    }
}
