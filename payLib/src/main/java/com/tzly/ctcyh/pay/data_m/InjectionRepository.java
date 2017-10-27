package com.tzly.ctcyh.pay.data_m;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by jianghw on 2017/4/26.
 * 依赖注入类
 */

public class InjectionRepository {
    /**
     * 初始化数据仓库
     *
     * @param applicationContext
     * @return
     */
    public static PayDataManager provideRepository(@NonNull Context applicationContext) {
        return PayDataManager.getInstance(
                RemoteData.getInstance(), LocalData.getInstance(applicationContext));
    }
}
