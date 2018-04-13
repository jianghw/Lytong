package com.zantong.mobilecttx.home_v;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.custom.banner.CBViewHolderCreator;
import com.tzly.ctcyh.router.custom.banner.ConvenientBanner;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.home.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.contract.home.INativeItemListener;
import com.zantong.mobilecttx.home.adapter.HomeDiscountsAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannersBean;
import com.zantong.mobilecttx.home.bean.ChildrenBean;
import com.zantong.mobilecttx.home.bean.ModuleBean;
import com.zantong.mobilecttx.home.bean.ModuleResponse;
import com.zantong.mobilecttx.home_p.FavorableBannerImgHolderView;
import com.zantong.mobilecttx.home_p.HomeFavorableFtyPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠页面
 */
public class HomeDiscountsFragment extends RecyclerListFragment<ModuleBean>
        implements IHomeFavorableFtyContract.IHomeFavorableFtyView {

    private ConvenientBanner mCustomConvenientBanner;

    /**
     * mPresenter
     */
    private IHomeFavorableFtyContract.IHomeFavorableFtyPresenter mPresenter;
    private HomeDiscountsAdapter mDiscountsAdapter;

    public static HomeDiscountsFragment newInstance() {
        return new HomeDiscountsFragment();
    }

    @Override
    public BaseAdapter<ModuleBean> createAdapter() {
        mDiscountsAdapter = new HomeDiscountsAdapter();
        return mDiscountsAdapter;
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
        localImages.add(R.mipmap.default_330_160);
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
    protected void initPresenter() {
        HomeFavorableFtyPresenter presenter = new HomeFavorableFtyPresenter(
                Injection.provideRepository(Utils.getContext()), this);

        if (mDiscountsAdapter != null)
            mDiscountsAdapter.setNativeItemListener(new INativeItemListener() {
                @Override
                public void onItemClick(ChildrenBean childrenBean) {
                    gotoPageByTargetPath(childrenBean);
                }
            });
    }

    /**
     * 本地控件点击事件
     */
    public void gotoPageByTargetPath(ChildrenBean childrenBean) {
        CarApiClient.commitAdClick(Utils.getContext(),
                childrenBean != null ? childrenBean.getId() : -1, "2",
                new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                    }
                });

        int statisticsId = childrenBean != null ? childrenBean.getStatisticsId() : -1;
        RouterUtils.gotoByStatistId(childrenBean.getTargetPath(), childrenBean.getTitle(),
                String.valueOf(statisticsId), getActivity());
    }

    @Override
    public void setPresenter(IHomeFavorableFtyContract.IHomeFavorableFtyPresenter presenter) {
        mPresenter = presenter;
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
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.moduleTree();
        if (mPresenter != null) mPresenter.getBanner();
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
                        return new FavorableBannerImgHolderView(new IDiscountsBanner() {
                            @Override
                            public void gotoByStatistId(String url, int statisticsId) {
                                RouterUtils.gotoByStatistId(url, "商品推荐",
                                        String.valueOf(statisticsId), getActivity());
                            }
                        });
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
        toastShort(responseDesc);
    }

    /**
     * 送豪礼
     */
    @Override
    public void getRewardSucceed(BannerBean bannerBean) {
    }

    /**
     * 数据加载成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof ModuleResponse) {
            ModuleResponse moduleResponse = (ModuleResponse) response;
            List<ModuleBean> moduleBeanList = moduleResponse.getData();
            setSimpleDataResult(moduleBeanList);
        } else
            responseError();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

}
