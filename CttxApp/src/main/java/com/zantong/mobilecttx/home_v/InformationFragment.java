package com.zantong.mobilecttx.home_v;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.home_p.InfomationPresenter;
import com.zantong.mobilecttx.home_p.InformationContract;
import com.zantong.mobilecttx.home_p.ModuleInfoAdapter;

/**
 * 资讯页面页面
 */
public class InformationFragment extends RecyclerListFragment<NewsInfoResponse.DataBean.NewsBean>
        implements InformationContract.InformationView {


    private static final String TYPE_ITEM = "type_item";
    private InformationContract.InformationPresenter mPresenter;

    @Override
    public BaseAdapter<NewsInfoResponse.DataBean.NewsBean> createAdapter() {
        return new ModuleInfoAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {

    }

    @Override
    protected void initPresenter() {
        InfomationPresenter mPresenter = new InfomationPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.findByType();
    }

    public static InformationFragment newInstance(String targetPath) {
        InformationFragment fragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_ITEM, targetPath);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setPresenter(InformationContract.InformationPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void findByTypeSucceed(NewsInfoResponse result) {
        setSimpleDataResult(result.getData().getNews());
    }

    @Override
    public void findByTypeError(String message) {

    }

    @Override
    public int getTypeItem() {
        String type = getArguments().getString(TYPE_ITEM);
        if (TextUtils.isEmpty(type)) type = "1";
        return Integer.valueOf(type);
    }
}
