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
import com.zantong.mobilecttx.utils.RefreshNewTools.BaseRecyclerAdapter;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.imagetools.ImageLoad;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 */
public class ViewVehiclesAdapter extends BaseRecyclerAdapter<OpenQueryBean.RspInfoBean.UserCarsInfoBean, ViewVehiclesAdapter.MyViewHolder>{

    Context mContext ;
    int payNumber = 2;
    LinkedList<OpenQueryBean.RspInfoBean.UserCarsInfoBean> mLinkedList;
    List<OpenQueryBean.RspInfoBean.UserCarsInfoBean> mIllegalQueryBeans = new ArrayList<>();

    public ViewVehiclesAdapter(Context context) {
        super(context);
    }

    public ViewVehiclesAdapter(Context mContext, LinkedList mItemLists){
        super(mContext, mItemLists);
        this.mContext = mContext;
        this.mLinkedList = mItemLists;
    }

    public static enum ITEM_TYPE{
        ITEM_TYPE_NORAML, ITEM_TYPE_SPECIAL
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(ITEM_TYPE.ITEM_TYPE_NORAML.ordinal() == viewType){
            view = LayoutInflater.from(mContext).inflate(R.layout.view_vehicles_item, parent, false);
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.check_car_item_special, parent, false);
        }
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(payNumber != 2 || payNumber != position){
            holder.is_pay_flag.setVisibility(View.VISIBLE);
            if(2 == payNumber && position<payNumber){
                holder.is_pay_flag.setImageResource(R.mipmap.pay_notice_image);
            }else if(2 == payNumber && position>payNumber){
                holder.is_pay_flag.setImageResource(R.mipmap.no_pay_notice_image);
            }else {
                holder.is_pay_flag.setVisibility(View.GONE);
            }
            if(2 != payNumber || position != payNumber){
                holder.query_listory_car_number.setText(mItemLists.get(position).getCarnum());
                if(Tools.isStrEmpty(mItemLists.get(position).getUntreatamt())){
                    holder.view_vehicles_penalty_tv.setText("共计罚款 0元");
                }else{
                    long money = Long.parseLong(mItemLists.get(position).getUntreatamt());

                    try {
                        holder.view_vehicles_penalty_tv.setText("共计罚款 " + AmountUtils.changeF2Y(money)+"元");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(Tools.isStrEmpty(mItemLists.get(position).getUntreatcent())){
                    holder.view_vehicles_penalty_tv.setText("共计罚分 0分");
                }else{
                    holder.view_vehicles_penalty_point_tv.setText("共计罚分 " + mItemLists.get(position).getUntreatcent() + "分");
                }

                if("0".equals(mItemLists.get(position).getUntreatcount()) || Tools.isStrEmpty(mItemLists.get(position).getUntreatcount())){
                    holder.query_history_time.setText("暂无违章");
                    holder.query_history_time.setTextColor(getContext().getResources().getColor(R.color.colorGrayFontSmmall));
                }else{
                    holder.query_history_time.setText(mItemLists.get(position).getUntreatcount()+" 条违章");
                    holder.query_history_time.setTextColor(getContext().getResources().getColor(R.color.colorTranFontRed));
                }
            }

            ImageLoad.load(mItemLists.get(position).getCarimage(), holder.view_vehicles_image_head);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(payNumber == 2 && payNumber == position){
            return ITEM_TYPE.ITEM_TYPE_SPECIAL.ordinal();
        }else{
            return ITEM_TYPE.ITEM_TYPE_NORAML.ordinal();
        }
//        return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        ImageView is_pay_flag;
        TextView query_listory_car_number;
        TextView view_vehicles_penalty_tv;
        TextView view_vehicles_penalty_point_tv;
        TextView query_history_time;
        ImageView view_vehicles_image_head;
        public MyViewHolder(View itemView) {
            super(itemView);

            is_pay_flag = (ImageView) itemView.findViewById(R.id.is_pay_flag);
            query_listory_car_number = (TextView) itemView.findViewById(R.id.query_listory_car_number);
            view_vehicles_penalty_tv = (TextView) itemView.findViewById(R.id.view_vehicles_penalty_tv);
            view_vehicles_penalty_point_tv = (TextView) itemView.findViewById(R.id.view_vehicles_penalty_point_tv);
            query_history_time = (TextView) itemView.findViewById(R.id.query_history_time);
            view_vehicles_image_head = (ImageView) itemView.findViewById(R.id.view_vehicles_image_head);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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

    public void setPayNumber(int payNumber) {
        this.payNumber = payNumber;
    }

}
