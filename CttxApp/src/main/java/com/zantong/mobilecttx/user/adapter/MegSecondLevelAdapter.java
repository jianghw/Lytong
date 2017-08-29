package com.zantong.mobilecttx.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.user.bean.Meg;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2017/2/23.
 */

public class MegSecondLevelAdapter extends BaseAdapter<Meg> {
    private Context mContext;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, Meg data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mContent.setText(data.getTitle());
            if("1".equals(data.getFlag())){ //1 已读  0未读
                holder.mIsOrder.setText("已读");
                holder.mIsOrder.setTextColor(mContext.getResources().getColor(R.color.gray_4a));
            }else{
                holder.mIsOrder.setText("未读");
                holder.mIsOrder.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_meg_second_level, viewGroup, false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_meg_context)
        TextView mContent;
        @Bind(R.id.item_meg_is_order)
        TextView mIsOrder;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
