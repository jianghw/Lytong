package com.zantong.mobilecttx.home_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.bean.UnimpededBannerBean;
import com.zantong.mobilecttx.home_p.IUnimpededBannerContract;
import com.zantong.mobilecttx.home_p.UnimpededBannerAdapter;
import com.zantong.mobilecttx.home_p.UnimpededBannerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面
 */
public class HomePagerFragment extends RecyclerListFragment<UnimpededBannerBean>
        implements IUnimpededBannerContract.IUnimpededBannerView {

    private static final String LIST_DATA = "LIST_DATA";
    private IUnimpededBannerContract.IUnimpededBannerPresenter presenter;

    public static HomePagerFragment newInstance(List<UnimpededBannerBean> list) {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        ArrayList<UnimpededBannerBean> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        bundle.putParcelableArrayList(LIST_DATA, arrayList);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected int reSetOrientation() {
        return 1;
    }

    protected boolean isRefresh() {
        return false;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected void responseData(Object response) {
        ArrayList<UnimpededBannerBean> lis = getArguments().getParcelableArrayList(LIST_DATA);
        setSimpleDataResult(lis);
    }

    @Override
    public BaseAdapter<UnimpededBannerBean> createAdapter() {
        return new UnimpededBannerAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof UnimpededBannerBean)) return;
        UnimpededBannerBean bannerBean = (UnimpededBannerBean) data;

        if (presenter != null) presenter.saveStatisticsCount(
                String.valueOf(bannerBean.getStatisticsId()));

        //点击事件
        FragmentManager manager = getChildFragmentManager();
        Fragment fragment = manager.findFragmentByTag("router_fgt");
        if (fragment != null && fragment instanceof RouterFragment) {
            RouterFragment routerFragment = (RouterFragment) fragment;
            routerFragment.clickItemData(bannerBean.getTargetPath(), bannerBean.getTitle());
        } else {
            ToastUtils.toastShort("未知错误");
        }
    }

    @Override
    protected void initPresenter() {
        UnimpededBannerPresenter mPresenter = new UnimpededBannerPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        responseData(null);

        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //创建fragment但是不绘制UI
        RouterFragment htmlFragment = RouterFragment.newInstance();
        transaction.add(htmlFragment, "router_fgt").commit();
    }

    @Override
    public void setPresenter(IUnimpededBannerContract.IUnimpededBannerPresenter presenter) {
        this.presenter = presenter;
    }

}
