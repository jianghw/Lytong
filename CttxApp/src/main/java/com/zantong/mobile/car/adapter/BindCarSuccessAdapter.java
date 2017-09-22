package com.zantong.mobile.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobile.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobile.car.bean.CanPayCarBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/12.
 */
public class BindCarSuccessAdapter extends BaseAdapter<CanPayCarBean.RspInfoBean.UserCarsInfoBean> {

    Context mContext ;
    int flag = 2;
//    LinkedList<Map<String, String>> mLinkedList;
//    List<Map<String, String>> mIllegalQueryBeans = new ArrayList<>();


    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, CanPayCarBean.RspInfoBean.UserCarsInfoBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.car_number.setText(data.getCarnum());
        if(data.getEnginenum() != null){
            holder.engine_number.setText("发动机号："+data.getEnginenum());
        }else{
            holder.engine_number.setText("发动机号：*****");
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.bind_card_success_item, viewGroup, false);
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
        @Bind(R.id.car_number)
        TextView car_number;
        @Bind(R.id.engine_number)
        TextView engine_number;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
