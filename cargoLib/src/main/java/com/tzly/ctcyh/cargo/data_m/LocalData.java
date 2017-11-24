package com.tzly.ctcyh.cargo.data_m;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.cargo.router.CargoRouter;

import java.lang.ref.WeakReference;

/**
 * Created by jianghw on 2017/4/26.
 * 本地数据处理
 */

public class LocalData implements ILocalSource {
    @Nullable
    private static LocalData INSTANCE = null;
    private final WeakReference<Context> weakReference;

    /**
     * 懒汉式，线程不安全
     */
    public static LocalData getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalData(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private LocalData(Context context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    public Context getWeakReference() {
        return weakReference.get();
    }

    @Override
    public String getUserID() {
        return CargoRouter.getUserID();
    }
}
