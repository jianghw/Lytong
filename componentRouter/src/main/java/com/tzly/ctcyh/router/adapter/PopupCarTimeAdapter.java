package com.tzly.ctcyh.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.bean.response.SparringGoodsBean;

import java.util.List;

/**
 * 底部弹出框 列表显示
 */
public class PopupCarTimeAdapter extends BaseAdapter {

    private final List<SparringGoodsBean> mDataList;
    private final Context activityContext;

    public PopupCarTimeAdapter(Context context, List<SparringGoodsBean> aresBeanList) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_car_type, null);
            holder.mTextName = (TextView) convertView.findViewById(R.id.tv_title);
            holder.mTextTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.mImageChoice = (ImageView) convertView.findViewById(R.id.img_choice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SparringGoodsBean sparringGoodsBean = mDataList.get(position);
        if (sparringGoodsBean != null) {
            holder.mTextName.setText(sparringGoodsBean.getName());
            if (sparringGoodsBean.getPrice() > 0)
                holder.mTextTime.setText(sparringGoodsBean.getPrice() + "元/小时");
            holder.mImageChoice.setVisibility(sparringGoodsBean.isChoice() ? View.VISIBLE : View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView mTextName;
        TextView mTextTime;
        ImageView mImageChoice;
    }
}