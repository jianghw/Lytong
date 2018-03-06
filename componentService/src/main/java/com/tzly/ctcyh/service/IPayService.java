package com.tzly.ctcyh.service;

import android.app.Activity;
import android.content.Context;

import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.java.response.violation.ViolationNum;

import java.util.List;

import rx.Observable;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */

public interface IPayService {

    void gotoPayTypeActivity(Activity context, String orderId);

    void gotoCouponStatusActivity(Context context);

    /**
     * web html
     */
    void gotoWebHtmlActivity(Context context, String title, String url);

    void gotoWebHtmlActivity(Context context, String title, String url, String num, String enginenum);

}
