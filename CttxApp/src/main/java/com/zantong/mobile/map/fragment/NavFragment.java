package com.zantong.mobile.map.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import com.zantong.mobile.map.adapter.NavAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobile.base.fragment.BaseListFragment;
import com.zantong.mobile.model.AppInfo;

/**
 * 导航页面
 * @author Sandy
 * create at 16/11/25 下午2:53
 */
@SuppressLint("ValidFragment")
public class NavFragment extends BaseListFragment<AppInfo> {



    @Override
    public void initView(View view) {
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {

    }

    @Override
    public void initData() {
    }


    @Override
    public BaseAdapter<AppInfo> createAdapter() {
        return new NavAdapter();
    }

}
