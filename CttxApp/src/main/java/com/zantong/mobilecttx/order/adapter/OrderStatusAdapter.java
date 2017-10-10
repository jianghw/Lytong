package com.zantong.mobilecttx.order.adapter;

import android.annotation.SuppressLint;
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

import static cn.qqtheme.framework.util.image.ImageOptions.getMessageOptions;

/**
 * Created by zhoujie on 2017/2/13.
 * 消息页面
 */

public class OrderStatusAdapter extends BaseAdapter<OrderListBean> {
    private static final int TYPE_UNPAID = 1;
    private static final int TYPE_CANCEL = 2;
    private static final int TYPE_COMPLETED = 3;
    private static final int TYPE_COMPLETED_ANNUAL = 6;
    private static final int TYPE_COMPLETED_SUBSCRIBE = 7;

    private Context mAdapterContext;
    private ItemClickListener mClickListener;

    /**
     * 自定义类型布局
     * 0未至付，1已支付,2取消或过期，3进行中 4已完成
     */
    @Override
    public int getItemViewType(int position) {
        OrderListBean bean = getAll().get(position);
        int status = bean.getOrderStatus();
        int type = bean.getType();
        if (status == 0) {
            return TYPE_UNPAID;
        } else if (status == 2) {
            return TYPE_CANCEL;
        } else if (type == 6 && (status == 1 || status == 3 || status == 4 || status == 11 || status == 12 || status == 13)) {
            return TYPE_COMPLETED_ANNUAL;//年检
        } else if (type == 7 && (status == 1 || status == 3 || status == 4 || status == 14)) {
            return TYPE_COMPLETED_SUBSCRIBE;//预约模块
        } else {
            return TYPE_CANCEL;
        }
    }

    /**
     * 布局创建
     */
    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);

        int resource;
        switch (viewType) {
            case TYPE_UNPAID:
                resource = R.layout.rv_item_order_unpaid;
                break;
            case TYPE_CANCEL:
                resource = R.layout.rv_item_order_cancel;
                break;
            case TYPE_COMPLETED_ANNUAL:
                resource = R.layout.rv_item_order_completed_annual;
                break;
            case TYPE_COMPLETED_SUBSCRIBE:
                resource = R.layout.rv_item_order_completed_subscribe;
                break;
            default:
                resource = R.layout.rv_item_order_cancel;
                break;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        switch (itemType) {
            case TYPE_UNPAID:
                return new UnpaidViewHolder(view);
            case TYPE_CANCEL:
                return new CancelViewHolder(view);
            case TYPE_COMPLETED_ANNUAL:
                return new AnnualViewHolder(view);
            case TYPE_COMPLETED_SUBSCRIBE:
                return new SubscribeViewHolder(view);
            default:
                return new CancelViewHolder(view);//未知版本处理
        }
    }

    /**
     * 数据绑定
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3进行中 4已完成
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, OrderListBean data) {
        if (data == null) return;
        switch (viewHolder.getItemViewType()) {
            case TYPE_UNPAID:
                UnpaidViewHolder unpaidViewHolder = (UnpaidViewHolder) viewHolder;
                cancelProcessing(unpaidViewHolder, data);
                unpaidProcessing(unpaidViewHolder, data);
                break;
            case TYPE_CANCEL:
                CancelViewHolder cancelViewHolder = (CancelViewHolder) viewHolder;
                cancelProcessing(cancelViewHolder, data);
                break;
            case TYPE_COMPLETED_ANNUAL:
                AnnualViewHolder annualViewHolder = (AnnualViewHolder) viewHolder;
                cancelProcessing(annualViewHolder, data);
                annualProcessing(annualViewHolder, data);
                break;
            case TYPE_COMPLETED_SUBSCRIBE:
                SubscribeViewHolder subscribeViewHolder = (SubscribeViewHolder) viewHolder;
                cancelProcessing(subscribeViewHolder, data);
                subscribeProcessing(subscribeViewHolder, data);
                break;
            default:
                break;
        }
    }

    private void unpaidProcessing(UnpaidViewHolder holder, final OrderListBean data) {
        holder.mTvCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.doClickCancel(data);
            }
        });
        holder.mTvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.doClickPay(data);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void cancelProcessing(BaseViewHolder holder, OrderListBean data) {
        String orderNum = mAdapterContext.getResources().getString(R.string.tv_order_num);
        holder.mTvOrderNum.setText(String.format(orderNum, data.getOrderId()));

        int orderStatus = data.getOrderStatus();
        changeTextColorByStatus(orderStatus, holder.mTvOrderStatus);

        ImageLoader.getInstance().displayImage(data.getIcon(), holder.mImgBrand, getMessageOptions());

        holder.mTvBrand.setText(data.getGoodsName());
        holder.mTvDate.setText(data.getCreateDate());

        float price = data.getAmount();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(price);
        holder.mTvPrice.setText("￥" + format);
    }

    /**
     * 年检
     */
    private void annualProcessing(AnnualViewHolder holder, final OrderListBean data) {
        int orderStatus = data.getOrderStatus();
        int itemType = data.getType();

        holder.mTvDriving.setVisibility(itemType == 6 && (orderStatus == 11 || orderStatus == 12)
                ? View.VISIBLE : View.GONE);
        holder.mTvCourier.setVisibility(itemType == 6 && (orderStatus == 1 || orderStatus == 3 || orderStatus == 4)
                ? View.VISIBLE : View.GONE);
        holder.mTvUncourier.setVisibility(itemType == 6 && orderStatus == 13 ? View.VISIBLE : View.GONE);

        //自驾办理
        if (holder.mTvDriving.getVisibility() != View.GONE) {
            holder.mTvDriving.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickDriving(data);
                }
            });
        }
        //呼叫快递
        if (holder.mTvCourier.getVisibility() != View.GONE) {
            holder.mTvCourier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickCourier(data);
                }
            });
        }
    }

    private void subscribeProcessing(SubscribeViewHolder holder, final OrderListBean data) {
        int orderStatus = data.getOrderStatus();
        int itemType = data.getType();

        holder.mTvSubscribe.setVisibility(itemType == 7 && (orderStatus == 1 || orderStatus == 3 || orderStatus == 4)
                ? View.VISIBLE : View.GONE);
        holder.mTvCancelSubscribe.setVisibility(itemType == 7 && orderStatus == 14 ? View.VISIBLE : View.GONE);

        if (holder.mTvSubscribe.getVisibility() != View.GONE) {
            holder.mTvSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickSubscribe(data);
                }
            });
        }
        if (holder.mTvCancelSubscribe.getVisibility() != View.GONE) {
            holder.mTvCancelSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickUnSubscribe(data);
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

    /**
     * 未支付
     */
    private class UnpaidViewHolder extends BaseViewHolder {
        TextView mTvPayLine;
        TextView mTvPay;
        TextView mTvCancelOrder;
        RelativeLayout mLayPay;

        public UnpaidViewHolder(View view) {
            super(view);
            this.mTvPayLine = (TextView) view.findViewById(R.id.tv_pay_line);
            this.mTvPay = (TextView) view.findViewById(R.id.tv_pay);
            this.mTvCancelOrder = (TextView) view.findViewById(R.id.tv_cancel_order);
            this.mLayPay = (RelativeLayout) view.findViewById(R.id.lay_pay);
        }
    }

    /**
     * 取消
     */
    private static class CancelViewHolder extends BaseViewHolder {
        CancelViewHolder(View view) {
            super(view);
        }
    }

    private static class BaseViewHolder extends BaseRecyclerViewHolder {
        TextView mTvOrderNum;
        TextView mTvOrderStatus;
        ImageView mImgBrand;
        TextView mTvBrand;
        TextView mTvDate;
        TextView mTvPrice;

        BaseViewHolder(View view) {
            super(view);
            this.mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
            this.mTvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
            this.mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
            this.mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
            this.mTvDate = (TextView) view.findViewById(R.id.tv_date);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        }
    }

    private static class AnnualViewHolder extends BaseViewHolder {
        TextView mTvPayLine;
        TextView mTvDriving;
        TextView mTvCourier;
        TextView mTvUncourier;
        RelativeLayout mLayAnnual;

        public AnnualViewHolder(View view) {
            super(view);
            this.mTvPayLine = (TextView) view.findViewById(R.id.tv_pay_line);
            this.mTvDriving = (TextView) view.findViewById(R.id.tv_driving);
            this.mTvCourier = (TextView) view.findViewById(R.id.tv_courier);
            this.mTvUncourier = (TextView) view.findViewById(R.id.tv_uncourier);
            this.mLayAnnual = (RelativeLayout) view.findViewById(R.id.lay_annual);
        }
    }

    private static class SubscribeViewHolder extends BaseViewHolder {
        TextView mTvPayLine;
        TextView mTvSubscribe;
        TextView mTvCancelSubscribe;
        RelativeLayout mLaySubscribe;

        public SubscribeViewHolder(View view) {
            super(view);
            this.mTvPayLine = (TextView) view.findViewById(R.id.tv_pay_line);
            this.mTvSubscribe = (TextView) view.findViewById(R.id.tv_subscribe);
            this.mTvCancelSubscribe = (TextView) view.findViewById(R.id.tv_cancel_subscribe);
            this.mLaySubscribe = (RelativeLayout) view.findViewById(R.id.lay_subscribe);
        }
    }

}
