package com.zantong.mobilecttx.car.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.bean.VehicleLicenseBean;
import com.zantong.mobilecttx.utils.rsa.Des3;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 车俩管理列表
 */
public class ManageCarListAdapter extends BaseAdapter<VehicleLicenseBean> {

    private static final int ITEM_TYPE_TITLE = 100;
    private static final int ITEM_TYPE_CONTENT = 200;

    private List<VehicleLicenseBean> mUserCarInfoBeanList;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, VehicleLicenseBean vehicleLicenseBean) {
        if (vehicleLicenseBean != null) {
            switch (viewHolder.getItemViewType()) {
                case ITEM_TYPE_TITLE:
                    TitleViewHolder holder0 = (TitleViewHolder) viewHolder;
                    holder0.mTitle.setText(vehicleLicenseBean.getIsPayable() == -1
                            ? "可缴费车辆" : "仅限违章查询车辆");
                    break;
                case ITEM_TYPE_CONTENT:
                    ViewHolder holder = (ViewHolder) viewHolder;
                    holder.mCarNumber.setText(Des3.decode(vehicleLicenseBean.getPlateNo()));
                    int ispaycar = vehicleLicenseBean.getIsPayable();
                    holder.mFlag.setVisibility(ispaycar == 1 ? View.VISIBLE : View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 自定义类型布局
     */
    @Override
    public int getItemViewType(int position) {

        return isPayCar(position) ? ITEM_TYPE_CONTENT : ITEM_TYPE_TITLE;
    }

    private boolean isPayCar(int position) {
        return mUserCarInfoBeanList != null
                && mUserCarInfoBeanList.get(position) != null
                && mUserCarInfoBeanList.get(position).getIsPayable() >= 0;
    }

    public View createView(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == ITEM_TYPE_TITLE) {
            return inflater.inflate(R.layout.item_manage_vehicles_group, viewGroup, false);
        } else {
            return inflater.inflate(R.layout.recycle_list_item_car, viewGroup, false);
        }
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {

        return itemType == ITEM_TYPE_TITLE ? new TitleViewHolder(view) : new ViewHolder(view);
    }

    public void setDataList(List<VehicleLicenseBean> userCarInfoBeen) {
        mUserCarInfoBeanList = userCarInfoBeen;
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

    public static class TitleViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_manage_vehicles_title)
        TextView mTitle;

        public TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
