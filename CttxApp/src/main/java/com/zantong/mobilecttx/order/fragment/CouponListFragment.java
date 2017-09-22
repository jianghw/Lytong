package com.zantong.mobilecttx.order.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRecyclerListJxFragment;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;
import com.zantong.mobilecttx.order.adapter.RechargCouponAdapter;
import com.zantong.mobilecttx.widght.SpaceItemDecoration;

import java.util.ArrayList;

import com.tzly.annual.base.global.JxGlobal;

/**
 * 选择优惠劵 页面 加油
 */
public class CouponListFragment extends BaseRecyclerListJxFragment<RechargeCouponBean> {

    private static final String ARG_PARAM1 = "param1";

    public static CouponListFragment newInstance(ArrayList<RechargeCouponBean> rechargeCouponBeen) {
        CouponListFragment fragment = new CouponListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, rechargeCouponBeen);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public BaseAdapter<RechargeCouponBean> createAdapter() {
        return new RechargCouponAdapter();
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof RechargeCouponBean) {
            RechargeCouponBean couponBean = (RechargeCouponBean) data;

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(JxGlobal.putExtra.recharge_coupon_bean_extra, couponBean);
            intent.putExtras(bundle);
            getActivity().setResult(JxGlobal.resultCode.recharge_coupon_choice, intent);
            getActivity().finish();

//            if (getArguments() != null) {
//                ArrayList<RechargeCouponBean> arrayList =
//                        getArguments().getParcelableArrayList(ARG_PARAM1);
//                for (RechargeCouponBean bean : arrayList) {
//                    bean.setChoice(bean.getId() == couponBean.getId());
//                }
//            }
//            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onRefreshData() {
    }

    /**
     * 获取RecyclerHeader
     */
    protected View customViewHeader() {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.custom_recharge_coupon_btn, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView unUsed = (TextView) rootView.findViewById(R.id.btn_unused);
        rootView.setLayoutParams(layoutParams);
        unUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().setResult(
                        JxGlobal.resultCode.recharge_coupon_unchoice, new Intent());
                getActivity().finish();
            }
        });
        return rootView;
    }

    @Override
    protected void initFragmentView(View view) {
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.ds_30);
        getCustomRecycler().addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }

    @Override
    protected void onFirstDataVisible() {
        if (getArguments() != null) {
            ArrayList<RechargeCouponBean> arrayList =
                    getArguments().getParcelableArrayList(ARG_PARAM1);
            setSimpleDataResult(arrayList);
        }
    }

    @Override
    protected void DestroyViewAndThing() {
    }

}
