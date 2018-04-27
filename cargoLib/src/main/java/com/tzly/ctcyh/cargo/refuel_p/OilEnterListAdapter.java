package com.tzly.ctcyh.cargo.refuel_p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.java.response.oil.OilModuleResponse;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;

import java.util.List;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class OilEnterListAdapter extends BaseAdapter<OilModuleResponse.DataBean> {

    private final IRouterStatisticsId routerStatisticsId;

    public OilEnterListAdapter(IRouterStatisticsId routerStatisticsId) {
        this.routerStatisticsId = routerStatisticsId;
    }

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

        List<OilModuleResponse.DataBean.ChildrenBean> list = dataBean.getChildren();
        if (list != null && list.size() > 0) {
            final OilModuleResponse.DataBean.ChildrenBean childrenBean = list.get(0);
            holder.tv_line_item.setVisibility(View.VISIBLE);
            holder.tv_line_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (routerStatisticsId != null) {
                        routerStatisticsId.gotoByStatistId(childrenBean.getTargetPath(),
                                childrenBean.getTitle(), childrenBean.getStatisticsId());
                    }
                }
            });
        } else {
            holder.tv_line_item.setVisibility(View.GONE);
        }
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView iv_oil_item;
        TextView tv_line_item;

        ViewHolder(View view) {
            super(view);
            this.tv_line_item = (TextView) view.findViewById(R.id.tv_line);
            this.iv_oil_item = (ImageView) view.findViewById(R.id.iv_oil_item);
        }
    }
}
