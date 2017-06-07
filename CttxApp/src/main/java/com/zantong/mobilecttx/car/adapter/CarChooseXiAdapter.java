package com.zantong.mobilecttx.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.car.bean.CarXiBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhoujie on 2016/11/17.
 */

public class CarChooseXiAdapter  extends BaseAdapter<CarXiBean> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, CarXiBean data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.mText.setText(data.getSeriesName());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_common_list, viewGroup,false);
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
        @Bind(R.id.item_common__list_text)
        TextView mText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
