package com.zantong.mobilecttx.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.adapter.VehicleTypeAdapter;
import com.zantong.mobilecttx.utils.PullToRefreshLayout;
import com.zantong.mobilecttx.utils.PullableRecyclerView;
import com.zantong.mobilecttx.utils.RefreshNewTools.BaseRecyclerAdapter;
import com.zantong.mobilecttx.utils.RefreshNewTools.WrapAdapter;
import com.zantong.mobilecttx.utils.RefreshNewTools.WrapRecyclerView;
import com.zantong.mobilecttx.utils.StateBarSetting;
import com.zantong.mobilecttx.utils.TitleSetting;
import com.zantong.mobilecttx.utils.VehicleTypeTools;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectCarTypeActivity extends AppCompatActivity {
    @Bind(R.id.view_pullrefresh)
    PullToRefreshLayout view_pullrefresh;
    @Bind(R.id.view_recyclerview)
    PullableRecyclerView view_recyclerview;
    VehicleTypeAdapter mVehicleTypeAdapter ;
    private WrapAdapter mWrapAdapter;
    private LinkedList<String> mLinkedList = new LinkedList<>();
    private WrapRecyclerView mViewWrapRecyclerview;

    public static final String CAR_TYPE = "vehicleType";
    public static final int REQUEST_CAR_TYPE = 1001;
    public static final int RESULT_CAR_TYPE = 1002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_type);
        ButterKnife.bind(this);
        StateBarSetting.settingBar(this);
        TitleSetting.getInstance().initTitle(this, "车辆类型", R.mipmap.back_btn_image, null, null, null);
        view_pullrefresh.setPullUpEnable(false);
        view_pullrefresh.setPullDownEnable(false);
        mLinkedList = VehicleTypeTools.getVehicleType();

        mVehicleTypeAdapter = new VehicleTypeAdapter(this, mLinkedList);
        mVehicleTypeAdapter.setOnRecyclerViewListener(new BaseRecyclerAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(View view, int position) {
                mVehicleTypeAdapter.setSelectPosition(position);
                Intent data = new Intent();
                data.putExtra(CAR_TYPE, position);
                setResult(RESULT_CAR_TYPE, data);
                finish();
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        mViewWrapRecyclerview = (WrapRecyclerView) view_pullrefresh.getPullableView();


        mWrapAdapter = new WrapAdapter(mVehicleTypeAdapter);
        mViewWrapRecyclerview.setAdapter(mWrapAdapter);
        mViewWrapRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}
