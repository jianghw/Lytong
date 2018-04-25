package com.zantong.mobilecttx.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.car.adapter.ManageCarListAdapter;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.contract.IManageCarFtyContract;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.presenter.car.ManageCarFtyPresenter;
import com.zantong.mobilecttx.router.MainRouter;

import java.util.List;

/**
 * 车辆列表
 */
public class ManageCarListFragment extends RecyclerListFragment<VehicleLicenseBean>
        implements IManageCarFtyContract.IManageCarFtyView {

    private IManageCarFtyContract.IManageCarFtyPresenter mPresenter;
    private ManageCarListAdapter mCarListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ManageCarListFragment newInstance() {
        return new ManageCarListFragment();
    }

    /**
     * adapter
     */
    @Override
    public BaseAdapter<VehicleLicenseBean> createAdapter() {
        mCarListAdapter = new ManageCarListAdapter();
        return mCarListAdapter;
    }

    /**
     * 获取RecyclerHeader No
     *
     * @deprecated
     */
    protected View customViewHeader_No() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(getActivity());
        textView.setText("可缴费罚款车辆");
        textView.setTextSize(14);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.ds_45),
                (int) getResources().getDimension(R.dimen.ds_30),
                (int) getResources().getDimension(R.dimen.ds_45),
                (int) getResources().getDimension(R.dimen.ds_30));
        textView.setLayoutParams(layoutParams);
        return textView;
    }


    @Override
    protected void initPresenter() {
        ManageCarFtyPresenter mPresenter = new ManageCarFtyPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void setPresenter(IManageCarFtyContract.IManageCarFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getAllVehicles();
    }

    /**
     * 点击事项 可序列化
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof VehicleLicenseBean) {
            VehicleLicenseBean bean = (VehicleLicenseBean) data;
            if (bean.getIsPayable() < 0) return;

            Bundle bundle = new Bundle();
            bundle.putParcelable(MainGlobal.putExtra.car_item_bean_extra, bean);
            MainRouter.gotoViolationActivity(getActivity(), bundle);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 操作失败
     */
    @Override
    public void addVehicleLicenseError(String message) {
        errorListData(message);
    }

    protected void errorListData(String message) {
        setSimpleDataResult(null);
        ToastUtils.toastShort(message);

        ManageCarActivity activity = (ManageCarActivity) getActivity();
        if (activity != null) activity.isAddCarTitle(3);
    }

    @Override
    public void allVehiclesError(String message) {
        errorListData(message);
    }

    @Override
    public void addVehicleLicenseSucceed(List<VehicleLicenseBean> licenseBeanList) {
        mCarListAdapter.setDataList(licenseBeanList);
        setSimpleDataResult(licenseBeanList);

        ManageCarActivity activity = (ManageCarActivity) getActivity();
        LoginData.getInstance().mCarNum = LoginData.getInstance().mServerCars.size();
        if (activity != null) activity.isAddCarTitle(LoginData.getInstance().mCarNum);
    }

    /**
     * 回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MainGlobal.resultCode.violation_query_submit
                || resultCode == MainGlobal.resultCode.violation_query_del
                || resultCode == MainGlobal.resultCode.set_pay_car_succeed) {

            if (mPresenter != null) mPresenter.getAllVehicles();
        }
    }
}
