package com.tzly.ctcyh.router.custom.refresh;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 执行自动回滚的handler
 */
public class UpdateHandler extends Handler {

    private WeakReference<PullToRefreshLayout> mLayout;

    public UpdateHandler(PullToRefreshLayout layout) {
        mLayout = new WeakReference<>(layout);
    }

    @Override
    public void handleMessage(Message msg) {
        PullToRefreshLayout refreshLayout = mLayout.get();
        if (null != refreshLayout) {
            // 回弹速度随下拉距离moveDeltaY增大而增大
            refreshLayout.mMoveSpeed = (float) (18 + 5 * Math.tan(Math.PI / 2
                    / refreshLayout.getMeasuredHeight()
                    * (refreshLayout.pullDownY + Math.abs(refreshLayout.pullUpY))));
            if (!refreshLayout.isTouch) {
                // 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
                if (refreshLayout.mViewState == PullToRefreshLayout.REFRESHING
                        && refreshLayout.pullDownY <= refreshLayout.mRefreshDist) {
                    refreshLayout.pullDownY = refreshLayout.mRefreshDist;
                    refreshLayout.mMyTimer.cancel();
                } else if (refreshLayout.mViewState == PullToRefreshLayout.LOADING
                        && -refreshLayout.pullUpY <= refreshLayout.mUploadMoreDist) {
                    refreshLayout.pullUpY = -refreshLayout.mUploadMoreDist;
                    refreshLayout.mMyTimer.cancel();
                }
            }

            if (refreshLayout.pullDownY > 0)
                refreshLayout.pullDownY -= refreshLayout.mMoveSpeed;
            else if (refreshLayout.pullUpY < 0)
                refreshLayout.pullUpY += refreshLayout.mMoveSpeed;
            if (refreshLayout.pullDownY < 0) {
                // 已完成回弹
                refreshLayout.pullDownY = 0;
                if (null == refreshLayout.customRefreshView) {
                    if (refreshLayout.pullDownArrows == null) {
                        return;
                    }
                    refreshLayout.pullDownArrows.clearAnimation();
                }
                // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                if (refreshLayout.mViewState != PullToRefreshLayout.REFRESHING && refreshLayout.mViewState != PullToRefreshLayout.LOADING)
                    refreshLayout.changeState(PullToRefreshLayout.INIT);
                refreshLayout.mMyTimer.cancel();
                refreshLayout.requestLayout();
            }
            if (refreshLayout.pullUpY > 0) {
                // 已完成回弹
                refreshLayout.pullUpY = 0;
                if (null == refreshLayout.customUploadMoreView) {
                    refreshLayout.pullUpArrows.clearAnimation();
                }
                // 隐藏上拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                if (refreshLayout.mViewState != PullToRefreshLayout.REFRESHING && refreshLayout.mViewState != PullToRefreshLayout.LOADING)
                    refreshLayout.changeState(PullToRefreshLayout.INIT);
                refreshLayout.mMyTimer.cancel();
                refreshLayout.requestLayout();
            }
            // 刷新布局,会自动调用onLayout
            refreshLayout.requestLayout();
            // 没有拖拉或者回弹完成
            if (refreshLayout.pullDownY + Math.abs(refreshLayout.pullUpY) == 0)
                refreshLayout.mMyTimer.cancel();
        }
    }
}
