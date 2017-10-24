package com.tzly.ctcyh.pay.coupon_v;

import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.pay.bean.response.CouponBean;
import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.coupon_p.CouponListPresenter;
import com.tzly.ctcyh.pay.coupon_p.ICouponListContract;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.JxBaseRecyclerListFragment;
import com.tzly.ctcyh.router.util.ToastUtils;

/**
 * 选择优惠劵 页面 加油
 */
public class CouponListFragment extends
        JxBaseRecyclerListFragment<CouponBean> implements ICouponListContract.ICouponListView {
    /**
     * p
     */
    private ICouponListContract.ICouponListPresenter mPresenter;

    @Override
    protected int initFragmentView() {
        return 0;
    }

    @Override
    protected void bindFragmentView(View fragmentView) {
        CouponListPresenter mPresenter = new CouponListPresenter(
                InjectionRepository.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public BaseAdapter<CouponBean> createAdapter() {
        return null;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
    }

    @Override
    protected void onRefreshData() {
        if (mPresenter != null) mPresenter.getCouponByType();
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.getCouponByType();
    }

    public static CouponListFragment newInstance(String type) {
        CouponListFragment f = new CouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.coupon_list_type, type);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void loadingDialog() {
        showActivityLoading();
    }

    @Override
    public void dismissDialog() {
        dismissActivityLoading();
    }

    @Override
    public void setPresenter(ICouponListContract.ICouponListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getExtraType() {
        return getArguments().getString(PayGlobal.putExtra.coupon_list_type);
    }

    @Override
    public void couponByTypeError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void couponByTypeSucceed(CouponResponse response) {

    }
}
