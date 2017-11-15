package com.zantong.mobilecttx.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.router.ScreenUtils;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.util.ConvertUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.home.bean.ChildrenBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠页面复杂页面
 */
public class ServiceDiscountsAdapter extends BaseAdapter<ChildrenBean> {
    /**
     * 展示样式: 1固定图标，2优惠页中部广告，3单行两图，4单行三图
     */
    private static final int ITEM_TYPE_TWO_PIC = 3;
    private static final int ITEM_TYPE_THREE_PIC = 4;
    private Context mAdapterContext;

    /**
     * 自定义类型布局
     */
    @Override
    public int getItemViewType(int position) {
        ChildrenBean bean = getAll().get(position);
        switch (bean.getShowType()) {
            case 3:
                return ITEM_TYPE_TWO_PIC;
            case 4:
                return ITEM_TYPE_THREE_PIC;
            default:
                return 3;
        }
    }

    /**
     * 创建布局
     */
    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);
        int resource;
        switch (viewType) {
            case ITEM_TYPE_TWO_PIC:
                resource = R.layout.adapter_discounts_item_2_url;
                break;
            case ITEM_TYPE_THREE_PIC:
                resource = R.layout.adapter_discounts_item_3_url;
                break;
            default:
                resource = R.layout.adapter_discounts_item_2_url;
                break;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        switch (itemType) {
            case ITEM_TYPE_TWO_PIC://每行显示2附图片，view内边距22px,recyclerView内边距22px
                stateImageParams(view, itemType);
                return new ImageViewHolder(view);
            case ITEM_TYPE_THREE_PIC:
                stateImageParams(view, itemType);
                return new ImageTextViewHolder(view);
            default:
                return new ImageViewHolder(view);
        }
    }

    /**
     * 根据状态布局设置 长 宽
     */
    protected void stateImageParams(View view, int itemType) {
        int width = ((ScreenUtils.widthPixels(mAdapterContext) - ConvertUtils.dp2px(7.33f) * 2) / (itemType == ITEM_TYPE_TWO_PIC ? 2 : 3));
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = (itemType == ITEM_TYPE_TWO_PIC) ? width  : (int) (width * 1.2);
        view.setLayoutParams(layoutParams);
    }

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, ChildrenBean childrenBean) {
        if (childrenBean == null) return;

        switch (viewHolder.getItemViewType()) {
            case ITEM_TYPE_TWO_PIC:
                ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
                imageTransactionProcessing(imageViewHolder, childrenBean);
                break;
            case ITEM_TYPE_THREE_PIC:
                ImageTextViewHolder imageTextViewHolder = (ImageTextViewHolder) viewHolder;
                imageTextTransactionProcessing(imageTextViewHolder, childrenBean);
                break;
            default:
                break;
        }
    }

    private void imageTransactionProcessing(ImageViewHolder imageViewHolder,
                                            ChildrenBean childrenBean) {
        ImageLoadUtils.loadTwoRectangle(childrenBean.getImg(), imageViewHolder.mImageView);
        imageViewHolder.mTvBoby.setText(childrenBean.getSubTitle());
    }

    private void imageTextTransactionProcessing(ImageTextViewHolder imageTextViewHolder,
                                                ChildrenBean childrenBean) {
        imageTextViewHolder.mTvTitle.setText(childrenBean.getTitle());
        imageTextViewHolder.mTvBoby.setText(childrenBean.getSubTitle());
        ImageLoadUtils.loadThreeRectangle(childrenBean.getImg(), imageTextViewHolder.mImageBitmap);
    }


    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */

    public static class ImageViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.imageView)
        ImageView mImageView;
        @Bind(R.id.tv_body)
        TextView mTvBoby;

        public ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ImageTextViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.img_bitmap)
        ImageView mImageBitmap;
        @Bind(R.id.tv_title)
        TextView mTvTitle;
        @Bind(R.id.tv_body)
        TextView mTvBoby;

        public ImageTextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
