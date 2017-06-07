package com.zantong.mobilecttx.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.user.bean.CouponFragmentBean;
import com.zantong.mobilecttx.utils.ImageOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠卷
 */

public class CouponAdapter extends BaseAdapter<CouponFragmentBean> {

    private final String mCouponStatus;

    public CouponAdapter(String couponStatus) {
        super();
        mCouponStatus = couponStatus;
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        Context mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.item_coupon, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position,
                         final CouponFragmentBean couponFragmentBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (couponFragmentBean != null) {
            //TODO 靠~Glide
            ImageLoader.getInstance().displayImage(
                    couponFragmentBean.getCouponImage(),
                    holder.mImg,
                    ImageOptions.getDefaultOptions());

            holder.mTitle.setText(couponFragmentBean.getCouponName());
            holder.mContent.setText("有效时间 " + couponFragmentBean.getCouponValidityEnd());

            holder.mStuta.setImageResource(
                    mCouponStatus.equals("1")
                            ? R.mipmap.coupon_red : R.mipmap.coupon_gray);
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
        @Bind(R.id.coupon_stuta)
        ImageView mStuta;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
