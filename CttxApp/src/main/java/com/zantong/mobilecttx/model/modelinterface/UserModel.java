package com.zantong.mobilecttx.model.modelinterface;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;

/**
 * Created by Administrator on 2016/4/22.
 */
public interface UserModel {

    void loadUser(final OnLoadServiceBackUI listener);
}
