package com.zantong.mobilecttx.weizhang.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.interf.ItemClickListener;
import com.zantong.mobilecttx.interf.SectionStateChangeListener;
import com.zantong.mobilecttx.weizhang.bean.ViolationCarInfo;
import com.zantong.mobilecttx.weizhang.bean.ViolationItemInfo;

import java.util.ArrayList;

/**
 * Created by lenovo on 2/23/2016.
 */
public class SectionedExpandableListAdapter extends RecyclerView.Adapter<SectionedExpandableListAdapter.ViewHolder> {

    //data array
    private ArrayList<Object> mDataArrayList;

    //context
    private final Context mContext;

    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;

    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.item_violation_history_section;
    private static final int VIEW_TYPE_ITEM = R.layout.item_violation_history_content; //TODO : change this

    boolean isOpen = false;

    public SectionedExpandableListAdapter(Context context, ArrayList<Object> dataArrayList,
                                          final LinearLayoutManager gridLayoutManager, ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
        mSectionStateChangeListener = sectionStateChangeListener;
        mDataArrayList = dataArrayList;
    }

    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof ViolationCarInfo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (holder.viewType) {
            case VIEW_TYPE_ITEM :
                final ViolationItemInfo item = (ViolationItemInfo) mDataArrayList.get(position);
//                holder.itemTextView.setText(item.getName());
                holder.mTm.setText("违章时间："+item.getPeccancydate());
                holder.mAount.setText(item.getOrderprice()+"元");
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(item);
                    }
                });
                break;
            case VIEW_TYPE_SECTION :
                final ViolationCarInfo section = (ViolationCarInfo) mDataArrayList.get(position);
//                holder.sectionTextView.setText(section.getName());
                holder.mPlateNum.setText(section.getCarnum());
                holder.mCountAmount.setText(section.getTotalPrice()+"元");
                holder.mSectionLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        for (int i = 0; i < mDataArrayList.size(); i++){
//                            if (position != i){
//                                ViolationCarInfo info = (ViolationCarInfo)mDataArrayList.get(i);
//                                mSectionStateChangeListener.onSectionStateChanged(section, false);
//                            }
//                        }
                        if (isOpen)  {
                            isOpen = false;
                             mSectionStateChangeListener.onSectionStateChanged(section, true);
                        } else{
                            isOpen = true;
                              mSectionStateChangeListener.onSectionStateChanged(section, false);
                        }

                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position))
            return VIEW_TYPE_SECTION;
        else return VIEW_TYPE_ITEM;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        View view;
        int viewType;

        //for section
        TextView mPlateNum;
        TextView mEingineNum;
        TextView mCountAmount;
        View mSectionLayout;

        //for item
        TextView mTm;
        TextView mAount;

        public ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            if (viewType == VIEW_TYPE_ITEM) {
                mTm = (TextView) view.findViewById(R.id.item_violation_history_tm);
                mAount = (TextView) view.findViewById(R.id.item_violation_history_amount);
            } else {
                mSectionLayout = view.findViewById(R.id.item_violation_history_layout);
                mPlateNum = (TextView) view.findViewById(R.id.item_violation_history_plate);
                mEingineNum = (TextView) view.findViewById(R.id.item_violation_history_engine);
                mCountAmount = (TextView) view.findViewById(R.id.item_violation_history_count);
            }
        }
    }

    public void refresh(){
        notifyDataSetChanged();
    }
}
