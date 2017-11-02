package com.zantong.mobilecttx.weizhang.fragment;

import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.weizhang.activity.ViolationListActivity;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import java.util.List;

/**
 * 所有订单
 */
public class ViolationListFragment extends BaseRecyclerListJxFragment<ViolationBean> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ViolationListActivity.RefreshListener mRefreshListener;

    private ViolationResultAdapter mCurAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static ViolationListFragment newInstance() {
        return new ViolationListFragment();
    }

    public static ViolationListFragment newInstance(String param1, String param2) {
        ViolationListFragment fragment = new ViolationListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<ViolationBean> createAdapter() {
        mCurAdapter = new ViolationResultAdapter();
        return mCurAdapter;
    }

    /**
     * @deprecated sb
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
    }

    @Override
    protected void onRefreshData() {
        if (mRefreshListener != null) mRefreshListener.refreshListData(0);
    }

    @Override
    protected void initFragmentView(View view) {
        mCurAdapter.setItemPayListener(new ViolationResultAdapter.ItemPayListener() {
            @Override
            public void doClickPay(ViolationBean bean) {
                if(mRefreshListener!=null)mRefreshListener.doClickPay(bean);
            }
        });
    }

    @Override
    protected void onFirstDataVisible() {}

    @Override
    protected void DestroyViewAndThing() {}

    /**
     * 显示数据
     */
    public void setPayOrderListData(List<ViolationBean> data) {
        setSimpleDataResult(data);
    }

    public void setRefreshListener(ViolationListActivity.RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }
}
