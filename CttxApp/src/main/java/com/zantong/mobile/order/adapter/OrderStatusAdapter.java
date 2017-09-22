package com.zantong.mobile.order.adapter;

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
import com.zantong.mobile.R;
import com.zantong.mobile.order.bean.OrderListBean;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.tzly.annual.base.util.image.ImageOptions.getMessageOptions;

/**
 * Created by zhoujie on 2017/2/13.
 * 消息页面
 */

public class OrderStatusAdapter extends BaseAdapter<OrderListBean> {
    private Context mAdapterContext;
    private ItemClickListener mClickListener;

    /**
     * 布局创建
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);
        return inflater.inflate(R.layout.recycle_list_item_order, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 数据绑定
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3进行中 4已完成
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final OrderListBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            String orderNum = mAdapterContext.getResources().getString(R.string.tv_order_num);
            holder.mOrderNum.setText(String.format(orderNum, data.getOrderId()));

            int orderStatus = data.getOrderStatus();
            changeTextColorByStatus(orderStatus, holder.mOrderStatus);

            ImageLoader.getInstance().displayImage(data.getIcon(), holder.mOrderBrand, getMessageOptions());

            holder.mOrderTvBrand.setText(data.getGoodsName());
            holder.mOrderDate.setText(data.getCreateDate());

            float price = data.getAmount();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String format = decimalFormat.format(price);
            holder.mOrderPrice.setText("￥" + format);
            //旧
            holder.mOrderPayLine.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);
            holder.mLayPay.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);

            int itemType = data.getType();
// 待支付/自驾办理/呼叫快递
            holder.mOrderPayLine.setVisibility(
                    orderStatus == 0 ||
                            itemType == 6 && (orderStatus == 1 || orderStatus == 3 || orderStatus == 4 || orderStatus == 11 || orderStatus == 12 || orderStatus == 13)
                            || itemType == 7 && (orderStatus == 14 || orderStatus == 1 || orderStatus == 3 || orderStatus == 4)
                            ? View.VISIBLE : View.GONE);
            holder.mLayPay.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);
            holder.mLayAnnual.setVisibility(
                    itemType == 6 && (orderStatus == 1 || orderStatus == 3 || orderStatus == 4 || orderStatus == 11 || orderStatus == 12 || orderStatus == 13)
                            ? View.VISIBLE : View.GONE);
            holder.mLaySubsribe.setVisibility(
                    itemType == 7 && (orderStatus == 14 || orderStatus == 1 || orderStatus == 3 || orderStatus == 4)
                            ? View.VISIBLE : View.GONE);
//年检模块按钮
            holder.mOrderDriving.setVisibility(
                    itemType == 6 && (orderStatus == 11 || orderStatus == 12)
                            ? View.VISIBLE : View.GONE);
            holder.mOrderCourier.setVisibility(
                    itemType == 6 && (orderStatus == 1 || orderStatus == 3 || orderStatus == 4) ? View.VISIBLE : View.GONE);
            holder.mOrderUnCourier.setVisibility(itemType == 6 && orderStatus == 13 ? View.VISIBLE : View.GONE);
//预约模块按钮
            holder.mTvSubscribe.setVisibility(
                    itemType == 7 && (orderStatus == 1 || orderStatus == 3 || orderStatus == 4)
                            ? View.VISIBLE : View.GONE);
            holder.mTvSubscribeCancel.setVisibility(
                    itemType == 7 && orderStatus == 14 ? View.VISIBLE : View.GONE);

            if (holder.mLayPay.getVisibility() != View.GONE) {
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

            if (holder.mOrderDriving.getVisibility() != View.GONE) {
                holder.mOrderDriving.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) mClickListener.doClickDriving(data);
                    }
                });
            }
            if (holder.mOrderCourier.getVisibility() != View.GONE) {
                holder.mOrderCourier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) mClickListener.doClickCourier(data);
                    }
                });
            }

            if (holder.mTvSubscribe.getVisibility() != View.GONE) {
                holder.mTvSubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) mClickListener.doClickSubscribe(data);
                    }
                });
            }
            if (holder.mTvSubscribeCancel.getVisibility() != View.GONE) {
                holder.mTvSubscribeCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) mClickListener.doClickUnSubscribe(data);
                    }
                });
            }

        }
    }

    /**
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3已锁定
     */
    private void changeTextColorByStatus(int status, TextView tvPayStatus) {
        switch (status) {
            case 0:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvOrange_ef));
                tvPayStatus.setText("待支付");
                break;
            case 1:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("已支付");
                break;
            case 3:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("进行中");
                break;
            case 4:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("已完成");
                break;
            case 2:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvBlack_b2));
                tvPayStatus.setText("已取消");
                break;
            default:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvBlack_b2));
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
        @Bind(R.id.lay_annual)
        RelativeLayout mLayAnnual;
        @Bind(R.id.tv_driving)
        TextView mOrderDriving;
        @Bind(R.id.tv_courier)
        TextView mOrderCourier;
        @Bind(R.id.tv_uncourier)
        TextView mOrderUnCourier;

        @Bind(R.id.lay_subscribe)
        RelativeLayout mLaySubsribe;
        @Bind(R.id.tv_subscribe)
        TextView mTvSubscribe;
        @Bind(R.id.tv_cancel_subscribe)
        TextView mTvSubscribeCancel;

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

        void doClickDriving(OrderListBean bean);

        void doClickCourier(OrderListBean bean);

        void doClickSubscribe(OrderListBean bean);

        void doClickUnSubscribe(OrderListBean bean);
    }

}
