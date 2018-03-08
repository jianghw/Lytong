package com.tzly.ctcyh.pay.coupon_p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.response.CouponBean;

import java.text.DecimalFormat;


/**
 * 优惠卷
 */

public class CouponListAdapter extends BaseAdapter<CouponBean> {

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.pay_adapter_coupon_list, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, CouponBean couponBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (couponBean != null) {
            holder.mCouponTitle.setText(couponBean.getCouponName());
            holder.mCouponContent.setText("有效时间 " + couponBean.getCouponValidityEnd());

            int couponType = couponBean.getCouponType();
            int value = couponBean.getCouponValue();

            if (couponType == 2) {
                holder.mTvPrice.setText(new DecimalFormat("#0.#").format(value / 10));
                holder.mTvUnit.setText("折");
            } else if (couponType == 3) {
                holder.mTvPrice.setText(String.valueOf(value));
                holder.mTvUnit.setText("元");
            }
            holder.mTvRemark.setText(couponBean.getCouponUse());

            holder.mCouponImg.setChecked(couponBean.isChoice());
        }
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    static class ViewHolder extends BaseRecyclerViewHolder {
        RadioButton mCouponImg;
        TextView mCouponTitle;
        TextView mCouponContent;
        RelativeLayout mLayReLeft;
        TextView mTvPrice;
        TextView mTvUnit;
        TextView mTvRemark;
        RelativeLayout mLayReRight;

        ViewHolder(View view) {
            super(view);
            this.mCouponImg = (RadioButton) view.findViewById(R.id.coupon_img);
            this.mCouponTitle = (TextView) view.findViewById(R.id.coupon_title);
            this.mCouponContent = (TextView) view.findViewById(R.id.coupon_time);
            this.mLayReLeft = (RelativeLayout) view.findViewById(R.id.lay_re_left);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
            this.mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
            this.mLayReRight = (RelativeLayout) view.findViewById(R.id.lay_re_right);
        }
    }
}
