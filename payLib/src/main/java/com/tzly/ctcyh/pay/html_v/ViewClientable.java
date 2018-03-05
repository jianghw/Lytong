package com.tzly.ctcyh.pay.html_v;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * Created by jianghw on 18-3-5.
 */

public interface ViewClientable {
    void shouldOverrideUrlLoading(WebView view, String url);

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);
}
