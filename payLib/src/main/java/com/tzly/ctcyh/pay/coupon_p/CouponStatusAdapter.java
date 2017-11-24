package com.tzly.ctcyh.pay.coupon_p;

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
import com.tzly.ctcyh.pay.BuildConfig;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.bean.response.CouponStatusBean;
import com.tzly.ctcyh.router.custom.image.ImageOptions;

import java.text.DecimalFormat;

/**
 * 优惠卷
 */

public class CouponStatusAdapter extends BaseAdapter<CouponStatusBean> {

    private final ICouponStatusContract.ICouponStatusView mContractView;
    private Context mAdapterContext;

    public CouponStatusAdapter(ICouponStatusContract.ICouponStatusView view) {
        mContractView = view;
    }

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

        if (!BuildConfig.App_Url) {//测试没初始化
            ImageLoader.getInstance().displayImage(
                    statusBean.getCouponImage(), holder.mImgCoupon,
                    ImageOptions.getMessageOptions());
        }

        holder.mTvTitle.setText(statusBean.getCouponName());
        holder.mTvContent.setText("有效时间 " + statusBean.getCouponValidityEnd());

        String limit = statusBean.getCouponLimit();
        String value = statusBean.getCouponValue();
        String type = statusBean.getCouponType();
        //couponType：1无（比如兑换码）  2折扣  3满减
        if (type.equals("2")) {
            holder.mTvPrice.setText(new DecimalFormat("#0.#").format(Float.valueOf(value) / 10));
            holder.mTvUnit.setText("折");
        } else if (type.equals("3")) {
            holder.mTvPrice.setText(value);
            holder.mTvUnit.setText("元");
        } else if (type.equals("1")) {
            holder.mTvPrice.setText("兑换码");
            holder.mTvUnit.setVisibility(View.INVISIBLE);
        } else {
            holder.mTvPrice.setText("未知状态");
            holder.mTvUnit.setVisibility(View.INVISIBLE);
        }
        holder.mTvRemark.setText(statusBean.getCouponBusiness());

        holder.mImgStatus.setBackgroundResource(mContractView.getCouponStatus().equals("1")
                ? R.mipmap.pay_ic_coupon_red : R.mipmap.pay_ic_coupon_gray);
        holder.mImgInvalid.setVisibility(mContractView.getCouponStatus().equals("1") ? View.GONE : View.VISIBLE);
        holder.mImgCoupon.setImageAlpha(mContractView.getCouponStatus().equals("1") ? 255 : 30);
        holder.mTvTitle.setTextColor(mAdapterContext.getResources().getColor(mContractView.getCouponStatus().equals("1")
                ? R.color.res_color_black_4d : R.color.res_color_black_b3));
        holder.mTvContent.setTextColor(mAdapterContext.getResources().getColor(mContractView.getCouponStatus().equals("1")
                ? R.color.res_color_black_b3 : R.color.res_color_gray_e6));
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView mImgCoupon;
        TextView mTvTitle;
        TextView mTvContent;
        ImageView mImgInvalid;
        TextView mTvPrice;
        TextView mTvUnit;
        TextView mTvRemark;
        RelativeLayout mImgStatus;

        ViewHolder(View view) {
            super(view);
            this.mImgCoupon = (ImageView) view.findViewById(R.id.img_coupon);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvContent = (TextView) view.findViewById(R.id.tv_content);
            this.mImgInvalid = (ImageView) view.findViewById(R.id.img_invalid);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
            this.mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
            this.mImgStatus = (RelativeLayout) view.findViewById(R.id.img_status);
        }
    }
}
