package com.zantong.mobilecttx.daijia.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.PoiInfo;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.chongzhi.activity.ChooseAddressActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.daijia.bean.DJTokenResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaCreateResult;
import com.zantong.mobilecttx.daijia.dto.DaiJiaCreateDTO;
import com.zantong.mobilecttx.daijia.dto.DaiJiaDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.HashUtils;
import com.zantong.mobilecttx.utils.OnClickUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 代驾首页
 * Created by zhoujie on 2017/2/16.
 */

public class DrivingActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.driving_address)
    TextView mDrivingAddress;
    @Bind(R.id.driving_near_siji)
    TextView mDrivingSiJi;
    @Bind(R.id.driving_phone)
    EditText mDrivingPhone;


    private String city = "上海";
    private double latitude;
    private double longitude;
    private int driverNum;//附件代驾司机数

    private LocationClient mLocationClient = null;

    @Override
    public void initView() {
        setTitleText("代驾");
        mDrivingAddress.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void initData() {

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListenner());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        option.setProdName("music_make");
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        mDrivingPhone.setText(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        setEnsureText("代驾订单");
        mDrivingPhone.requestFocus();
        mDrivingPhone.setSelection(PublicData.getInstance().mLoginInfoBean.getPhoenum().length());
    }

    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        Act.getInstance().gotoIntentLogin(this, DrivingOrderActivity.class);
    }

    @OnClick({R.id.driving_address_layout, R.id.driving_address, R.id.frist_next, R.id.driving_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.driving_address_layout:
                MobclickAgent.onEvent(this, Config.getUMengID(18));
                Intent intent = new Intent(this, ChooseAddressActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.driving_address:
                MobclickAgent.onEvent(this, Config.getUMengID(18));
                Intent intent1 = new Intent(this, ChooseAddressActivity.class);
                startActivityForResult(intent1, 1000);
                break;
            case R.id.frist_next:
                if (OnClickUtils.isFastDoubleClick()) {
                    MobclickAgent.onEvent(this, Config.getUMengID(17));
                    huJiaoDriving();
                } else {
                    ToastUtils.toastShort( "亲，您点的太快了");
                }
                break;
            case R.id.driving_price:
                Act.getInstance().gotoIntentLogin(this, DrivingPriceActivity.class);
                break;
        }
    }

    /**
     * 呼叫代驾
     */
    private void huJiaoDriving() {
        String phone = mDrivingPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(this, "手机号不能为空");
            return;
        }
        String time = "1488253689";
        try {
            time = StringUtils.getTimeToStr();
        } catch (Exception e) {

        }

        DaiJiaCreateDTO dto = new DaiJiaCreateDTO();
        dto.setUsrId(RSAUtils.strByEncryptionLiYing(PublicData.getInstance().userID, true));
        dto.setName(phone);
        dto.setMobile(phone);
        dto.setAddress(mDrivingAddress.getText().toString());
        dto.setAddressLat(latitude);
        dto.setAddressLng(longitude);
        dto.setDriverNum("1");
        dto.setTime(time);

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("mobile", phone);
        hashMap.put("address", mDrivingAddress.getText().toString());
        hashMap.put("time", time);
        hashMap.put("addressLng", String.valueOf(longitude));
        hashMap.put("addressLat", String.valueOf(latitude));
        hashMap.put("name", phone);
        hashMap.put("driverNum", String.valueOf("1"));
        hashMap.put("usrId", PublicData.getInstance().userID);
        String hashStr = HashUtils.getSignature(hashMap);
        dto.setHash(hashStr);

        GlobalConfig.getInstance().eventIdByUMeng(26);

        CarApiClient.huJiaoDaiJia(this, dto, new CallBack<DaiJiaCreateResult>() {
            @Override
            public void onSuccess(DaiJiaCreateResult result) {
                if (result.getResponseCode() == 2000) {
                    ToastUtils.showShort(DrivingActivity.this, "呼叫成功");
                    String orderId = result.getData().getOrderId();
                    Act.getInstance().gotoIntent(DrivingActivity.this, DODetailBeingActivity.class, orderId);
                    finish();
                } else {
                    ToastUtils.showShort(DrivingActivity.this, result.getResponseDesc());
                }
            }
        });
    }

    /**
     * 获取附近车辆信息
     */
    private void getNearByDriver() {
        String time = "1488253689";
        try {
            time = StringUtils.getTimeToStr();
        } catch (Exception e) {

        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("cityName", city);
        hashMap.put("time", time);
        hashMap.put("latitude", String.valueOf(latitude));
        hashMap.put("longitude", String.valueOf(longitude));
        DaiJiaDTO dto = new DaiJiaDTO();
        dto.setCityName(city);
        dto.setTime(time);
        dto.setLatitude(String.valueOf(latitude));
        dto.setLongitude(String.valueOf(longitude));
        String hashStr = HashUtils.getSignature(hashMap);
        dto.setHash(hashStr);
        CarApiClient.getDaiJiaToken(this, dto, new CallBack<DJTokenResult>() {
            @Override
            public void onSuccess(DJTokenResult result) {
                if (result.getResponseCode() == 2000) {
                    mDrivingSiJi.setText("在您附近有" + result.getData().getNearByNum()
                            + "名司机在服务，最近的司机离你" + result.getData().getShortestDistance()
                            + "千米，离到达时间预计" + result.getData().getExpectTime() + "分钟。");
                    mDrivingSiJi.setVisibility(View.VISIBLE);
                    driverNum = Integer.valueOf(result.getData().getNearByNum());
                    preparation();
                }
            }
        });
    }

    class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            } else {
                Log.i("drivingactivity", "坐标:" + location.getLatitude() + "和" + location.getLongitude() +
                        "错误码" + location.getLocType());
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                logMsg(location.getAddrStr());
            }
            mLocationClient.stop();
        }

    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        final String s = str;
        try {
            if (mDrivingAddress != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDrivingAddress.post(new Runnable() {
                            @Override
                            public void run() {
                                mDrivingAddress.setText(s);
                                getNearByDriver();
                            }
                        });
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 2000 && data != null) {
            PoiInfo poiInfo = data.getParcelableExtra("poi");
            mDrivingAddress.setText(poiInfo.address);
            latitude = poiInfo.location.latitude;
            longitude = poiInfo.location.longitude;
            getNearByDriver();
        }
    }

    private ReadyHandler handler = null;

    private class ReadyHandler extends Handler {
        private int outTime;
        private ReadyListener listener;

        public ReadyHandler(int outTime, ReadyListener l) {
            this.outTime = outTime;
            this.listener = l;
        }

        public ReadyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.listener.onGoMain(++outTime);
                    if (outTime > 0 && outTime <= 10) {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    /**
     * 准备
     */
    private void preparation() {
        handler = new ReadyHandler(0, new ReadyListener() {
            @Override
            public void onGoMain(int time) {
                if (time == 10) {
                    mDrivingSiJi.setVisibility(View.GONE);
                    handler.removeMessages(1);
                }
            }
        });
        handler.sendEmptyMessage(1);
    }

    private interface ReadyListener {
        void onGoMain(int time);
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_driving;
    }

}
