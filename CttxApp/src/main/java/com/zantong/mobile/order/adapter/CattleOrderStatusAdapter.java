package com.zantong.mobile.order.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tzly.annual.base.imple.CattleOrderItemListener;
import com.zantong.mobile.R;
import com.tzly.annual.base.bean.response.OrderListBean;

import java.text.DecimalFormat;

import static com.tzly.annual.base.util.image.ImageOptions.getMessageOptions;

/**
 * 消息页面
 */
public class CattleOrderStatusAdapter extends BaseAdapter<OrderListBean> {

    private static final int TYPE_HAVE = 1;
    private static final int TYPE_AUDIT = 2;
    private static final int TYPE_PROCESS = 3;
    private static final int TYPE_COMPLETED = 4;

    private Context mAdapterContext;
    private CattleOrderItemListener mClickListener;

    /**
     * 1--"已接单" 0,1,3,5,13
     * 2--资料审核中 7,8
     * 3--办理中 6
     * 4--完成 9,10,11,12,4
     */
    @Override
    public int getItemViewType(int position) {
        OrderListBean bean = getAll().get(position);
        int status = bean.getOrderStatus();

        if (status == 0 || status == 1 || status == 3 || status == 5 || status == 13) {
            return TYPE_HAVE;
        } else if (status == 7 || status == 8) {
            return TYPE_AUDIT;
        } else if (status == 6) {
            return TYPE_PROCESS;
        } else if (status == 9 || status == 10 || status == 11 || status == 12 || status == 4) {
            return TYPE_COMPLETED;
        } else {
            return TYPE_HAVE;
        }
    }

    /**
     * 布局创建
     */
    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        mAdapterContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);

        int resource;
        switch (viewType) {
            case TYPE_HAVE:
                resource = R.layout.rv_item_order_cattle;
                break;
            case TYPE_AUDIT:
                resource = R.layout.rv_item_order_cattle;
                break;
            case TYPE_PROCESS:
                resource = R.layout.rv_item_order_cattle;
                break;
            case TYPE_COMPLETED:
                resource = R.layout.rv_item_order_cancel;
                break;
            default:
                resource = R.layout.rv_item_order_cancel;
                break;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        switch (itemType) {
            case TYPE_HAVE:
                return new HaveViewHolder(view);
            case TYPE_AUDIT:
                return new AuditViewHolder(view);
            case TYPE_PROCESS:
                return new ProcessViewHolder(view);
            case TYPE_COMPLETED:
                return new CompletedViewHolder(view);
            default:
                return new CompletedViewHolder(view);//未知版本处理
        }
    }

    /**
     * 数据绑定
     */
    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, final OrderListBean data) {
        if (data == null) return;
        switch (viewHolder.getItemViewType()) {
            case TYPE_HAVE:
                HaveViewHolder haveViewHolder = (HaveViewHolder) viewHolder;
                completedProcessing(haveViewHolder, data);
                haveProcessing(haveViewHolder, data);
                break;
            case TYPE_AUDIT:
                AuditViewHolder auditViewHolder = (AuditViewHolder) viewHolder;
                completedProcessing(auditViewHolder, data);
                auditProcessing(auditViewHolder, data);
                break;
            case TYPE_PROCESS:
                ProcessViewHolder processViewHolder = (ProcessViewHolder) viewHolder;
                completedProcessing(processViewHolder, data);
                processProcessing(processViewHolder, data);
                break;
            case TYPE_COMPLETED:
                CompletedViewHolder completedViewHolder = (CompletedViewHolder) viewHolder;
                completedProcessing(completedViewHolder, data);
                break;
            default:
                break;
        }
    }

    private void haveProcessing(HaveViewHolder haveViewHolder, final OrderListBean data) {
        haveViewHolder.mTvCattle.setText("资料审核");
        haveViewHolder.mTvCattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.doClickHave(data);
            }
        });
    }

    private void auditProcessing(AuditViewHolder auditViewHolder, final OrderListBean data) {
        auditViewHolder.mTvCattle.setText("办    理");
        auditViewHolder.mTvCattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.doClickAudit(data);
            }
        });
    }

    private void processProcessing(ProcessViewHolder processViewHolder, final OrderListBean data) {
        processViewHolder.mTvCattle.setText("完成订单");
        processViewHolder.mTvCattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.doClickAudit(data);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void completedProcessing(BaseViewHolder holder, OrderListBean data) {
        String orderNum = mAdapterContext.getResources().getString(R.string.tv_order_num);
        holder.mTvOrderNum.setText(String.format(orderNum, data.getOrderId()));

        int orderStatus = data.getOrderStatus();
        changeTextColorByStatus(orderStatus, holder.mTvOrderStatus);

        ImageLoader.getInstance().displayImage(data.getIcon(), holder.mImgBrand, getMessageOptions());

        holder.mTvBrand.setText(data.getGoodsName());
        holder.mTvDate.setText(data.getCreateDate());

        float price = data.getAmount();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(price);
        holder.mTvPrice.setText("￥" + format);
    }

    /**
     * orderStatus : 2 	订单状态,0未至付，1已支付,2取消或过期，3已锁定
     */
    private void changeTextColorByStatus(int status, TextView tvPayStatus) {

        if (status == 0 || status == 1 || status == 3 || status == 5 || status == 13) {
            tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
            tvPayStatus.setText("已接单");
        } else if (status == 7 || status == 8) {
            tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
            tvPayStatus.setText("资料审核中");
        } else if (status == 6) {
            tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvGreen_80));
            tvPayStatus.setText("办理中");
        } else if (status == 9 || status == 10 || status == 11 || status == 12 || status == 4) {
            tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvBlack_b2));
            tvPayStatus.setText("已完成");
        } else {
            tvPayStatus.setTextColor(mAdapterContext.getResources().getColor(R.color.colorTvBlack_b2));
            tvPayStatus.setText("未知状态");
        }
    }

    public void setItemClickListener(CattleOrderItemListener listener) {
        mClickListener = listener;
    }

    private static class BaseViewHolder extends BaseRecyclerViewHolder {
        TextView mTvOrderNum;
        TextView mTvOrderStatus;
        ImageView mImgBrand;
        TextView mTvBrand;
        TextView mTvDate;
        TextView mTvPrice;
        TextView mTvPayLine;
        TextView mTvCattle;
        RelativeLayout mLayCattle;

        BaseViewHolder(View view) {
            super(view);
            this.mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
            this.mTvOrderStatus = (TextView) view.findViewById(R.id.tv_order_status);
            this.mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
            this.mTvBrand = (TextView) view.findViewById(R.id.tv_brand);
            this.mTvDate = (TextView) view.findViewById(R.id.tv_date);
            this.mTvPrice = (TextView) view.findViewById(R.id.tv_price);
            this.mTvPayLine = (TextView) view.findViewById(R.id.tv_pay_line);
            this.mTvCattle = (TextView) view.findViewById(R.id.tv_cattle);
            this.mLayCattle = (RelativeLayout) view.findViewById(R.id.lay_cattle);
        }
    }

    private static class HaveViewHolder extends BaseViewHolder {
        public HaveViewHolder(View view) {
            super(view);
        }
    }

    private class AuditViewHolder extends BaseViewHolder {
        public AuditViewHolder(View view) {super(view);}
    }

    private class ProcessViewHolder extends BaseViewHolder {
        public ProcessViewHolder(View view) {super(view);}
    }

    private class CompletedViewHolder extends BaseViewHolder {
        public CompletedViewHolder(View view) {super(view);}
    }
}
