package com.tzly.ctcyh.router;

/**
 * ui 路由规则 页面+地址信息
 * 组合接口
 */

public interface IUiRouter extends IComponentRouter {
    /**
     * 优先级
     */
    int PRIORITY_NORMAL = 0;
    int PRIORITY_LOW = -1000;
    int PRIORITY_HEIGHT = 1000;

    void registerUI(IComponentRouter router);

    /**
     * 包含优先级的注册
     *
     * @param router   传递规则
     * @param priority 优先级
     */
    void registerUI(IComponentRouter router, int priority);

    /**
     * 注销路由规则
     *
     * @param router 传递规则
     */
    void unregisterUI(IComponentRouter router);
}
