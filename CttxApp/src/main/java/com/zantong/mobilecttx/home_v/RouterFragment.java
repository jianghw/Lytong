package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tzly.ctcyh.java.response.active.ActiveBean_1;
import com.tzly.ctcyh.java.response.active.ActiveConfigBean;
import com.tzly.ctcyh.java.response.active.ActiveConfigResponse;
import com.tzly.ctcyh.router.custom.dialog.CouponDialogFragment;
import com.tzly.ctcyh.router.custom.dialog.DialogUtils;
import com.tzly.ctcyh.router.custom.dialog.IOnCouponSubmitListener;
import com.tzly.ctcyh.router.custom.dialog.MessageDialogFragment;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.home_p.ActivePresenter;
import com.zantong.mobilecttx.home_p.IActiveContract;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.CarBeautyActivity;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Fragment 下拉刷新基类
 */
public class RouterFragment extends Fragment implements IActiveContract.IActiveView {

    /**
     * 控制器
     */
    private IActiveContract.IActivePresenter mPresenter;

    private String mExtraChannel;
    private String mExtraRegisterDate;

    public static RouterFragment newInstance() {
        RouterFragment f = new RouterFragment();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    /**
     * 当该fragment被添加到Activity时回调，该方法只会被调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 创建fragment时被回调。该方法只会调用一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivePresenter presenter = new ActivePresenter(
                Injection.provideRepository(Utils.getContext()), this);

    }

    /**
     * 每次创建、绘制该fragment的View组件时回调该方法，fragment将会显示该方法的View组件
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 当fragment所在的Activity被创建完成后调用该方法。
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 销毁该fragment所包含的View组件时调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 销毁fragment时被回调，该方法只会被调用一次。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当该fragment从Activity中被删除、被替换完成时回调该方法，
     * onDestory()方法后一定会回调onDetach()方法。该方法只会被调用一次。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Context getContext() {
        if (super.getContext() != null && super.getContext().getApplicationContext() != null) {
            return super.getContext().getApplicationContext();
        }
        return super.getContext();
    }

    /**
     * 点击处理事件
     */
    public void clickItemData(String path, String title) {

        if (!TextUtils.isEmpty(path)) {
            if (path.contains("http")) {//启动公司自己html
                MainRouter.gotoWebHtmlActivity(getContext(), title, path);
            } else if (path.equals("native_app_recharge")) {//加油充值
                MainRouter.gotoRechargeActivity(getActivity());
            } else if (path.equals("native_app_loan")) {

            } else if (path.equals("native_app_toast")) {//敬请期待
                ToastUtils.toastShort("此功能开发中,敬请期待~");
            } else if (path.equals("native_app_daijia")) {//代驾
                enterDrivingActivity();
            } else if (path.equals("native_app_enhancement")) {//科目强化
                MainRouter.gotoSubjectActivity(getContext(), 0);
            } else if (path.equals("native_app_sparring")) {//陪练
                MainRouter.gotoSparringActivity(getContext(), 0);
            } else if (path.equals("native_app_drive_share")) {//分享
                Intent intent = new Intent();
                intent.putExtra(JxGlobal.putExtra.share_position_extra, 1);
                Act.getInstance().gotoLoginByIntent(getActivity(), ShareParentActivity.class, intent);
            } else if (path.equals("native_app_car_beauty")) {//汽车美容
                Act.getInstance().gotoIntentLogin(getActivity(), CarBeautyActivity.class);
            } else if (path.equals("native_app_driver")) {//驾校报名
                MainRouter.gotoFahrschuleActivity(getContext(), 0);
            } else if (path.equals("native_app_yearCheckMap")) {//年检地图
                gotoAnnualInspectionMap();
            } else if (path.equals("native_app_oilStation")) {//优惠加油站
                gotoAnnualOilMap();
            } else if (path.equals("native_app_endorsement")) {//违章缴费记录
                licenseCheckGrade(2);
            } else if (path.equals("native_app_drivingLicense")) {//驾驶证查分
                licenseCheckGrade(1);
            } else if (path.equals("native_app_97recharge")) {//97加油
                MainRouter.gotoDiscountOilActivity(getContext());
            } else if (path.equals("native_app_97buyCard")) {//97加油购卡
                MainRouter.gotoBidOilActivity(getContext());
            } else if (path.equals("native_app_mainRecharge")) {//97加油购卡前页
                MainRouter.gotoFoldOilActivity(getContext());
            } else if (path.equals("native_app_allOil")) {//加油统一入口
                MainRouter.gotoOilEnterActivity(getContext());
            } else if (path.equals("native_app_drivingNewScore")) {//驾照查分
                MainRouter.gotoLicenseCargoActivity(getContext());
            } else {//其他
                ToastUtils.toastShort("此版本暂无此状态页面,请更新最新版本");
            }
        }
    }

    /**
     * 进入地图年检页面
     */
    public void gotoAnnualInspectionMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 4000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            annualInspectionMap();
        }
    }

    private void annualInspectionMap() {
        MainRouter.gotoInspectionMapActivity(getContext());
    }

    /**
     * 加油地图
     */
    public void gotoAnnualOilMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 3000, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            annualOilMap();
        }
    }

    private void annualOilMap() {
        MainRouter.gotoOilMapActivity(getContext());
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
            MainRouter.gotoPaymentActivity(getContext(), new Gson().toJson(fromJson));
        } else if (fromJson != null && position == 1) {
            MainRouter.gotoLicenseDetailActivity(getContext(), new Gson().toJson(fromJson));
        } else {
            MainRouter.gotoLicenseGradeActivity(getContext(), position);
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
        MainRouter.gotoDrivingActivity(getContext());
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
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }

    @PermissionSuccess(requestCode = 3000)
    public void doOilMapSuccess() {
        annualOilMap();
    }


    @PermissionFail(requestCode = 3000)
    public void doOilMapFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }

    @PermissionSuccess(requestCode = 4000)
    public void doMapSuccess() {
        annualInspectionMap();
    }

    @PermissionFail(requestCode = 4000)
    public void doMapFail() {
        ToastUtils.toastShort("您已关闭定位权限,请手机设置中打开");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void setPresenter(IActiveContract.IActivePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getChannel() {
        return mExtraChannel;
    }

    @Override
    public void responseError(String message) {
        ToastUtils.toastShort(message);
    }

    private void saveSubmit(String id) {
        SPUtils.instance().put(id, true);
    }

    @Override
    public String getResisterDate() {
        return mExtraRegisterDate;
    }

    @Override
    public void configError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void configSucceed(ActiveConfigResponse response) {
        ActiveConfigBean bean = response.getData();
        //标记id
        final String mId_Only = bean.getId();
        boolean chancleID = SPUtils.instance().getBoolean(mId_Only, false);
        if (chancleID) return;

        String configType = bean.getConfigType();
        final String beanExtra = bean.getExtra();
        if (configType.equals("1")) {
            List<ActiveConfigBean.CouponInfoBean> infoBeanList = bean.getCouponInfo();
            if (!infoBeanList.isEmpty()) {
                ActiveConfigBean.CouponInfoBean infoBean = infoBeanList.get(0);

                CouponDialogFragment dialogFragment = CouponDialogFragment.newInstance(
                        infoBean.getCouponId(), infoBean.getCouponName(), infoBean.getCouponBusiness(),
                        infoBean.getCouponType(), infoBean.getCouponValue());

                dialogFragment.setClickListener(new IOnCouponSubmitListener() {
                    @Override
                    public void submit(String couponId) {
                        if (mPresenter != null) mPresenter.receiveCoupon(couponId);
                        saveSubmit(mId_Only);
                        gotoHtmlByExtra(beanExtra);
                    }

                    @Override
                    public void cancel() {
                    }
                });
                dialogFragment.show(getChildFragmentManager(), "coupon_dialog");
            } else {
            }
        } else if (configType.equals("2")) {//消息
            MessageDialogFragment fragment = MessageDialogFragment.newInstance(beanExtra);
            fragment.setClickListener(new IOnCouponSubmitListener() {
                @Override
                public void submit(String couponId) {
                    saveSubmit(mId_Only);
                }

                @Override
                public void cancel() {
                }
            });

            DialogUtils.showDialog(getActivity(), fragment, "message_dialog");
        } else if (configType.equals("3")) {//网页
            if (!TextUtils.isEmpty(beanExtra)) gotoHtml(beanExtra);
        }
    }

    private void gotoHtmlByExtra(String beanExtra) {
        Type type = new TypeToken<List<ActiveBean_1>>() {
        }.getType();
        List<ActiveBean_1> beanList = new Gson().fromJson(beanExtra, type);
        if (beanList != null && !beanList.isEmpty()) {
            ActiveBean_1 bean_1 = beanList.get(0);
            if (!TextUtils.isEmpty(bean_1.getUrl())) gotoHtml(bean_1.getUrl());
        }
    }

    private void gotoHtml(String beanExtra) {
        MainRouter.gotoWebHtmlActivity(getContext(), "产品页面", beanExtra);
    }

    public void activeToShow(String channel, String date) {
        //渠道
        mExtraChannel = channel;
        //日期
        mExtraRegisterDate = date;

        if (mPresenter != null) mPresenter.getConfig(channel,date);
    }
}