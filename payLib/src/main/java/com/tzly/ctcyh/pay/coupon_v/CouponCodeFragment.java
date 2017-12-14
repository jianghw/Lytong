package com.tzly.ctcyh.pay.coupon_v;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.bean.BaseResponse;
import com.tzly.ctcyh.pay.bean.response.CouponStatusBean;
import com.tzly.ctcyh.pay.bean.response.CouponStatusList;
import com.tzly.ctcyh.pay.bean.response.CouponStatusResponse;
import com.tzly.ctcyh.pay.coupon_p.CouponCodePresenter;
import com.tzly.ctcyh.pay.coupon_p.CouponStatusAdapter;
import com.tzly.ctcyh.pay.coupon_p.CouponStatusPresenter;
import com.tzly.ctcyh.pay.coupon_p.ICouponStatusContract;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 码券
 */
public class CouponCodeFragment extends RecyclerListFragment<CouponStatusBean>
        implements ICouponStatusContract.ICouponStatusView {

    private static final String STATUS = "status";
    private ICouponStatusContract.ICouponStatusPresenter mPresenter;

    /**
     * 分隔栏
     */
    protected int getCustomDecoration() {
        return getResources().getDimensionPixelSize(R.dimen.res_y_20);
    }

    @Override
    public BaseAdapter<CouponStatusBean> createAdapter() {
        return new CouponStatusAdapter(this);
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof CouponStatusBean)) return;
        CouponStatusBean statusBean = (CouponStatusBean) data;
        PayRouter.gotoCouponDetailActivity(getActivity(), statusBean.getCouponId());
    }

    @Override
    protected void initPresenter() {
        CouponCodePresenter mPresenter = new CouponCodePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.couponUserList();
    }

    public static Fragment newInstance(int status) {
        CouponCodeFragment statusFragment = new CouponCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS, status);
        statusFragment.setArguments(bundle);
        return statusFragment;
    }

    @Override
    public void setPresenter(ICouponStatusContract.ICouponStatusPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getCouponStatus() {
        int status = getArguments().getInt(STATUS);
        return String.valueOf(status);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 是否可以侧滑删除
     */
    @Override
    protected boolean isDeleteItem() {
        return getArguments().getInt(STATUS) == 1;
    }

    /**
     * 和子控件高度一致
     */
    @Override
    protected int resetDeleteItemHeight() {
        return getResources().getDimensionPixelSize(R.dimen.res_y_158);
    }

    /**
     * 删除点击
     */
    @Override
    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
        ArrayList<CouponStatusBean> messageList = mAdapter.getAll();
        int position = adapterPosition - 1;
        if (messageList != null && position >= 0) {
            CouponStatusBean meg = messageList.get(position);
            mPresenter.delUsrCoupon(meg.getCouponId(), position);
        }
    }

    /**
     * 获取数据成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof CouponStatusResponse) {
            CouponStatusResponse couponResponse = (CouponStatusResponse) response;
            CouponStatusList data = couponResponse.getData();
            List<CouponStatusBean> couponList = null;
            if (data != null) couponList = data.getCouponList();
            setSimpleDataResult(couponList);
        } else
            responseError();
    }

    @Override
    public void deleteCodeError(String message) {
        toastShort(message);
    }

    @Override
    public void deleteCodeSucceed(BaseResponse response, int position) {
        mAdapter.remove(position);
        toastShort(response.getResponseDesc());

        if (position == 0 && mAdapter.getAll().isEmpty()) showStateEmpty();
    }
}