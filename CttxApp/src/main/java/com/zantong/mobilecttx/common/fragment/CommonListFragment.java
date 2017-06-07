package com.zantong.mobilecttx.common.fragment;

import android.content.Intent;
import android.view.View;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.adapter.CommonListAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.common.activity.CommonListActivity;

import java.util.Arrays;

/**
 * 公共列表类
 * @author Sandy
 * create at 16/10/13 上午1:38
 */
public class CommonListFragment extends BaseListFragment<String>{

    @Override
    protected void onLoadMoreData() {
    }

    @Override
    protected void onRefreshData() {
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        Intent intent = new Intent();
        intent.putExtra(CommonListActivity.COMMON_EXTRA, data.toString());
        PublicData.getInstance().commonListData = data.toString();
        getActivity().setResult(CommonListActivity.RESULT_COMMON_TYPE, intent);
        getActivity().finish();
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    public BaseAdapter<String> createAdapter() {
        return new CommonListAdapter();
    }

    @Override
    public void initData() {
        switch (PublicData.getInstance().commonListType){
            case 0:
                break;
            case 1://驾照有效期限
                setDataResult(Arrays.asList(this.getResources().getStringArray(R.array.driving_time)));
                break;
            case 2://准驾类型
                setDataResult(Arrays.asList(this.getResources().getStringArray(R.array.driving_type)));
                break;
            case 3://车辆类型
                setDataResult(Arrays.asList(this.getResources().getStringArray(R.array.car_type)));
                break;
        }
    }

}
