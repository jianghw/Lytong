package com.zantong.mobilecttx.order.fragment;

import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.order.bean.OrderListBean;

/**
 * 所有订单
 */
public class OrderAllStatusFragment extends BaseRecyclerListJxFragment<OrderListBean> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static OrderAllStatusFragment newInstance() {
        return new OrderAllStatusFragment();
    }

    public static OrderAllStatusFragment newInstance(String param1, String param2) {
        OrderAllStatusFragment fragment = new OrderAllStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseAdapter<OrderListBean> createAdapter() {
        return null;
    }


    @Override
    protected void onRecyclerItemClick(View view, Object data) {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return 0;
    }

    @Override
    protected void initFragmentView(View view) {

    }

    @Override
    protected void onFirstDataVisible() {

    }

    @Override
    protected void DestroyViewAndThing() {

    }
}
