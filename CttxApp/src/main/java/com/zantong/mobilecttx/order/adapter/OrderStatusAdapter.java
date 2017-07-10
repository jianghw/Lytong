package com.zantong.mobilecttx.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.order.bean.OrderListBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2017/2/13.
 * 消息页面
 */

public class OrderStatusAdapter extends BaseAdapter<OrderListBean> {
    private Context mContext;

    /**
     * 布局创建
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     * @param i
     * @return
     */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.recycle_list_item_order, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 数据绑定
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, OrderListBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mOrderNum.setText(data.getOrderId());
            holder.mOrderStatus.setText(orderStatusFactory(data.getOrderStatus()));
        }
    }

    /**
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3已锁定
     */
    protected String orderStatusFactory(int status) {
        switch (status) {
            case 0:
                return "待支付";
            case 1:
                return "已支付";
            case 2:
                return "已取消";
            case 3:
                return "已锁定";
            default:
                return "未知状态";
        }
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.tv_order_num)
        TextView mOrderNum;
        @Bind(R.id.tv_order_status)
        TextView mOrderStatus;
        @Bind(R.id.img_brand)
        ImageView mOrderBrand;
        @Bind(R.id.tv_brand)
        TextView mOrderTvBrand;
        @Bind(R.id.tv_date)
        TextView mOrderDate;
        @Bind(R.id.tv_price)
        TextView mOrderPrice;
        @Bind(R.id.tv_pay)
        TextView mOrderPay;
        @Bind(R.id.tv_cancel_order)
        TextView mOrderCanclePay;
        @Bind(R.id.tv_delete_order)
        TextView mOrderDeletePay;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
