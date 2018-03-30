package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.custom.scroll.ScrollBottomLayout;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.adapter.superadapter.SingleAdapter;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountResponse;
import com.zantong.mobilecttx.share_p.StatisTextCountAdapter;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有订单
 */
public class ViolationListFragment extends RecyclerListFragment<ViolationBean> {

    private IViolationListUi mViolationListUi;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof IViolationListUi)
            mViolationListUi = (IViolationListUi) activity;
    }

    @Override
    protected void loadingFirstData() {
    }

    @Override
    protected void clickRefreshData() {
        showStateLoading();
        onRefreshData();
    }

    @Override
    protected void onRefreshData() {
        if (mViolationListUi != null) mViolationListUi.refreshListData(0);
    }

    /**
     * 显示数据
     */
    @Override
    protected void responseData(Object response) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String toJson = gson.toJson(response);
        List<ViolationBean> carResult = gson.fromJson(toJson,
                new TypeToken<List<ViolationBean>>() {
                }.getType());

        setSimpleDataResult(carResult);
    }

    public static ViolationListFragment newInstance() {
        return new ViolationListFragment();
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<ViolationBean> createAdapter() {
        return new ViolationResultAdapter(mViolationListUi);
    }

    /**
     * @deprecated sb
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
    }

    @Override
    protected void initPresenter() {
    }


    @Override
    protected void initScrollChildView(View view) {
        if (view == null || !(view instanceof ScrollBottomLayout)) return;
        ScrollBottomLayout bottomLayout = (ScrollBottomLayout) view;
        View childView = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_status_by_av, bottomLayout, true);
        bottomLayout.customChildView(childView);

        RecyclerView recyclerView = (RecyclerView) childView.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        StatisTextCountAdapter mGridAdapter = new StatisTextCountAdapter();
        recyclerView.setAdapter(mGridAdapter);
        List<StatistCountResponse.DataBean.ListBean> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new StatistCountResponse.DataBean.ListBean("111", 11, "11", 11));
        }
        mGridAdapter.append(data);

        bottomLayout.setRecycler(recyclerView);
    }
}
