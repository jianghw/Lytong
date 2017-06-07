package com.zantong.mobilecttx.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.user.bean.OrderItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单适配器
 * @author Sandy
 * create at 16/6/2 下午12:54
 */
public class OrderAdapter extends BaseAdapter<OrderItem> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, OrderItem data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            // /* 0-初始 1-核保成功 2-支付完成 3-签单成功  */
            switch (data.getOrderste()){
                case 0 :
                    holder.mStatus.setText("未核保");
                    break;
                case 1 :
                    holder.mStatus.setText("未支付");
                    break;
                case 2 :
                    holder.mStatus.setText("未签单");
                    break;
                case 3 :
                    holder.mStatus.setText("已签单");
                    break;
            }
            holder.mStatus.setTextColor(data.getOrderste() == 2 || data.getOrderste() == 3 ?
                    mContext.getResources().getColor(R.color.gray_66) :
                    mContext.getResources().getColor(R.color.red));
            holder.mSn.setText(data.getCastinspolcycode());
            holder.mName.setText(data.getInsInfo().get(0).getInsnm());
            holder.mUname.setText(data.getAppsnm());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mine_order_item, viewGroup,false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view,int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.mine_order_item_status)
        TextView mStatus;
        @Bind(R.id.mine_order_item_sn)
        TextView mSn;
        @Bind(R.id.mine_order_item_uname)
        TextView mUname;
        @Bind(R.id.mine_order_item_name)
        TextView mName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
