package com.zantong.mobilecttx.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.user.bean.MsgBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的消息适配器
 * @author Sandy
 * create at 16/6/2 下午12:54
 */
public class MsgAdapter extends BaseAdapter<MsgBean> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, MsgBean data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.mTitle.setText(data.getMsg_title());
            holder.mDate.setText("2016-06-06 18:24:57");
            holder.mContent.setText(data.getMsg_content());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mine_msg_item,viewGroup,false);

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
        @Bind(R.id.mine_msg_item_date)
        TextView mDate;
        @Bind(R.id.mine_msg_item_title)
        TextView mTitle;
        @Bind(R.id.mine_msg_item_content)
        TextView mContent;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
