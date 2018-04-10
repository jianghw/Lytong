package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.custom.SpaceItemDecoration;
import com.tzly.ctcyh.router.custom.scroll.ScrollBottomLayout;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home_v.RouterFragment;
import com.zantong.mobilecttx.violation_p.AdvImageAdapter;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import java.util.List;

/**
 * 所有订单
 */
public class ViolationListFragment extends RecyclerListFragment<ViolationBean> {

    private static final String TAG_POSITON = "tag_position";
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

    public static ViolationListFragment newInstance(String position) {
        ViolationListFragment fragment = new ViolationListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_POSITON, position);
        fragment.setArguments(args);
        return fragment;
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

    RecyclerView recycler;

    @Override
    protected void initScrollChildView(View view) {
        if (view == null || !(view instanceof ScrollBottomLayout))
            return;
        if (getArguments().getString(TAG_POSITON) == null
                || getArguments().getString(TAG_POSITON).equals("1"))
            return;

        ScrollBottomLayout bottomLayout = (ScrollBottomLayout) view;
        View childView = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_status_by_av, bottomLayout, true);
        bottomLayout.customChildView(childView);

        recycler = (RecyclerView) childView.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setNestedScrollingEnabled(false);
        recycler.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.res_y_20)));

        bottomLayout.setRecycler(recycler);

        if (mViolationListUi != null) mViolationListUi.findIsValidAdvert();

        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //创建fragment但是不绘制UI
        RouterFragment htmlFragment = RouterFragment.newInstance();
        transaction.add(htmlFragment, "router_fgt").commit();
    }

    public void validAdvertSucceed(ValidAdvResponse result) {
        List<ValidAdvResponse.DataBean> resultData = result.getData();
        if (resultData == null || resultData.isEmpty()) return;

        AdvImageAdapter adapter = new AdvImageAdapter();
        if (recycler != null) recycler.setAdapter(adapter);
        adapter.append(resultData);
        adapter.setItemClickListener(new AdvImageAdapter.ClickUrlAdapter() {
            @Override
            public void clickUrl(String url) {
                gotoByPath(url);
            }
        });
    }

    public void gotoByPath(String url) {
        //点击事件
        FragmentManager manager = getChildFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_fgt");
        if (fragment != null && fragment instanceof RouterFragment) {
            RouterFragment routerFragment = (RouterFragment) fragment;
            routerFragment.clickItemData(url, "产品页面");
        } else {
            ToastUtils.toastShort("未知错误");
        }
    }

    protected void statusViewSelf(View view, int state) {
        TextView tvEmpty = (TextView) view.findViewById(R.id.tv_empty);

        if (state == MultiState.EMPTY) {
            Drawable nav_up = getResources().getDrawable(R.mipmap.ic_layout_empty);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tvEmpty.setCompoundDrawables(null, nav_up, null, null);
            tvEmpty.setText("您没有违章需要处理");
        }

    }
}
