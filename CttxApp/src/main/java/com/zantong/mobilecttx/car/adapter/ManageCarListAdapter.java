package com.zantong.mobilecttx.car.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.utils.rsa.Des3;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 车俩管理列表
 */
public class ManageCarListAdapter extends BaseAdapter<UserCarInfoBean> {

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, UserCarInfoBean carInfoBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (carInfoBean != null) {
            holder.mCarNumber.setText(Des3.decode(carInfoBean.getCarnum()));
            String ispaycar = carInfoBean.getIspaycar();
            holder.mFlag.setVisibility(!TextUtils.isEmpty(ispaycar) && ispaycar.equals("1")
                    ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.recycle_list_item_car, viewGroup, false);
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

}
