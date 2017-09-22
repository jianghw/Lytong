package com.zantong.mobile.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobile.R;
import com.zantong.mobile.common.bean.CommonProblem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemAdapter extends BaseAdapter<CommonProblem> {

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, CommonProblem data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mTextPositon.setText(data.getProblemId() + "、");
            holder.mText.setText(data.getProblemChileTitle());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.item_commom_problem, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.tv_positon)
        TextView mTextPositon;
        @Bind(R.id.common_problem_title)
        TextView mText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
