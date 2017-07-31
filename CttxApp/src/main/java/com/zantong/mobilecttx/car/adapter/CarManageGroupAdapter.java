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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 帮助与反馈适配器
 *
 * @author Sandy
 *         create at 16/6/2 下午12:54
 */
public class CarManageGroupAdapter extends BaseAdapter<CarInfoDTO> {

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CONTENT = 1;
    private List<CarInfoDTO> mData = new ArrayList<>();
    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, CarInfoDTO data) {
        switch (viewHolder.getItemViewType()) {
            case TYPE_TITLE:
                TitleViewHolder holder0 = (TitleViewHolder) viewHolder;
                if ("1".equals(data.getIspaycar())) {
                    holder0.mTitle.setText("可缴费车辆");
                } else {
                    holder0.mTitle.setText("紧查询车辆");
                }

                break;
            case TYPE_CONTENT:
                ViewHolder holder = (ViewHolder) viewHolder;
                if (data != null) {
                    holder.mCarNumber.setText(data.getCarnum());
                    if ("1".equals(data.getIspaycar())) {
                        holder.mFlag.setVisibility(View.VISIBLE);
                    } else {
                        holder.mFlag.setVisibility(View.GONE);
                    }
                }
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (isPayCar(position)) {
            return TYPE_CONTENT;
        } else {
            return TYPE_TITLE;
        }
    }

    private boolean isPayCar(int pos) {
        if ("1".equals(mData.get(pos).getIspaycar())) {
            return true;
        }
        return false;
    }

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        if (viewType == TYPE_TITLE) {
            return inflate.inflate(R.layout.item_manage_vehicles_group, viewGroup, false);
        } else {
            return inflate.inflate(R.layout.recycle_list_item_car, viewGroup, false);
        }
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        if (itemType == TYPE_TITLE) {
            return new TitleViewHolder(view);
        } else {
            return new ViewHolder(view);
        }
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

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class TitleViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_manage_vehicles_title)
        TextView mTitle;

        public TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
