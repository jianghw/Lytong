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
import com.tzly.ctcyh.pay.bean.response.CouponCodeBean;

/**
 * 优惠卷
 */

public class CouponCodeAdapter extends BaseAdapter<CouponCodeBean> {

    private Context mAdapterContext;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);
        return inflater.inflate(R.layout.pay_adapter_coupon_code, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * channel：1 嗨修 2惠保养 3一元购 4电影券
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, CouponCodeBean statusBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (statusBean == null) return;

        String channel = statusBean.getChannel();
        holder.mTvTitle.setText(channel);


        holder.mTvDate.setText("有效时间:" + statusBean.getStartTime() +
                "-" + statusBean.getEndTime());
        holder.mTvCode.setText(statusBean.getCode());

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
        TextView mTvCode;
        TextView mTvPrice;
        TextView mTvUnit;
        RelativeLayout mImgStatus;

        ViewHolder(View view) {
            super(view);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvDate = (TextView) view.findViewById(R.id.tv_date);
            this.mTvContent = (TextView) view.findViewById(R.id.tv_content);
            this.mTvCode = (TextView) view.findViewById(R.id.tv_code);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
            this.mImgStatus = (RelativeLayout) view.findViewById(R.id.relay_status);
        }
    }
}
