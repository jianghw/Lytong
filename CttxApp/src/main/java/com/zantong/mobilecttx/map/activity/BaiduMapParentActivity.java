package com.zantong.mobilecttx.map.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
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
public class BaiduMapParentActivity extends BaseJxActivity {

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_map_baidu;
    }

    @Override
    protected void initFragmentView(View view) {

        Intent intent = getIntent();
        MapStatus.Builder builder = new MapStatus.Builder();

        if (intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle bundle = intent.getExtras();
            LatLng latLng = new LatLng(bundle.getDouble("y"), bundle.getDouble("x"));
            builder.target(latLng);
        }

        builder.overlook(-20).zoom(15);
        BaiduMapOptions baiduMapOptions = new BaiduMapOptions()
                .mapStatus(builder.build())
                .compassEnabled(false)
                .zoomControlsEnabled(false);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(baiduMapOptions);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.lay_map, mapFragment, "map_fragment").commit();
    }

    @Override
    protected void DestroyViewAndThing() {
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
}
