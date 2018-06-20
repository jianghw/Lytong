package com.zantong.mobilecttx.violation_v;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.RudenessScreenHelper;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home_v.RouterUtils;
import com.zantong.mobilecttx.violation_p.ViolationListAdapter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import java.util.List;

/**
 * 所有订单
 */
public class ViolationListFragment extends RecyclerListFragment<ViolationBean> {

    private static final String TAG_POSITON = "tag_position";

    private IViolationListUi mViolationListUi;

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mViolationListUi = null;
    }

    public static ViolationListFragment newInstance(String position) {
        ViolationListFragment fragment = new ViolationListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_POSITON, position);
        fragment.setArguments(args);
        return fragment;
    }

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

    /**
     * 下拉刷新
     */
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

        if (carResult != null && !carResult.isEmpty()) {
            carResult.add(new ViolationBean());
        }

        setSimpleDataResult(carResult);
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<ViolationBean> createAdapter() {
        return new ViolationListAdapter(mViolationListUi);
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

    /**
     * 不同布局
     */
    protected boolean needScrollRecycler() {
        return getArguments().getString(TAG_POSITON).equals("1");
    }

    /**
     * 底部控件
     */
    @Override
    protected void bindExtraBottomView(View view) {
        RouterUtils.gotoAdvModuleFragment(
                getChildFragmentManager(), view.getId(), mViolationListUi.titleCar());
    }

    protected void statusViewSelf(View view, int state) {
        TextView tvEmpty = (TextView) view.findViewById(R.id.tv_empty);

        if (state == MultiState.EMPTY) {
            Drawable nav_up = getResources().getDrawable(R.mipmap.ic_layout_empty);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tvEmpty.setCompoundDrawables(null, nav_up, null, null);

            tvEmpty.setText("亲!未查到您的违章");
        }
        String position = getArguments().getString(TAG_POSITON);

        if (position != null && position.equals("1")) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvEmpty.getLayoutParams();
            layoutParams.topMargin = RudenessScreenHelper.ptInpx(30);
            tvEmpty.setLayoutParams(layoutParams);

            if (state == MultiState.EMPTY) {
                RouterUtils.gotoAdvModuleFragment(getChildFragmentManager(),
                        R.id.empty_child, mViolationListUi.titleCar());
            } else if (state == MultiState.ERROR) {
                RouterUtils.gotoAdvModuleFragment(getChildFragmentManager(),
                        R.id.error_child, mViolationListUi.titleCar());
            } else if (state == MultiState.NET_ERROR) {
                RouterUtils.gotoAdvModuleFragment(getChildFragmentManager(),
                        R.id.net_child, mViolationListUi.titleCar());
            }
        }
    }
}
