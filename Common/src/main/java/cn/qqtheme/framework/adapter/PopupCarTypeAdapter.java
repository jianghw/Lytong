package cn.qqtheme.framework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.qqtheme.framework.R;

/**
 * 底部弹出框 列表显示
 */
public class PopupCarTypeAdapter extends BaseAdapter {

    private final List<String> mDataList;
    private final Context activityContext;

    public PopupCarTypeAdapter(Context context, List<String> aresBeanList) {
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        TextView mTextName;
    }
}