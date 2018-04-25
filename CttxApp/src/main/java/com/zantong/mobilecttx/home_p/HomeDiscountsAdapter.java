package com.zantong.mobilecttx.home_p;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.router.util.RudenessScreenHelper;
import com.tzly.ctcyh.router.util.ScreenUtils;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.util.ConvertUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.contract.home.INativeItemListener;
import com.zantong.mobilecttx.home.adapter.ServiceDiscountsAdapter;
import com.zantong.mobilecttx.home.bean.ChildrenBean;
import com.zantong.mobilecttx.home.bean.ModuleBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠页面复杂页面
 */
public class HomeDiscountsAdapter extends BaseAdapter<ModuleBean> {
    /**
     * 展示样式: 1固定图标，2优惠页中部广告，3单行两图，4单行三图
     */
    private static final int ITEM_TYPE_NATIVE = 1;
    private static final int ITEM_TYPE_SHARE = 2;
    private static final int ITEM_TYPE_TWO_PIC = 3;
    private static final int ITEM_TYPE_THREE_PIC = 4;
    private Context mAdapterContext;
    private INativeItemListener mINativeItemListener;

    /**
     * 自定义类型布局
     */
    @Override
    public int getItemViewType(int position) {
        ModuleBean bean = getAll().get(position);
        switch (bean.getShowType()) {
            case 1:
                return ITEM_TYPE_NATIVE;
            case 2:
                return ITEM_TYPE_SHARE;
            case 3:
                return ITEM_TYPE_TWO_PIC;
            case 4:
                return ITEM_TYPE_THREE_PIC;
            default:
                return 2;
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
            case ITEM_TYPE_NATIVE:
                resource = R.layout.recycle_list_item_native;
                break;
            case ITEM_TYPE_SHARE:
                resource = R.layout.recycle_list_item_share;
                break;
            case ITEM_TYPE_TWO_PIC:
            case ITEM_TYPE_THREE_PIC:
                resource = R.layout.recycle_list_item_service;
                break;
            default:
                resource = R.layout.recycle_list_item_share;
                break;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        switch (itemType) {
            case ITEM_TYPE_NATIVE:
                return new NativeViewHolder(view);
            case ITEM_TYPE_SHARE:
                return new ShareViewHolder(view);
            case ITEM_TYPE_TWO_PIC:
            case ITEM_TYPE_THREE_PIC:
                return new ServiceViewHolder(view, itemType);
            default:
                return new ShareViewHolder(view);//未知版本处理
        }
    }

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, ModuleBean moduleBean) {
        if (moduleBean == null) return;

        switch (viewHolder.getItemViewType()) {
            case ITEM_TYPE_NATIVE:
                NativeViewHolder nativeViewHolder = (NativeViewHolder) viewHolder;
                nativeTransactionProcessing(nativeViewHolder, moduleBean);
                break;
            case ITEM_TYPE_SHARE:
                ShareViewHolder shareViewHolder = (ShareViewHolder) viewHolder;
                shareTransactionProcessing(shareViewHolder, moduleBean);
                break;
            case ITEM_TYPE_TWO_PIC:
            case ITEM_TYPE_THREE_PIC:
                ServiceViewHolder serviceViewHolder = (ServiceViewHolder) viewHolder;
                serviceTransactionProcessing(serviceViewHolder, moduleBean);
                break;
            default:
                break;
        }
    }

    /**
     * 服务模块recyclerView 边距测量绘制
     * row-->2时
     */
    private void serviceTransactionProcessing(final ServiceViewHolder serviceViewHolder, ModuleBean moduleBean) {
        serviceViewHolder.mTvTitle.setText(moduleBean.getTitle());

        int row = (serviceViewHolder.getItemViewType() == ITEM_TYPE_TWO_PIC) ? 2 : 3;
        List<ChildrenBean> childrenBeanList = moduleBean.getChildren();

        final int maxHeight = maxHeight(childrenBeanList, row);
        final int minHeight = minHeight(childrenBeanList, row);

        ViewGroup.LayoutParams layoutParams = serviceViewHolder.mRvRecycler.getLayoutParams();
        layoutParams.height = minHeight;
        serviceViewHolder.mRvRecycler.setLayoutParams(layoutParams);

        ServiceDiscountsAdapter discountsAdapter = new ServiceDiscountsAdapter();
        discountsAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (data instanceof ChildrenBean) {
                    ChildrenBean childrenBean = (ChildrenBean) data;
                    if (mINativeItemListener != null)
                        mINativeItemListener.onItemClick(childrenBean);
                }
            }
        });
        discountsAdapter.appendOnly(childrenBeanList);
        serviceViewHolder.mRvRecycler.setAdapter(discountsAdapter);

        serviceViewHolder.mLayFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceViewHolder.mLayFold.getVisibility() != View.VISIBLE) return;

                if (maxHeight > minHeight)
                    runItemAnimation(serviceViewHolder, maxHeight, minHeight);
            }
        });

        serviceViewHolder.mTvFold.setText(row == 2 && childrenBeanList.size() > 4
                || row == 3 && childrenBeanList.size() > 6 ? "展开更多" : "收起更多");
        serviceViewHolder.mLayFold.setVisibility(row == 2 && childrenBeanList.size() > 4
                || row == 3 && childrenBeanList.size() > 6 ? View.VISIBLE : View.GONE);
    }

    private void runItemAnimation(ServiceViewHolder serviceViewHolder, int maxHeight, int minHeight) {
        String title = serviceViewHolder.mTvFold.getText().toString();
        ValueAnimator valueAnimator = title.contains("收起")
                ? createAnimators(serviceViewHolder, maxHeight, minHeight)
                : createAnimators(serviceViewHolder, minHeight, maxHeight);
        valueAnimator.start();
    }

    /**
     * item 可以显示的最高高度
     */
    private int maxHeight(List<ChildrenBean> childrenBeanList, int row) {
        //计算行数
        int lineNumber = childrenBeanList.size() % row == 0
                ? childrenBeanList.size() / row : childrenBeanList.size() / row + 1;
        int height = row == 2 ? 260 : 300;
        return RudenessScreenHelper.ptInpx(height) * lineNumber;

        //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
       /* int width = (ScreenUtils.widthPixels(mAdapterContext) -
                (row == 2 ? RudenessScreenHelper.ptInpx(15f) * 2 : RudenessScreenHelper.ptInpx(20f) * 2)) / row;

        return (row == 2 ? width : (int) (width * 1.2)) * lineNumber
                + (row == 2 ? RudenessScreenHelper.ptInpx(7.33f) * 2 : RudenessScreenHelper.ptInpx(10f) * 2);*/
    }

    /**
     * item 可以显示的最小高度
     */
    private int minHeight(List<ChildrenBean> childrenBeanList, int row) {
        //计算行数
        int lineNumber = childrenBeanList.size() % row == 0
                ? childrenBeanList.size() / row : childrenBeanList.size() / row + 1;
        lineNumber = (row == 2)
                ? childrenBeanList.size() >= 4 ? 2 : lineNumber
                : childrenBeanList.size() >= 6 ? 2 : lineNumber;

        int height = row == 2 ? 260 : 300;
        return RudenessScreenHelper.ptInpx(height) * lineNumber;

        //高度的计算需要自己好好理解，否则会产生嵌套recyclerView可以滑动的现象
        /*int width = (ScreenUtils.widthPixels(mAdapterContext) -
                (row == 2 ? ConvertUtils.dp2px(7.33f) * 2 : ConvertUtils.dp2px(10f) * 2)) / row;

        return (row == 2 ? width : (int) (width * 1.2)) * lineNumber
                + (row == 2 ? ConvertUtils.dp2px(7.33f) * 2 : ConvertUtils.dp2px(10f) * 2);*/
    }

    /**
     * 创建动画
     */
    private ValueAnimator createAnimators(final ServiceViewHolder serviceViewHolder, int from, int to) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams params = serviceViewHolder.mRvRecycler.getLayoutParams();
                params.height = (int) animation.getAnimatedValue();
                serviceViewHolder.mRvRecycler.setLayoutParams(params);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                String title = serviceViewHolder.mTvFold.getText().toString();
                serviceViewHolder.mTvFold.setText(title.contains("展开") ? "收起更多" : "展开更多");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String title = serviceViewHolder.mTvFold.getText().toString();
                Drawable nav_up = mAdapterContext.getResources().getDrawable(title.contains("收起")
                        ? R.mipmap.arrow_up : R.mipmap.arrow_down);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                serviceViewHolder.mTvFold.setCompoundDrawables(null, null, nav_up, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                String title = serviceViewHolder.mTvFold.getText().toString();
                Drawable nav_up = mAdapterContext.getResources().getDrawable(title.contains("收起")
                        ? R.mipmap.arrow_up : R.mipmap.arrow_down);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                serviceViewHolder.mTvFold.setCompoundDrawables(null, null, nav_up, null);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return valueAnimator;
    }

    /**
     * 分享模块 方法继续走下去
     */
    private void shareTransactionProcessing(ShareViewHolder shareViewHolder, ModuleBean moduleBean) {
        List<ChildrenBean> childrenBeanList = moduleBean.getChildren();
        ChildrenBean bean = null;
        for (ChildrenBean childrenBean : childrenBeanList) {
            if (!TextUtils.isEmpty(childrenBean.getImg())) {
                bean = childrenBean;
                break;
            }
        }
        if (bean != null) {
            ImageLoadUtils.loadShareRectangle(bean.getImg(), shareViewHolder.mImageShare);
            final ChildrenBean finalBean = bean;
            shareViewHolder.mImageShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mINativeItemListener != null) mINativeItemListener.onItemClick(finalBean);
                }
            });
        }
    }

    /**
     * 本地模块
     */
    private void nativeTransactionProcessing(NativeViewHolder nativeViewHolder, ModuleBean moduleBean) {
        List<ChildrenBean> childrenBeanList = moduleBean.getChildren();

        if (childrenBeanList.size() <= 0 || childrenBeanList.get(0) == null) return;
        final ChildrenBean bean_0 = childrenBeanList.get(0);
        nativeViewHolder.mLayLocality1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mINativeItemListener != null) mINativeItemListener.onItemClick(bean_0);
            }
        });
        nativeViewHolder.mTvOil.setText(bean_0.getTitle());
        nativeViewHolder.mTvOilClick.setText(bean_0.getSubTitle());
        ImageLoadUtils.loadNativeCircle(bean_0.getImg(), nativeViewHolder.mImgOil);

        if (childrenBeanList.size() <= 1 || childrenBeanList.get(1) == null) return;
        final ChildrenBean bean_1 = childrenBeanList.get(1);
        nativeViewHolder.mLayLocality2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mINativeItemListener != null) mINativeItemListener.onItemClick(bean_1);
            }
        });
        nativeViewHolder.mTvInsurance.setText(bean_1.getTitle());
        nativeViewHolder.mTvInsuranceClick.setText(bean_1.getSubTitle());
        ImageLoadUtils.loadNativeCircle(bean_1.getImg(), nativeViewHolder.mImgInsurance);

        if (childrenBeanList.size() <= 2 || childrenBeanList.get(2) == null) return;
        final ChildrenBean bean_2 = childrenBeanList.get(2);
        nativeViewHolder.mLayNative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mINativeItemListener != null) mINativeItemListener.onItemClick(bean_2);
            }
        });
        nativeViewHolder.mTvNative1.setText(bean_2.getTitle());
        ImageLoadUtils.loadNativeCircle(bean_2.getImg(), nativeViewHolder.mImgNative1);

        if (childrenBeanList.size() <= 3 || childrenBeanList.get(3) == null) return;
        final ChildrenBean bean_3 = childrenBeanList.get(3);
        nativeViewHolder.mLayNative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mINativeItemListener != null) mINativeItemListener.onItemClick(bean_3);
            }
        });
        nativeViewHolder.mTvNative2.setText(bean_3.getTitle());
        ImageLoadUtils.loadNativeCircle(bean_3.getImg(), nativeViewHolder.mImgNative2);

        if (childrenBeanList.size() <= 4 || childrenBeanList.get(4) == null) return;
        final ChildrenBean bean_4 = childrenBeanList.get(4);
        nativeViewHolder.mLayNative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mINativeItemListener != null) mINativeItemListener.onItemClick(bean_4);
            }
        });
        nativeViewHolder.mTvNative3.setText(bean_4.getTitle());
        ImageLoadUtils.loadNativeCircle(bean_4.getImg(), nativeViewHolder.mImgNative3);

        if (childrenBeanList.size() <= 5 || childrenBeanList.get(5) == null) return;
        final ChildrenBean bean_5 = childrenBeanList.get(5);
        nativeViewHolder.mLayNative4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mINativeItemListener != null) mINativeItemListener.onItemClick(bean_5);
            }
        });
        nativeViewHolder.mTvNative4.setText(bean_5.getTitle());
        ImageLoadUtils.loadNativeCircle(bean_5.getImg(), nativeViewHolder.mImgNative4);
    }

    /**
     * 监听事件
     */
    public void setNativeItemListener(INativeItemListener itemListener) {
        mINativeItemListener = itemListener;
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class NativeViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.img_oil)
        ImageView mImgOil;
        @Bind(R.id.tv_oil)
        TextView mTvOil;
        @Bind(R.id.tv_oil_click)
        TextView mTvOilClick;
        @Bind(R.id.lay_locality_1)
        LinearLayout mLayLocality1;

        @Bind(R.id.img_insurance)
        ImageView mImgInsurance;
        @Bind(R.id.tv_insurance)
        TextView mTvInsurance;
        @Bind(R.id.tv_insurance_click)
        TextView mTvInsuranceClick;
        @Bind(R.id.lay_locality_2)
        LinearLayout mLayLocality2;

        @Bind(R.id.img_native_1)
        ImageView mImgNative1;
        @Bind(R.id.tv_native_1)
        TextView mTvNative1;
        @Bind(R.id.lay_native_1)
        LinearLayout mLayNative1;
        @Bind(R.id.img_native_2)
        ImageView mImgNative2;
        @Bind(R.id.tv_native_2)
        TextView mTvNative2;
        @Bind(R.id.lay_native_2)
        LinearLayout mLayNative2;
        @Bind(R.id.img_native_3)
        ImageView mImgNative3;
        @Bind(R.id.tv_native_3)
        TextView mTvNative3;
        @Bind(R.id.lay_native_3)
        LinearLayout mLayNative3;
        @Bind(R.id.img_native_4)
        ImageView mImgNative4;
        @Bind(R.id.tv_native_4)
        TextView mTvNative4;
        @Bind(R.id.lay_native_4)
        LinearLayout mLayNative4;

        public NativeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ShareViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.img_share)
        ImageView mImageShare;

        public ShareViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ServiceViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.tv_service_title)
        TextView mTvTitle;
        @Bind(R.id.custom_recycler)
        XRecyclerView mRvRecycler;
        @Bind(R.id.lay_fold)
        RelativeLayout mLayFold;
        @Bind(R.id.tv_fold)
        TextView mTvFold;

        public ServiceViewHolder(View view, int itemType) {
            super(view);
            ButterKnife.bind(this, view);

            int row = (itemType == ITEM_TYPE_TWO_PIC) ? 2 : 3;
            //每行显示3个，水平显示
            GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), row, GridLayoutManager.VERTICAL, false);

            mRvRecycler.setLayoutManager(layoutManager);
            //动态设置其内边距 多种状态布局时
            float padding = itemType == ITEM_TYPE_TWO_PIC ? 15f : 20f;
            // mRvRecycler.setPadding(0, 0, 0, RudenessScreenHelper.ptInpx(30f));
        }
    }

}
