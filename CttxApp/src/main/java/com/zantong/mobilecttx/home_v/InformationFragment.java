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
    protected boolean isRefresh() {
        return true;
    }

    @Override
    public BaseAdapter<NewsInfoResponse.DataBean.NewsBean> createAdapter() {
        return new ModuleInfoAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
      /*  if (data != null && data instanceof NewsInfoResponse.DataBean.NewsBean) {
            NewsInfoResponse.DataBean.NewsBean newsBean = (NewsInfoResponse.DataBean.NewsBean) data;
            String url = BuildConfig.isDeta
                    ? "http://h5dev.liyingtong.com/news/index.html?id=" + newsBean.getId()
                    : "http://h5.liyingtong.com/news/index.html?id=" + newsBean.getId();
            MainRouter.gotoWebHtmlActivity(getActivity(), newsBean.getTitle(), url);
        }*/
    }

    @Override
    protected void initPresenter() {
        InfomationPresenter mPresenter = new InfomationPresenter(
                Injection.provideRepository(Utils.getContext()), this);

        getCustomRecycler().setNestedScrollingEnabled(true);
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
    public void responseData(Object response) {
        if (response instanceof NewsInfoResponse) {
            NewsInfoResponse data = (NewsInfoResponse) response;
            setSimpleDataResult(data.getData().getNews());
        } else
            responseError();
    }

    @Override
    public int getTypeItem() {
        String type = getArguments().getString(TYPE_ITEM);
        if (TextUtils.isEmpty(type)) type = "1";
        return Integer.valueOf(type);
    }
}
