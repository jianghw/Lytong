package com.zantong.mobilecttx.home_v;

import android.content.Intent;

import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.custom.htmltxt.HtmlHttpImageGetter;
import com.tzly.ctcyh.router.custom.htmltxt.HtmlTextView;
import com.tzly.ctcyh.router.custom.htmltxt.IHtmlTextClick;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.router.MainRouter;

/**
 * Created by jianghw on 18-2-24.
 */

public class RichTextActivity extends AbstractBaseActivity {
    private HtmlTextView mTvHtml;

    @Override
    protected int initContentView() {
        return R.layout.main_activity_rich_text;
    }

    @Override
    protected void bundleIntent(Intent intent) {

    }

    @Override
    protected void bindFragment() {
        initView();
    }

    @Override
    protected void initContentData() {

        mTvHtml.setClickHtml("",
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
        MainRouter.gotoWebHtmlActivity(this, "Web页面", tableHtml);
    }
}
