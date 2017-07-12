package com.zantong.mobilecttx.map.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.dto.CarManagerDTO;
import com.zantong.mobilecttx.car.dto.CarMarnagerDetailDTO;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.map.bean.GasStation;
import com.zantong.mobilecttx.map.bean.GasStationDetail;
import com.zantong.mobilecttx.map.bean.GasStationDetailResult;
import com.zantong.mobilecttx.map.bean.GasStationResult;
import com.zantong.mobilecttx.map.bean.YearCheck;
import com.zantong.mobilecttx.map.bean.YearCheckDetail;
import com.zantong.mobilecttx.map.bean.YearCheckDetailResult;
import com.zantong.mobilecttx.map.bean.YearCheckResult;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.DensityUtils;
import com.zantong.mobilecttx.utils.DistanceUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 地图
 *
 * @author Sandy
 *         create at 16/9/19 上午11:46
 */
public class BaiduMapActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements BaiduMap.OnMapLoadedCallback,
        BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener, BaiduMap.OnMapTouchListener {

    public static final int TYPE_JIAYOU = 0;
    public static final int TYPE_NIANJIAN = 1;

    @Bind(R.id.activity_map_layout)
    LinearLayout mMapLayout;
    @Bind(R.id.activity_baidumap)
    MapView mMapView;

    @Bind(R.id.map_bottom_view)
    View mMapBottomView;
    @Bind(R.id.bottom_view_nianjian_layout)
    View mMapBottomNianJianLayout;
    @Bind(R.id.bottom_view_jiayou_layout)
    View mMapBottomJiaYouLayout;
    @Bind(R.id.tap_text)
    Button mTapTextView;
    @Bind(R.id.bottom_view_name)
    TextView mMapBottomName;
    @Bind(R.id.bottom_view_addr)
    TextView mMapBottomAddr;
    @Bind(R.id.bottom_view_distance)
    TextView mMapBottomDistance;
    @Bind(R.id.bottom_view_time)
    TextView mMapBottomTime;
    @Bind(R.id.bottom_view_tel)
    TextView mMapBottomTel;
    @Bind(R.id.bottom_view_jiayou_price1)
    TextView mJiuErMoney;
    @Bind(R.id.bottom_view_jiayou_discount1)
    TextView mJiuErJiangJiaMoney;
    @Bind(R.id.bottom_view_jiayou_dicount_tm1)
    TextView mJiuErYouHui;
    @Bind(R.id.bottom_view_jiayou_price2)
    TextView mJiuWuMoney;
    @Bind(R.id.bottom_view_jiayou_discount2)
    TextView mJiuWuJiangJiaMoney;
    @Bind(R.id.bottom_view_jiayou_dicount_tm2)
    TextView mJiuWuYouHui;
    @Bind(R.id.map_type_layout)
    View mBtnTypeLayout;
    @Bind(R.id.map_type1)
    TextView mBtnType1;
    @Bind(R.id.map_type2)
    TextView mBtnType2;

    //默认经纬度
    private double latitude = 31.230372;// 纬度
    private double longitude = 121.473662;// 经度
    private LatLng defaultPos = new LatLng(latitude, longitude);// 默认坐标

    public static BaiduMapActivity mapActivity;
    private Animation mapLocationIcon;
    private CarManagerDTO dto;
    List<YearCheck> mYearCheckList;
    List<GasStation> mGasStationList;
    GasStationDetail mGasStation;
    YearCheckDetail mYearCheck;
    CarMarnagerDetailDTO mCarnagerDetailDTO;
    public String latitudeStr;
    public String longitudeStr;
    //    private String type = "1";//年检类型 1年检  2免年检(默认)

    private String provider;
    BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    boolean ifFrist = true;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_baidumap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapView.setMapCustomEnable(true);
        super.onCreate(savedInstanceState);
        mapActivity = this;
    }

    @Override
    protected void baseLeftTitleEnsure() {
        super.baseLeftTitleEnsure();
    }

    @Override
    protected void baseRightTitleEnsure() {
        super.baseRightTitleEnsure();
    }

    @Override
    public void initView() {
        mMapView.showScaleControl(false);//默认是true，显示缩放按钮
        mMapView.showZoomControls(false);//默认是true，显示比例尺

        //BaiduMap管理具体的某一个MapView： 旋转，移动，缩放，事件。。。
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
        //设置缩放级别，默认级别为12
        MapStatusUpdate mapstatusUpdate = MapStatusUpdateFactory.zoomTo(15);
        ;
        mBaiduMap.setMapStatus(mapstatusUpdate);

        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mBaiduMap.setOnMapTouchListener(this);
        mBaiduMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        //设置地图中心点，默认是上海
        MapStatusUpdate mapstatusUpdatePoint = MapStatusUpdateFactory.newLatLng(defaultPos);
        mBaiduMap.setMapStatus(mapstatusUpdatePoint);


        mapLocationIcon = AnimationUtils.loadAnimation(this, R.anim.center_map_bounds);

        mCarnagerDetailDTO = new CarMarnagerDetailDTO();

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
    }

    @Override
    public void initData() {
        dto = new CarManagerDTO();
        dto.setLng(String.valueOf(longitude));
        dto.setLat(String.valueOf(latitude));
        switch (PublicData.getInstance().mapType) {
            case TYPE_JIAYOU:
                setTitleText("加油优惠");
                getGasList();
                break;
            case TYPE_NIANJIAN:
                setTitleText("年检");
                setEnsureText("年检须知");
                dto.setType("2");
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        DensityUtils.getScreenWidth(this) * 618 / 750, DensityUtils.getScreenWidth(this) * 144 / 1334);
                layoutParams.setMargins(24, 24, 24, 24);
                mBtnTypeLayout.setLayoutParams(layoutParams);
                mBtnTypeLayout.setVisibility(View.VISIBLE);
                getYearCheck();
                break;
        }
        bottomView();
    }

    //焦点在底部view时拦截地图的滑动
    private void bottomView() {
        mMapBottomView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mLocationClient.stop();
        MapView.setMapCustomEnable(false);
    }


    /**
     * 获取加油站集合
     */
    private void getGasList() {
        CarApiClient.getGasStationList(this, dto, new CallBack<GasStationResult>() {
            @Override
            public void onSuccess(GasStationResult result) {
                if (result.getData() != null && result.getData().size() > 0) {
                    addGasStationMarkersToMap(result.getData());
                }
            }
        });
    }

    /**
     * 获取年检集合
     */
    private void getYearCheck() {
        CarApiClient.getYearCheckList(this, dto, new CallBack<YearCheckResult>() {
            @Override
            public void onSuccess(YearCheckResult result) {
                addYearCheckMarkersToMap(result.getData());
            }
        });
    }

    /**
     * 添加年检地点
     *
     * @param list
     */
    private void addYearCheckMarkersToMap(List<YearCheck> list) {
        mBaiduMap.clear();
        if (list != null) {
            mYearCheckList = list;
            for (int i = 0; i < list.size(); i++) {
                LatLng l = new LatLng(list.get(i).getLat(), list.get(i).getLng());
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.map_mark_view, null);
                TextView mText = (TextView) view.findViewById(R.id.map_mark_text);
                mText.setText(list.get(i).getName());
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(l).icon(bitmap).period(list.get(i).getId());
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            }
        } else {
            mMapBottomView.setVisibility(View.GONE);
        }
    }

    /**
     * 添加加油地点
     *
     * @param list
     */
    private void addGasStationMarkersToMap(List<GasStation> list) {
        mBaiduMap.clear();
        if (list != null) {
            mGasStationList = list;
            for (int i = 0; i < list.size(); i++) {
                LatLng l = new LatLng(list.get(i).getLat(), list.get(i).getLng());

                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.map_mark_view, null);
                TextView mText = (TextView) view.findViewById(R.id.map_mark_text);
                TextView mDText = (TextView) view.findViewById(R.id.map_mark_discount_text);
                mText.setText(list.get(i).getName());

                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(l).icon(bitmap).period(list.get(i).getId());
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);

            }
        } else {
            mMapBottomView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取加油站一条信息
     *
     * @param id
     */
    private void getGasStationItem(int id) {
        mCarnagerDetailDTO.setId(id);
        CarApiClient.getGasStationDetail(this, mCarnagerDetailDTO, new CallBack<GasStationDetailResult>() {
            @Override
            public void onSuccess(GasStationDetailResult result) {
                mGasStation = result.getData();
                String juLingStr = DistanceUtils.getDistance(latitudeStr, longitudeStr, mGasStation.getLat(), mGasStation.getLng());
                mMapBottomName.setText(mGasStation.getName());
                mMapBottomAddr.setText(mGasStation.getAddress());
                mMapBottomDistance.setText(juLingStr);
                mJiuErMoney.setText(mGasStation.getNinetyTwoStandard());
                mJiuErJiangJiaMoney.setText(mGasStation.getNinetyTwoNum());
                mJiuWuMoney.setText(mGasStation.getNinetyFiveStandard());
                mJiuWuJiangJiaMoney.setText(mGasStation.getNinetyFiveNum());
                String youHui = "  --  --";
                if (!TextUtils.isDigitsOnly(mGasStation.getPreferentialPeriod())) {
                    youHui = mGasStation.getPreferentialPeriod();
                }
                mJiuErYouHui.setText("优惠时段:" + youHui);
                mJiuWuYouHui.setText("优惠时段:" + youHui);
            }
        });
    }

    /**
     * 获取年检一条信息
     *
     * @param id
     */
    private void getYearCheckItem(int id) {
        mCarnagerDetailDTO.setId(id);
        CarApiClient.getYearCheckDetail(this, mCarnagerDetailDTO, new CallBack<YearCheckDetailResult>() {
            @Override
            public void onSuccess(YearCheckDetailResult result) {
                mYearCheck = result.getData();
                mMapBottomName.setText(mYearCheck.getName());
                mMapBottomAddr.setText(mYearCheck.getAddress());
                mMapBottomDistance.setText(DistanceUtils.getDistance(latitudeStr, longitudeStr, "" + mYearCheck.getLat(), "" + mYearCheck.getLng()));
                mMapBottomTel.setText("".equals(mYearCheck.getPhoneNoTwo()) ? mYearCheck.getPhoneNoOne() : mYearCheck.getPhoneNoOne() + "," + mYearCheck.getPhoneNoTwo());
                mMapBottomTime.setText(mYearCheck.getBusinessTime());
            }
        });
    }

    @OnClick({R.id.bottom_view_navigation, R.id.map_type1, R.id.map_type2, R.id.tap_text})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bottom_view_navigation:
                Intent intent;
                switch (PublicData.getInstance().mapType) {
                    case TYPE_JIAYOU:
                        MobclickAgent.onEvent(this, Config.getUMengID(16));
                        if ("".equals(mGasStation.getLng()) || "".equals(mGasStation.getLat())) {
                            ToastUtils.showShort(this, "暂未定位成功");
                            return;
                        }
                        intent = new Intent(this, NavActivity.class);
                        intent.putExtra("nav_name", mGasStation.getName());
                        intent.putExtra("nav_lat", mGasStation.getLat());
                        intent.putExtra("nav_lng", mGasStation.getLng());
                        startActivity(intent);
                        break;
                    case TYPE_NIANJIAN:
                        MobclickAgent.onEvent(this, Config.getUMengID(14));
                        if ("".equals(String.valueOf(mYearCheck.getLng())) || "".equals(String.valueOf(mYearCheck.getLat()))) {
                            ToastUtils.showShort(this, "暂未定位成功");
                            return;
                        }
                        intent = new Intent(this, NavActivity.class);
                        intent.putExtra("nav_name", mYearCheck.getName());
                        intent.putExtra("nav_lat", mYearCheck.getLat() + "");
                        intent.putExtra("nav_lng", mYearCheck.getLng() + "");
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.map_type1:
                GlobalConfig.getInstance().eventIdByUMeng(6);
                dto.setType("1");
                mBtnType1.setTextColor(getResources().getColor(R.color.red));
                mBtnType2.setTextColor(getResources().getColor(R.color.gray_99));
                getYearCheck();
                break;
            case R.id.map_type2:
                GlobalConfig.getInstance().eventIdByUMeng(5);
                dto.setType("2");
                mBtnType1.setTextColor(getResources().getColor(R.color.gray_99));
                mBtnType2.setTextColor(getResources().getColor(R.color.red));
                getYearCheck();
                break;
            case R.id.tap_text:
                mLocationClient.start();
                break;
        }
    }

    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        PublicData.getInstance().webviewUrl = "file:///android_asset/www/nianjian_desc.html";
        PublicData.getInstance().webviewTitle = "年检须知";
        PublicData.getInstance().isCheckLogin = false;
        Act.getInstance().gotoIntent(this, BrowserActivity.class);
    }

    public String geLat() {
        return latitudeStr;
    }

    public String geLng() {
        return longitudeStr;
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMapBottomView.setVisibility(View.VISIBLE);
        int id = marker.getPeriod();
        switch (PublicData.getInstance().mapType) {
            case TYPE_JIAYOU:
                mMapBottomJiaYouLayout.setVisibility(View.VISIBLE);
                getGasStationItem(id);
                break;
            case TYPE_NIANJIAN:
                mMapBottomNianJianLayout.setVisibility(View.VISIBLE);
                getYearCheckItem(id);
                break;
        }
        return false;

    }

    @Override
    public void onTouch(MotionEvent motionEvent) {
        mMapBottomView.setVisibility(View.GONE);
    }

    class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            } else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.i("abc", "坐标:" + location.getLatitude() + "和" + location.getLongitude() + "错误码" + location.getLocType());

                // 显示个人位置图标
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.latitude(location.getLatitude());
                builder.longitude(location.getLongitude());
                MyLocationData data = builder.build();
                mBaiduMap.setMyLocationData(data);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
            }
            mLocationClient.stop();

        }

    }


}
