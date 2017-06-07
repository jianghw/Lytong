package com.zantong.mobilecttx.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.bean.CommonProblem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemAdapter extends BaseAdapter<CommonProblem> {
    private Context mContext;


    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, CommonProblem data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.mText.setText(data.getProblemId() + ". " + data.getProblemChileTitle());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_commom_problem, viewGroup,false);
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
        @Bind(R.id.common_problem_title)
        TextView mText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
