package com.zantong.mobilecttx.share_p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.fahrschule.bean.StatistCountBean;

/**
 * Created by jianghw on 2017/10/12.
 * Description:
 * Update by:
 * Update day:
 */

public class StatisCountAdapter extends BaseAdapter<StatistCountBean> {
    private Context mAdapterContext;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.main_adapter_statis_count, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, StatistCountBean statusBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (statusBean == null) return;
        String string = mAdapterContext.getResources().getString(R.string.main_tv_count_share);
        String count = String.format(string, statusBean.getCount());
        holder.mTvCount.setText(count);

        holder.mTvName.setText(statusBean.getName());
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        TextView mTvCount;
        TextView mTvName;

        ViewHolder(View view) {
            super(view);
            this.mTvCount = (TextView) view.findViewById(R.id.tv_count);
            this.mTvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
