package com.zantong.mobilecttx.model.modelinterface;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface SimpleModel {
    void loadUpdate(final OnLoadServiceBackUI listener, String url, int index);
}
