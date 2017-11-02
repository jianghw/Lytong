package com.zantong.mobilecttx.chongzhi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.chongzhi.bean.RechargeBean;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单适配器
 */
public class OilPriceAdapter extends BaseAdapter<RechargeBean> {

    private final RechargeDTO mRechargeDTO;
    private Context mContext;

    public OilPriceAdapter(RechargeDTO rechargeDTO) {
        mRechargeDTO = rechargeDTO;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, final int position, final RechargeBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mAmount.setText(data.getAmount());

            double value = Double.parseDouble(
                    TextUtils.isEmpty(data.getDiscount()) ? "1" : data.getDiscount()) * 10;

//            holder.mDiscount.setVisibility(value < 10 && data.getCouponType() == 2 ? View.VISIBLE : View.GONE);
            holder.mDiscount.setVisibility( View.GONE);

            String valueString = new DecimalFormat("#0.0#").format(value);
            holder.mDiscount.setText(valueString + "折");

            holder.mAmount.setBackgroundResource(data.isCheckd()
                    ? R.drawable.shape_keyboard_btn : R.drawable.shape_clean_btn);
            holder.mAmount.setTextColor(data.isCheckd()
                    ? mContext.getResources().getColor(R.color.colorWhite)
                    : mContext.getResources().getColor(R.color.colorTvBlack_b3));
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.item_recharge, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.fay_recharge)
        View mLayout;
        @Bind(R.id.item_recharge_amount)
        TextView mAmount;
        @Bind(R.id.item_recharge_discount)
        TextView mDiscount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
