package com.zantong.mobilecttx.map.activity;

import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 百度地图
 * Update by:
 * Update day:
 */
public class BaiduMapParentActivity extends BaseJxActivity
        implements View.OnClickListener, SensorEventListener {

    private FrameLayout mLayMap;
    private RadioButton mRbStart;
    private RadioButton mRbCenter;
    private RadioButton mRbEnd;
    private RadioGroup mLayRg;
    private ImageButton mImgLocation;
    private ImageButton mImgBlowUp;
    private ImageButton mImgZoomOut;

    private SensorManager mSensorManager;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    private BaiduMapOptions baiduMapOptions;
    boolean isFirstLoc = true; // 是否首次定位

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    private MyLocationData locData;
    private SupportMapFragment mapFragment;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
//        Intent intent = getIntent();
//        MapStatus.Builder builder = new MapStatus.Builder();
//        if (intent.hasExtra("x") && intent.hasExtra("y")) {
//            // 当用intent参数时，设置中心点为指定点
//            Bundle bundle = intent.getExtras();
//            LatLng latLng = new LatLng(bundle.getDouble("y"), bundle.getDouble("x"));
//            builder.target(latLng);
//        }
//        //设置缩放级别，默认级别为12
//        builder.overlook(-20).zoom(15);
//        baiduMapOptions = new BaiduMapOptions()
//                .mapStatus(builder.build())
//                .compassEnabled(false)
//                .zoomControlsEnabled(false);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_map_baidu;
    }

    public void initView() {
        mLayMap = (FrameLayout) findViewById(R.id.lay_map);
        mLayRg = (RadioGroup) findViewById(R.id.lay_rg);
        mRbStart = (RadioButton) findViewById(R.id.rb_start);
        mRbCenter = (RadioButton) findViewById(R.id.rb_center);
        mRbEnd = (RadioButton) findViewById(R.id.rb_end);
        mImgLocation = (ImageButton) findViewById(R.id.img_location);
        mImgLocation.setOnClickListener(this);
        mImgBlowUp = (ImageButton) findViewById(R.id.img_blowUp);
        mImgBlowUp.setOnClickListener(this);
        mImgZoomOut = (ImageButton) findViewById(R.id.img_zoomOut);
        mImgZoomOut.setOnClickListener(this);
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("年检");
        initView();

        mapFragment = SupportMapFragment.newInstance(baiduMapOptions);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.lay_map, mapFragment, "map_fragment").commit();

        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    }

    /**
     * 控制mBaiduMap 不为空
     */
    @Override
    protected void onStart() {
        super.onStart();
        // 地图初始化
        mMapView = mapFragment.getMapView();
        mBaiduMap = mapFragment.getBaiduMap();
        //默认是true，显示缩放按钮
        mMapView.showScaleControl(true);
        //默认是true，显示比例尺
        mMapView.showZoomControls(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListenner());

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void DestroyViewAndThing() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_location:
                //定位图标设置
                isFirstLoc = true;
                mLocClient.start();
                break;
            case R.id.img_blowUp:
                break;
            case R.id.img_zoomOut:
                break;
        }
    }

    /**
     * implements  SensorEventListener
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
