package com.zantong.mobilecttx.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.order.bean.OrderListBean;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.zantong.mobilecttx.utils.ImageOptions.getMessageOptions;

/**
 * Created by zhoujie on 2017/2/13.
 * 消息页面
 */

public class OrderStatusAdapter extends BaseAdapter<OrderListBean> {
    private Context mContext;
    private ItemClickListener mClickListener;

    /**
     * 布局创建
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
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
    public void showData(BaseRecyclerViewHolder viewHolder, int position, final OrderListBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            String orderNum = mContext.getResources().getString(R.string.tv_order_num);
            holder.mOrderNum.setText(String.format(orderNum, data.getOrderId()));

            int orderStatus = data.getOrderStatus();
            changeTextColorByStatus(orderStatus, holder.mOrderStatus);

            ImageLoader.getInstance().displayImage(data.getIcon(), holder.mOrderBrand, getMessageOptions());

            holder.mOrderTvBrand.setText(data.getGoodsName());
            holder.mOrderDate.setText(data.getCreateDate());

            int price = data.getAmount();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String format = decimalFormat.format(price);
            holder.mOrderPrice.setText("￥" + format);

            holder.mOrderPayLine.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);
            holder.mLayPay.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);

            if (holder.mLayPay.getVisibility() == View.GONE) return;
            holder.mOrderCanclePay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickCancel(data);
                }
            });
            holder.mOrderPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickPay(data);
                }
            });
        }
    }

    /**
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3已锁定
     */
    private void changeTextColorByStatus(int status, TextView tvPayStatus) {
        switch (status) {
            case 0:
                tvPayStatus.setTextColor(mContext.getResources().getColor(R.color.colorTvOrange_ef));
                tvPayStatus.setText("待支付");
                break;
            case 1:
                tvPayStatus.setTextColor(mContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("已支付");
                break;
            case 2:
                tvPayStatus.setTextColor(mContext.getResources().getColor(R.color.colorTvBlack_b2));
                tvPayStatus.setText("已取消");
                break;
            default:
                tvPayStatus.setTextColor(mContext.getResources().getColor(R.color.colorTvBlack_b2));
                tvPayStatus.setText("未知状态");
                break;
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

        @Bind(R.id.tv_pay_line)
        TextView mOrderPayLine;
        @Bind(R.id.lay_pay)
        RelativeLayout mLayPay;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        mClickListener = listener;
    }

    public interface ItemClickListener {

        void doClickCancel(OrderListBean bean);

        void doClickPay(OrderListBean bean);
    }

}
