package com.zantong.mobilecttx.fahrschule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.tzly.ctcyh.router.bean.response.SubjectGoodsBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的订单适配器
 */
public class SubjectGoodsAdapter extends BaseAdapter<SubjectGoodsBean> {

    private Context mContext;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, final int position, SubjectGoodsBean goodsBean) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (goodsBean != null) {
            holder.mAmount.setText(goodsBean.getGoods().getName());

            holder.mAmount.setBackgroundResource(goodsBean.isChoice()
                    ? R.drawable.shape_keyboard_btn : R.drawable.shape_clean_btn);
            holder.mAmount.setTextColor(goodsBean.isChoice()
                    ? mContext.getResources().getColor(R.color.colorWhite)
                    : mContext.getResources().getColor(R.color.colorTvBlack_b3));
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return inflater.inflate(R.layout.recycle_list_item_subject, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_subject)
        TextView mAmount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
