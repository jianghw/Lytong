package com.zantong.mobilecttx.huodong.adapter;

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

/**
 * 帮助与反馈适配器
 *
 * @author Sandy
 *         create at 16/6/2 下午12:54
 */
public class HundredPlanCarsAdapter extends BaseAdapter<UserCarInfoBean> {

    private Context mContext;


    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, UserCarInfoBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            holder.mCarNumber.setText(data.getCarnum());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        return inflate.inflate(R.layout.item_hundred_plan_car, viewGroup, false);
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
