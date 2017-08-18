package com.zantong.mobilecttx.map.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.IBaiduMapContract;
import com.zantong.mobilecttx.map.bean.YearCheck;
import com.zantong.mobilecttx.map.bean.YearCheckResult;
import com.zantong.mobilecttx.map.dto.AnnualDTO;
import com.zantong.mobilecttx.presenter.map.BaiduMapPresenter;

import java.util.List;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 百度地图
 * Update by:
 * Update day:
 */
public class BaiduMapParentActivity extends BaseJxActivity
        implements View.OnClickListener, SensorEventListener, IBaiduMapContract.IBaiduMapView {

    private RadioButton mRbStart;
    private RadioButton mRbCenter;
    private RadioButton mRbEnd;
    private RadioGroup mLayRg;
    private CardView mImgLocation;
    private CardView mImgBlowUp;
    private CardView mImgZoomOut;

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

    private IBaiduMapContract.IBaiduMapPresenter mPresenter;
    /**
     * 地图类型 -1、年检
     */
    private int mMapType = -1;
    /**
     * 地图中的服务类型
     */
    private String mServiceType;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(GlobalConstant.putExtra.map_type_extra)) {
            mMapType = intent.getIntExtra(GlobalConstant.putExtra.map_type_extra, 0);
        }

        MapStatus.Builder builder = new MapStatus.Builder();
        if (intent != null && intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            LatLng latLng = new LatLng(b.getDouble("y"), b.getDouble("x"));
            builder.target(latLng);
        }
        builder.overlook(-20).zoom(mCurZoom);
        baiduMapOptions = new BaiduMapOptions().mapStatus(builder.build())
                .compassEnabled(false).zoomControlsEnabled(false);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_map_baidu;
    }

    public void initView() {
        mMapView = (MapView) findViewById(R.id.lay_map);
        mLayRg = (RadioGroup) findViewById(R.id.lay_rg);
        mRbStart = (RadioButton) findViewById(R.id.rb_start);
        mRbCenter = (RadioButton) findViewById(R.id.rb_center);
        mRbEnd = (RadioButton) findViewById(R.id.rb_end);
        mImgLocation = (CardView) findViewById(R.id.img_location);
        mImgLocation.setOnClickListener(this);
        mImgBlowUp = (CardView) findViewById(R.id.img_blowUp);
        mImgBlowUp.setOnClickListener(this);
        mImgZoomOut = (CardView) findViewById(R.id.img_zoomOut);
        mImgZoomOut.setOnClickListener(this);

        mRbStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPresenter != null && isChecked
                        && mMapType == GlobalConstant.MapType.annual_inspection_map) {
                    mServiceType = String.valueOf(GlobalConstant.MapType.annual_led_service);
                    mPresenter.annualInspectionList();
                }
            }
        });
        mRbCenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPresenter != null && isChecked
                        && mMapType == GlobalConstant.MapType.annual_inspection_map) {
                    mServiceType = String.valueOf(GlobalConstant.MapType.annual_site_service);
                    mPresenter.annualInspectionList();
                }
            }
        });
        mRbEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

//        mLayRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_start:
//                        if (mPresenter != null
//                                && mMapType == GlobalConstant.MapType.annual_inspection_map) {
//                            mServiceType = String.valueOf(GlobalConstant.MapType.annual_led_service);
//                            mPresenter.annualInspectionList();
//                        }
//                        break;
//                    case R.id.rb_center:
//                        if (mPresenter != null
//                                && mMapType == GlobalConstant.MapType.annual_inspection_map) {
//                            mServiceType = String.valueOf(GlobalConstant.MapType.annual_site_service);
//                            mPresenter.annualInspectionList();
//                        }
//                        break;
//                    case R.id.rb_end:
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }

    @Override
    protected void initFragmentView(View view) {
        initView();

        BaiduMapPresenter presenter = new BaiduMapPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);

//        if (mapFragment == null) {
//            mapFragment = SupportMapFragment.newInstance(baiduMapOptions);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(R.id.lay_map, mapFragment, "map_fragment").commit();
//        }

        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    }

    @Override
    protected void initViewStatus() {
        if (mMapType == GlobalConstant.MapType.annual_inspection_map) {
            initTitleContent("年检");
            mRbStart.setText("免检领标");
            mRbCenter.setText("年检站点");
            mRbEnd.setText("外牌代办点");

            mRbStart.setChecked(true);
        }
    }

    /**
     * 控制mBaiduMap 不为空
     */
    @Override
    protected void onStart() {
        super.onStart();
        // 地图初始化
        mBaiduMap = mMapView.getMap();

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
        if (mPresenter != null) mPresenter.unSubscribe();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        clearOverlay(null);
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

    private int mCurZoom = 16;

    /**
     * 地图控制点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_location:
                //定位图标设置
                isFirstLoc = true;
                mLocClient.start();
                break;
            case R.id.img_blowUp:
                //放大
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.img_zoomOut:
                //缩小
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
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
                builder.target(latLng).zoom(mCurZoom);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 网络模块
     */
    @Override
    public void setPresenter(IBaiduMapContract.IBaiduMapPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    @Override
    public AnnualDTO getAnnualDTO() {
        AnnualDTO annualDTO = new AnnualDTO();
        annualDTO.setLat(String.valueOf(mCurrentLat));
        annualDTO.setLng(String.valueOf(mCurrentLon));
        annualDTO.setType(mMapType == GlobalConstant.MapType.annual_inspection_map
                ? mServiceType : "-1");
        annualDTO.setScope("200");
        return annualDTO;
    }

    /**
     * 年检 网络获取
     */
    @Override
    public void annualInspectionListSucceed(YearCheckResult result) {
        List<YearCheck> yearCheckList = result.getData();

        clearOverlay(null);
        initOverlay(yearCheckList);
    }

    /**
     * 地图清空
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }

    @Override
    public void annualInspectionListError(String msg) {
        serverError(msg);
    }

    protected void serverError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    /**
     * 覆盖地标view
     */
    public BitmapDescriptor getCustomOverView(String msg) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_baidu_map_over, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_location);
        TextView textView = (TextView) view.findViewById(R.id.tv_price);
        if (!TextUtils.isEmpty(msg)) textView.setText(msg);
        return BitmapDescriptorFactory.fromView(view);
    }

    /**
     * 覆盖地标data
     */
    public void initOverlay(List<YearCheck> yearCheckList) {
        if (yearCheckList == null || yearCheckList.isEmpty()) return;

        for (YearCheck yearCheck : yearCheckList) {
            LatLng latLng = new LatLng(yearCheck.getLng(), yearCheck.getLat());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng).icon(getCustomOverView("免"))
                    .zIndex(9).draggable(true);

            markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(markerOptions);
        }


        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker) {
            }

            public void onMarkerDragEnd(Marker marker) {
            }

            public void onMarkerDragStart(Marker marker) {
            }
        });
    }
}
