package com.tzly.ctcyh.router;

/**
 * 定义规则 application 注入
 */
public interface IApplicationLike {

    void onCreate();

    void onStop();
}
