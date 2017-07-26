package com.zantong.mobilecttx.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 帮助与反馈适配器
 *
 * @author Sandy
 *         create at 16/6/2 下午12:54
 */
public class CarManageAdapter extends BaseAdapter<CarInfoDTO> {

    private Context mContext;
    private int payNumber = 0;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, CarInfoDTO data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mCarNumber.setText(data.getCarnum());
            if ("1".equals(data.getIspaycar())) {
                holder.mFlag.setVisibility(View.VISIBLE);
            } else {
                holder.mFlag.setVisibility(View.GONE);
            }

        }
    }

    public static enum ITEM_TYPE {
        ITEM_TYPE_NORAML, ITEM_TYPE_PAY
    }

    @Override
    public int getItemViewType(int position) {
        if (payNumber == 2 && position == 0) {
            return ITEM_TYPE.ITEM_TYPE_PAY.ordinal();
        } else {
            return ITEM_TYPE.ITEM_TYPE_NORAML.ordinal();
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        return inflate.inflate(R.layout.recycle_list_item_car, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_manage_vehicles_num)
        TextView mCarNumber;
        @Bind(R.id.item_manage_vehicles_flag)
        TextView mFlag;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setPayNumber(int payNumber) {
        this.payNumber = payNumber;
    }

}
