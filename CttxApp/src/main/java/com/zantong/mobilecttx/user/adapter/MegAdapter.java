package com.zantong.mobilecttx.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.user.bean.MessageType;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2017/2/13.
 * 消息页面
 */

public class MegAdapter extends BaseAdapter<MessageType> {
    /**
     * 布局创建
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     * @param i
     * @return
     */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        Context mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.item_meg_push, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 子类自己实现有要求的数据添加
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void append(List<MessageType> itemDataTypes) {
        if (itemDataTypes.size() > 0) {
            for (MessageType messageType : itemDataTypes) {
                if (messageType.getIsDeleted() == 0)
                    getAll().add(messageType.getId() - 1, messageType);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 数据绑定
     *
     * @param viewHolder 基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param position   位置
     * @param data       数据集合
     */
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, MessageType data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mTitle.setText(data.getTitle());
            holder.mContent.setText(data.getDescription());

            int count = data.getCount();
            holder.mCount.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
            holder.mCount.setText(count > 99 ? count + "+" : String.valueOf(count));
        }
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.meg_img)
        ImageView mImg;
        @Bind(R.id.meg_title)
        TextView mTitle;
        @Bind(R.id.meg_content)
        TextView mContent;
        @Bind(R.id.meg_count)
        TextView mCount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
