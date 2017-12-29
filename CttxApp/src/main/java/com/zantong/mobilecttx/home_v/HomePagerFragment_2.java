package com.zantong.mobilecttx.home_v;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.bean.BaseResponse;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.bean.UnimpededBannerBean;
import com.zantong.mobilecttx.fahrschule_v.SparringActivity;
import com.zantong.mobilecttx.home_p.IUnimpededBannerContract;
import com.zantong.mobilecttx.home_p.UnimpededBannerAdapter;
import com.zantong.mobilecttx.home_p.UnimpededBannerPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.CarBeautyActivity;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

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
    private void clickItemData(UnimpededBannerBean bannerBean) {
        CarApiClient.commitAdClick(Utils.getContext(),
                bannerBean != null ? bannerBean.getId() : -1, "3",
                new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                    }
                });

        if (bannerBean != null && !TextUtils.isEmpty(bannerBean.getTargetPath())) {
            String path = bannerBean.getTargetPath();
            if (path.contains("http")) {//启动公司自己html
                MainRouter.gotoHtmlActivity(getActivity(), bannerBean.getTitle(), path);
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
            } else {//其他
                toastShort("此版本暂无此状态页面,请更新最新版本");
            }
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

}
