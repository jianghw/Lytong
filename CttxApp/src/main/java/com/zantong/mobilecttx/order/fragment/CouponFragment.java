package com.zantong.mobilecttx.order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.ICouponAtyContract;
import com.zantong.mobilecttx.presenter.CouponAtyPresenter;
import com.zantong.mobilecttx.order.activity.CouponDetailActivity;
import com.zantong.mobilecttx.order.adapter.CouponAdapter;
import com.zantong.mobilecttx.order.bean.CouponFragmentBean;
import com.zantong.mobilecttx.order.bean.CouponFragmentLBean;
import com.zantong.mobilecttx.order.bean.CouponFragmentResult;
import com.zantong.mobilecttx.order.bean.MessageResult;
import com.zantong.mobilecttx.widght.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import com.tzly.annual.base.util.ToastUtils;

/**
 * 优惠券
 */

public class CouponFragment extends BaseRecyclerListJxFragment<CouponFragmentBean>
        implements ICouponAtyContract.ICouponAtyView {


    private static final String POSITION = "position";
    private ICouponAtyContract.ICouponAtyPresenter mPresenter;

    public static CouponFragment newInstance(int status) {
        CouponFragment f = new CouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, status);
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected void initFragmentView(View view) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.ds_30);
        getCustomRecycler().addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        CouponAtyPresenter mPresenter = new CouponAtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    public void setPresenter(ICouponAtyContract.ICouponAtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof CouponFragmentBean)) return;
        CouponFragmentBean couponFragmentBean = (CouponFragmentBean) data;
        Intent intent = new Intent(getActivity(), CouponDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("CouponFragmentBean", couponFragmentBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 删除点击
     */
    @Override
    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
        ArrayList<CouponFragmentBean> messageList = mAdapter.getAll();
        int position = adapterPosition - 1;
        if (messageList != null && messageList.size() >= adapterPosition && position >= 0) {
            CouponFragmentBean meg = messageList.get(position);
            mPresenter.delUsrCoupon(meg, position);
        }
    }

    @Override
    protected void onFirstDataVisible() {
        if (mPresenter != null) mPresenter.usrCouponInfo();
    }

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    /**
     * adapter 构造器
     */
    @Override
    public BaseAdapter<CouponFragmentBean> createAdapter() {
        return new CouponAdapter(getCouponStatus());
    }

    /**
     * 是否可以侧滑删除
     *
     * @return
     */
    @Override
    protected boolean isDeleteItem() {
        return true;
    }

    @Override
    protected int resetDeleteItemHeight() {
        return getResources().getDimensionPixelSize(R.dimen.ds_210);
    }

    @Override
    public void usrCouponInfoSucceed(CouponFragmentResult result) {
        CouponFragmentLBean dateBean = result.getData();
        if (dateBean != null) {
            List<CouponFragmentBean> couponList = dateBean.getCouponList();
            mPresenter.processingDataFiltrate(couponList);
        } else {
            setSimpleDataResult(null);
        }
    }

    @Override
    public void usrCouponInfoError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void delUsrCouponSucceed(MessageResult result, int position) {
        mAdapter.remove(position);
        ToastUtils.toastShort(result.getResponseDesc());

        if (position == 0 && mAdapter.getAll().isEmpty()) onFirstDataVisible();
    }

    @Override
    public void delUsrCouponError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void setListDataResult(List<CouponFragmentBean> megList) {
        setSimpleDataResult(megList);
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    /**
     * 页面标记
     */
    @Override
    public String getCouponStatus() {
        int anInt = getArguments().getInt(POSITION, 1);
        return String.valueOf(anInt);
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }
}
