package com.zantong.mobilecttx.user.fragment;

import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.user.adapter.HelpAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import cn.qqtheme.framework.util.ToastUtils;

import java.util.Arrays;

/**
 * @author Sandy
 */
public class HelpFragment extends BaseListFragment<String>{

    public HelpFragment(){

    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        ToastUtils.showShort(this.getActivity(),data.toString());
    }

    @Override
    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    public BaseAdapter<String> createAdapter() {
        return new HelpAdapter();
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }
    @Override
    protected void getData() {
        String[] content = getResources().getStringArray(R.array.mine_help_content);
        setDataResult(Arrays.asList(content));
    }
}
