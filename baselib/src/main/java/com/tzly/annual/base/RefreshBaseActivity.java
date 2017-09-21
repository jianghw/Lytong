package com.tzly.annual.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;

import com.tzly.annual.base.custom.ScrollSwipeRefreshLayout;

import java.lang.ref.WeakReference;

/**
 * Created by jianghw on 2017/9/12.
 * Description: 基类activity
 * Update by:
 * Update day:
 */

public abstract class RefreshBaseActivity extends JxBaseActivity {

    private ScrollSwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected int initContentView() {
        return R.layout.base_activity_base_refresh;
    }

    protected void bindContentView(View contentView) {
        mSwipeRefreshLayout = (ScrollSwipeRefreshLayout) contentView.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setDistanceToTriggerSync(500);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mRefreshHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshHandler.sendEmptyMessage(100);
                            }
                        }, 100);
                    }
                });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View childView = inflater.inflate(initChildView(), mSwipeRefreshLayout, true);
        bindChildView(childView);
    }

    @Override
    protected void userRefreshData() {
        startRefreshData();
    }

    private RefreshHandler mRefreshHandler = new RefreshHandler(this);

    private static class RefreshHandler extends Handler {
        WeakReference<Activity> weakReference;

        private RefreshHandler(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference != null) {
                RefreshBaseActivity activity = (RefreshBaseActivity) weakReference.get();
                if (activity != null) {
                    switch (msg.what) {
                        case 100:
                            activity.startRefreshData();
                            break;
                        case 200:
                            activity.delayTime();
                            break;
                        case 300:
                            activity.endRefreshData();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * 延迟刷新结果
     */
    protected void delayTime() {
        mRefreshHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshHandler.sendEmptyMessage(300);
            }
        }, 3000);
    }

    /**
     * 刷新数据
     */
    protected void startRefreshData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRefreshHandler.sendEmptyMessage(200);
        userRefreshContentData();
    }

    protected void endRefreshData() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 用户刷新动作操作
     */
    protected abstract void userRefreshContentData();


    /**
     * 子控件布局
     */
    protected abstract int initChildView();

    /**
     * 绑定子控件
     */
    protected abstract void bindChildView(View childView);

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRefreshHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

}
