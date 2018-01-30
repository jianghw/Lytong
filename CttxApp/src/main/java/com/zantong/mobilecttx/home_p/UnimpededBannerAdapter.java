package com.zantong.mobilecttx.home_p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.bean.UnimpededBannerBean;


/**
 * 优惠卷
 */

public class UnimpededBannerAdapter extends BaseAdapter<UnimpededBannerBean> {

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.main_adapter_banner_list, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {

        return new ViewHolder(view);
    }

    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, UnimpededBannerBean bean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (bean != null) {
            ImageLoadUtils.loadDefault(bean.getImg(), holder.mImg);
            holder.mTvTitle.setText(bean.getTitle());
        }
    }

    static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView mImg;
        TextView mTvTitle;

        ViewHolder(View view) {
            super(view);
            this.mImg = (ImageView) view.findViewById(R.id.img);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
