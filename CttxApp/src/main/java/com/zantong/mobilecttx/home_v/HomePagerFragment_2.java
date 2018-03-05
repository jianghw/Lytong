package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.bean.UnimpededBannerBean;
import com.zantong.mobilecttx.home_p.IUnimpededBannerContract;
import com.zantong.mobilecttx.home_p.UnimpededBannerAdapter;
import com.zantong.mobilecttx.home_p.UnimpededBannerPresenter;
import com.zantong.mobilecttx.map.activity.BaiduMapParentActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.CarBeautyActivity;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面
 */
public class HomePagerFragment_2 extends RecyclerListFragment<UnimpededBannerBean>
        implements IUnimpededBannerContract.IUnimpededBannerView {

    private static final String LIST_DATA = "LIST_DATA";
    private IUnimpededBannerContract.IUnimpededBannerPresenter presenter;

    public static HomePagerFragment_2 newInstance(List<UnimpededBannerBean> list) {
        HomePagerFragment_2 fragment = new HomePagerFragment_2();
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

        clickItemData(bannerBean);
    }

    @Override
    protected void initPresenter() {
        UnimpededBannerPresenter mPresenter = new UnimpededBannerPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        responseData(null);
    }

    @Override
    public void setPresenter(IUnimpededBannerContract.IUnimpededBannerPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 点击处理事件
     */
    public void clickItemData(UnimpededBannerBean bannerBean) {
        if (bannerBean != null && !TextUtils.isEmpty(bannerBean.getTargetPath())) {
            String path = bannerBean.getTargetPath();
            if (path.contains("http")) {//启动公司自己html
                MainRouter.gotoWebHtmlActivity(getActivity(), bannerBean.getTitle(), path);
            } else if (path.equals("native_app_recharge")) {//加油充值
                MainRouter.gotoRechargeActivity(getActivity());
            } else if (path.equals("native_app_loan")) {

            } else if (path.equals("native_app_toast")) {//敬请期待
                toastShort("此功能开发中,敬请期待~");
            } else if (path.equals("native_app_daijia")) {//代驾
                enterDrivingActivity();
            } else if (path.equals("native_app_enhancement")) {//科目强化
                MainRouter.gotoSubjectActivity(getActivity(), 0);
            } else if (path.equals("native_app_sparring")) {//陪练
                MainRouter.gotoSparringActivity(getActivity(), 0);
            } else if (path.equals("native_app_drive_share")) {//分享
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.share_position_extra, 1);
                Act.getInstance().gotoLoginByIntent(getActivity(), ShareParentActivity.class, intent);
            } else if (path.equals("native_app_car_beauty")) {//汽车美容
                Act.getInstance().gotoIntentLogin(getActivity(), CarBeautyActivity.class);
            } else if (path.equals("native_app_driver")) {//驾校报名
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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


    @PermissionSuccess(requestCode = 3000)
    public void doOilMapSuccess() {
        gotoOilMap();
    }


    @PermissionFail(requestCode = 3000)
    public void doOilMapFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

    @PermissionSuccess(requestCode = 4000)
    public void doMapSuccess() {
        gotoMap();
    }

    @PermissionFail(requestCode = 4000)
    public void doMapFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }

}
