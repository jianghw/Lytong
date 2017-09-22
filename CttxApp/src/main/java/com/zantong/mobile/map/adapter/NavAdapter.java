package com.zantong.mobile.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobile.model.AppInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 导航列表适配器
 * @author Sandy
 * create at 16/11/26 上午1:50
 */
public class NavAdapter extends BaseAdapter<AppInfo> {

    private Context mContext;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, AppInfo data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.mName.setText(data.getAppLabel());
            holder.mImg.setImageDrawable(data.getAppIcon());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_map_nav,viewGroup,false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view,int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_map_imageview)
        ImageView mImg;
        @Bind(R.id.item_map_appname)
        TextView mName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
