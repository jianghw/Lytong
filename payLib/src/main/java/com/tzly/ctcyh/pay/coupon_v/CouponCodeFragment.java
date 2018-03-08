package com.tzly.ctcyh.pay.coupon_v;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.response.CouponCodeBean;
import com.tzly.ctcyh.pay.response.CouponCodeResponse;
import com.tzly.ctcyh.pay.coupon_p.CouponCodeAdapter;
import com.tzly.ctcyh.pay.coupon_p.CouponCodePresenter;
import com.tzly.ctcyh.pay.coupon_p.ICouponCodeContract;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 码券
 */
public class CouponCodeFragment extends RecyclerListFragment<CouponCodeBean>
        implements ICouponCodeContract.ICouponCodeView {

    private static final String STATUS = "status";
    private ICouponCodeContract.ICouponCodePresenter mPresenter;

    /**
     * 分隔栏
     */
    protected int getCustomDecoration() {
        return getResources().getDimensionPixelSize(R.dimen.res_y_20);
    }

    @Override
    public BaseAdapter<CouponCodeBean> createAdapter() {
        return new CouponCodeAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof CouponCodeBean)) return;
        CouponCodeBean statusBean = (CouponCodeBean) data;
        PayRouter.gotoCouponDetailActivity(getActivity(), statusBean.getId());
    }

    @Override
    protected void initPresenter() {
        CouponCodePresenter mPresenter = new CouponCodePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getCodeList();
    }

    public static Fragment newInstance() {

        return new CouponCodeFragment();
    }

    public static Fragment newInstance(int status) {
        CouponCodeFragment statusFragment = new CouponCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS, status);
        statusFragment.setArguments(bundle);
        return statusFragment;
    }

    @Override
    public void setPresenter(ICouponCodeContract.ICouponCodePresenter presenter) {
        mPresenter = presenter;
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
        return true;
    }

    /**
     * 和子控件高度一致
     */
    @Override
    protected int resetDeleteItemHeight() {
        return getResources().getDimensionPixelSize(R.dimen.res_y_180);
    }

    /**
     * 删除点击
     */
    @Override
    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
        ArrayList<CouponCodeBean> messageList = mAdapter.getAll();
        int position = adapterPosition - 1;
        if (messageList != null && position >= 0) {
            CouponCodeBean codeBean = messageList.get(position);
            mPresenter.deleteCode(codeBean.getId(), position);
        }
    }

    /**
     * 获取数据成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof CouponCodeResponse) {
            CouponCodeResponse couponResponse = (CouponCodeResponse) response;
            List<CouponCodeBean> data = couponResponse.getData();
            setSimpleDataResult(data);
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
