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

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.browser.BrowserHtmlActivity;
import com.zantong.mobilecttx.contract.home.IHomeFavorableFtyContract;
import com.zantong.mobilecttx.contract.home.INativeItemListener;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.fahrschule.activity.SparringActivity;
import com.zantong.mobilecttx.home.adapter.FavorableBannerImgHolderView;
import com.zantong.mobilecttx.home.adapter.HomeDiscountsAdapter;
import com.zantong.mobilecttx.home.adapter.LocalImageHolderView;
import com.zantong.mobilecttx.home.bean.BannerBean;
import com.zantong.mobilecttx.home.bean.BannersBean;
import com.zantong.mobilecttx.home.bean.ChildrenBean;
import com.zantong.mobilecttx.home.bean.ModuleBean;
import com.zantong.mobilecttx.home.bean.ModuleResponse;
import com.zantong.mobilecttx.presenter.home.HomeFavorableFtyPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share.activity.CarBeautyActivity;
import com.zantong.mobilecttx.share.activity.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.custom.banner.CBViewHolderCreator;
import cn.qqtheme.framework.custom.banner.ConvenientBanner;
import cn.qqtheme.framework.global.JxConfig;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;


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
    protected void onRecyclerItemClick(View view, Object data) {}

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
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    protected void initFragmentView(View view) {
        HomeFavorableFtyPresenter presenter = new HomeFavorableFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);

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

        if (childrenBean != null && !TextUtils.isEmpty(childrenBean.getTargetPath())) {
            String path = childrenBean.getTargetPath();
            if (path.contains("http")) {//启动公司自己html
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, childrenBean.getTitle());
                intent.putExtra(JxGlobal.putExtra.browser_url_extra, path);
                Act.getInstance().gotoLoginByIntent(getActivity(), BrowserHtmlActivity.class, intent);
            } else if (path.equals("native_app_recharge")) {//加油充值
                MainRouter.gotoRechargeActivity(getActivity());
            } else if (path.equals("native_app_loan")) {

            } else if (path.equals("native_app_toast")) {
                ToastUtils.toastShort("此功能开发中,敬请期待~");
            } else if (path.equals("native_app_daijia")) {//代驾
                JxConfig.getInstance().eventIdByUMeng(25);
                enterDrivingActivity();
            } else if (path.equals("native_app_enhancement")) {//科目强化
                JxConfig.getInstance().eventIdByUMeng(37);
                MainRouter.gotoSubjectActivity(getActivity());
            } else if (path.equals("native_app_sparring")) {//陪练
                JxConfig.getInstance().eventIdByUMeng(38);
                Act.getInstance().gotoIntentLogin(getActivity(), SparringActivity.class);
            } else if (path.equals("native_app_drive_share")) {//分享
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.share_position_extra, 1);
                Act.getInstance().gotoLoginByIntent(getActivity(), ShareParentActivity.class, intent);
            } else if (path.equals("native_app_car_beauty")) {//汽车美容
                JxConfig.getInstance().eventIdByUMeng(27);
                Act.getInstance().gotoIntentLogin(getActivity(), CarBeautyActivity.class);
            } else if (path.equals("native_app_driver")) {//驾校报名
                JxConfig.getInstance().eventIdByUMeng(28);
                Act.getInstance().gotoIntentLogin(getActivity(), FahrschuleActivity.class);
            } else {//其他
                ToastUtils.toastShort("此版本暂无此状态页面,请更新最新版本");
            }
        }
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
    public void getRewardSucceed(BannerBean bannerBean) {}

    /**
     * 获取模块
     */
    @Override
    public void moduleTreeError(String message) {
        setSimpleDataResult(null);
        ToastUtils.toastShort(message + "请下拉刷新");
    }

    /**
     * 数据加载成功
     */
    @Override
    public void moduleTreeSucceed(ModuleResponse result) {
        List<ModuleBean> moduleBeanList = result.getData();
        setSimpleDataResult(moduleBeanList);
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
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
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }
}
