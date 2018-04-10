package com.zantong.mobilecttx.violation_p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.java.response.violation.ValidAdvResponse;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.zantong.mobilecttx.R;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class AdvImageAdapter extends BaseAdapter<ValidAdvResponse.DataBean> {
    private Context mAdapterContext;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.main_adapter_adv_image_item, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             final int position, ValidAdvResponse.DataBean dataBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (dataBean == null) return;

        String url = dataBean.getImage();
        if (!url.contains("http")) {
            url = (BuildConfig.isDeta ? BuildConfig.beta_base_url : BuildConfig.release_base_url) + url;
        }
        ImageLoadUtils.loadShareRectangle(url, holder.imageView);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.img_adv);
        }
    }
}
