package com.zantong.mobile.weizhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.zantong.mobile.R;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.weizhang.bean.RspInfoBean;
import com.zantong.mobile.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobile.widght.UISwitchButton;

import com.tzly.annual.base.util.CustomDialog;
import com.tzly.annual.base.util.DateTools;

/**
 * 违章查分数据列表
 */
public class LicenseDetailAdapter extends BaseAdapter<RspInfoBean.ViolationInfoBean> {

    private ItemClickListener mClickListener;
    private LicenseFileNumDTO mLicenseFileNumDTO;
    private Context mAdapterContext;

    @Override
    public void bindViewData(BaseRecyclerViewHolder viewHolder, int position, RspInfoBean.ViolationInfoBean violationInfoBean) {
        ViewHolder holder = (ViewHolder) viewHolder;

        if (violationInfoBean == null) return;
        String violationdate = violationInfoBean.getViolationdate();
        holder.mTvViolationDate.setText(DateTools.formattedAccordingDate(violationdate));

        String paydate = violationInfoBean.getPaydate();
        holder.mTvPaymentDate.setText(DateTools.formattedAccordingDate(paydate));

        String carnum = violationInfoBean.getCarnum();
        holder.mTvPaymentPlate.setText(Des3.decode(carnum));

        final String violationcent = violationInfoBean.getViolationcent();
        holder.mTvMark.setText(TextUtils.isEmpty(violationcent) ? "0" :
                Integer.valueOf(violationcent) > 0 ? "-" + Integer.valueOf(violationcent) : "0");

        final String violationnum = violationInfoBean.getViolationnum();
        holder.mTvTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.doClickViolation(violationnum);
            }
        });

        holder.mTvTitlePeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//小问号
                String scoringCycle = mAdapterContext.getResources().getString(R.string.tv_custom_scoring_cycle);
                CustomDialog.customContentDialog(mAdapterContext,"计分统计周期",scoringCycle);
            }
        });

        holder.mCustomSwitchBtn.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mClickListener != null)
                            mClickListener.doClickSwitchBtn(isChecked, violationcent);
                    }
                });

        holder.mCustomSwitchBtn.setChecked(true);

        if (mLicenseFileNumDTO != null) {
            long startD = DateTools.getDataTime(mLicenseFileNumDTO.getStrtdt());
            long endD = DateTools.getDataTime(mLicenseFileNumDTO.getEnddt());

            long violationDate = DateTools.getDataTime(violationdate);
            long payDate = DateTools.getDataTime(paydate);

            holder.mTvLine.setVisibility(violationDate < startD || violationDate > endD
                    || payDate < startD || payDate > endD ? View.VISIBLE : View.GONE);
            holder.mLayPeriod.setVisibility(violationDate < startD || violationDate > endD
                    || payDate < startD || payDate > endD ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mAdapterContext=viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mAdapterContext);
        return inflater.inflate(R.layout.recycle_list_item_license_det, viewGroup, false);
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    public void setLicenseFileNumDTO(LicenseFileNumDTO licenseFileNumDTO) {
        mLicenseFileNumDTO = licenseFileNumDTO;
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {

        private final TextView mTvTitleViolationDate;
        private final TextView mTvTitleRight;
        private final TextView mTvViolationDate;
        private final TextView mTvTitlePaymentDate;
        private final TextView mTvPaymentDate;
        private final TextView mTvTitlePaymentPlate;
        private final TextView mTvPaymentPlate;
        private final TextView mTvTitleMark;
        private final TextView mTvLine;
        private final TextView mTvTitlePeriod;
        private final UISwitchButton mCustomSwitchBtn;
        private final TextView mTvMark;
        private final RelativeLayout mLayPeriod;

        public ViewHolder(View view) {
            super(view);

            this.mTvTitleViolationDate = (TextView) view.findViewById(R.id.tv_title_violation_date);
            this.mTvTitleRight = (TextView) view.findViewById(R.id.tv_title_right);
            this.mTvViolationDate = (TextView) view.findViewById(R.id.tv_violation_date);
            this.mTvTitlePaymentDate = (TextView) view.findViewById(R.id.tv_title_payment_date);
            this.mTvPaymentDate = (TextView) view.findViewById(R.id.tv_payment_date);
            this.mTvTitlePaymentPlate = (TextView) view.findViewById(R.id.tv_title_payment_plate);
            this.mTvPaymentPlate = (TextView) view.findViewById(R.id.tv_payment_plate);
            this.mTvTitleMark = (TextView) view.findViewById(R.id.tv_title_mark);
            this.mTvMark = (TextView) view.findViewById(R.id.tv_mark);
            this.mLayPeriod = (RelativeLayout) view.findViewById(R.id.lay_period);
            this.mTvLine = (TextView) view.findViewById(R.id.tv_line);
            this.mTvTitlePeriod = (TextView) view.findViewById(R.id.tv_title_period);
            this.mCustomSwitchBtn = (UISwitchButton) view.findViewById(R.id.custom_switch_btn);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        mClickListener = listener;
    }

    public interface ItemClickListener {
        void doClickViolation(String num);

        void doClickSwitchBtn(boolean isChecked, String violationcent);
    }

}
