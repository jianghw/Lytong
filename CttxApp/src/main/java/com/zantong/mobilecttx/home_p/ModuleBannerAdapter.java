package com.zantong.mobilecttx.home_p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.bean.ModuleBannerBean;
import com.zantong.mobilecttx.home_v.IRouterStatisticsId;


/**
 * 优惠卷
 */

public class ModuleBannerAdapter extends BaseAdapter<ModuleBannerBean> {

    private final IRouterStatisticsId iRouterStatisticsId;
    private final int item;

    public ModuleBannerAdapter(IRouterStatisticsId iRouterStatisticsId, int item) {
        this.iRouterStatisticsId = iRouterStatisticsId;
        this.item = item;
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int resource;
        switch (this.item) {
            case 6:
                resource = R.layout.main_recycler_item_banner_list_6;
                break;
            case 5:
                resource = R.layout.main_recycler_item_banner_list_5;
                break;
            default:
                resource = R.layout.main_recycler_item_banner_list_6;
                break;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {

        return new ViewHolder(view);
    }

    @Override
    public void bindViewData(final BaseRecyclerViewHolder viewHolder,
                             int position, final ModuleBannerBean bannerBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (bannerBean == null) return;

        ImageLoadUtils.loadDefault(bannerBean.getImg(), holder.mImg);
        holder.mTvTitle.setText(bannerBean.getTitle());

        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iRouterStatisticsId != null) {
                    iRouterStatisticsId.gotoByStatistId(bannerBean.getTargetPath(),
                            bannerBean.getTitle(),
                            bannerBean.getStatisticsId());
                }
            }
        });
    }

    static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView mImg;
        TextView mTvTitle;
        RelativeLayout relative;

        ViewHolder(View view) {
            super(view);
            this.relative = (RelativeLayout) view.findViewById(R.id.rl_banner);
            this.mImg = (ImageView) view.findViewById(R.id.img);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
