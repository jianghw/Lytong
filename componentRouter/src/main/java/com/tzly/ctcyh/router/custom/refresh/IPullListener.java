package com.tzly.ctcyh.router.custom.refresh;

/**
 * 刷新加载回调接口
 *
 * @author chenjing
 */
public interface IPullListener {
    /**
     * 刷新操作
     */
    void onRefresh(PullToRefreshLayout pullToRefreshLayout);

    /**
     * 加载操作
     */
    void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
}
