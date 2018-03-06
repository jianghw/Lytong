package com.tzly.ctcyh.pay.html_v;

import android.webkit.WebView;

/**
 * Created by jianghw on 18-3-5.
 */

public interface ChromeClientable {
    void onProgressChanged(WebView view, int newProgress);

    void onReceivedTitle(WebView view, String title);
}
