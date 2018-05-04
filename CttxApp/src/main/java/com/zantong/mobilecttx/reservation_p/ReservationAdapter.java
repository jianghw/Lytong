package com.zantong.mobilecttx.reservation_p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.tzly.ctcyh.java.response.reservation.ReservationResponse;
import com.zantong.mobilecttx.R;

import java.util.List;

/**
 * 预约 子布局
 */

public class ReservationAdapter extends BaseAdapter<ReservationResponse.DataBean> {

    private Context mContextAdapter;

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContextAdapter = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return inflater.inflate(R.layout.main_recycler_item_reservation, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder,
                             int position, ReservationResponse.DataBean dataBean) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (dataBean == null) return;

//        ImageLoadUtils.loadNativeCircle(dataBean.getImg(), holder.mImgBrand);
        String business = dataBean.getBusiness();
        holder.mRlBusiness.setVisibility(TextUtils.isEmpty(business) ? View.GONE : View.VISIBLE);
        holder.mTvBusiness.setText(business);

        if (business.equals("个人沪牌代拍")) {
            holder.mImgBrand.setImageResource(R.mipmap.img_gepai);
        } else if (business.equals("公司沪牌代拍")) {
            holder.mImgBrand.setImageResource(R.mipmap.img_gongpai);
        } else if (business.equals("车牌贷款")) {
            holder.mImgBrand.setImageResource(R.mipmap.img_daikuan);
        } else if (business.equals("高价卖车")) {
            holder.mImgBrand.setImageResource(R.mipmap.img_maiche);
        }

        String bespeakDate = dataBean.getBespeakDate();
        holder.mRlTime.setVisibility(TextUtils.isEmpty(bespeakDate) ? View.GONE : View.VISIBLE);
        holder.mTvTime.setText(bespeakDate);

        List<String> lis = dataBean.getMeal();
        holder.mRlPackage.setVisibility(lis == null || lis.size() < 1 ? View.GONE : View.VISIBLE);
        StringBuilder stringBuilder = new StringBuilder();
        if (lis != null && lis.size() > 0) {
            for (String string : lis) {
                stringBuilder.append(string).append("/");
            }
        }
        String mel = stringBuilder.toString();
        holder.mTvPackage.setText(TextUtils.isEmpty(mel)
                ? "暂无" : mel.substring(0, mel.length() - 1));

        String remark = dataBean.getRemark();
        holder.mRlRemark.setVisibility(TextUtils.isEmpty(remark) ? View.GONE : View.VISIBLE);
        holder.mTvRemark.setText(remark);

        holder.mTvServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playTel();
            }
        });
    }


    private void playTel() {
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_DIAL); // 设置动作
        Uri data = Uri.parse("tel:" + "4008216158"); // 设置数据
        intent.setData(data);
        mContextAdapter.startActivity(intent);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        ImageView mImgBrand;
        TextView mTvBusiness;
        LinearLayout mRlBusiness;
        TextView mTvTime;
        LinearLayout mRlTime;
        TextView mTvPackage;
        LinearLayout mRlPackage;
        TextView mTvRemark;
        LinearLayout mRlRemark;

        LinearLayout mLlContent;
        TextView mTvLine;
        TextView mTvServer;
        RelativeLayout mRlServer;

        ViewHolder(View view) {
            super(view);
            this.mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
            this.mTvBusiness = (TextView) view.findViewById(R.id.tv_business);
            this.mRlBusiness = (LinearLayout) view.findViewById(R.id.rl_business);
            this.mTvTime = (TextView) view.findViewById(R.id.tv_time);
            this.mRlTime = (LinearLayout) view.findViewById(R.id.rl_time);
            this.mTvPackage = (TextView) view.findViewById(R.id.tv_package);
            this.mRlPackage = (LinearLayout) view.findViewById(R.id.rl_package);
            this.mTvRemark = (TextView) view.findViewById(R.id.tv_remark);
            this.mRlRemark = (LinearLayout) view.findViewById(R.id.rl_remark);
            this.mLlContent = (LinearLayout) view.findViewById(R.id.ll_content);
            this.mTvLine = (TextView) view.findViewById(R.id.tv_line);
            this.mTvServer = (TextView) view.findViewById(R.id.tv_server);
            this.mRlServer = (RelativeLayout) view.findViewById(R.id.rl_server);
        }
    }
}
