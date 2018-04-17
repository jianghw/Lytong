package com.zantong.mobilecttx.map.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.DensityUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.IBaiduMapContract;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.map.bean.GasStation;
import com.zantong.mobilecttx.map.bean.GasStationDetail;
import com.zantong.mobilecttx.map.bean.GasStationDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheck;
import com.zantong.mobilecttx.map.bean.YearCheckDetail;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResponse;
import com.zantong.mobilecttx.map.bean.YearCheckResponse;
import com.zantong.mobilecttx.map.dto.AnnualDTO;
import com.zantong.mobilecttx.presenter.map.BaiduMapPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DistanceUtils;

import java.util.List;

/**
 * Created by jianghw on 2017/7/7.
 * Description: 百度地图
 */
public class BaiduMapParentActivity extends AbstractBaseActivity
        implements View.OnClickListener, SensorEventListener,
        IBaiduMapContract.IBaiduMapView, BaiduMap.OnMarkerClickListener {

    private RadioButton mRbStart;
    private RadioButton mRbCenter;
    private RadioButton mRbEnd;
    private RadioGroup mLayRg;
    private CardView mImgLocation;
    private CardView mImgBlowUp;
    private CardView mImgZoomOut;

    private SensorManager mSensorManager;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    /**
     * 精度
     */
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

    private RelativeLayout mLay94;
    private CheckBox mCB94;
    /**
     * 是否只显示94
     */
    private boolean isCheckBox = false;

    @Override
    protected int initContentView() {
        return R.layout.activity_map_baidu;
    }

    @Override
    protected void bundleIntent(Intent intent) {

        if (intent != null && intent.hasExtra(MainGlobal.putExtra.map_type_extra)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mMapType = bundle.getInt(
                        MainGlobal.putExtra.map_type_extra, 0);
            }
        }

        /*MapStatus.Builder builder = new MapStatus.Builder();
        if (intent != null && intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            LatLng latLng = new LatLng(b.getDouble("y"), b.getDouble("x"));
            builder.target(latLng);
        }
        builder.overlook(-20).zoom(mCurZoom);
        baiduMapOptions = new BaiduMapOptions().mapStatus(builder.build())
                .compassEnabled(false).zoomControlsEnabled(false);*/
    }

    @Override
    protected void bindFragment() {
        initView();

        BaiduMapPresenter presenter = new BaiduMapPresenter(
                Injection.provideRepository(Utils.getContext()), this);

        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // 地图初始化
        mBaiduMap = mMapView.getMap();
        // 设置点击marker事件监听器
        mBaiduMap.setOnMarkerClickListener(this);

        //默认是true，显示缩放按钮
        mMapView.showScaleControl(true);
        //默认是true，显示比例尺
        mMapView.showZoomControls(false);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListenner());
        //地图状态监听
        mBaiduMap.setOnMapStatusChangeListener(changeListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void initContentData() {
        if (mMapType == MainGlobal.MapType.annual_inspection_map) {
            titleContent("年检");
            titleMore("年检须知");

            mRbStart.setText("免检领标");
            mRbCenter.setText("年检站点");
            mRbEnd.setText("外牌代办点");
        } else if (mMapType == MainGlobal.MapType.annual_oil_map) {
            titleContent("加油优惠");

            mRbStart.setText("92#优惠");
            mRbCenter.setText("95#优惠");
            mRbEnd.setText("0#优惠");

            mLay94.setVisibility(View.VISIBLE);
        }
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

        mLay94 = (RelativeLayout) findViewById(R.id.lay_94);
        mCB94 = (CheckBox) findViewById(R.id.cb_94);
        mCB94.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkRadioButton();
            }
        });

        mLayRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkRadioButton();
            }
        });

        /*mRbStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPresenter != null && isChecked
                        && mMapType == MainGlobal.MapType.annual_inspection_map) {
                    mServiceType = String.valueOf(MainGlobal.MapType.annual_led_service);

                    if (mPresenter != null) mPresenter.annualInspectionList();
                } else if (mPresenter != null && isChecked
                        && mMapType == MainGlobal.MapType.annual_oil_map) {
                    mServiceType = String.valueOf(MainGlobal.MapType.annual_92_service);

                    if (mPresenter != null) mPresenter.gasStationList();
                }
            }
        });
        mRbCenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPresenter != null && isChecked
                        && mMapType == MainGlobal.MapType.annual_inspection_map) {
                    mServiceType = String.valueOf(MainGlobal.MapType.annual_site_service);

                    if (mPresenter != null) mPresenter.annualInspectionList();
                } else if (mPresenter != null && isChecked
                        && mMapType == MainGlobal.MapType.annual_oil_map) {
                    mServiceType = String.valueOf(MainGlobal.MapType.annual_95_service);

                    if (mPresenter != null) mPresenter.gasStationList();
                }
            }
        });
        mRbEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPresenter != null && isChecked
                        && mMapType == MainGlobal.MapType.annual_inspection_map) {
                    mServiceType = String.valueOf(MainGlobal.MapType.annual_agent_service);

                    if (mPresenter != null) mPresenter.annualInspectionList();
                } else if (mPresenter != null && isChecked
                        && mMapType == MainGlobal.MapType.annual_oil_map) {
                    mServiceType = String.valueOf(MainGlobal.MapType.annual_0_service);

                    if (mPresenter != null) mPresenter.gasStationList();
                }
            }
        });*/

    }


    protected void checkRadioButton() {
        if (mRbStart.isChecked()) {
            if (mPresenter != null
                    && mMapType == MainGlobal.MapType.annual_inspection_map) {
                mServiceType = String.valueOf(MainGlobal.MapType.annual_led_service);

                if (mPresenter != null) mPresenter.annualInspectionList();
            } else if (mPresenter != null
                    && mMapType == MainGlobal.MapType.annual_oil_map) {
                mServiceType = String.valueOf(MainGlobal.MapType.annual_92_service);

                if (mPresenter != null) mPresenter.gasStationList();
            }
        } else if (mRbCenter.isChecked()) {
            if (mPresenter != null
                    && mMapType == MainGlobal.MapType.annual_inspection_map) {
                mServiceType = String.valueOf(MainGlobal.MapType.annual_site_service);

                if (mPresenter != null) mPresenter.annualInspectionList();
            } else if (mPresenter != null
                    && mMapType == MainGlobal.MapType.annual_oil_map) {
                mServiceType = String.valueOf(MainGlobal.MapType.annual_95_service);

                if (mPresenter != null) mPresenter.gasStationList();
            }
        } else if (mRbEnd.isChecked()) {
            if (mPresenter != null
                    && mMapType == MainGlobal.MapType.annual_inspection_map) {
                mServiceType = String.valueOf(MainGlobal.MapType.annual_agent_service);

                if (mPresenter != null) mPresenter.annualInspectionList();
            } else if (mPresenter != null
                    && mMapType == MainGlobal.MapType.annual_oil_map) {
                mServiceType = String.valueOf(MainGlobal.MapType.annual_0_service);

                if (mPresenter != null) mPresenter.gasStationList();
            }
        }
    }

    @Override
    protected void rightClickListener() {
        if (mMapType == MainGlobal.MapType.annual_inspection_map) {

            MainRouter.gotoWebHtmlActivity(this, "年检须知",
                    "file:///android_asset/www/nianjian_desc.html");
        }
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
    protected void onDestroy() {
        super.onDestroy();

        isFirstLoc = false;
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private int mCurZoom = 14;

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
     * 点击地标
     * mBaiduMap.setOnMarkerClickListener(this);
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        int id = marker.getPeriod();
        if (mPresenter != null && mMapType == MainGlobal.MapType.annual_inspection_map)
            mPresenter.annualInspection(id);
        else if (mPresenter != null && mMapType == MainGlobal.MapType.annual_oil_map)
            mPresenter.gasStation(id);
        return true;
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
            // 构造定位数据
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

                /**
                 * 最除网络请求 先定位后按键
                 */
                if (!mRbStart.isChecked() && !mRbCenter.isChecked() && !mRbEnd.isChecked())
                    mRbStart.setChecked(true);
            }
            mLocClient.stop();
        }
    }

    public BaiduMap.OnMapStatusChangeListener changeListener
            = new BaiduMap.OnMapStatusChangeListener() {

        @Override
        public void onMapStatusChangeStart(MapStatus arg0) {
        }

        /**
         * 地图状态变化中
         * @param status 当前地图状态
         */
        @Override
        public void onMapStatusChange(MapStatus status) {
        }

        /**
         * 地图状态改变结束
         * @param status 地图状态改变结束后的地图状态
         */
        @Override
        public void onMapStatusChangeFinish(MapStatus status) {
            int[] location = new int[2];
            mMapView.getLocationOnScreen(location);
            Point p = new Point(location[0] + mMapView.getWidth() / 2,
                    location[1] + mMapView.getHeight() / 2);
            //已经获取到屏幕中心经纬度，可上传或者地理转码
            LatLng latLng = mBaiduMap.getProjection().fromScreenLocation(p);
            mCurrentLat = latLng.latitude;
            mCurrentLon = latLng.longitude;
        }
    };

    /**
     * 网络模块
     */
    @Override
    public void setPresenter(IBaiduMapContract.IBaiduMapPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public AnnualDTO getAnnualDTO() {

        AnnualDTO annualDTO = new AnnualDTO();
        annualDTO.setLat(String.valueOf(mCurrentLat == 0.0 ? 31.230372 : mCurrentLat));
        annualDTO.setLng(String.valueOf(mCurrentLon == 0.0 ? 121.473662 : mCurrentLon));

        if (mCB94 != null && mCB94.isChecked()) {
            annualDTO.setType("7");
        } else {
            annualDTO.setType(mServiceType);
        }
        annualDTO.setScope("10");
        return annualDTO;
    }

    /**
     * 年检 网络获取
     */
    @Override
    public void annualInspectionListSucceed(YearCheckResponse result) {
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

    /**
     * 点击单个年检
     */
    @Override
    public void annualInspectionSucceed(YearCheckDetailResponse result) {
        popupAnnualBottom(this, result.getData());
    }

    @Override
    public void annualInspectionError(String message) {
        serverError(message);
    }

    /**
     * 点击单个年检
     */
    @Override
    public void gasStationError(String message) {
        serverError(message);
    }

    @Override
    public void gasStationSucceed(GasStationDetailResponse result) {
        popupStationBottom(this, result.getData());
    }

    /**
     * 获取加油站点集合
     */
    @Override
    public void gasStationListError(String message) {
        serverError(message);
    }

    @Override
    public void gasStationListSucceed(List<GasStation> gasStations) {
        clearOverlay(null);
        initOverlayOil(gasStations);
    }

    protected void serverError(String message) {
        ToastUtils.toastShort(message);
    }

    /**
     * 自定义覆盖地标view
     */
    @SuppressLint("SetTextI18n")
    public BitmapDescriptor getCustomOverView(Object object) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.custom_baidu_map_over, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_location);

        TextView textView = (TextView) view.findViewById(R.id.tv_price);
        if (mMapType == MainGlobal.MapType.annual_inspection_map) {
            textView.setText("免");
        } else if (mMapType == MainGlobal.MapType.annual_oil_map) {
            if (object instanceof GasStation) {
                GasStation bean = (GasStation) object;

                if (String.valueOf(MainGlobal.MapType.annual_92_service).equals(mServiceType)) {
                    String tn = bean.getNinetyTwoNum();
                    textView.setText(TextUtils.isEmpty(tn) || Double.valueOf(tn) == 0 ? " " : "-" + tn);
                } else if (String.valueOf(MainGlobal.MapType.annual_95_service).equals(mServiceType)) {
                    String fn = bean.getNinetyFiveNum();
                    textView.setText(TextUtils.isEmpty(fn) || Double.valueOf(fn) == 0 ? " " : "-" + fn);
                } else if (String.valueOf(MainGlobal.MapType.annual_0_service).equals(mServiceType)) {
                    String zn = bean.getZeroNum();
                    textView.setText(TextUtils.isEmpty(zn) || Double.valueOf(zn) == 0 ? " " : "-" + zn);
                }
            }
        }
        return BitmapDescriptorFactory.fromView(view);
    }

    /**
     * 年检
     */
    public void popupAnnualBottom(Context context, final YearCheckDetail checkDetail) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog_Popup);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.custom_dialog_inspection_map, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNav(checkDetail);
            }
        });
        TextView tvDistance = (TextView) inflate.findViewById(R.id.tv_distance);
        TextView tvName = (TextView) inflate.findViewById(R.id.tv_name);
        TextView tvAddress = (TextView) inflate.findViewById(R.id.tv_address);

        TextView tvPhone = (TextView) inflate.findViewById(R.id.tv_phone);
        TextView tvDate = (TextView) inflate.findViewById(R.id.tv_date);
        TextView tvScope = (TextView) inflate.findViewById(R.id.tv_scope);
        TextView btnCommission = (TextView) inflate.findViewById(R.id.btn_commission);
        btnCommission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                annualInspection();
            }
        });

        tvName.setText(checkDetail.getName());
        tvAddress.setText(checkDetail.getAddress());
        String distance = DistanceUtils.getDistance(String.valueOf(mCurrentLat), String.valueOf(mCurrentLon),
                String.valueOf(checkDetail.getLat()), String.valueOf(checkDetail.getLng()));
        tvDistance.setText(distance);

        tvPhone.setText(TextUtils.isEmpty(checkDetail.getPhoneNoTwo())
                ? checkDetail.getPhoneNoOne() : checkDetail.getPhoneNoOne() + "," + checkDetail.getPhoneNoTwo());
        tvDate.setText(checkDetail.getBusinessTime());
        tvScope.setText(TextUtils.isEmpty(checkDetail.getBusinessScope()) ? "暂无" : checkDetail.getBusinessScope());

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(true);

        initWinBottomDialogParams(context, dialog);
        //显示对话框
        dialog.show();
    }

    protected void annualInspection() {
        MainRouter.gotoWebHtmlActivity(this, "年检服务",
                BuildConfig.isDeta ? "http://139.196.183.121:3000/myCar"
                        : "http://nianjian.liyingtong.com/myCar");
    }

    /**
     * 加油
     */
    @SuppressLint("SetTextI18n")
    private void popupStationBottom(Context context, final GasStationDetail stationDetail) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog_Popup);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.custom_dialog_oil_map, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNav(stationDetail);
            }
        });
        TextView tvDistance = (TextView) inflate.findViewById(R.id.tv_distance);
        TextView tvName = (TextView) inflate.findViewById(R.id.tv_name);
        TextView tvAddress = (TextView) inflate.findViewById(R.id.tv_address);

        TextView tv92 = (TextView) inflate.findViewById(R.id.tv_92);
        TextView tv92Dis = (TextView) inflate.findViewById(R.id.tv_92_discounts);
        TextView tv92Period = (TextView) inflate.findViewById(R.id.tv_92_period);
        TextView tv95 = (TextView) inflate.findViewById(R.id.tv_95);
        TextView tv95Dis = (TextView) inflate.findViewById(R.id.tv_95_discounts);
        TextView tv95Period = (TextView) inflate.findViewById(R.id.tv_95_period);
        TextView tv0 = (TextView) inflate.findViewById(R.id.tv_0);
        TextView tv0Dis = (TextView) inflate.findViewById(R.id.tv_0_discounts);
        TextView tv0Period = (TextView) inflate.findViewById(R.id.tv_0_period);

        tvName.setText(stationDetail.getName());
        tvAddress.setText(stationDetail.getAddress());
        String distance = DistanceUtils.getDistance(String.valueOf(mCurrentLat), String.valueOf(mCurrentLon),
                String.valueOf(stationDetail.getLat()), String.valueOf(stationDetail.getLng()));
        tvDistance.setText(distance);

        textIsEmpty(tv92, stationDetail.getNinetyTwoStandard());
        textIsEmpty(tv92Dis, stationDetail.getNinetyTwoNum());
        tv92Dis.setTextColor(getResources().getColor(
                tv92Dis.getText().toString().contains("无") ? R.color.colorTvBlack_b3 : R.color.colorTvRed_f33));
        tv92Period.setText("优惠时段：" + stationDetail.getPreferentialPeriod());

        textIsEmpty(tv95, stationDetail.getNinetyFiveStandard());
        textIsEmpty(tv95Dis, stationDetail.getNinetyFiveNum());
        tv95Dis.setTextColor(getResources().getColor(
                tv95Dis.getText().toString().contains("无") ? R.color.colorTvBlack_b3 : R.color.colorTvRed_f33));
        tv95Period.setText("优惠时段：" + stationDetail.getPreferentialPeriod());

        textIsEmpty(tv0, stationDetail.getZeroStandard());
        textIsEmpty(tv0Dis, stationDetail.getZeroNum());
        tv0Dis.setTextColor(getResources().getColor(
                tv0Dis.getText().toString().contains("无") ? R.color.colorTvBlack_b3 : R.color.colorTvRed_f33));
        tv0Period.setText("优惠时段：" + stationDetail.getPreferentialPeriod());

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(true);

        initWinBottomDialogParams(context, dialog);
        //显示对话框
        dialog.show();
    }

    public void textIsEmpty(TextView textView, String content) {
        textView.setText(TextUtils.isEmpty(content) ? "暂无" : content);
    }

    public static void initWinBottomDialogParams(Context context, Dialog dialog) {
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.width = DensityUtils.getScreenWidth(context);
            dialogWindow.setAttributes(params);
        }
    }

    /**
     * 覆盖地标data
     */
    public void initOverlay(List<YearCheck> yearCheckList) {
        if (yearCheckList == null || yearCheckList.isEmpty()) return;

        for (YearCheck yearCheck : yearCheckList) {
            LatLng latLng = new LatLng(yearCheck.getLat(), yearCheck.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(getCustomOverView(yearCheck))
                    .period(yearCheck.getId())
                    .zIndex(5).draggable(false);

            markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(markerOptions);
        }
    }

    public void initOverlayOil(List<GasStation> gasStations) {
        if (gasStations == null || gasStations.isEmpty()) return;

        for (GasStation gasStation : gasStations) {
            LatLng latLng = new LatLng(gasStation.getLat(), gasStation.getLng());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .icon(getCustomOverView(gasStation))
                    .period(gasStation.getId())
                    .zIndex(5).draggable(false);

            markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(markerOptions);
        }
    }

    /**
     * 导航功能
     */
    protected void goToNav(Object object) {
        Intent intent = new Intent(this, NavActivity.class);
        if (object instanceof YearCheckDetail) {
            YearCheckDetail bean = (YearCheckDetail) object;
            intent.putExtra("nav_name", bean.getName());
            intent.putExtra("nav_lat", String.valueOf(bean.getLat()));
            intent.putExtra("nav_lng", String.valueOf(bean.getLng()));
        } else if (object instanceof GasStationDetail) {
            GasStationDetail bean = (GasStationDetail) object;
            intent.putExtra("nav_name", bean.getName());
            intent.putExtra("nav_lat", String.valueOf(bean.getLat()));
            intent.putExtra("nav_lng", String.valueOf(bean.getLng()));
        }
        startActivity(intent);
    }
}
