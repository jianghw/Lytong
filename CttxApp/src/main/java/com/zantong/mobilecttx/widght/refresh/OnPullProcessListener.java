package com.zantong.mobilecttx.widght.refresh;

import android.view.View;

/**
 * 下拉刷新或上拉加载更多过程监听器
 *
 * @author LynnChurch
 */
public interface OnPullProcessListener {

    int REFRESH = 1; // 刷新

    int LOADMORE = 2; // 加载更多

    /**
     * 准备 （提示下拉刷新或上拉加载更多）
     *
     * @param v
     * @param which 刷新或加载更多
     */
    void onPrepare(View v, int which);

    /**
     * 开始 （提示释放刷新或释放加载更多）
     *
     * @param v
     * @param which 刷新或加载更多
     */
    void onStart(View v, int which);

    /**
     * 处理中
     *
     * @param v
     * @param which 刷新或加载更多
     */
    void onHandling(View v, int which);

    /**
     * 完成
     *
     * @param v
     * @param which 刷新或加载更多
     */
    void onFinish(View v, int which);

    /**
     * 用于获取拉取的距离
     *
     * @param v
     * @param pullDistance
     * @param which        刷新或加载更多
     */
    void onPull(View v, float pullDistance, int which);
}
