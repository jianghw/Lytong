package com.zantong.mobilecttx.car.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.presenter.ManageVehiclesPresenter;
import com.zantong.mobilecttx.car.fragment.CarManageFragment;

import butterknife.Bind;


public class CarManageActivity extends BaseMvpActivity<IBaseView, ManageVehiclesPresenter> implements View.OnClickListener, IBaseView {

    @Bind(R.id.context_all)
    LinearLayout contextAll;
    @Bind(R.id.vehicles_change_car_btn)
    TextView mChangePayCar;
    private FragmentManager fragmentManager;
    private int payCarNumber = 0;
    private OpenQueryBean.RspInfoBean mRspInfoBean;

    @Override
    public ManageVehiclesPresenter initPresenter() {
        return new ManageVehiclesPresenter(this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_manage_vehicles;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public TextView getChangePayCar() {
        return mChangePayCar;
    }

    @Override
    public void initView() {
        setTitleText("车辆管理");
        setEnsureImg(R.mipmap.icon_addcar);
        AddCarActivity.isFrom = false;
    }

    public void setPayCarNumber(int payCarNumber){
        this.payCarNumber = payCarNumber;
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        CarManageFragment mManageVehiclesFragment = new CarManageFragment();
        transaction.replace(R.id.mine_help_layout, mManageVehiclesFragment);
        transaction.commit();
    }

}
