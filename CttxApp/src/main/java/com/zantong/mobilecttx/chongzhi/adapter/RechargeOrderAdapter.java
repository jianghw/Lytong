package com.zantong.mobilecttx.chongzhi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderBean;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.eventbus.OrderCancelEvent;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.common.activity.BrowserForPayActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 充值订单适配器
 *
 * @author Sandy
 *         2017-01-09 14:48:57
 */
public class RechargeOrderAdapter extends BaseAdapter<RechargeOrderBean> {

    private Context mContext;
    private int mTag;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, final RechargeOrderBean data) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mOrderAmount.setText("￥" + data.getRechargeMoney());
            holder.mAmount.setText("￥" + data.getProdMoney());
            holder.mTime.setText("订单时间：" + data.getRechargeDate());
            holder.mCount.setText("×" + data.getQuantity());
            holder.mNum.setText("共" + data.getQuantity() + "件商品");
            // 0：待支付；1：支付失败；2：已支付（钱支付）；3：退款中；4：退款成功；5：取消
            switch (data.getOrderStatus()) {
                case 0:
                    holder.mStatus.setText("未支付");
                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.mOptionLayout.setVisibility(View.VISIBLE);

                    break;
                case 1:
                    holder.mStatus.setText("支付失败");
                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.mOptionLayout.setVisibility(View.VISIBLE);
                    holder.mCancel.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.mStatus.setText("已完成");
                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_99));
                    holder.mOptionLayout.setVisibility(View.GONE);
                case 3:
                    holder.mStatus.setText("退款中");
                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_99));
                    holder.mOptionLayout.setVisibility(View.GONE);
                case 4:
                    holder.mStatus.setText("已退款");
                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_99));
                    holder.mOptionLayout.setVisibility(View.GONE);
                case 5:
                    holder.mStatus.setText("已取消");
                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_99));
                    holder.mOptionLayout.setVisibility(View.GONE);
                    break;
            }

            holder.mSn.setText("卡号：\n" + data.getOilCardNum());
            holder.mImg.setBackgroundResource("2".equals(data.getOilType()) ? R.mipmap.icon_zhongshiyou : R.mipmap.icon_zhongshihua);
        }
        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.remindDialog(mContext, "温馨提醒", "您确定要取消该订单吗？", "取消", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CancelRechargeOrderDTO dto = new CancelRechargeOrderDTO();
                        dto.setUserId(RSAUtils.strByEncryption(mContext, PublicData.getInstance().userID, true));
                        dto.setOrderId(data.getOrderId());

                        CarApiClient.cancelOrder(mContext, dto, new CallBack<BaseResult>() {
                            @Override
                            public void onSuccess(BaseResult result) {
                                if (result.getResponseCode() == 2000) {
                                    data.setOrderStatus(2);
                                    holder.mStatus.setText("已取消");
                                    holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_99));
                                    holder.mOptionLayout.setVisibility(View.GONE);
                                    EventBus.getDefault().post(new OrderCancelEvent(true));
                                    notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        });
        holder.mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.createRechargeDialog(mContext, data.getOrderId(),
                        data.getRechargeMoney(), data.getPayType(),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int orderPrice = 0;
                                try{
                                    orderPrice = Integer.valueOf((int) (Double.valueOf(data.getRechargeMoney()) * 100));
                                }catch (Exception e){

                                }
                                String payUrl = "http://139.196.183.121:8081/payment/payForWapb2cPay?orderid=" +
                                        data.getOrderId() + "&amount=" +
                                        orderPrice + "&payType=0";
                                CarApiClient.getPayOrderSn(mContext, payUrl, new CallBack<PayOrderResult>() {
                                    @Override
                                    public void onSuccess(PayOrderResult result) {
                                        if (result.getResponseCode() == 2000){
                                            PublicData.getInstance().webviewTitle = "支付";
                                            PublicData.getInstance().webviewUrl = result.getData();
                                            Act.getInstance().lauchIntentToLogin(mContext,BrowserForPayActivity.class);
                                        }
                                    }
                                });
                            }
                        });
            }
        });

    }


    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_recharge_order, viewGroup, false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_recharge_time)//创建时间
                TextView mTime;
        @Bind(R.id.item_recharge_content_status)//订单状态
                TextView mStatus;
        @Bind(R.id.item_recharge_content_img)//加油站图片
                ImageView mImg;
        @Bind(R.id.item_recharge_content_sn)//订单编号
                TextView mSn;
        @Bind(R.id.item_recharge_num)//订单数量
                TextView mNum;
        @Bind(R.id.item_recharge_count)//订单数量
                TextView mCount;
        @Bind(R.id.item_recharge_orderamount)//订单金额
                TextView mOrderAmount;
        @Bind(R.id.item_recharge_amount)//订单金额
                TextView mAmount;
        @Bind(R.id.item_recharge_option_layout)//底部布局
                View mOptionLayout;

        @Bind(R.id.item_recharge_cancel)//取消订单
                TextView mCancel;
        @Bind(R.id.item_recharge_pay)
        TextView mPay;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private void cancelOrder(final RechargeOrderBean data) {

    }


}
