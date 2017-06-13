package com.zantong.mobilecttx.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.utils.UiHelpers;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 共用选择列表适配器
 * 1、加油充值中用到
 */
public class CommonSelectAdapter extends BaseAdapter<CommonTwoLevelMenuBean> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, final int position, CommonTwoLevelMenuBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mName.setText(data.getContext());

            holder.mFlag.setVisibility(data.getContext().contains("畅通卡") ? View.VISIBLE : View.GONE);

            holder.mChoice.setVisibility(data.getId() == -1 || data.getId() == -2 ? View.VISIBLE : View.GONE);
            holder.mChoice.setImageResource(data.getId() == -1 ? R.mipmap.btn_tick_on : R.mipmap.btn_tick_off);

            UiHelpers.setTextViewIcon(mContext,
                    holder.mName,
                    data.getImgId(),
                    -1,
                    -1,
                    UiHelpers.DRAWABLE_LEFT);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.item_common_select, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {

        @Bind(R.id.item_common_selelct_name)
        TextView mName;
        @Bind(R.id.item_common_selelct_flag)
        ImageView mFlag;
        @Bind(R.id.img_choice)
        ImageView mChoice;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
