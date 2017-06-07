package com.zantong.mobilecttx.car.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.Tools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/12.
 */
public class CanPayCarAdapter extends BaseAdapter<OpenQueryBean.RspInfoBean.UserCarsInfoBean> {

    Context mContext ;
    int flag = 2;
//    LinkedList<Map<String, String>> mLinkedList;
//    List<Map<String, String>> mIllegalQueryBeans = new ArrayList<>();


    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, OpenQueryBean.RspInfoBean.UserCarsInfoBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.query_listory_car_number.setText(data.getCarnum());
        if(Tools.isStrEmpty(data.getUntreatamt())){
            holder.view_vehicles_penalty_tv.setText("共计罚款 0元");
        }else{
            long money = Long.parseLong(data.getUntreatamt());
            try {
                holder.view_vehicles_penalty_tv.setText("共计罚款 "+ AmountUtils.changeF2Y(money)+"元");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(Tools.isStrEmpty(data.getUntreatcent())){
            holder.view_vehicles_penalty_tv.setText("共计罚分 0分");
        }else{
            holder.view_vehicles_penalty_point_tv.setText("共计罚分 "+data.getUntreatcent()+"分");
        }
        if(data.getUntreatcount() != null && "0".equals(data.getUntreatcount())){
            holder.query_history_time.setText("暂无违章");
            holder.query_history_time.setTextColor(mContext.getResources().getColor(R.color.colorGrayFontSmmall));
        }else{
            holder.query_history_time.setText(data.getUntreatcount()+"条违章");
            holder.query_history_time.setTextColor(mContext.getResources().getColor(R.color.colorTranFontRed));
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        View view = inflate.inflate(R.layout.view_vehicles_item, viewGroup, false);
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
        @Bind(R.id.query_listory_car_number)
        TextView query_listory_car_number;
        @Bind(R.id.view_vehicles_penalty_tv)
        TextView view_vehicles_penalty_tv;
        @Bind(R.id.view_vehicles_penalty_point_tv)
        TextView view_vehicles_penalty_point_tv;
        @Bind(R.id.query_history_time)
        TextView query_history_time;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }



}
