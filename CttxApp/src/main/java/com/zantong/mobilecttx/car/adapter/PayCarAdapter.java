package com.zantong.mobilecttx.car.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/12.
 */
public class PayCarAdapter extends BaseAdapter<PayCar> {

    Context mContext ;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, PayCar data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mNum.setText(data.getCarnum());

        List<UserCarInfoBean> userCars = PublicData.getInstance().mServerCars;
        for (int i = 0; i < userCars.size(); i++){
            if (userCars.get(i).getCarnum().equals(data.getCarnum())){

            }

        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.item_mine_pay_car, viewGroup, false);
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view,int itemType) {
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
