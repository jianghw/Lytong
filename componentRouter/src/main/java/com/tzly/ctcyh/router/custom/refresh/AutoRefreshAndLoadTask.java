package com.tzly.ctcyh.router.custom.refresh;

import android.os.AsyncTask;

/**
 * Created by jianghw on 2017/8/24.
 * Description:
 * Update by:
 * Update day:
 */
class AutoRefreshAndLoadTask extends AsyncTask<Integer, Float, String> {

    private PullToRefreshLayout mPullToRefreshLayout;

    public AutoRefreshAndLoadTask(PullToRefreshLayout pullToRefreshLayout) {
        mPullToRefreshLayout = pullToRefreshLayout;
    }

    @Override
    protected String doInBackground(Integer... params) {
        while (mPullToRefreshLayout.pullDownY < 4 / 3 * mPullToRefreshLayout.mRefreshDist) {
            mPullToRefreshLayout.pullDownY += mPullToRefreshLayout.mMoveSpeed;
            publishProgress(mPullToRefreshLayout.pullDownY);
            try {
                Thread.sleep(params[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mPullToRefreshLayout.changeState(PullToRefreshLayout.REFRESHING);
        // 刷新操作
        if (mPullToRefreshLayout.mIPullListener != null)
            mPullToRefreshLayout.mIPullListener.onRefresh(mPullToRefreshLayout);
        if (null != mPullToRefreshLayout.mOnRefreshProcessListener) {
            mPullToRefreshLayout.mOnRefreshProcessListener.onStart(mPullToRefreshLayout.mRefreshView,
                    IPullProcessListener.REFRESH);
        }
        mPullToRefreshLayout.hide();
    }

    @Override
    protected void onProgressUpdate(Float... values) {
        if (mPullToRefreshLayout.pullDownY > mPullToRefreshLayout.mRefreshDist)
            mPullToRefreshLayout.changeState(PullToRefreshLayout.RELEASE_TO_REFRESH);
        mPullToRefreshLayout.requestLayout();
    }

}
