package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.zantong.mobilecttx.weizhang.adapter.ViolationResultAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

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
    protected void loadingFirstData() {}

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
    protected void onRecyclerItemClick(View view, Object data) {}

    @Override
    protected void initPresenter() {}

}
