package com.zantong.mobilecttx.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.car.bean.MyCarBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 违章查询-我的车辆
 * @author Sandy
 * create at 16/6/2 下午12:54
 */
public class MyCarAdapter extends BaseAdapter<MyCarBean> {

    private Context mContext;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, MyCarBean data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            holder.mCarName.setText(data.getCar_name());
//            holder.mEngineNum.setText(data.getEngine_num());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater= LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mine_car_item,viewGroup,false);

        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view,int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.mine_car_item_layout)
        View mDate;
        @Bind(R.id.mine_car_item_name)
        TextView mCarName;
        @Bind(R.id.mine_car_item_enginenum)
        TextView mEngineNum;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
