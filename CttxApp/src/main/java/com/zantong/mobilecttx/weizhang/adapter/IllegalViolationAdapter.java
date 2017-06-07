package com.zantong.mobilecttx.weizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.BaseRecyclerAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/12.
 */
public class IllegalViolationAdapter extends BaseRecyclerAdapter<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean, IllegalViolationAdapter.MyViewHolder> {

    private List<String> mIllegalViolation;
    private boolean newFlag = false;
    public IllegalViolationAdapter(Context context) {
        super(context);
    }

    public IllegalViolationAdapter(Context mContext, LinkedList mItemLists, List<String> mIllegalViolation) {
        super(mContext, mItemLists);
        this.mIllegalViolation = mIllegalViolation;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.illegal_violation_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LogUtils.i(mItemLists.get(position).getViolationdate()+"<<<<"+mItemLists.get(position).getViolationtime());
        try {
            String date = mItemLists.get(position).getViolationdate();
            date = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8) + " ";
            String time = mItemLists.get(position).getViolationtime();
            time = time.substring(0,2)+":"+time.substring(2,4);
            holder.illegalViolationTime.setText(date+time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.illegal_violation_money.setText(AmountUtils.changeF2Y(mItemLists.get(position).getViolationamt())+"元");
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.illegal_violation_cent.setText(mItemLists.get(position).getViolationcent()+"分");
        if("1".equals(mItemLists.get(position).getProcessste())){
            holder.illegal_violation_item_deal.setVisibility(View.VISIBLE);
        }else{
            holder.illegal_violation_item_deal.setVisibility(View.GONE);
        }

//        if(null == mIllegalViolation || mIllegalViolation.size() == 0){
//            holder.illegal_violation_item_new.setVisibility(View.VISIBLE);
//            newFlag = true;
//        }else{
//            if(mIllegalViolation.contains(mItemLists.get(position).getViolationnum())){
//                holder.illegal_violation_item_new.setVisibility(View.GONE);
//            }else{
//                newFlag = true;
//                holder.illegal_violation_item_new.setVisibility(View.VISIBLE);
//            }
//        }


    }

    public boolean getNewFlag(){
        return newFlag;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @Bind(R.id.preferential_activity_title)
        TextView preferentialActivityTitle;
        @Bind(R.id.illegal_violation_time)
        TextView illegalViolationTime;
        @Bind(R.id.illegal_violation_money)
        TextView illegal_violation_money;
        @Bind(R.id.illegal_violation_cent)
        TextView illegal_violation_cent;
        @Bind(R.id.illegal_violation_item_new)
        ImageView illegal_violation_item_new;
        @Bind(R.id.illegal_violation_item_deal)
        TextView illegal_violation_item_deal;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecyclerViewListener.onItemClick(v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onRecyclerViewListener.onItemLongClick(getPosition());
            return true;
        }
    }

    public void setIllegalViolation(List<String> mIllegalViolation){
        this.mIllegalViolation = mIllegalViolation;
    }
}
