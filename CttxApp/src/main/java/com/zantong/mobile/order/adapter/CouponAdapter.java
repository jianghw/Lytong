package com.zantong.mobile.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobile.R;
import com.zantong.mobile.order.bean.CouponFragmentBean;
import com.tzly.annual.base.util.image.ImageOptions;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠卷
 */

public class CouponAdapter extends BaseAdapter<CouponFragmentBean> {

    private final String mCouponStatus;
    private Context mAdapterContext;

    public CouponAdapter(String couponStatus) {
        super();
        mCouponStatus = couponStatus;
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);
        return inflater.inflate(R.layout.item_coupon, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position,
                             CouponFragmentBean couponFragmentBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (couponFragmentBean != null) {
            //TODO 靠~Glide
            ImageLoader.getInstance().displayImage(
                    couponFragmentBean.getCouponImage(),
                    holder.mImg,
                    ImageOptions.getMessageOptions());

            holder.mTitle.setText(couponFragmentBean.getCouponName());
            holder.mContent.setText("有效时间 " + couponFragmentBean.getCouponValidityEnd());

            holder.mTvPrice.setText(couponFragmentBean.getCouponContent());
            holder.mTvRemark.setText(couponFragmentBean.getCouponUse());

            int couponType = couponFragmentBean.getCouponType();
            int value = couponFragmentBean.getCouponValue();

            if (couponType == 2) {
                holder.mTvPrice.setText(new DecimalFormat("#0.0#").format(value / 100));
                holder.mTvUnit.setText("折");
            } else if (couponType == 3) {
                holder.mTvPrice.setText(String.valueOf(value));
                holder.mTvUnit.setText("元");
            }
            holder.mTvRemark.setText(couponFragmentBean.getCouponUse());

            holder.mStuta.setBackground(mAdapterContext.getResources().getDrawable(mCouponStatus.equals("1")
                    ? R.mipmap.coupon_bg_r : R.mipmap.coupon_bg_r_d));

            holder.mInvalidCoupon.setVisibility(mCouponStatus.equals("1") ? View.GONE : View.VISIBLE);

            holder.mImg.setImageAlpha(mCouponStatus.equals("1") ? 255 : 30);

            holder.mTitle.setTextColor(mAdapterContext.getResources().getColor(
                    mCouponStatus.equals("1") ? R.color.colorTvBlack_4d : R.color.colorTvBlack_b3));

            holder.mContent.setTextColor(mAdapterContext.getResources().getColor(
                    mCouponStatus.equals("1") ? R.color.colorTvBlack_b3 : R.color.colorTvGray_e6));
        }
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
