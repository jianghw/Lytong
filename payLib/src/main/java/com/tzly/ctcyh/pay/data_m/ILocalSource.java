package com.tzly.ctcyh.pay.data_m;

import android.content.Context;

import com.tzly.ctcyh.java.request.RequestHeadDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ILocalSource {

    Context getWeakReference();

    public String getUserID();

    RequestHeadDTO requestHeadDTO();

    String rasByStr(String str);
}
