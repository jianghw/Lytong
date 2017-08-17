package com.zantong.mobilecttx.home.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.contract.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.daijia.activity.DrivingActivity;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule.activity.SparringActivity;
import com.zantong.mobilecttx.fahrschule.activity.SubjectActivity;
import com.zantong.mobilecttx.home.adapter.FavorableBannerImgHolderView;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannersBean;
import com.zantong.mobilecttx.presenter.home.HomeFavorableFtyPresenter;
import com.zantong.mobilecttx.share.activity.CarBeautyActivity;
import com.zantong.mobilecttx.share.activity.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;
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
    private LinearLayout mLayAdmissions;
    private TextView mTvAdmissions;
    private LinearLayout mLaySparring;
    private LinearLayout mLaySubject;
    private TextView mTvSparring;
    private TextView mTvSubject;

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
        if (mPresenter != null) mPresenter.getBanner();
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

        String buffer = getStringBuffer(5120);
        mTvAdmissions.setText(Html.fromHtml(buffer));

        mTvSparring.setText(Html.fromHtml(getStringBuffer(100)));
        mTvSubject.setText(Html.fromHtml(getStringBuffer(960)));
    }

    @NonNull
    private String getStringBuffer(int price) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("￥");
        buffer.append("&#160;");
        buffer.append(price);
        buffer.append("&#160;");
        buffer.append("<font color=\"#b3b3b3\">");
        buffer.append("起");
        buffer.append("</font>");
        return buffer.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCampaignCustom(false);

        if (mPresenter != null) mPresenter.getBanner();
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
        mLayOrder.setOnClickListener(this);
        mLayRepair = (LinearLayout) view.findViewById(R.id.lay_repair);
        mLayRepair.setOnClickListener(this);
        mLayRiver = (LinearLayout) view.findViewById(R.id.lay_river);
        mLayRiver.setOnClickListener(this);
        mLayBeauty = (LinearLayout) view.findViewById(R.id.lay_beauty);
        mLayBeauty.setOnClickListener(this);
        mLayAdmissions = (LinearLayout) view.findViewById(R.id.lay_admissions);
        mLayAdmissions.setOnClickListener(this);
        mTvAdmissions = (TextView) view.findViewById(R.id.tv_admissions);
        mTvSparring = (TextView) view.findViewById(R.id.tv_sparring);
        mTvSubject = (TextView) view.findViewById(R.id.tv_subject);
        mLaySparring = (LinearLayout) view.findViewById(R.id.lay_sparring);
        mLaySparring.setOnClickListener(this);
        mLaySubject = (LinearLayout) view.findViewById(R.id.lay_subject);
        mLaySubject.setOnClickListener(this);

        mImgBanner = (ImageView) view.findViewById(R.id.img_banner);
        mImgBanner.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_banner:
                Intent intent = new Intent();
                intent.putExtra(GlobalConstant.putExtra.share_position_extra, 1);
                Act.getInstance().gotoLoginByIntent(getActivity(), ShareParentActivity.class, intent);
                break;
            case R.id.lay_order://加油
                GlobalConfig.getInstance().eventIdByUMeng(21);

                Act.getInstance().gotoIntentLogin(getActivity(), RechargeActivity.class);
                break;
            case R.id.lay_repair://害羞
                GlobalConfig.getInstance().eventIdByUMeng(23);

                PublicData.getInstance().webviewUrl = Config.HOME_CAR_WASH_URL;
                PublicData.getInstance().webviewTitle = "洗车";
                PublicData.getInstance().isCheckLogin = true;
                Act.getInstance().gotoIntent(getActivity(), BrowserActivity.class);
                break;
            case R.id.lay_beauty://美容
                GlobalConfig.getInstance().eventIdByUMeng(27);

                Act.getInstance().gotoIntentLogin(getActivity(), CarBeautyActivity.class);
                break;
            case R.id.lay_river://代驾
                GlobalConfig.getInstance().eventIdByUMeng(25);

                enterDrivingActivity();
                break;
            case R.id.lay_admissions://驾校招生
                GlobalConfig.getInstance().eventIdByUMeng(28);

                Act.getInstance().gotoIntentLogin(getActivity(), FahrschuleActivity.class);
                break;
            case R.id.lay_sparring://驾校陪练
                Act.getInstance().gotoIntentLogin(getActivity(), SparringActivity.class);
                break;
            case R.id.lay_subject://科目强化
                Act.getInstance().gotoIntentLogin(getActivity(), SubjectActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 进入年检页面
     */
    public void enterDrivingActivity() {
        if (!PublicData.getInstance().loginFlag) {
            Act.getInstance().gotoIntentLogin(getActivity(), DrivingActivity.class);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            Act.getInstance().gotoIntentLogin(getActivity(), DrivingActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        Act.getInstance().gotoIntentLogin(getActivity(), DrivingActivity.class);
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }
}
