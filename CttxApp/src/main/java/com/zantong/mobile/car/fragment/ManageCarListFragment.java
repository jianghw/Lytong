package com.zantong.mobile.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobile.car.activity.ManageCarActivity;
import com.zantong.mobile.car.adapter.ManageCarListAdapter;
import com.zantong.mobile.car.bean.VehicleLicenseBean;
import com.zantong.mobile.contract.IManageCarFtyContract;
import com.zantong.mobile.home.bean.HomeCarResult;
import com.zantong.mobile.presenter.car.ManageCarFtyPresenter;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.weizhang.activity.ViolationActivity;

import java.util.List;

/**
 * 车辆列表
 */
public class ManageCarListFragment extends BaseRecyclerListJxFragment<VehicleLicenseBean>
        implements IManageCarFtyContract.IManageCarFtyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private IManageCarFtyContract.IManageCarFtyPresenter mPresenter;
    private ManageCarListAdapter mCarListAdapter;
    private boolean mFirstInit = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static ManageCarListFragment newInstance() {
        return new ManageCarListFragment();
    }

    public static ManageCarListFragment newInstance(String param1, String param2) {
        ManageCarListFragment fragment = new ManageCarListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

    /**
     * adapter
     */
    @Override
    public BaseAdapter<VehicleLicenseBean> createAdapter() {
        mCarListAdapter = new ManageCarListAdapter();
        return mCarListAdapter;
    }

    /**
     * 点击事项 可序列化
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof VehicleLicenseBean) {
            VehicleLicenseBean bean = (VehicleLicenseBean) data;
            if (bean.getIsPayable() < 0) return;
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(JxGlobal.putExtra.car_item_bean_extra, bean);
            intent.putExtras(bundle);
            Act.getInstance().gotoLoginByIntent(getActivity(), ViolationActivity.class, intent);
        }
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    @Override
    protected void initFragmentView(View view) {

        ManageCarFtyPresenter mPresenter = new ManageCarFtyPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);
    }

    @Override
    public void setPresenter(IManageCarFtyContract.IManageCarFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getAllVehicles();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFirstInit) mFirstInit = false;
        else if (mPresenter != null) mPresenter.getAllVehicles();
    }

    @Override
    public void textNoticeInfoError(String message) {
    }

    @Override
    public void textNoticeInfoSucceed(HomeCarResult result) {
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
        activity.isAddCarTitle(3);
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
        MemoryData.getInstance().mCarNum = MemoryData.getInstance().mServerCars.size();
        activity.isAddCarTitle(MemoryData.getInstance().mCarNum);
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }
}
