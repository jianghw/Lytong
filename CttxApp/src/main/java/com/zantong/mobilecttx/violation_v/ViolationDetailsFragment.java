package com.zantong.mobilecttx.violation_v;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.global.JxGlobal;
import com.tzly.ctcyh.router.util.FormatUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.rea.Des3;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.violation_p.IViolationDetailsContract;
import com.zantong.mobilecttx.violation_p.IViolationDetailsPresenter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 加油充值
 */
public class ViolationDetailsFragment extends RefreshFragment
        implements IViolationDetailsContract.IViolationDetailsView, View.OnClickListener {

    private static final String VIOLATION_NUM = "violation_num";

    /**
     * 车牌信息
     */
    private TextView mTvCarNumTitle;
    private TextView mTvCarNum;
    /**
     * 违章地点
     */
    private TextView mViolationLocationTitle;
    private TextView mViolationLocationText;
    /**
     * 违章内容
     */
    private TextView mViolationContentTitle;
    private TextView mViolationContentText;
    /**
     * 违章时间
     */
    private TextView mViolationTimeTitle;
    private TextView mViolationTimeText;
    /**
     * 当前状态
     */
    private TextView mViolationStateTitle;
    private TextView mViolationStateText;
    /**
     * 罚款金额
     */
    private TextView mViolationMoneyTitle;
    private TextView mViolationMoneyText;
    /**
     * 加处金额
     */
    private TextView mViolationMoneyZhinajin;
    private ImageView mDetailZhinajinImg;
    /**
     * 200 元
     */
    private TextView mViolationMoneyZhinajinText;
    /**
     * 扣分情况
     */
    private TextView mViolationPointsTitle;
    private TextView mViolationPointsText;
    /**
     * 缴费时间
     */
    private TextView mViolationPayTitle;
    private TextView mViolationPayText;
    private RelativeLayout mViolationPayRl;
    private TextView mViloationDetailCommitDesc;
    /**
     * 违章缴费
     */
    private Button mNextBtn;

    private IViolationDetailsContract.IViolationDetailsPresenter mPresenter;

    public static ViolationDetailsFragment newInstance(String violationnum) {
        ViolationDetailsFragment detailsFragment = new ViolationDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIOLATION_NUM, violationnum);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }

    @Override
    public void setPresenter(IViolationDetailsContract.IViolationDetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int fragmentView() {
        return R.layout.activity_violation_details;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        IViolationDetailsPresenter mPresenter = new IViolationDetailsPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    public void initView(View fragment) {
        mTvCarNumTitle = (TextView) fragment.findViewById(R.id.tv_carNum_title);
        mTvCarNum = (TextView) fragment.findViewById(R.id.tv_carNum);
        mViolationLocationTitle = (TextView) fragment.findViewById(R.id.violation_location_title);
        mViolationLocationText = (TextView) fragment.findViewById(R.id.violation_location_text);
        mViolationContentTitle = (TextView) fragment.findViewById(R.id.violation_content_title);
        mViolationContentText = (TextView) fragment.findViewById(R.id.violation_content_text);
        mViolationTimeTitle = (TextView) fragment.findViewById(R.id.violation_time_title);
        mViolationTimeText = (TextView) fragment.findViewById(R.id.violation_time_text);
        mViolationStateTitle = (TextView) fragment.findViewById(R.id.violation_state_title);
        mViolationStateText = (TextView) fragment.findViewById(R.id.violation_state_text);
        mViolationMoneyTitle = (TextView) fragment.findViewById(R.id.violation_money_title);
        mViolationMoneyText = (TextView) fragment.findViewById(R.id.violation_money_text);
        mViolationMoneyZhinajin = (TextView) fragment.findViewById(R.id.violation_money_zhinajin);
        mDetailZhinajinImg = (ImageView) fragment.findViewById(R.id.detail_zhinajin_img);
        mDetailZhinajinImg.setOnClickListener(this);
        mViolationMoneyZhinajinText = (TextView) fragment.findViewById(R.id.violation_money_zhinajin_text);
        mViolationPointsTitle = (TextView) fragment.findViewById(R.id.violation_points_title);
        mViolationPointsText = (TextView) fragment.findViewById(R.id.violation_points_text);
        mViolationPayTitle = (TextView) fragment.findViewById(R.id.violation_pay_title);
        mViolationPayText = (TextView) fragment.findViewById(R.id.violation_pay_text);
        mViolationPayRl = (RelativeLayout) fragment.findViewById(R.id.violation_pay_rl);
        mViloationDetailCommitDesc = (TextView) fragment.findViewById(R.id.viloation_detail_commit_desc);
        mNextBtn = (Button) fragment.findViewById(R.id.next_btn);
        mNextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_btn) {
            submitData();
        } else if (v.getId() == R.id.detail_zhinajin_img) {
            lateFeeDialog();
        }
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.violationDetails_v003();
    }

    @Override
    public String getViolationNum() {
        return getArguments().getString(VIOLATION_NUM);
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof ViolationDetailsBean) {
            ViolationDetailsBean detailsBean = (ViolationDetailsBean) response;
            ViolationDetailsBean.RspInfoBean rspInfo = detailsBean.getRspInfo();

            setSimpleDataResult(rspInfo);
        } else
            responseError();
    }

    @Override
    public void responseCustomError(ViolationDetailsBean response) {
        showStateError();

        if (response.getSYS_HEAD().getReturnMessage().contains("处罚决定书")) {
            DialogUtils.createDialog(getActivity(),
                    "请使用处罚决定书编号进行缴纳",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                        }
                    });
        }else{
            toastShort(response.getSYS_HEAD().getReturnMessage());
        }
    }

    private void setSimpleDataResult(ViolationDetailsBean.RspInfoBean rspInfo) {
        String carNum = Des3.decode(rspInfo.getCarnum());
        mTvCarNum.setText(carNum);

        mViolationLocationText.setText(rspInfo.getViolationplace());
        mViolationContentText.setText(rspInfo.getViolationtype());

        if ("0".equals(rspInfo.getProcessste())
                || "2".equals(rspInfo.getProcessste())) {
            mViolationStateText.setText("未缴费");
        } else {
            mViolationStateText.setText("已缴费");
        }

        DateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
        DateFormat formatTime = new SimpleDateFormat("HHmm", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat timeDate = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);

        Date dateDate;
        Date dateTime;
        String dateString;
        String timeString;

        try {
            dateDate = formatDate.parse(rspInfo.getViolationdate());
            dateTime = formatTime.parse(rspInfo.getViolationtime());
            dateString = yearDate.format(dateDate);
            timeString = timeDate.format(dateTime);
            mViolationTimeText.setText(dateString + " " + timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ("1".equals(LoginData.getInstance().mHashMap.get("mRes"))) {
            mViolationPayRl.setVisibility(View.VISIBLE);
            try {
                dateDate = formatDate.parse(rspInfo.getPaydate());
                dateTime = formatTime.parse(rspInfo.getPaytime());
                dateString = yearDate.format(dateDate);
                timeString = timeDate.format(dateTime);
                mViolationPayText.setText(dateString + " " + timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            mViolationPayRl.setVisibility(View.GONE);
        }

        try {
            mViolationMoneyText.setText(FormatUtils.displayPrice(rspInfo.getViolationamt()) + "元");
            mViolationMoneyZhinajin.setText(FormatUtils.displayPrice(rspInfo.getZhinajin()) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("0".equals(rspInfo.getViolationcent())) {
            mViolationPointsText.setText("没有扣分");
        } else {
            mViolationPointsText.setText(rspInfo.getViolationcent() + "分");
        }

        String processste = rspInfo.getProcessste();
        if (("0".equals(processste) || "2".equals(processste))) {//未交费//是否处罚决定书
            mNextBtn.setEnabled(true);
            mNextBtn.setText("违 章 缴 费");
        } else {//已缴费
            mViloationDetailCommitDesc.setVisibility(View.GONE);
            mNextBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 滞纳金dialog
     */
    private void lateFeeDialog() {
        DialogUtils.createLateFeeDialog(getActivity(), "滞纳金说明", "根据《中华人民共和国道" +
                "路交通安全法》108条:当事人应当自收到行政处罚决定书" +
                "之日起15日内，到指定的银行缴纳罚款。\n" +
                "109条：到期不缴纳罚款的，每日按罚款数额的3%加处罚款；\n" +
                "\n" +
                "*滞纳金总额不会超过罚款本金的100%。\n" +
                "*没有去开罚单的“电子警察”记录不会产生滞纳金。");
    }

    protected void submitData() {
        ViolationBean violationBean = new ViolationBean();
        violationBean.setCarnum(mTvCarNum.getText().toString());
        violationBean.setViolationamt(mViolationMoneyText.getText().toString());
        violationBean.setViolationdate(mViolationTimeText.getText().toString());
        violationBean.setViolationnum(getViolationNum());

        showPayFragment(violationBean);
    }

    /**
     * 支付弹出框
     */
    private void showPayFragment(ViolationBean bean) {
        Intent intent = new Intent(getActivity(), ViolationPayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(JxGlobal.putExtra.violation_pay_bean_extra, bean);
        intent.putExtras(bundle);
        startActivity(intent);

        getActivity().overridePendingTransition(R.anim.push_bottom_in, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }
}