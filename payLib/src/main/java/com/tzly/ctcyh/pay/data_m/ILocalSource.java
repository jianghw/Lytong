package com.tzly.ctcyh.pay.data_m;

import android.content.Context;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface ILocalSource {

    Context getWeakReference();

    public String getUserID();
}
