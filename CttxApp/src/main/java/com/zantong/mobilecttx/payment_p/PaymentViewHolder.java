package com.zantong.mobilecttx.payment_p;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

import drawthink.expandablerecyclerview.holder.BaseViewHolder;

/**
 * Created by jianghw on 17-12-27.
 */

public class PaymentViewHolder extends BaseViewHolder {

    public final TextView tvCarTitle;

    public final TextView tvDateV;
    public final TextView tvDateP;
    public final TextView tvPrice;
    public final TextView tvScore;
    public final TextView tvNum;

    public PaymentViewHolder(Context ctx, View itemView, int viewType) {
        super(ctx, itemView, viewType);

        tvCarTitle = (TextView) itemView.findViewById(R.id.tv_carTitle);

        tvDateV = (TextView) itemView.findViewById(R.id.tv_date_v);
        tvDateP = (TextView) itemView.findViewById(R.id.tv_date_p);
        tvNum = (TextView) itemView.findViewById(R.id.tv_num);
        tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        tvScore = (TextView) itemView.findViewById(R.id.tv_score);
    }

    /**
     * 对应方法中返回对应Layout布局文件中根节点的ID。
     */
    @Override
    public int getChildViewResId() {
        return R.id.rl_child;
    }

    @Override
    public int getGroupViewResId() {
        return R.id.rl_group;
    }
}
