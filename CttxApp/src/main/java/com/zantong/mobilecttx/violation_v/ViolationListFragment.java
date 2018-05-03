package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.router.MainRouter;
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

    protected void initScrollChildView(View view) {

        if (getArguments().getString(TAG_POSITON) != null
                && getArguments().getString(TAG_POSITON).equals("1")) {

            FragmentManager manager = getChildFragmentManager();
            Fragment fragment = MainRouter.getAdvActiveFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_child, fragment, "scroll_child_fragment");
            transaction.commit();
        }
    }

    protected void statusViewSelf(View view, int state) {
        TextView tvEmpty = (TextView) view.findViewById(R.id.tv_empty);

        if (state == MultiState.EMPTY && getArguments().getString(TAG_POSITON).equals("1")) {
            Drawable nav_up = getResources().getDrawable(R.mipmap.ic_layout_empty);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tvEmpty.setCompoundDrawables(null, nav_up, null, null);
            tvEmpty.setText("您没有违章需要处理");

            FragmentManager manager = getChildFragmentManager();
            Fragment fragment = MainRouter.getAdvActiveFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_child, fragment, "scroll_child_fragment");
            transaction.commit();
        }
    }
}
