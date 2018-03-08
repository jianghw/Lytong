package com.tzly.ctcyh.pay.coupon_p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.response.CouponStatusBean;

import java.text.DecimalFormat;

/**
 * 优惠卷
 */

public class CouponStatusAdapter extends BaseAdapter<CouponStatusBean> {

    private Context mAdapterContext;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);
        return inflater.inflate(R.layout.pay_adapter_coupon_status, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, CouponStatusBean statusBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (statusBean == null) return;

        holder.mTvTitle.setText(statusBean.getCouponName());
        holder.mTvDate.setText("有效时间:" + statusBean.getCouponValidityStart() +
                "-" + statusBean.getCouponValidityEnd());
        holder.mTvContent.setText("仅限:" + statusBean.getCouponBusiness() + " 业务使用");

        String value = statusBean.getCouponValue();
        String type = statusBean.getCouponType();
        //couponType：1无（比如兑换码）  2折扣  3满减
        if (type.equals("2")) {
            holder.mTvPrice.setText(new DecimalFormat("#0.#").format(Float.valueOf(value) / 10));
            holder.mTvUnit.setVisibility(View.VISIBLE);
            holder.mTvUnit.setText("折");
        } else if (type.equals("3")) {
            holder.mTvPrice.setText(value);
            holder.mTvUnit.setVisibility(View.VISIBLE);
            holder.mTvUnit.setText("元");
        } else if (type.equals("1")) {
            holder.mTvPrice.setText("兑换码");
            holder.mTvUnit.setVisibility(View.INVISIBLE);
        } else {
            holder.mTvPrice.setText("未知状态");
            holder.mTvUnit.setVisibility(View.INVISIBLE);
        }

        holder.mImgStatus.setBackgroundResource(statusBean.isEnable()
                ? R.mipmap.pay_ic_coupon_red : R.mipmap.pay_ic_coupon_gray);

        holder.mTvTitle.setTextColor(mAdapterContext.getResources().getColor(statusBean.isEnable()
                ? R.color.res_color_black_4d : R.color.res_color_black_b3));
        holder.mTvDate.setTextColor(mAdapterContext.getResources().getColor(statusBean.isEnable()
                ? R.color.res_color_black_b3 : R.color.res_color_gray_e6));
        holder.mTvContent.setTextColor(mAdapterContext.getResources().getColor(statusBean.isEnable()
                ? R.color.res_color_black_b3 : R.color.res_color_gray_e6));
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {

        TextView mTvTitle;
        TextView mTvDate;
        TextView mTvContent;
        TextView mTvPrice;
        TextView mTvUnit;
        RelativeLayout mImgStatus;

        ViewHolder(View view) {
            super(view);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvDate = (TextView) view.findViewById(R.id.tv_date);
            this.mTvContent = (TextView) view.findViewById(R.id.tv_content);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
            this.mImgStatus = (RelativeLayout) view.findViewById(R.id.relay_status);
        }
    }
}
