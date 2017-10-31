package com.zantong.mobilecttx.chongzhi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.chongzhi.adapter.ChooseAddressAdapter;
import com.zantong.mobilecttx.presenter.HelpPresenter;

import butterknife.Bind;

/**
 * 选择地址页面
 * Created by zhoujie on 2017/2/17.
 */

public class ChooseAddressActivity extends BaseMvpActivity<IBaseView, HelpPresenter>
        implements TextWatcher,ChooseAddressAdapter.OnRecyclerviewItemListener {

    @Bind(R.id.choose_address_input)
    EditText mInput;
    @Bind(R.id.choose_address_dingwei)
    TextView mDingwei;
    @Bind(R.id.choose_address_city)
    TextView mCity;
    @Bind(R.id.choose_address_baidu_no_data)
    TextView mNoData;
    @Bind(R.id.choose_address_tv)
    TextView mTv;
    @Bind(R.id.choose_address_ding_layout)
    LinearLayout mLayout;
    @Bind(R.id.choose_address_baidu_recycler)
    RecyclerView mBaiduRecycler;

    private ChooseAddressAdapter mAdapter;
    public LocationClient mLocationClient = null;
    private PoiSearch mPoiSearch = PoiSearch.newInstance();
    private String city;

    @Override
    public void initView() {
        setTitleText("选择地址");
        mDingwei.setMovementMethod(ScrollingMovementMethod.getInstance());
        mLocationClient = new LocationClient(this);
        setLocationOption();
        mLocationClient.registerLocationListener(new MyLocationListenner());
        mLocationClient.start();
    }

    @Override
    public void initData() {
        mInput.addTextChangedListener(this);
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mAdapter = new ChooseAddressAdapter();
        mAdapter.setListener(this);
        mBaiduRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBaiduRecycler.setAdapter(mAdapter);
    }

    /**
     * 检索
     */
    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if(poiResult.getAllPoi() == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR){
                mTv.setVisibility(View.VISIBLE);
                mLayout.setVisibility(View.VISIBLE);
                mBaiduRecycler.setVisibility(View.GONE);
                mNoData.setVisibility(View.VISIBLE);
            }else{
                mTv.setVisibility(View.GONE);
                mLayout.setVisibility(View.GONE);
                mBaiduRecycler.setVisibility(View.VISIBLE);
                mNoData.setVisibility(View.GONE);
                mAdapter.setDatas(poiResult.getAllPoi());
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.isEmpty(city)){
            city = "上海";
        }
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city)
                .keyword(s.toString())
                .pageCapacity(15)
                .pageNum(1));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void OnItemClick(View view, int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("poi", mAdapter.getAll().get(position));
        intent.putExtras(bundle);
        setResult(2000, intent);
        finish();
    }

    class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                LogUtils.i("location_failed");
                return;
            } else {
                city = location.getCity();
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
            if (mDingwei != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDingwei.post(new Runnable() {
                            @Override
                            public void run() {
                                mDingwei.setText(s);
                                mCity.setText(city);
                            }
                        });
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        option.setProdName("music_make");
        mLocationClient.setLocOption(option);
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_choose_adderss;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
    }
}
