package com.zantong.mobilecttx.weizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.RefreshNewTools.BaseRecyclerAdapter;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/5/10.
 */
public class VehicleTypeAdapter extends BaseRecyclerAdapter<String, VehicleTypeAdapter.MyViewHolder> {

    private int selectPosition = -1;

    public VehicleTypeAdapter(Context context) {
        super(context);
    }

    public VehicleTypeAdapter(Context mContext, LinkedList mItemLists){
        super(mContext, mItemLists);
    }

    @Override
    public VehicleTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vehicle_type_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VehicleTypeAdapter.MyViewHolder holder, int position) {
//        MyViewHolder viewHolder = (MyViewHolder) holder;
        holder.vehicle_type_text.setText(mItemLists.get(position));
        if(holder.vehicle_type_text.getText().toString().equals(LoginData.getInstance().mHashMap.get("VehicleType"))){
            holder.vehicle_type_choose.setVisibility(View.VISIBLE);
        }else{
            holder.vehicle_type_choose.setVisibility(View.GONE);
        }
        if(selectPosition == position){
            holder.vehicle_type_choose.setVisibility(View.VISIBLE);
        }else if(selectPosition != -1){
            holder.vehicle_type_choose.setVisibility(View.GONE);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView vehicle_type_text;
        public ImageView vehicle_type_choose;

        public MyViewHolder(View itemView) {
            super(itemView);
            vehicle_type_text = (TextView) itemView.findViewById(R.id.vehicle_type_text);
            vehicle_type_choose = (ImageView) itemView.findViewById(R.id.vehicle_type_choose);
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

    public void setSelectPosition(int position){
        this.selectPosition = position;
        notifyDataSetChanged();
    }
}
