package com.tzly.ctcyh.pay.html_v;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.tzly.ctcyh.java.request.card.ApplyCTCardDTO;
import com.tzly.ctcyh.java.response.orc.BindCarBean;
import com.tzly.ctcyh.java.response.orc.DrivingOcrBean;
import com.tzly.ctcyh.java.response.violation.ViolationNumBean;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.html_p.IWebHtmlContract;
import com.tzly.ctcyh.pay.html_p.WebHtmlPresenter;
import com.tzly.ctcyh.pay.response.OrderDetailBean;
import com.tzly.ctcyh.pay.response.OrderDetailResponse;
import com.tzly.ctcyh.pay.response.PayUrlResponse;
import com.tzly.ctcyh.pay.response.PayWeixinResponse;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;

import static com.tzly.ctcyh.router.custom.primission.PermissionGen.PER_REQUEST_CODE;
import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;

/**
 * Fragment 下拉刷新基类
 */
public class WebHtmlFragment extends Fragment implements IWebHtmlContract.IWebHtmlView {

    private static final String BANK_NAME = "bank_name";
    private static final String BANK_MOBILE = "bank_mobile";
    private static final String BANK_CERTNUM = "bank_certnum";
    private static final String BANK_CARDNAME = "bank_cardname";

    private FmentToAtyable mFmentToAtyable;
    private IWebHtmlContract.IWebHtmlPresenter mPresenter;
    /**
     * 支付分渠道
     */
    private String channel;

    public static WebHtmlFragment newInstance() {
        WebHtmlFragment f = new WebHtmlFragment();
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
        if (activity instanceof FmentToAtyable) {
            mFmentToAtyable = (FmentToAtyable) activity;
        }
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

        WebHtmlPresenter presenter = new WebHtmlPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
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

    @Override
    public void showLoading() {
        if (mFmentToAtyable != null) mFmentToAtyable.showLoading();
    }

    @Override
    public void dismissLoading() {
        if (mFmentToAtyable != null) mFmentToAtyable.dismissLoading();
    }

    @Override
    public void setPresenter(IWebHtmlContract.IWebHtmlPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getOrderId() {
        return null;
    }

    public void bank_v003(String violationNum) {
        if (mPresenter != null) mPresenter.bank_v003_01(violationNum);
    }

    public void orderDetail(String orderId) {
        if (mPresenter != null) mPresenter.intervalOrder(orderId);
    }

    /**
     * 网络响应
     */
    @Override
    public void orderDetailCompleted() {
        errorStatus();
    }

    @Override
    public void intervalError(String message) {
        toastShort(message);
        errorStatus();
    }

    @Override
    public void orderDetailError(String message) {
        toastShort(message);
        errorStatus();
    }

    /**
     * 支付成功
     */
    @Override
    public void orderDetailSucceed(OrderDetailResponse response) {
        OrderDetailBean orderDetailBean = response.getData();
        if (orderDetailBean != null) {
            int orderStatus = orderDetailBean.getOrderStatus();
            //成功页面
            if (orderStatus == 1) succeedStatus(orderDetailBean);
        }
    }

    /**
     * 违章支付成功
     */
    protected void succeedStatus(OrderDetailBean orderDetailBean) {
        String channel = mFmentToAtyable.getChannel();
        toastShort("支付完成");

        if (orderDetailBean.getType() == 2 || orderDetailBean.getType() == 6) {
            PayRouter.gotoPaySucceedActivity(getContext(), String.valueOf(orderDetailBean.getType()));
        } else if (TextUtils.isEmpty(channel)) {
            PayRouter.gotoMainActivity(getContext(), 1);
        } else {
            PayRouter.gotoActiveActivity(getContext(), Integer.valueOf(channel));
        }
    }

    protected void errorStatus() {
        String channel = mFmentToAtyable.getChannel();
        toastShort("未完成支付");
        if (TextUtils.isEmpty(channel)) {
            PayRouter.gotoMainActivity(getContext(), 1);
        } else {
            PayRouter.gotoActiveActivity(getContext(), Integer.valueOf(channel));
        }
    }

    @Override
    public void bankPayHtmlError(String message) {
        toastShort(message);
    }

    @Override
    public void bankPayHtmlSucceed(PayUrlResponse response, String orderId) {
        if (TextUtils.isEmpty(this.channel)) toastShort("渠道标记值为空");
        //web里打开
        PayRouter.gotoWebHtmlActivity(getActivity(),
                "银行支付", response.getData(), orderId, 1, this.channel);
    }

    @Override
    public void weChatPayError(String message) {
        toastShort(message);
    }

    @Override
    public void weChatPaySucceed(PayWeixinResponse response, String orderId) {
        if (TextUtils.isEmpty(this.channel)) toastShort("渠道标记值为空");
        //web里打开
        PayRouter.gotoWebHtmlActivity(getActivity(),
                "微信支付", response.getData().getMweburl(), orderId, 4, this.channel);
    }

    @Override
    public void bank_v003_01Error(String msg) {
        toastShort("获取违章数据失败" + msg);
        gotoActive();
    }

    @Override
    public void updateStateError(String msg) {
        toastShort("更新同步数据失败" + msg);
        gotoActive();
    }

    @Override
    public void updateStateSucceed(ViolationNumBean result) {
        String processste = result.getRspInfo().getProcessste();
        if (processste.equals("1") || processste.equals("3")) {
            PayRouter.gotoPaySucceedActivity(getContext(), "2");
        }else{
            gotoActive();
        }
    }

    private void gotoActive() {
        if (TextUtils.isEmpty(mFmentToAtyable.getEnginenum()))
            PayRouter.gotoMainActivity(getContext(), 1);
        else
            PayRouter.gotoActiveActivity(getContext(), 2);
    }

    /**
     * javascript--java
     */
    @JavascriptInterface
    public void ToastMsg(String msg) {
        ToastUtils.toastShort(msg);
    }

    @JavascriptInterface
    public void LogUtilsMsg(String msg) {
        LogUtils.e(msg);
    }

    @JavascriptInterface
    public boolean isLogin() {
        return PayRouter.isLogin();
    }

    @JavascriptInterface
    public void gotoLogin() {
        PayRouter.gotoLoginActivity(getContext());
    }

    @JavascriptInterface
    public Location getLocaltion() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            LocationListener locationListener = new LocationListener() {
                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {
                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {
                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    //绑畅通卡
    @JavascriptInterface
    public void bindCard() {
        if (getBindCardStatus()==1) {
            PayRouter.gotoUnblockedCardActivity(getContext());
        } else {
            PayRouter.gotoMyCardActivity(getContext());
        }
    }

    //加油充值
    @JavascriptInterface
    public void addOil() {
        PayRouter.gotoRechargeActivity(getContext());
    }

    //代驾
    @JavascriptInterface
    public void chaser() {
        PayRouter.gotoDrivingActivity(getContext());
    }

    //分享领积分
    @JavascriptInterface
    public void shareActivity() {
    }

    //获取用户ID
    @JavascriptInterface
    public String getUserId() {
        return PayRouter.getUserID();
    }

    //获取用户ID
    @JavascriptInterface
    public String getEncreptUserId() {
        return PayRouter.getRASUserID();
    }

    //加密工具
    @JavascriptInterface
    public String getRASByStr(String str) {
        return PayRouter.getRASByStr(str);
    }

    //获取绑卡状态 0已绑卡  1未绑卡
    @JavascriptInterface
    public int getBindCardStatus() {
        return TextUtils.isEmpty(PayRouter.getUserFilenum()) ? 1 : 0;
    }

    //查询违章
    @JavascriptInterface
    public void queryViolations() {
        PayRouter.gotoViolationActivity(getContext());
    }

    //跳转到积分规则页面
    @JavascriptInterface
    public void popAttention() {
        PayRouter.gotoHundredRuleActivity(getContext());
    }

    //去年检地图地址
    @JavascriptInterface
    public void goNianjianMap() {
        PayRouter.gotoNianjianMapActivity(getContext());
    }

    //去往违章列表页面
    @JavascriptInterface
    public void searchViolationList(String carnum, String enginenum, String carnumtype) {
        PayRouter.gotoViolationListActivity(getContext(), carnum, enginenum, carnumtype);
    }

    //js调摄像机
    @JavascriptInterface
    public void callCamera() {
        takePhoto();
    }

    /**
     * 拍照
     */
    public void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE});
        } else {
            goToCamera();
        }
    }

    /**
     * 行驶证 拍照前权限调用
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        goToCamera();
    }

    protected void goToCamera() {
        PayRouter.gotoVehicleCameraActivity(getActivity());
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        toastShort("您已关闭摄像头权限,请设置中打开");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //拍照回调
        if (requestCode == 110 && resultCode == 200) {
            vehicleCameraSucceed(data);
        }
    }

    private void vehicleCameraSucceed(Intent intent) {
        String vehicleInfo = intent.getStringExtra("vehicleInfo");
        BindCarBean carBean = new Gson().fromJson(vehicleInfo, BindCarBean.class);

        DrivingOcrBean bean = new DrivingOcrBean();
        bean.setIssueDate(carBean.getIssueDate());
        bean.setName(carBean.getName());
        bean.setRegisterDate(carBean.getRegisterDate());
        bean.setVin(carBean.getVin());
        bean.setVehicleType(carBean.getVehicleType());
        bean.setCardNo(carBean.getCardNo());
        bean.setEnginePN(carBean.getEnginePN());
        bean.setModel(carBean.getModel());
        bean.setAddress(carBean.getAddr());
        bean.setUseCharacte(carBean.getUseCharace());

        if (!TextUtils.isEmpty(vehicleInfo)) {
            if (mFmentToAtyable != null)
                mFmentToAtyable.callbackCamera("javascript:callbackCamera(" + new Gson().toJson(bean) + ");");
        } else {
            toastShort("行驶证图片解析失败(55)，请重试");
        }
    }

    //银行支付
    @JavascriptInterface
    public void payMOTOrder(String coupon, String orderId, String amount) {
        float orderPrice = Float.valueOf(amount);
        int price = (int) (orderPrice * 100);
        if (mPresenter != null) mPresenter.bankPayHtml(orderId, String.valueOf(price), coupon);
    }

    //微信支付
    @JavascriptInterface
    public void weChatPay(String coupon, String orderId, String amount) {
        float orderPrice = Float.valueOf(amount);
        int price = (int) (orderPrice * 100);
        if (mPresenter != null) mPresenter.weChatPay(coupon, orderId, String.valueOf(price));
    }

    //阿里支付
    @JavascriptInterface
    public void aliPay(String coupon, String orderId, String amount) {
        float orderPrice = Float.valueOf(amount);
        int price = (int) (orderPrice * 100);

        PayRouter.gotoAliHtmlActivity(getActivity(),
                "支付宝支付", orderId, 3, price, Integer.valueOf(coupon), this.channel);
    }

    //支付渠道
    @JavascriptInterface
    public void channelAction(String channel) {
        this.channel = channel;
    }

    /**
     * 保存用户资料
     */
    @JavascriptInterface
    public void saveBankByCard(String name, String mobile, String certNum, String cardName) {
        Bundle bundle = getArguments();
        bundle.putString(BANK_NAME, name);
        bundle.putString(BANK_MOBILE, mobile);
        bundle.putString(BANK_CERTNUM, certNum);
        bundle.putString(BANK_CARDNAME, cardName);
    }

    /**
     * 提交用户资料
     */
    @JavascriptInterface
    public void submitBankByCard() {
        ApplyCTCardDTO applyCTCardDTO = new ApplyCTCardDTO();
        applyCTCardDTO.setUsrid(getUserId());
        Bundle bundle = getArguments();
        applyCTCardDTO.setUsrname(bundle.getString(BANK_NAME));
        applyCTCardDTO.setCtfnum(getRASByStr(bundle.getString(BANK_CERTNUM)));
        applyCTCardDTO.setPhoenum(getRASByStr(bundle.getString(BANK_MOBILE)));
        applyCTCardDTO.setCardname(bundle.getString(BANK_CARDNAME));

        if (mPresenter != null) mPresenter.applyRecord(applyCTCardDTO);
    }

    //关闭页面
    @JavascriptInterface
    public void backApp() {
        if (mFmentToAtyable != null) mFmentToAtyable.backApp();
    }
}
