package com.zantong.mobilecttx.weizhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.weizhang.bean.ViolationInfo;
import com.zantong.mobilecttx.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 缴费记录适配器
 * @author Sandy
 * create at 16/6/7 下午2:38
 */
public class PayHistoryAdapter extends BaseAdapter<ViolationInfo> {

    private Context mContext;

    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, ViolationInfo data) {
        ViewHolder holder = (ViewHolder)viewHolder;
        if (data != null){
            StringBuilder sb = new StringBuilder();
            try {

                sb.append(data.getViolationdate().substring(0,4)).append("-").
                        append(data.getViolationdate().substring(4, 6)).append("-").
                        append(data.getViolationdate().substring(6,8));
                holder.mTm.setText(sb.toString());
            }catch (Exception e){

            }
            holder.mCarNum.setText(data.getCarnum());
            holder.mAmount.setText("罚:"+ StringUtils.getPriceDoubleFormat(data.getViolationamt()));
            holder.mScore.setText("扣:"+data.getViolationcent());
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mine_payhistory_item, viewGroup,false);
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
        @Bind(R.id.payhistory_item_tm)
        TextView mTm;
        @Bind(R.id.payhistory_item_carnum)
        TextView mCarNum;//车牌号
        @Bind(R.id.payhistory_item_violationamt)
        TextView mAmount;//罚款金额
        @Bind(R.id.payhistory_item_violationcent)
        TextView mScore;//扣分数

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
