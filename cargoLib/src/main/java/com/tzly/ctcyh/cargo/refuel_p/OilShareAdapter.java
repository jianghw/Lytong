package com.tzly.ctcyh.cargo.refuel_p;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.java.response.oil.OilAccepterInfoResponse;
import com.tzly.ctcyh.router.util.Utils;

import java.util.List;

/**
 * 提醒
 */
public class OilShareAdapter extends BaseAdapter<OilAccepterInfoResponse.DataBean> {
    private final IAdapterClick itemClick;
    private Context mAdapterContext;

    public OilShareAdapter(IAdapterClick iAdapterClick) {
        this.itemClick = iAdapterClick;
    }

    @Override
    public int getItemViewType(int position) {
        OilAccepterInfoResponse.DataBean bean = getAll().get(position);
        String stage = bean.getStage();
        List<OilAccepterInfoResponse.DataBean.CouponBean> coupon = bean.getCoupon();
        if (coupon == null || coupon.isEmpty()) {
            stage = "0";
        }
//        return Integer.valueOf(stage);
        return 1;
    }

    @Override
    public View createView(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        int resource;
        if (viewType == 0) {
            resource = R.layout.cargo_recycler_item_oil_share_remind;
        } else if (viewType == 1) {
            resource = R.layout.cargo_recycler_item_oil_share;
        } else {
            resource = R.layout.cargo_recycler_item_oil_share;
        }
        return inflater.inflate(resource, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        if (itemType == 0) {
            return new RemindViewHolder(view);
        } else {
            return new ViewHolder(view);
        }
    }

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, OilAccepterInfoResponse.DataBean dataBean) {
        int type = viewHolder.getItemViewType();
        String stage = handlerStage(dataBean.getStage());
        if (type == 0) {
            RemindViewHolder holder = (RemindViewHolder) viewHolder;
            String phone = dataBean.getPhone();
            try {
                phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            } catch (Exception e) {
                e.printStackTrace();
            }


            StringBuilder sb = new StringBuilder();
            sb.append("用户");
            sb.append("<font color=\"#f3362b\">");
            sb.append(phone);
            sb.append("</font>");
            sb.append(stage);
            holder.mTvUser.setText(Html.fromHtml(sb.toString()));

            holder.mTvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClick != null) itemClick.clickItem(view);
                }
            });
        } else {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.mLayCoupon.removeAllViews();

            String phone = dataBean.getPhone();
            try {
                phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("用户");
            sb.append("<font color=\"#f3362b\">");
            sb.append(phone);
            sb.append("</font>");
            sb.append(stage);
            holder.mTvUser.setText(Html.fromHtml(sb.toString()));

            List<OilAccepterInfoResponse.DataBean.CouponBean> couponBeanList = dataBean.getCoupon();
            if (couponBeanList != null && !couponBeanList.isEmpty()) {
                for (OilAccepterInfoResponse.DataBean.CouponBean couponBean : couponBeanList) {
                    String title = couponBean.getTitle();
                    int counts = couponBean.getCounts();
                    makeTextView(holder, title, counts);
                }
            }
        }
    }

    private String handlerStage(String stage) {
        if (stage.equals("0")) {
            return "已接受";
        } else if (stage.equals("1")) {
            return "已注册";
        } else if (stage.equals("2")) {
            return "已下单";
        } else if (stage.equals("3")) {
            return "已支付";
        }
        return "未知状态";
    }

    private void makeTextView(ViewHolder holder, String title, int counts) {
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append("&#160;");
        sb.append("<font color=\"#f3362b\">");
        sb.append(counts);
        sb.append("&#160;");
        sb.append("</font>");
        sb.append("张");

        TextView textView = new TextView(Utils.getContext());
        textView.setTextColor(Utils.getContext().getResources().getColor(R.color.res_color_black_4d));
        textView.setText(Html.fromHtml(sb.toString()));
        textView.setTextSize(12);
        holder.mLayCoupon.addView(textView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView mImgLeft;
        TextView mTvUser;
        TextView mTvStatus;
        LinearLayout mLayCoupon;

        ViewHolder(View view) {
            super(view);
            this.mImgLeft = (ImageView) view.findViewById(R.id.img_left);
            this.mTvUser = (TextView) view.findViewById(R.id.tv_user);
            this.mTvStatus = (TextView) view.findViewById(R.id.tv_status);
            this.mLayCoupon = (LinearLayout) view.findViewById(R.id.lay_coupon);
        }
    }

    public static class RemindViewHolder extends BaseRecyclerViewHolder {
        ImageView mImgLeft;
        TextView mTvUser;
        TextView mTvStatus;

        RemindViewHolder(View view) {
            super(view);
            this.mImgLeft = (ImageView) view.findViewById(R.id.img_left);
            this.mTvUser = (TextView) view.findViewById(R.id.tv_user);
            this.mTvStatus = (TextView) view.findViewById(R.id.tv_status);
        }
    }
}
