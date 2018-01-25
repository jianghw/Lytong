package com.tzly.ctcyh.cargo.refuel_p;

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
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.BidOilBean;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOilBean;
import com.tzly.ctcyh.router.util.FormatUtils;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class BidOilAdapter extends BaseAdapter<BidOilBean> {
    private Context mAdapterContext;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.cargo_adapter_bid_oil, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, BidOilBean statusBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (statusBean == null) return;
        holder.mFayRecharge.setSelected(statusBean.isSelect());

        holder.mTvAmount.setText(FormatUtils.submitPrice(statusBean.getPrice()));
        holder.mTvPrice.setText(statusBean.getName());

        int color = mAdapterContext.getResources().getColor(
                statusBean.isSelect() ? R.color.res_color_red_f3 : R.color.res_color_black_4d);
        holder.mTvUnit.setTextColor(color);
        holder.mTvAmount.setTextColor(color);
        holder.mTvPrice.setTextColor(color);

        String dis = statusBean.getDiscount();
        String discount = FormatUtils.showDiscount(dis);
        holder.mTvDiscount.setText(discount + "折");
        //小于10时为又折扣
        double d = Double.valueOf(discount) ;
        holder.mTvDiscount.setVisibility(d < 10 ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        TextView mTvAmount;
        TextView mTvDiscount;
        TextView mTvPrice;
        TextView mTvUnit;
        RelativeLayout mFayRecharge;

        ViewHolder(View view) {
            super(view);
            this.mTvAmount = (TextView) view.findViewById(R.id.tv_amount);
            this.mTvUnit = (TextView) view.findViewById(R.id.tv_unit);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvDiscount = (TextView) view.findViewById(R.id.tv_discount);
            this.mFayRecharge = (RelativeLayout) view.findViewById(R.id.fay_recharge);
        }
    }
}
