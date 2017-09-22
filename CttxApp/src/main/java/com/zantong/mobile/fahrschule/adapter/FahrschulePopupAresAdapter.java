package com.zantong.mobile.fahrschule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.zantong.mobile.fahrschule.bean.MerchantAresBean;

import java.util.List;

/**
 * 弹出框
 */
public class FahrschulePopupAresAdapter extends BaseAdapter {

    private final List<MerchantAresBean> mDataList;
    private final Context activityContext;

    public FahrschulePopupAresAdapter(Context context, List<MerchantAresBean> aresBeanList) {
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
        MerchantAresBean bean = mDataList.get(position);
        holder.mTextName.setText(bean != null ? bean.getFullName() : "");
        return convertView;
    }

    class ViewHolder {
        TextView mTextName;
    }
}