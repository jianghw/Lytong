package com.tzly.ctcyh.pay.html_v;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzly.ctcyh.router.base.JxBaseFragment;

/**
 * Fragment 下拉刷新基类
 */
public class Html5Fragment extends JxBaseFragment {

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    public static Html5Fragment newInstance(Activity activity) {
        Html5Fragment f = new Html5Fragment();
        return f;
    }

  /*  @JavascriptInterface
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
        if (Tools.isStrEmpty(LoginData.getInstance().filenum)) {
            Act.getInstance().gotoIntentLogin(mJSContext, UnblockedCardActivity.class);
        } else {
            Act.getInstance().gotoIntentLogin(mJSContext, MyCardActivity.class);
        }
    }

    //加油充值
    @JavascriptInterface
    public void addOil() {
        mJSContext.startActivity(new Intent(mJSContext, RechargeActivity.class));
    }

    //代驾
    @JavascriptInterface
    public void chaser() {
        mJSContext.startActivity(new Intent(mJSContext, DrivingActivity.class));
    }

    //分享领积分
    @JavascriptInterface
    public void shareActivity() {
    }

    //获取用户ID
    @JavascriptInterface
    public String getUserId() {
        return LoginData.getInstance().userID;
    }

    //获取绑卡状态 0已绑卡  1未绑卡
    @JavascriptInterface
    public int getBindCardStatus() {
        return "".equals(LoginData.getInstance().filenum) ? 1 : 0;
    }

    //查询违章
    @JavascriptInterface
    public void queryViolations() {
    }

    //获取用户ID
    @JavascriptInterface
    public String getEncreptUserId() {
        return RSAUtils.strByEncryptionLiYing(LoginData.getInstance().userID, true);
    }

    //跳转到积分规则页面
    @JavascriptInterface
    public void popAttention() {
        mJSContext.startActivity(new Intent(mJSContext, HundredRuleActivity.class));
    }

    @JavascriptInterface
    public void getSource(String html) {
        LogUtils.e("------" + html);
    }

    //去年检地图地址
    @JavascriptInterface
    public void goNianjianMap() {
        Act.getInstance().gotoIntentLogin(mJSContext, BaiduMapParentActivity.class);
    }

    //去往违章列表页面
    @JavascriptInterface
    public void searchViolationList(String carnum, String enginenum, String carnumtype) {
        LoginData.getInstance().mHashMap.put("IllegalViolationName", carnum);
        LoginData.getInstance().mHashMap.put("carnum", carnum);
        LoginData.getInstance().mHashMap.put("enginenum", enginenum);
        LoginData.getInstance().mHashMap.put("carnumtype", carnumtype);

        ViolationDTO dto = new ViolationDTO();
        dto.setCarnum(RSAUtils.strByEncryption(carnum, true));
        dto.setEnginenum(RSAUtils.strByEncryption(enginenum, true));
        dto.setCarnumtype(carnumtype);

        Intent intent = new Intent(mJSContext, ViolationListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("params", dto);
        intent.putExtras(bundle);
        intent.putExtra("plateNum", carnum);
        mJSContext.startActivity(intent);
    }

    //js调摄像机
    @JavascriptInterface
    public void callCamera() {
        EventBus.getDefault().post(new DriveLicensePhotoEvent());
    }

    //跳转支付页面
    @JavascriptInterface
    public void payMOTOrder(String orderId, String amount) {
        EventBus.getDefault().post(new PayMotoOrderEvent(orderId, amount));
    }*/

}
