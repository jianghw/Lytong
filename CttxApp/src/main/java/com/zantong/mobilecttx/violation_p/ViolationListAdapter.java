package com.zantong.mobilecttx.violation_p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.violation_v.IViolationListUi;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

public class ViolationListAdapter extends BaseAdapter<ViolationBean> {

    private final IViolationListUi mViolationListUi;
    private Context contextAdapter;

    public ViolationListAdapter(IViolationListUi violationListUi) {
        mViolationListUi = violationListUi;
    }

    /**
     * 状态1 广告
     */
    @Override
    public int getItemViewType(int position) {
        ViolationBean bean = getAll().get(position);
        if (TextUtils.isEmpty(bean.getCarnum())) return 1;
        else return 0;
    }

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        contextAdapter=viewGroup.getContext();

        LayoutInflater inflater = LayoutInflater.from(contextAdapter);
        int resource;
        if (viewType == 0) {
            resource = R.layout.recycler_item_violation_list;
        } else if (viewType == 1) {
            resource = R.layout.custom_recycler_foot_adv;
        } else {
            resource = R.layout.recycler_item_violation_list;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        if (itemType == 0) {
            return new ViewHolder(view);
        } else {
            return new AdvViewHolder(view);
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final ViolationBean violationBean) {
        int type = viewHolder.getItemViewType();
        if (type == 0) {
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
                    holder.mTvStatus.setTextColor(contextAdapter.getResources().getColor(R.color.res_color_red_ef));
                    holder.mTvPay.setVisibility(View.VISIBLE);
                } else {
                    holder.mTvStatus.setText("已处理");
                    holder.mTvStatus.setTextColor(contextAdapter.getResources().getColor(R.color.colorTvGreen_80));
                    holder.mTvPay.setVisibility(View.GONE);
                }
            }
        } else {
            AdvViewHolder holder = (AdvViewHolder) viewHolder;
        }
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

    public static class AdvViewHolder extends BaseRecyclerViewHolder {
        FrameLayout mLayFty;

        public AdvViewHolder(View view) {
            super(view);
            this.mLayFty = (FrameLayout) view.findViewById(R.id.fl_fty);
        }
    }
}
