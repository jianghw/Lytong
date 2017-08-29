package com.zantong.mobilecttx.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 共用选择列表适配器
 * 1、加油充值中用到
 */
public class RechargCouponAdapter extends BaseAdapter<RechargeCouponBean> {

    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, final int position, RechargeCouponBean couponBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (couponBean != null) {

            holder.mTitle.setText(couponBean.getCouponName());
            holder.mContent.setText("有效时间 " + couponBean.getCouponValidityEnd());

            int couponType = couponBean.getCouponType();
            int value = couponBean.getCouponValue();

            if (couponType == 2) {
                holder.mTvPrice.setText(new DecimalFormat("#0.0#").format(value / 100));
                holder.mTvUnit.setText("折");
            } else if (couponType == 3) {
                holder.mTvPrice.setText(String.valueOf(value));
                holder.mTvUnit.setText("元");
            }

            holder.mTvRemark.setText(couponBean.getCouponUse());

            holder.mImg.setImageResource(couponBean.isChoice() ? R.mipmap.coupon_s : R.mipmap.coupon_d);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.recycle_list_item_recharg_coupon, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.coupon_img)
        ImageView mImg;
        @Bind(R.id.coupon_title)
        TextView mTitle;
        @Bind(R.id.coupon_content)
        TextView mContent;
        @Bind(R.id.tv_remark)
        TextView mTvRemark;
        @Bind(R.id.tv_price)
        TextView mTvPrice;
        @Bind(R.id.tv_unit)
        TextView mTvUnit;
        @Bind(R.id.coupon_stuta)
        RelativeLayout mStuta;
        @Bind(R.id.coupon_invalid)
        ImageView mInvalidCoupon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
