package com.zantong.mobilecttx.map.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseActivity;
import com.zantong.mobilecttx.map.adapter.NavAdapter;
import com.zantong.mobilecttx.model.AppInfo;
import com.zantong.mobilecttx.utils.AMapUtil;
import com.zantong.mobilecttx.utils.BaiduIntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.log.LogUtils;

/**
 * 导航
 */
public class NavActivity extends BaseActivity {

    @Bind(R.id.map_nav_recycleview)
    XRecyclerView mXRecyclerView;

    PackageManager pm;
    NavAdapter mNavAdapter;

    String mName = "";
    String mLat = "";
    String mLng = "";
    @Override
    protected int getLayoutResId() {
        return R.layout.map_nav_activity;
    }

    @Override
    public void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setArrowImageView(R.mipmap.loading);
        mXRecyclerView.setLoadingMoreEnabled(false);
        mXRecyclerView.setPullRefreshEnabled(false);
        mNavAdapter = new NavAdapter();
        mXRecyclerView.setAdapter(mNavAdapter);
        mNavAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                AppInfo info = (AppInfo)data;
                //高德
                if (info.getPkgName().equals("com.autonavi.minimap")){
                    AMapUtil.goToNaviActivity(NavActivity.this,mName,mName,mLat ,mLng,"1","0");
                }
                //百度
                if (info.getPkgName().equals("com.baidu.BaiduMap")){
                    BaiduIntentUtil.goToNaviActivity(NavActivity.this,"我的位置","latlng:"+mLat+","+mLng+"|name:"+mLat,
                            "driving","上海","北京","上海","","","");

                }
                NavActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null){
            mName = intent.getStringExtra("nav_name");
            mLat = intent.getStringExtra("nav_lat");
            mLng = intent.getStringExtra("nav_lng");
        }
        LogUtils.i("mName:"+mName + "-mLat:" + mLat + "-mLng:" + mLng);
        queryFilterAppInfo();
    }



    // 根据查询条件，查询特定的ApplicationInfo
    private void queryFilterAppInfo() {

        pm = this.getPackageManager();

        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo

        for (ApplicationInfo app : listAppcations) {
            //非系统程序
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                if (app.packageName.equals("com.autonavi.minimap") || app.packageName.equals("com.baidu.BaiduMap")){
                    appInfos.add(getAppInfo(app));
                }
            }
            //本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
            else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
                if (app.packageName.equals("com.autonavi.minimap") || app.packageName.equals("com.baidu.BaiduMap")){
                    appInfos.add(getAppInfo(app));
                }
            }
        }
        mNavAdapter.append(appInfos);
    }

    // 构造一个AppInfo对象 ，并赋值
    private AppInfo getAppInfo(ApplicationInfo app) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String) app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);
        return appInfo;
    }


    @OnClick(R.id.map_nav_activity_layout)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.map_nav_activity_layout:
                finish();
                break;
        }
    }
}
