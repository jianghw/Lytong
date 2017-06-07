package com.zantong.mobilecttx.map.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.map.bean.GasStation;
import com.zantong.mobilecttx.utils.DistanceUtils;
import com.zantong.mobilecttx.map.activity.BaiduMapActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2016/10/31.
 */

public class GasStationAdapter extends RecyclerView.Adapter {
    private List<GasStation> mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnRecyclerviewItemListener mOnRecyclerviewItemListener;

    public GasStationAdapter(Context mContext, List<GasStation> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_gas_station, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        GasStation item = mDatas.get(position);
        viewHolder.mName.setText(item.getName());
        BaiduMapActivity a = (BaiduMapActivity)mContext;
        String distanceStr = DistanceUtils.getDistance(a.latitudeStr, a.longitudeStr, String.valueOf(item.getLat()), String.valueOf(item.getLng()));
        viewHolder.mDistance.setText(distanceStr);

        if (TextUtils.isEmpty(item.getNinetyTwoNum()) || item.getNinetyTwoNum().equals("0.00")) {
            viewHolder.mNinetwo.setText("无优惠");
            viewHolder.mNinetwo.setTextColor(mContext.getResources().getColor(R.color.gray_66));
        } else {
            viewHolder.mNinetwo.setText(item.getNinetyTwoNum());
            viewHolder.mNinetwo.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        viewHolder.mDistance.setText(DistanceUtils.getDistance(BaiduMapActivity.mapActivity.geLat(),
                BaiduMapActivity.mapActivity.geLng(),
                "" + item.getLat(), "" + item.getLng()));
        if (TextUtils.isEmpty(item.getNinetyFiveNum()) || item.getNinetyTwoNum().equals("0.00")) {
            viewHolder.mNinefive.setText("无优惠");
            viewHolder.mNinefive.setTextColor(mContext.getResources().getColor(R.color.gray_66));
        } else {
            viewHolder.mNinefive.setText(item.getNinetyFiveNum());
            viewHolder.mNinefive.setTextColor(mContext.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public GasStation getItem(int position) {
        return mDatas.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.item_gas_station_name)
        TextView mName;
        @Bind(R.id.item_gas_station_ninetwo)
        TextView mNinetwo;
        @Bind(R.id.item_gas_station_distance)
        TextView mDistance;
        @Bind(R.id.item_gas_station_ninefive)
        TextView mNinefive;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnRecyclerviewItemListener.OnItemClick(v, getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void setListener(OnRecyclerviewItemListener l) {
        this.mOnRecyclerviewItemListener = l;
    }

    public void setDatas(List<GasStation> mDatas) {
        if (mDatas != null && mDatas.size() > 1) {
            this.mDatas.clear();
            this.mDatas = mDatas;
            notifyDataSetChanged();
        }
    }

    public void clearDatas() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public List<GasStation> getDatas(){
        return this.mDatas;
    }

    public interface OnRecyclerviewItemListener {
        public void OnItemClick(View view, int position);

        public void OnItemLongClick(int position);
    }
}
