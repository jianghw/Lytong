package com.zantong.mobilecttx.car.fragment;

import android.app.Dialog;
import android.view.View;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.car.adapter.SetPayCarAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.presenter.SetPayCarFragmentPresenter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.interf.ModelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetPayCarFragment extends BaseListFragment<UserCarInfoBean> implements ModelView {

    static SetPayCarFragment mSingleFragment;
    private SetPayCarAdapter mSetPayCarAdapter;
    private SetPayCarFragmentPresenter mSetPayCarFragmentPresenter;
    private List<UserCarInfoBean> mRspInfoBean;
    private ArrayList<UserCarInfoBean> noPay = new ArrayList<>();
    private ArrayList<UserCarInfoBean> pay = new ArrayList<>();
    private String newPayNumber = "";
    private String oldPayNumber = "";
    private String carnumtype = "";
    private Dialog mDialog;

    public SetPayCarFragment() {
        mSetPayCarFragmentPresenter = new SetPayCarFragmentPresenter(this);
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    public HashMap<String, String> mapData() {
        HashMap<String, String> mHashMap = new HashMap<>();

        mHashMap.put("addcarnum", newPayNumber);
        mHashMap.put("delcarnum", oldPayNumber);
        mHashMap.put("carnumtype", carnumtype);
        return mHashMap;
    }

    @Override
    protected void onRecyclerItemClick(View view, final Object data) {
        final UserCarInfoBean mUserCarsInfoBean = (UserCarInfoBean) data;
        DialogUtils.createSelCarDialog(this.getActivity(), pay.get(0).getCarnum(), pay.get(1).getCarnum(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPayNumber = pay.get(0).getCarnum();
                newPayNumber = mUserCarsInfoBean.getCarnum();
                carnumtype = mUserCarsInfoBean.getCarnumtype();
                mSetPayCarFragmentPresenter.loadView(1);
                PublicData.getInstance().isSetPayCar = true;
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPayNumber = pay.get(1).getCarnum();
                newPayNumber = mUserCarsInfoBean.getCarnum();
                carnumtype = mUserCarsInfoBean.getCarnumtype();
                mSetPayCarFragmentPresenter.loadView(1);
                PublicData.getInstance().isSetPayCar = true;
            }
        });
//        IOSpopwindow menuPop = new IOSpopwindow(getActivity(), getView(), pay.get(0).getCarnum(), pay.get(1).getCarnum(), new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }, "提示：车辆改绑成功后12小时才可进行违章缴费");

    }

    @Override
    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    public BaseAdapter<UserCarInfoBean> createAdapter() {
        mSetPayCarAdapter = new SetPayCarAdapter();
        return mSetPayCarAdapter;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mRspInfoBean = PublicData.getInstance().mServerCars;
        if (mRspInfoBean != null){
            int size = mRspInfoBean.size();
            for (int i = 0; i < size; i++) {
                if ("0".equals(mRspInfoBean.get(i).getIspaycar())) {
                    noPay.add(mRspInfoBean.get(i));
                } else {
                    pay.add(mRspInfoBean.get(i));

                }
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        mSetPayCarAdapter.removeAll();
        setDataResult(noPay);
    }

    @Override
    public void showProgress() {
        mDialog = DialogUtils.showLoading(getActivity());
    }

    @Override
    public void updateView(Object object, int index) {
        mRspInfoBean = PublicData.getInstance().mServerCars;;
        int size = mRspInfoBean.size();
        for (int i = 0; i < size; i++) {
            if (oldPayNumber.equals(mRspInfoBean.get(i).getCarnum())) {
                mRspInfoBean.get(i).setIspaycar("0");
            }
            if (newPayNumber.equals(mRspInfoBean.get(i).getCarnum())) {
                mRspInfoBean.get(i).setIspaycar("1");
            }
        }
        UserInfoRememberCtrl.saveObject("userCarInfo", mRspInfoBean);
        ToastUtils.showShort(getActivity(), "修改成功");
        getActivity().finish();
    }

    @Override
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
