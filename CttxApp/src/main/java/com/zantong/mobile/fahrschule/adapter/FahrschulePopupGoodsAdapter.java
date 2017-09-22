package com.zantong.mobile.fahrschule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.zantong.mobile.fahrschule.bean.AresGoodsBean;

import java.util.List;

/**
 *
 */
public class FahrschulePopupGoodsAdapter extends BaseAdapter {

    private final List<AresGoodsBean> mDataList;
    private final Context activityContext;

    public FahrschulePopupGoodsAdapter(Context context, List<AresGoodsBean> aresBeanList) {
        activityContext = context;

        mDataList = aresBeanList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activityContext).inflate(R.layout.adapter_item_text, null);
            holder.mTextName = (TextView) convertView.findViewById(R.id.tv_body);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AresGoodsBean bean = mDataList.get(position);
        holder.mTextName.setText(bean != null ? bean.getName() : "");
        return convertView;
    }

    class ViewHolder {
        TextView mTextName;
    }
}