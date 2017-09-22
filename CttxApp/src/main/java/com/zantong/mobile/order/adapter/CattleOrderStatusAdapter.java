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
import com.tzly.annual.base.bean.response.CattleOrderBean;
import com.tzly.annual.base.imple.CattleOrderItemListener;
import com.zantong.mobile.R;

import java.text.DecimalFormat;

/**
 * 消息页面
 */
public class CattleOrderStatusAdapter extends BaseAdapter<CattleOrderBean> {

    private Context mAdapterContext;
    private CattleOrderItemListener mClickListener;

    /**
     * 自定义类型布局
     * 订单状态,0未至付，1已支付,2取消或过期，3进行中 4已完成
     * 5-已取件,6-证件齐全,7-证件不全,8-证件不全中,9-代办成功资料送达(6过来),
     * 10-代办成功资料送达(8过来),11-订单完成(6过来),12-订单完成(8过来),13-取件中
     */

    /**
     * 布局创建
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
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final CattleOrderBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            String orderNum = mAdapterContext.getResources().getString(R.string.tv_order_num);
            holder.mTvOrderNum.setText(String.format(orderNum, data.getOrderNum()));

            int orderStatus = data.getState();
            changeTextColorByStatus(orderStatus, holder.mTvOrderStatus);

//            ImageLoader.getInstance().displayImage(data.getIcon(), holder.mOrderBrand, getMessageOptions());

//            holder.mTvBrand.setText(data.getGoodsName());
            holder.mTvDate.setText(data.getCreateTime());

            float price = data.getPayableAmount();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String format = decimalFormat.format(price);
            holder.mTvPrice.setText("￥" + format);
            //旧
            holder.mTvPayLine.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);
            holder.mLayPay.setVisibility(orderStatus == 0 ? View.VISIBLE : View.GONE);

//            int itemType = data.getType();

            if (holder.mLayPay.getVisibility() != View.GONE) {
                holder.mTvPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickListener != null) mClickListener.doClickHave(data);
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
            case 14:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("已接单");
                break;
            case 3:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("资料审核中");
                break;
            case 32:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("办理中");
                break;
            case 4:
                tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvBlack_b2));
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

    public void setItemClickListener(CattleOrderItemListener listener) {
        mClickListener = listener;
    }

    public static class ViewHolder extends BaseRecyclerViewHolder {
        TextView mTvOrderNum;
        TextView mTvOrderStatus;
        ImageView mImgBrand;
        TextView mTvBrand;
        TextView mTvDate;
        TextView mTvPrice;
        TextView mTvPayLine;
        TextView mTvPay;
        TextView mTvCancelOrder;
        RelativeLayout mLayPay;
        TextView mTvDriving;
        TextView mTvCourier;
        TextView mTvUncourier;
        RelativeLayout mLayAnnual;
        TextView mTvSubscribe;
        TextView mTvCancelSubscribe;
        RelativeLayout mLaySubscribe;

        ViewHolder(View view) {
            super(view);

            this.mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
            this.mTvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
            this.mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
            this.mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
            this.mTvDate = (TextView) view.findViewById(R.id.tv_date);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvPayLine = (TextView) view.findViewById(R.id.tv_pay_line);
            this.mTvPay = (TextView) view.findViewById(R.id.tv_pay);
            this.mTvCancelOrder = (TextView) view.findViewById(R.id.tv_cancel_order);
            this.mLayPay = (RelativeLayout) view.findViewById(R.id.lay_pay);
            this.mTvDriving = (TextView) view.findViewById(R.id.tv_driving);
            this.mTvCourier = (TextView) view.findViewById(R.id.tv_courier);
            this.mTvUncourier = (TextView) view.findViewById(R.id.tv_uncourier);
            this.mLayAnnual = (RelativeLayout) view.findViewById(R.id.lay_annual);
            this.mTvSubscribe = (TextView) view.findViewById(R.id.tv_subscribe);
            this.mTvCancelSubscribe = (TextView) view.findViewById(R.id.tv_cancel_subscribe);
            this.mLaySubscribe = (RelativeLayout) view.findViewById(R.id.lay_subscribe);
        }
    }
}
