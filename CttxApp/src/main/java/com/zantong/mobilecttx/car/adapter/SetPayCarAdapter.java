package com.zantong.mobilecttx.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetPayCarAdapter extends BaseAdapter<UserCarInfoBean> {

    private Context mContext;
    private Object showFlag = 0;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position,UserCarInfoBean data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.car_number.setText(data.getCarnum());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.set_pay_car, viewGroup,false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view,int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder{
        @Bind(R.id.car_number)
        TextView car_number;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public void setSelectPosition(Object position){
        this.showFlag = position;
        notifyDataSetChanged();
    }
}
