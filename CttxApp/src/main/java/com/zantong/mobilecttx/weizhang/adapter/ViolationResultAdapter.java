package com.zantong.mobilecttx.weizhang.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViolationResultAdapter extends BaseAdapter<ViolationBean> {

    private ItemPayListener mClickListener;

    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final ViolationBean violationBean) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (violationBean != null) {
            String violationdate = violationBean.getViolationdate();//违章日期
            String violationtime = violationBean.getViolationtime();//违章时间
            String date = "";
            String time = "";
            if (!TextUtils.isEmpty(violationdate) && violationdate.length() >= 8) {
                date = violationdate.substring(0, 4)
                        + "-" + violationdate.substring(4, 6)
                        + "-" + violationdate.substring(6, 8);
            }
            if (!TextUtils.isEmpty(violationtime) && violationtime.length() >= 4) {
                time = violationtime.substring(0, 2) + ":" + violationtime.substring(2, 4);
            }
            holder.mTm.setText(date + " " + time);

            int processte = violationBean.getProcessste();

            holder.mLayAddr.setVisibility(processte == 2 || processte == 3 ? View.GONE : View.VISIBLE);
            holder.mLayReason.setVisibility(processte == 2 || processte == 3 ? View.GONE : View.VISIBLE);
            String violationplace = violationBean.getViolationplace();
            String violationtype = violationBean.getViolationtype();
            holder.mAddr.setText(violationplace);
            holder.mReason.setText(violationtype);

            String violationamt = violationBean.getViolationamt();
            String violationcent = violationBean.getViolationcent();//扣分
            holder.mAmount.setText(StringUtils.getPriceString(violationamt) + "元");
            holder.mCount.setText(violationcent + "分");

            holder.mPayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) mClickListener.doClickPay(violationBean);
                }
            });
            if (processte == 0 || processte == 2) {
                holder.mPayBtn.setVisibility(View.VISIBLE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_weichuli);
            } else if (processte == 1 || processte == 3) {
                holder.mPayBtn.setVisibility(View.GONE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_yichuli);
            } else {
                holder.mPayBtn.setVisibility(View.GONE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_yichuli);
            }
        }
    }

    public void setItemPayListener(ItemPayListener listener) {
        mClickListener = listener;
    }

    public interface ItemPayListener {
        void doClickPay(ViolationBean bean);
    }


    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.item_violation_result, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_violation_result_tm)
        TextView mTm;//违章时间
        @Bind(R.id.lay_address)
        LinearLayout mLayAddr;
        @Bind(R.id.item_violation_result_addr)
        TextView mAddr;//地点
        @Bind(R.id.lay_reason)
        LinearLayout mLayReason;
        @Bind(R.id.item_violation_result_reason)
        TextView mReason;//行为
        @Bind(R.id.item_violation_result_amount)
        TextView mAmount;//金额
        @Bind(R.id.item_violation_result_count)
        TextView mCount;//扣分数
        @Bind(R.id.item_violation_result_flag)
        ImageView mFlagImg;//标签
        @Bind(R.id.item_violation_result_pay)
        Button mPayBtn;//去处理

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
