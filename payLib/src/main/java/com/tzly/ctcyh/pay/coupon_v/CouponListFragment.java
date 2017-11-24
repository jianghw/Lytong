package com.tzly.ctcyh.pay.coupon_v;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.bean.response.CouponBean;
import com.tzly.ctcyh.pay.bean.response.CouponResponse;
import com.tzly.ctcyh.pay.coupon_p.CouponListAdapter;
import com.tzly.ctcyh.pay.coupon_p.CouponListPresenter;
import com.tzly.ctcyh.pay.coupon_p.ICouponListContract;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.router.base.RecyclerListFragment;

import java.util.List;

/**
 * 选择优惠劵 页面 加油
 */
public class CouponListFragment extends RecyclerListFragment<CouponBean>
        implements ICouponListContract.ICouponListView {
    /**
     * mP
     */
    private ICouponListContract.ICouponListPresenter mPresenter;

    /**
     * 分隔栏
     */
    protected int getCustomDecoration() {
        return getResources().getDimensionPixelSize(R.dimen.res_y_20);
    }

    @Override
    protected void initPresenter() {
        CouponListPresenter mPresenter = new CouponListPresenter(
                InjectionRepository.provideRepository(getActivity()), this);
    }

    /**
     * 获取RecyclerHeader
     */
    protected View customViewHeader() {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.pay_custom_coupon_unused, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView unUsed = (TextView) rootView.findViewById(R.id.btn_unused);
        rootView.setLayoutParams(layoutParams);
        unUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.setResult(PayGlobal.resultCode.coupon_unused, new Intent());
                    activity.finish();
                }
            }
        });
        return rootView;
    }

    @Override
    public BaseAdapter<CouponBean> createAdapter() {
        return new CouponListAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        CouponBean couponBean = (CouponBean) data;
        for (CouponBean bean : mAdapter.getAll()) {
            bean.setChoice(bean.getId() == couponBean.getId());
        }
        mAdapter.notifyDataSetChanged();

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PayGlobal.putExtra.coupon_list_bean, couponBean);
        intent.putExtras(bundle);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.setResult(PayGlobal.resultCode.coupon_used, intent);
        }
    }

    public static CouponListFragment newInstance(String type, int payType) {
        CouponListFragment f = new CouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.coupon_list_type, type);
        bundle.putInt(PayGlobal.putExtra.web_pay_type_extra, payType);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void setPresenter(ICouponListContract.ICouponListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getCouponByType();
    }

    @Override
    public String getExtraType() {
        return getArguments().getString(PayGlobal.putExtra.coupon_list_type);
    }

    @Override
    public int getPayType() {
        return getArguments().getInt(PayGlobal.putExtra.web_pay_type_extra);
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof CouponResponse) {
            CouponResponse couponResponse = (CouponResponse) response;
            List<CouponBean> beanList = couponResponse.getData();
            setSimpleDataResult(beanList);
        } else
            responseError();
    }

}
