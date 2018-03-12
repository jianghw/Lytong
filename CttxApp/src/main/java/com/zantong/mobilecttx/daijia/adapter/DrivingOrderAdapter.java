package com.zantong.mobilecttx.daijia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderListBean;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.HashUtils;
import com.zantong.mobilecttx.utils.StringUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2017/2/17.
 */

public class DrivingOrderAdapter extends BaseAdapter<DaiJiaOrderListBean> {

    private Context mContext;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final DaiJiaOrderListBean data) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mOrderId.setText(data.getOrderId());
            holder.mDate.setText(data.getCreateTime());
            holder.mStatus.setText(data.getOrderStatus());

            // -1其取消  1派单中
            if ("已取消".equals(data.getOrderStatus())) {
                holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_cc));
            } else if ("派单中".equals(data.getOrderStatus())) {
                holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.res_color_red_f3));
            } else {
                holder.mStatus.setTextColor(mContext.getResources().getColor(R.color.gray_66));
            }
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.item_driving, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.driving_item_date)
        TextView mDate;
        @Bind(R.id.driving_item_order_status)
        TextView mStatus;
        @Bind(R.id.driving_item_orderid)
        TextView mOrderId;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 取消订单dialog
     */
    private void cleanOrderDialog(final String orderId, final TextView mStatus, final TextView mOption) {
        DialogUtils.telDialog(mContext, "提示", "您确定取消订单吗？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaiJiaOrderDetailDTO dto = new DaiJiaOrderDetailDTO();
                String time = "1488253689";
                try {
                    time = StringUtils.getTimeToStr();
                } catch (Exception e) {

                }
                dto.setTime(time);
                dto.setOrderId(orderId);
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("time", time);
                hashMap.put("orderId", orderId);
                dto.setHash(HashUtils.getSignature(hashMap));
                CarApiClient.cancelDaiJiaOrderDetail(mContext, dto, new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                        if (result.getResponseCode() == 2000) {
                            mStatus.setText("已取消");
                            mStatus.setTextColor(R.color.gray_cc);
                            mOption.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}