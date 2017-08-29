package com.zantong.mobilecttx.home.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.home.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.home.adapter.FavorableBannerImgHolderView;
import com.zantong.mobilecttx.home.adapter.HomeDiscountsAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannersBean;
import com.zantong.mobilecttx.home.bean.ModuleBean;
import com.zantong.mobilecttx.home.bean.ModuleResult;
import com.zantong.mobilecttx.presenter.home.HomeFavorableFtyPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.widght.banner.CBViewHolderCreator;
import cn.qqtheme.framework.widght.banner.ConvenientBanner;


/**
 * 优惠页面
 */
public class HomeDiscountsFragment extends BaseRecyclerListJxFragment<ModuleBean>
        implements IHomeFavorableFtyContract.IHomeFavorableFtyView {

    private ConvenientBanner mCustomConvenientBanner;

    /**
     * mPresenter
     */
    private IHomeFavorableFtyContract.IHomeFavorableFtyPresenter mPresenter;

    public static HomeDiscountsFragment newInstance() {
        return new HomeDiscountsFragment();
    }

    @Override
    public BaseAdapter<ModuleBean> createAdapter() {
        return new HomeDiscountsAdapter();
    }

    /**
     * 无需使用
     */
    protected boolean isNeedItemClick() {
        return false;
    }

    /**
     * @deprecated 不用
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
    }

    /**
     * 获取RecyclerHeader
     */
    protected View customViewHeader() {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.custom_recycler_header_banner, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(layoutParams);

        mCustomConvenientBanner = (ConvenientBanner) rootView.findViewById(R.id.custom_convenientBanner);

        //广告页本地加载
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.mipmap.banner);
        mCustomConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);

        return rootView;
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    protected void initFragmentView(View view) {
        HomeFavorableFtyPresenter presenter = new HomeFavorableFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(IHomeFavorableFtyContract.IHomeFavorableFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.moduleTree();
        if (mPresenter != null) mPresenter.getBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCampaignCustom(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        startCampaignCustom(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        startCampaignCustom(hidden);
    }

    private void startCampaignCustom(boolean hidden) {
        if (hidden) {//不可见时
            //停止翻页
            if (mCustomConvenientBanner != null) mCustomConvenientBanner.stopTurning();
        } else {
            //开始自动翻页
            if (mCustomConvenientBanner != null && !mCustomConvenientBanner.isRunning())
                mCustomConvenientBanner.startTurning(4000);
        }
    }

    /**
     * 广告页面
     */
    @Override
    public void getBannerSucceed(BannerBean bannerBean) {
        List<BannersBean> banners = bannerBean.getBanners();
        if (banners == null || banners.size() < 1) return;
        //广告页面
        mCustomConvenientBanner.setPages(
                new CBViewHolderCreator<FavorableBannerImgHolderView>() {
                    @Override
                    public FavorableBannerImgHolderView createHolder() {
                        return new FavorableBannerImgHolderView();
                    }
                },
                banners)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.icon_dot_nor, R.mipmap.icon_dot_sel})
                //设置翻页的效果，不需要翻页效果可用不设
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
    }

    @Override
    public void getBannerError(String responseDesc) {
        ToastUtils.toastShort(responseDesc);
    }

    /**
     * 送豪礼
     */
    @Override
    public void getRewardSucceed(BannerBean bannerBean) {
    }

    /**
     * 获取模块
     */
    @Override
    public void moduleTreeError(String message) {
        ToastUtils.toastShort(message + "请下拉刷新");
    }

    @Override
    public void moduleTreeSucceed(ModuleResult result) {
        List<ModuleBean> moduleBeanList = result.getData();
        setSimpleDataResult(moduleBeanList);
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }
}
