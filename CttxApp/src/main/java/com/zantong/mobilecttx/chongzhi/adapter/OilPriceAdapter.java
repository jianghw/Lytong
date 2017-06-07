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
import com.zantong.mobilecttx.chongzhi.activity.RechargeActivity;
import com.zantong.mobilecttx.chongzhi.bean.RechargeBean;
import com.zantong.mobilecttx.chongzhi.dto.RechargeDTO;
import com.zantong.mobilecttx.utils.StringUtils;

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
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, final RechargeBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mAmount.setText(data.getAmount());

            double value = Double.parseDouble(
                    TextUtils.isEmpty(data.getDiscount()) ? "1" : data.getDiscount()) * 10;
            holder.mDiscount.setVisibility(value >= 10 ? View.GONE : View.VISIBLE);
            String valueString = new DecimalFormat("#.0").format(value);
            holder.mDiscount.setText(valueString + "折");

            holder.mAmount.setBackgroundResource(data.isCheckd()
                    ? R.drawable.shape_keyboard_btn : R.drawable.shape_clean_btn);
            holder.mAmount.setTextColor(data.isCheckd()
                    ? mContext.getResources().getColor(R.color.white)
                    : mContext.getResources().getColor(R.color.gray_25));

//            if (data.isCheckd()) {
//                holder.mAmount.setBackgroundResource(R.drawable.shape_keyboard_btn);
//                holder.mAmount.setTextColor(mContext.getResources().getColor(R.color.white));
//                setActualAmount(Double.parseDouble(data.getAmount()), Double.parseDouble(data.getDiscount()) / 10);
//            } else {
//                holder.mAmount.setBackgroundResource(R.drawable.shape_clean_btn);
//                holder.mAmount.setTextColor(mContext.getResources().getColor(R.color.gray_25));
//            }
        }
//        holder.mLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                double discount = 1;
//                double amount = Double.parseDouble(getAll().get(position).getAmount());
//
//                if (!"".equals(getAll().get(position).getDiscount())) {
//                    discount = Double.parseDouble(getAll().get(position).getDiscount()) / 10;
//                }
//
//                //设置实际金额
//                setActualAmount(amount, discount);
//                notifyDataSetChanged();
//            }
//        });
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
