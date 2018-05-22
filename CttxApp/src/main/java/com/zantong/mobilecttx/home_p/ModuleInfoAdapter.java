package com.zantong.mobilecttx.home_p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.java.response.news.NewsInfoResponse;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.router.MainRouter;


/**
 * 资讯
 */

public class ModuleInfoAdapter extends BaseAdapter<NewsInfoResponse.DataBean.NewsBean> {
    private static final int ITEM_TYPE_TOP_PIC = 1;
    private static final int ITEM_TYPE_LEFT_PIC = 2;
    private Context adapterContext;

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        adapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        int resource;
        switch (viewType) {
            case ITEM_TYPE_TOP_PIC:
                resource = R.layout.main_recycler_item_information_top;
                break;
            case ITEM_TYPE_LEFT_PIC:
                resource = R.layout.main_recycler_item_information_left;
                break;
            default:
                resource = R.layout.main_recycler_item_information_left;
                break;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        switch (itemType) {
            case ITEM_TYPE_TOP_PIC:
                return new TopViewHolder(view);
            case ITEM_TYPE_LEFT_PIC:
                return new LeftViewHolder(view);
            default:
                return new LeftViewHolder(view);//未知版本处理
        }
    }

    /**
     * 优惠卷 type  优惠券类型：1 无；2 折扣；3 代金券
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final NewsInfoResponse.DataBean.NewsBean newsBean) {
        if (newsBean == null) return;

        switch (viewHolder.getItemViewType()) {
            case ITEM_TYPE_TOP_PIC:
                TopViewHolder topViewHolder = (TopViewHolder) viewHolder;

                ImageLoadUtils.loadTwoRectangle(newsBean.getImage(), topViewHolder.mIvTop);
                topViewHolder.mTvTitle.setText(newsBean.getTitle());
                topViewHolder.mTvTime.setText(newsBean.getCreateTime());
                topViewHolder.mTvCount.setText(String.valueOf(newsBean.getCount()));
                topViewHolder.mTvNew.setVisibility(newsBean.isNewItem() ? View.VISIBLE : View.GONE);
                topViewHolder.mRlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemOnClick(newsBean);
                    }
                });
                break;
            case ITEM_TYPE_LEFT_PIC:
                LeftViewHolder leftViewHolder = (LeftViewHolder) viewHolder;

                ImageLoadUtils.loadNativeCircle(newsBean.getImage(), leftViewHolder.mIvLeft);
                leftViewHolder.mTvTitle.setText(newsBean.getTitle());
                leftViewHolder.mTvTime.setText(newsBean.getCreateTime());
                leftViewHolder.mTvCount.setText(String.valueOf(newsBean.getCount()));
                leftViewHolder.mRlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemOnClick(newsBean);
                    }
                });
                break;
            default:
                break;
        }
    }

    private void itemOnClick(NewsInfoResponse.DataBean.NewsBean newsBean) {
        String url = BuildConfig.isDeta
                ? "http://h5dev.liyingtong.com/news/index.html?id=" + newsBean.getId()
                : "http://h5.liyingtong.com/news/index.html?id=" + newsBean.getId();
        MainRouter.gotoWebHtmlActivity(adapterContext, "资讯详情", url);
    }

    /**
     * 自定义类型布局
     */
    @Override
    public int getItemViewType(int position) {
        NewsInfoResponse.DataBean.NewsBean benewsBean = getAll().get(position);
        switch (benewsBean.getStyle()) {
            case 1:
                return ITEM_TYPE_TOP_PIC;
            case 2:
                return ITEM_TYPE_LEFT_PIC;
            default:
                return 2;
        }
    }

    static class TopViewHolder extends BaseRecyclerViewHolder {
        RelativeLayout mRlayout;
        ImageView mIvTop;
        TextView mTvTitle;
        TextView mTvNew;
        TextView mTvTime;
        TextView mTvCount;

        TopViewHolder(View view) {
            super(view);
            this.mIvTop = (ImageView) view.findViewById(R.id.iv_top);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvNew = (TextView) view.findViewById(R.id.tv_new);
            this.mTvTime = (TextView) view.findViewById(R.id.tv_time);
            this.mTvCount = (TextView) view.findViewById(R.id.tv_count);
            this.mRlayout = (RelativeLayout) view.findViewById(R.id.rl_layout);
        }
    }

    static class LeftViewHolder extends BaseRecyclerViewHolder {
        RelativeLayout mRlayout;
        ImageView mIvLeft;
        TextView mTvTitle;
        TextView mTvNew;
        TextView mTvTime;
        TextView mTvCount;

        LeftViewHolder(View view) {
            super(view);
            this.mIvLeft = (ImageView) view.findViewById(R.id.iv_left);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvNew = (TextView) view.findViewById(R.id.tv_new);
            this.mTvTime = (TextView) view.findViewById(R.id.tv_time);
            this.mTvCount = (TextView) view.findViewById(R.id.tv_count);
            this.mRlayout = (RelativeLayout) view.findViewById(R.id.rl_layout);
        }
    }
}