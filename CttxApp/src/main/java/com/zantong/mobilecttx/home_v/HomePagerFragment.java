package com.zantong.mobilecttx.home_v;

import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.zantong.mobilecttx.base.bean.ModuleBannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面
 */
public class HomePagerFragment extends RecyclerListFragment<ModuleBannerBean> {

    private static final String LIST_DATA = "LIST_DATA";

    public static HomePagerFragment newInstance(List<ModuleBannerBean> list) {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        ArrayList<ModuleBannerBean> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        bundle.putParcelableArrayList(LIST_DATA, arrayList);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int reSetOrientation() {
        return 1;
    }

    protected boolean isRefresh() {
        return false;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected void responseData(Object response) {
        ArrayList<ModuleBannerBean> lis = getArguments().getParcelableArrayList(LIST_DATA);
        setSimpleDataResult(lis);
    }

    @Override
    public BaseAdapter<ModuleBannerBean> createAdapter() {
        return null;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof ModuleBannerBean)) return;
        ModuleBannerBean bannerBean = (ModuleBannerBean) data;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void loadingFirstData() {
        responseData(null);
    }

}
