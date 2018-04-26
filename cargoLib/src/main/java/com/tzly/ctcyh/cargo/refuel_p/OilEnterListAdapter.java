package com.tzly.ctcyh.cargo.refuel_p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class OilEnterListAdapter extends BaseAdapter<OilModuleResponse.DataBean> {

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.cargo_recycler_item_oil_enter, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, OilModuleResponse.DataBean dataBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (dataBean == null) return;

        ImageLoadUtils.loadTwoRectangle(dataBean.getImg(), holder.iv_oil_item);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView iv_oil_item;

        ViewHolder(View view) {
            super(view);
            this.iv_oil_item = (ImageView) view.findViewById(R.id.iv_oil_item);
        }
    }
}
