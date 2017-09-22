package com.zantong.mobile.model.modelinterface;

import com.zantong.mobile.api.OnLoadServiceBackUI;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface SimpleModel {
    void loadUpdate(final OnLoadServiceBackUI listener, String url, int index);
}
