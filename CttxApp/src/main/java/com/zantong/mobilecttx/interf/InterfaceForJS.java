package com.zantong.mobilecttx.interf;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.zantong.mobilecttx.card.activity.CardHomeActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.daijia.activity.DrivingActivity;
import com.zantong.mobilecttx.huodong.activity.HundredRuleActivity;
import com.zantong.mobilecttx.user.activity.GetBonusActivity;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.QueryActivity;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * @author Sandy
 *         create at 16/6/1 下午5:54
 */
public class InterfaceForJS {
    Context context;

    public InterfaceForJS(Context context) {
        this.context = context;
    }


    @JavascriptInterface
    public void ToastMsg(String msg) {
        ToastUtils.showShort(context, msg);
    }

    @JavascriptInterface
    public boolean isLogin() {
        if (!PublicData.getInstance().loginFlag) {
            return false;
        } else {
            return true;
        }
    }

    @JavascriptInterface
    public void gotoLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @JavascriptInterface
    public Location getLocaltion() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
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

    //绑畅通卡
    @JavascriptInterface
    public void bindCard() {
        if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {
            Act.getInstance().lauchIntentToLogin(context, CardHomeActivity.class);
        } else {
            Act.getInstance().lauchIntentToLogin(context, MyCardActivity.class);
        }
    }

    //加油充值
    @JavascriptInterface
    public void addOil() {
        context.startActivity(new Intent(context, RechargeActivity.class));
    }

    //代驾
    @JavascriptInterface
    public void chaser() {
        context.startActivity(new Intent(context, DrivingActivity.class));

    }

    //分享领积分
    @JavascriptInterface
    public void shareActivity() {
        context.startActivity(new Intent(context, GetBonusActivity.class));
    }

    //获取用户ID
    @JavascriptInterface
    public String getUserId() {
        LogUtils.i("userId" + PublicData.getInstance().userID);
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
        context.startActivity(new Intent(context, QueryActivity.class));
    }

    //获取用户ID
    @JavascriptInterface
    public String getEncreptUserId() {
        LogUtils.i("userId" + PublicData.getInstance().userID);
        return RSAUtils.strByEncryptionLiYing(context, PublicData.getInstance().userID, true);
    }

    //跳转到积分规则页面
    @JavascriptInterface
    public void popAttention() {
        context.startActivity(new Intent(context, HundredRuleActivity.class));
    }

}
