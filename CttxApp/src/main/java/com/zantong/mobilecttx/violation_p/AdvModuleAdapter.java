package com.zantong.mobilecttx.violation_p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.java.response.violation.AdvModuleResponse;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.zantong.mobilecttx.R;

/**
 * Created by jianghw on 2017/10/12.
 */

public class AdvModuleAdapter extends BaseAdapter<AdvModuleResponse.DataBean> {

    private ClickUrlAdapter clickUrlAdapter;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.main_recycler_item_adv_module, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, final AdvModuleResponse.DataBean dataBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (dataBean == null) return;

        holder.mTvTitle.setText(dataBean.getTitle());
        holder.mTvSubTitle.setText(dataBean.getSubTitle());

        String url = dataBean.getImg();
        if (!url.contains("http")) {
            url = (BuildConfig.isDeta ? BuildConfig.beta_base_url : BuildConfig.release_base_url) + url;
        }
        ImageLoadUtils.loadShareRectangle(url, holder.mImg);

        holder.mLayModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickUrlAdapter != null)
                    clickUrlAdapter.clickUrl(dataBean.getTargetPath(), dataBean.getStatisticsId());
            }
        });
    }

    public void setItemClickListener(ClickUrlAdapter clickUrlAdapter) {
        this.clickUrlAdapter = clickUrlAdapter;
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        TextView mTvTitle;
        TextView mTvSubTitle;
        ImageView mImg;
        LinearLayout mLayModule;

        ViewHolder(View view) {
            super(view);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvSubTitle = (TextView) view.findViewById(R.id.tv_subTitle);
            this.mImg = (ImageView) view.findViewById(R.id.img);
            this.mLayModule = (LinearLayout) view.findViewById(R.id.lay_module);
        }
    }

    public interface ClickUrlAdapter {
        void clickUrl(String url, int id);
    }

}
