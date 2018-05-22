package com.zantong.mobilecttx.violation_p;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.violation_v.IViolationListUi;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

public class ViolationListAdapter extends BaseAdapter<ViolationBean> {

    private final IViolationListUi mViolationListUi;

    public ViolationListAdapter(IViolationListUi violationListUi) {
        mViolationListUi = violationListUi;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final ViolationBean violationBean) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (violationBean != null) {
            String violationdate = violationBean.getViolationdate();//违章日期
            String violationtime = violationBean.getViolationtime();//违章时间
            String date = "", time = "";
            if (!TextUtils.isEmpty(violationdate) && violationdate.length() >= 8) {
                date = violationdate.substring(0, 4) + "-" + violationdate.substring(4, 6)
                        + "-" + violationdate.substring(6, 8);
            }
            if (!TextUtils.isEmpty(violationtime) && violationtime.length() >= 4) {
                time = violationtime.substring(0, 2) + ":" + violationtime.substring(2, 4);
            }
            holder.mTvTime.setText(date + " " + time);


            String violationplace = violationBean.getViolationplace();
            holder.mLayAddress.setVisibility(TextUtils.isEmpty(violationplace) ? View.GONE : View.VISIBLE);
            holder.mTvAdr.setText(violationplace);

            String violationtype = violationBean.getViolationtype();
            holder.mLayReason.setVisibility(TextUtils.isEmpty(violationtype) ? View.GONE : View.VISIBLE);
            holder.mTvReason.setText(violationtype);

            String violationamt = violationBean.getViolationamt();
            String violationcent = violationBean.getViolationcent();//扣分
            holder.mTvAmount.setText(StringUtils.getPriceString(violationamt) + "元");
            holder.mTvResult.setText(violationcent + "分");

            holder.mTvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViolationListUi != null) mViolationListUi.doClickPay(violationBean);
                }
            });

            int processte = violationBean.getProcessste();
            if (processte == 0 || processte == 2) {
                holder.mTvStatus.setText("未处理");
                holder.mTvStatus.setTextColor(Utils.getContext().getColor(R.color.res_color_red_ef));
                holder.mTvPay.setVisibility(View.VISIBLE);
            } else {
                holder.mTvStatus.setText("已处理");
                holder.mTvStatus.setTextColor(Utils.getContext().getColor(R.color.colorTvGreen_80));
                holder.mTvPay.setVisibility(View.GONE);
            }

           /* if (processte == 0 || processte == 2) {
                holder.mPayBtn.setVisibility(View.VISIBLE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_weichuli);
            } else if (processte == 1 || processte == 3) {
                holder.mPayBtn.setVisibility(View.GONE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_yichuli);
            } else {
                holder.mPayBtn.setVisibility(View.GONE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_yichuli);
            }*/
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.recycler_item_violation_list, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        TextView mTvStatus;
        TextView mTvTime;
        TextView mTvAdr;
        LinearLayout mLayAddress;
        TextView mTvReason;
        LinearLayout mLayReason;
        TextView mTvAmount;
        TextView mTvResult;
        TextView mTvPay;

        public ViewHolder(View view) {
            super(view);
            this.mTvTime = (TextView) view.findViewById(R.id.tv_time);
            this.mTvAdr = (TextView) view.findViewById(R.id.tv_adr);
            this.mTvStatus = (TextView) view.findViewById(R.id.tv_status);
            this.mLayAddress = (LinearLayout) view.findViewById(R.id.lay_address);
            this.mTvReason = (TextView) view.findViewById(R.id.tv_reason);
            this.mLayReason = (LinearLayout) view.findViewById(R.id.lay_reason);
            this.mTvAmount = (TextView) view.findViewById(R.id.tv_amount);
            this.mTvResult = (TextView) view.findViewById(R.id.tv_result);
            this.mTvPay = (TextView) view.findViewById(R.id.tv_pay);
        }
    }
}
