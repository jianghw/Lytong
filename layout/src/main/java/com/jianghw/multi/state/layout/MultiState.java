package com.jianghw.multi.state.layout;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jianghw on 2017/9/19.
 * Description: 用来固定状态
 * <p>
 * 1.RetentionPolicy.SOURCE —— 这种类型的Annotations只在源代码级别保留,编译时就会被忽略
 * 2.RetentionPolicy.CLASS —— 这种类型的Annotations编译时被保留,在class文件中存在,但JVM将会忽略
 * 3.RetentionPolicy.RUNTIME —— 这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用.
 */
@IntDef({MultiState.CONTENT, MultiState.EMPTY, MultiState.LOADING, MultiState.ERROR, MultiState.NET_ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface MultiState {
    int CONTENT = 0;
    int LOADING = 1;
    int EMPTY = 2;
    int ERROR = 3;
    int NET_ERROR = 4;
}
