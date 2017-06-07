package com.zantong.mobilecttx.weizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/10.
 */
public class QueryHistoryAdapter extends RecyclerView.Adapter<QueryHistoryAdapter.NormalViewHolder>{
    private LinkedList<QueryHistoryBean.QueryCarBean> mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnRecyclerviewItemListener mOnRecyclerviewItemListener;

    public static   enum  ITEM_TYPE{
        ITEM_TYPE_NORAML, ITEM_TYPE_SPECIAL
    }
    public QueryHistoryAdapter(Context mContext, LinkedList<QueryHistoryBean.QueryCarBean> mDatas){
        this.mDatas = mDatas;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public QueryHistoryAdapter.NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            NormalViewHolder mNormalViewHolder = new NormalViewHolder(mLayoutInflater.inflate(R.layout.query_history_item, parent, false));
            return mNormalViewHolder;
    }

    public void setListener(OnRecyclerviewItemListener l){
        this.mOnRecyclerviewItemListener = l;
    }

    @Override
    public void onBindViewHolder(QueryHistoryAdapter.NormalViewHolder holder, int position) {
        holder.query_listory_car_number.setText(mDatas.get(position).getCarNumber());
        holder.query_history_engine_number.setText("发动机号："+mDatas.get(position).getEngineNumber());
        holder.query_history_time.setText(mDatas.get(position).getQueryTime());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @Bind(R.id.query_listory_car_number)
        TextView query_listory_car_number;
        @Bind(R.id.query_history_engine_number)
        TextView query_history_engine_number;
        @Bind(R.id.query_history_time)
        TextView query_history_time;
        public NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mOnRecyclerviewItemListener.OnItemClick(v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mOnRecyclerviewItemListener.OnItemLongClick(getPosition());
            return true;
        }
    }

    public interface OnRecyclerviewItemListener{
        public void OnItemClick(View view, int position);
        public void OnItemLongClick(int position);
    }

}
