package com.zantong.mobile.contract;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;

import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobile.chongzhi.activity.RechargeActivity;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.eventbus.DriveLicensePhotoEvent;
import com.zantong.mobile.eventbus.PayMotoOrderEvent;
import com.zantong.mobile.map.activity.BaiduMapParentActivity;
import com.zantong.mobile.login_v.OldLoginActivity;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.weizhang.activity.ViolationListActivity;
import com.zantong.mobile.weizhang.dto.ViolationDTO;

import org.greenrobot.eventbus.EventBus;

import com.tzly.annual.base.util.ToastUtils;

/**
 * @author Sandy
 *         create at 16/6/1 下午5:54
 */
public class InterfaceForJS {

    private Context mJSContext;

    public InterfaceForJS(Context context) {
        this.mJSContext = context;
    }

    @JavascriptInterface
    public void ToastMsg(String msg) {
        ToastUtils.showShort(mJSContext, msg);
    }

    @JavascriptInterface
    public boolean isLogin() {
        return PublicData.getInstance().loginFlag;
    }

    @JavascriptInterface
    public void gotoLogin() {
        mJSContext.startActivity(new Intent(mJSContext, OldLoginActivity.class));
    }

    @JavascriptInterface
    public Location getLocaltion() {
        LocationManager locationManager = (LocationManager) mJSContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mJSContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mJSContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location;
            } else {
                return null;
            }
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
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                return location;
            } else {
                return null;
            }
        }
    }

    //加油充值
    @JavascriptInterface
    public void addOil() {
        mJSContext.startActivity(new Intent(mJSContext, RechargeActivity.class));
    }

    //分享领积分
    @JavascriptInterface
    public void shareActivity() {
    }

    //获取用户ID
    @JavascriptInterface
    public String getUserId() {
        return PublicData.getInstance().userID;
    }

    //获取绑卡状态 0已绑卡  1未绑卡
    @JavascriptInterface
    public int getBindCardStatus() {
        return "".equals(PublicData.getInstance().filenum) ? 1 : 0;
    }

    //查询违章
    @JavascriptInterface
    public void queryViolations() {
    }

    //获取用户ID
    @JavascriptInterface
    public String getEncreptUserId() {
        return RSAUtils.strByEncryptionLiYing(PublicData.getInstance().userID, true);
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
        PublicData.getInstance().mHashMap.put("IllegalViolationName", carnum);
        PublicData.getInstance().mHashMap.put("carnum", carnum);
        PublicData.getInstance().mHashMap.put("enginenum", enginenum);
        PublicData.getInstance().mHashMap.put("carnumtype", carnumtype);

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
    }

}