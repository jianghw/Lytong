package com.zantong.mobilecttx.user.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.user.bean.MessageType;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.tzly.annual.base.util.DateTools;

import static com.tzly.annual.base.util.image.ImageOptions.getMessageOptions;

/**
 * Created by zhoujie on 2017/2/13.
 * 消息页面
 */

public class MegAdapter extends BaseAdapter<MessageType> {
    private Context mContext;

    /**
     * 布局创建
     *
     * @param viewGroup 如果需要Context,可以viewGroup.getContext()获取
     * @param i
     * @return
     */
    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.item_meg_push, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 数据绑定
     *
     * @param viewHolder 基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param position   位置
     * @param data       数据集合
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, MessageType data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mTitle.setText(data.getMessageName());
            if (data.getColor() != null)
                holder.mTitle.setTextColor(Color.parseColor("#" + data.getColor()));

            holder.mContent.setText(data.getTitle());

            if (data.getIcon() != null)
                ImageLoader.getInstance().displayImage(data.getIcon(), holder.mImg, getMessageOptions());

            //1 已读  0未读
            holder.mSign.setText("1".equals(data.getFlag()) ? "已读" : "未读");
            holder.mSign.setTextColor(mContext.getResources().getColor(
                    "1".equals(data.getFlag()) ? R.color.gray_4a : R.color.red));

            holder.mDate.setText(DateTools.displayFormatDate(data.getTime()));
        }
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.msg_img)
        ImageView mImg;
        @Bind(R.id.msg_title)
        TextView mTitle;
        @Bind(R.id.msg_content)
        TextView mContent;
        @Bind(R.id.msg_sign)
        TextView mSign;
        @Bind(R.id.msg_date)
        TextView mDate;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
