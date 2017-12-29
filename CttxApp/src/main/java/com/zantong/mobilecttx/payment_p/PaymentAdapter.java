package com.zantong.mobilecttx.payment_p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzly.ctcyh.router.util.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.bean.RspInfoBean;

import java.util.List;

import drawthink.expandablerecyclerview.adapter.BaseRecyclerViewAdapter;
import drawthink.expandablerecyclerview.bean.RecyclerViewData;

/**
 * 缴费查询 子布局
 */

public class PaymentAdapter extends BaseRecyclerViewAdapter<String, RspInfoBean.ViolationInfoBean, PaymentViewHolder> {

    private final Context ctx;

    public PaymentAdapter(Context ctx, List<RecyclerViewData> datas) {
        super(ctx, datas);

        this.ctx = ctx;
    }

    /**
     * 布局
     */
    @Override
    public View getGroupView(ViewGroup parent) {
        return LayoutInflater.from(ctx).inflate(R.layout.main_item_rv_payment_p, parent, false);
    }

    @Override
    public View getChildView(ViewGroup parent) {
        return LayoutInflater.from(ctx).inflate(R.layout.main_item_rv_payment_c, parent, false);
    }

    @Override
    public PaymentViewHolder createRealViewHolder(Context ctx, View view, int viewType) {
        return new PaymentViewHolder(ctx, view, viewType);
    }

    /**
     * 数据设置
     */
    @Override
    public void onBindGroupHolder(PaymentViewHolder holder, int groupPos, int position, String groupData) {
        holder.tvCarTitle.setText(Des3.decode(groupData));
        holder.status.setText("详情");
        holder.arrow.setImageResource(R.mipmap.arrow_down);
    }

    @Override
    public void onBindChildpHolder(PaymentViewHolder holder, int groupPos, int childPos,
                                   int position, RspInfoBean.ViolationInfoBean childData) {
        holder.tvDateV.setText(childData.getViolationdate());
        holder.tvDateP.setText(childData.getPaydate());
        holder.tvNum.setText(childData.getViolationnum());

        holder.tvPrice.setText(Integer.valueOf(childData.getViolationamt()) / 100 + " 元");
        holder.tvScore.setText("扣 " + childData.getViolationcent() + " 分");
    }

    /**
     * true 全部可展开
     * fasle  同一时间只能展开一个
     */
    @Override
    public boolean canExpandAll() {
        return false;
    }
}
