package com.zantong.mobile.widght.refresh;

/**
 * 刷新加载回调接口
 *
 * @author chenjing
 */
public interface OnPullListener {
    /**
     * 刷新操作
     */
    void onRefresh(PullToRefreshLayout pullToRefreshLayout);

    /**
     * 加载操作
     */
    void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
}
