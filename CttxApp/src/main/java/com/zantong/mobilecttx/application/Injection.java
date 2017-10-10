package com.zantong.mobilecttx.application;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zantong.mobilecttx.model.repository.LocalData;
import com.zantong.mobilecttx.model.repository.RemoteData;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

/**
 * Created by jianghw on 2017/4/26.
 * 依赖注入类
 */

public class Injection {
    /**
     * 初始化数据仓库
     *
     * @param applicationContext
     * @return
     */
    public static RepositoryManager provideRepository(@NonNull Context applicationContext) {
        return RepositoryManager.getInstance(
                RemoteData.getInstance(), LocalData.getInstance(applicationContext));
    }
}
