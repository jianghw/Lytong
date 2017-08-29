package com.zantong.mobilecttx.car.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.bean.PayCar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PayCarAdapter extends BaseAdapter<PayCar> {

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, PayCar data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mNum.setText(data.getCarnum());
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflate = LayoutInflater.from(viewGroup.getContext());
        return inflate.inflate(R.layout.item_mine_pay_car, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_mine_pay_car_num)
        TextView mNum;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
