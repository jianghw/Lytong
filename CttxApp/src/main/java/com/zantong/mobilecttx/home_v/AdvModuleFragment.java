package com.zantong.mobilecttx.home_v;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzly.ctcyh.java.response.violation.AdvModuleResponse;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.router.custom.SpaceItemDecoration;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.home_p.AdvActivePresenter;
import com.zantong.mobilecttx.home_p.IAdvActiveContract;
import com.zantong.mobilecttx.violation_p.AdvImageAdapter;
import com.zantong.mobilecttx.violation_p.AdvModuleAdapter;

import java.util.List;

/**
 * Fragment 活动
 */
public class AdvModuleFragment extends Fragment implements IAdvActiveContract.IAdvActiveView {

    private IAdvActiveContract.IAdvActivePresenter mPresenter;

    private RecyclerView recycler;
    private RecyclerView recyclerModule;
    private AdvImageAdapter adapterAdv;
    private AdvModuleAdapter adapterModule;

    public static AdvModuleFragment newInstance() {
        AdvModuleFragment f = new AdvModuleFragment();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    /**
     * 当该fragment被添加到Activity时回调，该方法只会被调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 创建fragment时被回调。该方法只会调用一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdvActivePresenter presenter = new AdvActivePresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    /**
     * 每次创建、绘制该fragment的View组件时回调该方法，fragment将会显示该方法的View组件
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_status_by_av, null);

        bindContent(fragment);
        return fragment;
        //        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void bindContent(View childView) {
        recyclerModule = (RecyclerView) childView.findViewById(R.id.module_rv);
        GridLayoutManager layoutManager = new GridLayoutManager(Utils.getContext(), 3);
        recyclerModule.setLayoutManager(layoutManager);
        recyclerModule.setNestedScrollingEnabled(false);
        adapterModule = new AdvModuleAdapter();
        recyclerModule.setAdapter(adapterModule);
        adapterModule.setItemClickListener(new AdvModuleAdapter.ClickUrlAdapter() {
            @Override
            public void clickUrl(String url, int id) {
                RouterUtils.gotoByStatistId(url, "热门优惠", String.valueOf(id), getActivity());
            }
        });

        recycler = (RecyclerView) childView.findViewById(R.id.adv_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Utils.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setNestedScrollingEnabled(false);
        recycler.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.pt_20)));

        adapterAdv = new AdvImageAdapter();
        recycler.setAdapter(adapterAdv);
        adapterAdv.setItemClickListener(new AdvImageAdapter.ClickUrlAdapter() {
            @Override
            public void clickUrl(String url, int id) {
                RouterUtils.gotoByAdvId(url, "热门优惠", String.valueOf(id), getActivity());
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter != null) mPresenter.moduleList();
        if (mPresenter != null) mPresenter.findIsValidAdvert();
    }

    /**
     * 当fragment所在的Activity被创建完成后调用该方法。
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 销毁该fragment所包含的View组件时调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 销毁fragment时被回调，该方法只会被调用一次。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当该fragment从Activity中被删除、被替换完成时回调该方法，
     * onDestory()方法后一定会回调onDetach()方法。该方法只会被调用一次。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Context getContext() {
        if (super.getContext() != null && super.getContext().getApplicationContext() != null) {
            return super.getContext().getApplicationContext();
        }
        return super.getContext();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void setPresenter(IAdvActiveContract.IAdvActivePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void validAdvertError(String message) {

    }

    @Override
    public void validAdvertSucceed(ValidAdvResponse result) {
        List<ValidAdvResponse.DataBean> resultData = result.getData();

       /* List<ValidAdvResponse.DataBean> list = new ArrayList<>();
        list.addAll(resultData);
        list.addAll(resultData);*/

        adapterAdv.append(resultData);
    }

    @Override
    public void moduleListError(String message) {

    }

    @Override
    public void moduleListSucceed(AdvModuleResponse result) {
        List<AdvModuleResponse.DataBean> beanList = result.getData();

        /*List<AdvModuleResponse.DataBean> list = new ArrayList<>();
        list.addAll(beanList);
        list.addAll(beanList);*/

        adapterModule.append(beanList);
    }
}