package com.zantong.mobilecttx.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.home.adapter.FavorableBannerImgHolderView;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannersBean;
import com.zantong.mobilecttx.interf.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.presenter.home.HomeFavorableFtyPresenter;
import com.zantong.mobilecttx.share.activity.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.widght.banner.CBViewHolderCreator;
import cn.qqtheme.framework.widght.banner.ConvenientBanner;

/**
 * 优惠页面
 */
public class HomeFavorableFragment extends BaseRefreshJxFragment
        implements IHomeFavorableFtyContract.IHomeFavorableFtyView, View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    /**
     * mPresenter
     */
    private IHomeFavorableFtyContract.IHomeFavorableFtyPresenter mPresenter;

    private ConvenientBanner mCustomConvenientBanner;
    private LinearLayout mLayOrder;
    private LinearLayout mLayRepair;
    private LinearLayout mLayRiver;
    /**
     * 洗车美容
     */
    private LinearLayout mLayBeauty;
    private ImageView mImgBanner;

    public static HomeFavorableFragment newInstance() {
        return new HomeFavorableFragment();
    }

    public static HomeFavorableFragment newInstance(String param1, String param2) {
        HomeFavorableFragment fragment = new HomeFavorableFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_home_favorable;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        HomeFavorableFtyPresenter mPresenter = new HomeFavorableFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(IHomeFavorableFtyContract.IHomeFavorableFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getBanner();

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
    }

    @Override
    public void onResume() {
        super.onResume();
        startCampaignCustom(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        startCampaignCustom(false);
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

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
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

    public void initView(View view) {
        mCustomConvenientBanner = (ConvenientBanner) view.findViewById(R.id.custom_convenientBanner);
        mLayOrder = (LinearLayout) view.findViewById(R.id.lay_order);
        mLayRepair = (LinearLayout) view.findViewById(R.id.lay_repair);
        mLayRiver = (LinearLayout) view.findViewById(R.id.lay_river);
        mLayBeauty = (LinearLayout) view.findViewById(R.id.lay_beauty);
        mImgBanner = (ImageView) view.findViewById(R.id.img_banner);
        mImgBanner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_banner:
                Act.getInstance().lauchIntentToLogin(getActivity(), ShareParentActivity.class);
                break;
            default:
                break;
        }
    }
}
