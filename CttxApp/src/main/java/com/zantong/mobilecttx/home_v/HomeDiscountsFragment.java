package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.custom.banner.CBViewHolderCreator;
import com.tzly.ctcyh.router.custom.banner.ConvenientBanner;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.home.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.contract.home.INativeItemListener;
import com.zantong.mobilecttx.home_p.FavorableBannerImgHolderView;
import com.zantong.mobilecttx.home.adapter.HomeDiscountsAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannersBean;
import com.zantong.mobilecttx.home.bean.ChildrenBean;
import com.zantong.mobilecttx.home.bean.ModuleBean;
import com.zantong.mobilecttx.home.bean.ModuleResponse;
import com.zantong.mobilecttx.home_p.HomeFavorableFtyPresenter;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.CarBeautyActivity;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

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
                Injection.provideRepository(getContext()), this);

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

        int contenId = childrenBean != null ? childrenBean.getStatisticsId() : -1;
        if (mPresenter != null && contenId >= 0)
            mPresenter.saveStatisticsCount(String.valueOf(contenId));

        gotoWhere(childrenBean);
    }

    private void gotoWhere(ChildrenBean childrenBean) {
        if (childrenBean != null && !TextUtils.isEmpty(childrenBean.getTargetPath())) {
            String path = childrenBean.getTargetPath();
            if (path.contains("http")) {//启动公司自己html
                MainRouter.gotoWebHtmlActivity(getActivity(), childrenBean.getTitle(), path);
            } else if (path.equals("native_app_recharge")) {//加油充值
                MainRouter.gotoRechargeActivity(getActivity());
            } else if (path.equals("native_app_loan")) {

            } else if (path.equals("native_app_toast")) {
                toastShort("此功能开发中,敬请期待~");
            } else if (path.equals("native_app_daijia")) {//代驾
                MobUtils.getInstance().eventIdByUMeng(25);
                enterDrivingActivity();
            } else if (path.equals("native_app_enhancement")) {//科目强化
                MobUtils.getInstance().eventIdByUMeng(37);
                MainRouter.gotoSubjectActivity(getActivity(), 0);
            } else if (path.equals("native_app_sparring")) {//陪练
                MobUtils.getInstance().eventIdByUMeng(38);
                MainRouter.gotoSparringActivity(getActivity(), 0);
            } else if (path.equals("native_app_drive_share")) {//分享
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.share_position_extra, 1);
                Act.getInstance().gotoLoginByIntent(getActivity(), ShareParentActivity.class, intent);
            } else if (path.equals("native_app_car_beauty")) {//汽车美容
                MobUtils.getInstance().eventIdByUMeng(27);
                Act.getInstance().gotoIntentLogin(getActivity(), CarBeautyActivity.class);
            } else if (path.equals("native_app_driver")) {//驾校报名
                MobUtils.getInstance().eventIdByUMeng(28);
                MainRouter.gotoFahrschuleActivity(getActivity(), 0);
            } else if (path.equals("native_app_yearCheckMap")) {//年检地图
                enterMapActivity();
            } else if (path.equals("native_app_oilStation")) {//优惠加油站
                showOilContacts();
            } else if (path.equals("native_app_endorsement")) {//违章缴费记录
                licenseCheckGrade(2);
            } else if (path.equals("native_app_drivingLicense")) {//驾驶证查分
                licenseCheckGrade(1);
            } else if (path.equals("native_app_97recharge")) {//97加油
                MainRouter.gotoDiscountOilActivity(getActivity());
            } else if (path.equals("native_app_97buyCard")) {//97加油购卡
                MainRouter.gotoBidOilActivity(getActivity());
            } else {//其他
                toastShort("此版本暂无此状态页面,请更新最新版本");
            }
        }
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
                            public void getStatistId(int statisticsId) {
                                if (mPresenter != null) {
                                    mPresenter.saveStatisticsCount(String.valueOf(statisticsId));
                                }
                            }

                            @Override
                            public void gotoByPath(String url) {
                                ChildrenBean banner = new ChildrenBean();
                                banner.setTargetPath(url);
                                banner.setTitle("优惠页面");
                                gotoWhere(banner);
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

    protected void licenseCheckGrade(int position) {
        String grade = SPUtils.instance().getString(SPUtils.USER_GRADE);
        LicenseFileNumDTO fromJson = null;
        if (!TextUtils.isEmpty(grade)) {
            fromJson = new Gson().fromJson(grade, LicenseFileNumDTO.class);
        } else if (!TextUtils.isEmpty(MainRouter.getUserFilenum()) &&
                !TextUtils.isEmpty(MainRouter.getUserGetdate())) {
            fromJson = new LicenseFileNumDTO();
            fromJson.setFilenum(MainRouter.getUserFilenum());
            fromJson.setStrtdt(MainRouter.getUserGetdate());
        }

        if (fromJson != null && position == 2) {
            MainRouter.gotoPaymentActivity(getActivity(), new Gson().toJson(fromJson));
        } else if (fromJson != null && position == 1) {
            MainRouter.gotoLicenseDetailActivity(getActivity(), new Gson().toJson(fromJson));
        } else {
            MainRouter.gotoLicenseGradeActivity(getActivity(), position);
        }
    }

    /**
     * 加油地图
     */
    public void showOilContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 3000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoOilMap();
        }
    }

    private void gotoOilMap() {
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.map_type_extra, JxGlobal.MapType.annual_oil_map);
        Act.getInstance().gotoLoginByIntent(getActivity(), BaiduMapParentActivity.class, intent);
    }

    /**
     * 进入地图年检页面
     */
    public void enterMapActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 4000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoMap();
        }
    }

    private void gotoMap() {
        MainRouter.gotoMapActivity(getActivity());
    }

    /**
     * 进入代驾页面
     */
    public void enterDrivingActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 2000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoDriving();
        }
    }

    private void gotoDriving() {
        MainRouter.gotoDrivingActivity(getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 2000)
    public void doDrivingSuccess() {
        gotoDriving();
    }

    @PermissionFail(requestCode = 2000)
    public void doDrivingFail() {
        toastShort("您已关闭定位权限,请手机设置中打开");
    }

    @PermissionSuccess(requestCode = 4000)
    public void doMapSuccess() {
        gotoMap();
    }

    @PermissionFail(requestCode = 4000)
    public void doMapFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }

    @PermissionSuccess(requestCode = 3000)
    public void doOilMapSuccess() {
        gotoOilMap();
    }


    @PermissionFail(requestCode = 3000)
    public void doOilMapFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

}
