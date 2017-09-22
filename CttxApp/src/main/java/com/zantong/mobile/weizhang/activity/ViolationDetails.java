package com.zantong.mobile.weizhang.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobile.R;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.car.activity.ManageCarActivity;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.contract.ModelView;
import com.zantong.mobile.presenter.ViolationDetailsPresenterImp;
import com.zantong.mobile.utils.DialogUtils;
import com.zantong.mobile.utils.StringUtils;
import com.zantong.mobile.utils.Tools;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.weizhang.bean.ViolationBean;
import com.zantong.mobile.weizhang.bean.ViolationDetailsBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import com.tzly.annual.base.global.JxGlobal;

import static com.zantong.mobile.R.id.viloation_detail_commit_desc;
import static com.zantong.mobile.R.id.violation_money_text;
import static com.zantong.mobile.R.id.violation_money_zhinajin_text;
import static com.zantong.mobile.R.id.violation_pay_rl;
import static com.zantong.mobile.R.id.violation_pay_text;
import static com.zantong.mobile.R.id.violation_points_text;
import static com.zantong.mobile.R.id.violation_state_text;
import static com.zantong.mobile.R.id.violation_time_text;

/**
 * 违章详情
 */
public class ViolationDetails extends BaseJxActivity implements ModelView {

    @Bind(R.id.violation_location_title)
    TextView mViolationLocationTitle;
    @Bind(R.id.violation_location_text)
    TextView mViolationLocationText;
    @Bind(R.id.violation_content_title)
    TextView mViolationContentTitle;
    @Bind(R.id.violation_content_text)
    TextView mViolationContentText;
    @Bind(R.id.violation_time_title)
    TextView mViolationTimeTitle;
    @Bind(violation_time_text)
    TextView mViolationTimeText;
    @Bind(R.id.violation_state_title)
    TextView mViolationStateTitle;
    @Bind(violation_state_text)
    TextView mViolationStateText;
    @Bind(R.id.violation_money_title)
    TextView mViolationMoneyTitle;
    @Bind(violation_money_text)
    TextView mViolationMoneyText;
    @Bind(R.id.violation_money_zhinajin)
    TextView mViolationMoneyZhinajin;
    @Bind(R.id.detail_zhinajin_img)
    ImageView mDetailZhinajinImg;
    @Bind(violation_money_zhinajin_text)
    TextView mViolationMoneyZhinajinText;
    @Bind(R.id.violation_points_title)
    TextView mViolationPointsTitle;
    @Bind(violation_points_text)
    TextView mViolationPointsText;
    @Bind(R.id.violation_pay_title)
    TextView mViolationPayTitle;
    @Bind(violation_pay_text)
    TextView mViolationPayText;
    @Bind(violation_pay_rl)
    RelativeLayout mViolationPayRl;
    @Bind(viloation_detail_commit_desc)
    TextView mDescTextmDescText;
    @Bind(R.id.next_btn)
    Button mNextBtn;

    private ViolationDetailsPresenterImp mViolationDetailsPresenterImp;
    private boolean isPagCar = false;
    private int mCommitType; //0去缴费 1去绑卡 2去改绑车辆
    /**
     * 单号
     */
    private String mViolationnum;
    private ViolationDetailsBean mDetailsBean;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent.hasExtra(JxGlobal.putExtra.common_extra)) {
            mViolationnum = intent.getStringExtra(JxGlobal.putExtra.common_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.violation_details;
    }

    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("违章详情");
        mViolationDetailsPresenterImp = new ViolationDetailsPresenterImp(this);
    }

    protected void initViewStatus() {
        mViolationDetailsPresenterImp.loadView(mViolationnum);
    }

    @Override
    public void updateView(Object object, int index) {
        mDetailsBean = (ViolationDetailsBean) object;
        if (null != mDetailsBean) initText(mDetailsBean);
    }

    @Override
    protected void DestroyViewAndThing() {
        hideDialogLoading();
    }

    @SuppressLint("SetTextI18n")
    private void initText(ViolationDetailsBean violationDetailsBean) {

        mViolationLocationText.setText(violationDetailsBean.getRspInfo().getViolationplace());
        mViolationContentText.setText(violationDetailsBean.getRspInfo().getViolationtype());

        DateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
        DateFormat formatTime = new SimpleDateFormat("HHmm", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat timeDate = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);

        Date dateDate;
        Date dateTime;
        String dateString;
        String timeString;

        try {
            dateDate = formatDate.parse(violationDetailsBean.getRspInfo().getViolationdate());
            dateTime = formatTime.parse(violationDetailsBean.getRspInfo().getViolationtime());
            dateString = yearDate.format(dateDate);
            timeString = timeDate.format(dateTime);
            mViolationTimeText.setText(dateString + " " + timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ("1".equals(PublicData.getInstance().mHashMap.get("mRes"))) {
            mViolationPayRl.setVisibility(View.VISIBLE);
            try {
                dateDate = formatDate.parse(violationDetailsBean.getRspInfo().getPaydate());
                dateTime = formatTime.parse(violationDetailsBean.getRspInfo().getPaytime());
                dateString = yearDate.format(dateDate);
                timeString = timeDate.format(dateTime);
                mViolationPayText.setText(dateString + " " + timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            mViolationPayRl.setVisibility(View.GONE);
        }


        if ("0".equals(violationDetailsBean.getRspInfo().getProcessste())
                || "2".equals(violationDetailsBean.getRspInfo().getProcessste())) {
            mViolationStateText.setText("未缴费");
        } else {
            mViolationStateText.setText("已缴费");
        }

        try {
            mViolationMoneyText.setText(StringUtils.getPriceString(violationDetailsBean.getRspInfo().getViolationamt()) + "元");
            mViolationMoneyZhinajin.setText(StringUtils.getPriceString(violationDetailsBean.getRspInfo().getZhinajin()) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("0".equals(violationDetailsBean.getRspInfo().getViolationcent())) {
            mViolationPointsText.setText("没有扣分");
        } else {
            mViolationPointsText.setText(violationDetailsBean.getRspInfo().getViolationcent() + "分");
        }
        String violationNumber = Des3.decode(violationDetailsBean.getRspInfo().getCarnum());
        for (int i = 0; i < PublicData.getInstance().payData.size(); i++) {
            if (violationNumber.equals(PublicData.getInstance().payData.get(i).getCarnum())) {
                isPagCar = true;
                break;
            } else {
                isPagCar = false;
            }
        }
        String bitNumber = mViolationnum.substring(6, 7);

        if ("0".equals(violationDetailsBean.getRspInfo().getProcessste())
                || "2".equals(violationDetailsBean.getRspInfo().getProcessste())) {//未交费

            if ("1".equals(bitNumber) || "2".equals(bitNumber)) {//是否处罚决定书
                mNextBtn.setEnabled(true);
                mNextBtn.setText("违章缴费");
                mCommitType = 0;
            } else if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {
                MobclickAgent.onEvent(this, Config.getUMengID(11));
                mDescTextmDescText.setVisibility(View.VISIBLE);
                mDescTextmDescText.setText("您还未绑定畅通卡，违章缴费需要使用畅通卡");
                mNextBtn.setText("绑定畅通卡");
                mCommitType = 1;
            } else if (isPagCar || PublicData.getInstance().payData.size() < 2) {
                mNextBtn.setEnabled(true);
                mNextBtn.setText("违章缴费");
                mCommitType = 0;
            } else {
                mDescTextmDescText.setVisibility(View.VISIBLE);
                mDescTextmDescText.setText("您的绑定车辆已满，缴费需要更改绑定设置");
                mNextBtn.setText("车辆改绑");
                mCommitType = 2;
            }
        } else {//已缴费
            mDescTextmDescText.setVisibility(View.GONE);
            mNextBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.next_btn, R.id.detail_zhinajin_img})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                submitData();
                break;
            case R.id.detail_zhinajin_img:
                lateFeeDialog();
                break;
            default:
                break;
        }
    }

    protected void submitData() {
        switch (mCommitType) {
            case 0:
                PublicData.getInstance().mHashMap.clear();
                ViolationDetailsBean.RspInfoBean rspInfo = mDetailsBean.getRspInfo();
                ViolationBean violationBean = new ViolationBean();
                violationBean.setCarnum(rspInfo.getCarnum());
                violationBean.setViolationamt(rspInfo.getViolationamt());
                violationBean.setViolationdate(rspInfo.getViolationdate());
                violationBean.setViolationnum(rspInfo.getViolationnum());
                showPayFragment(violationBean);
                break;
            case 2:
                Act.getInstance().gotoIntentLogin(this, ManageCarActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 支付弹出框
     */
    private void showPayFragment(ViolationBean bean) {
        Intent intent = new Intent(this, ViolationPayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(JxGlobal.putExtra.violation_pay_bean_extra, bean);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.push_bottom_in, 0);
    }

    /**
     * 滞纳金dialog
     */
    private void lateFeeDialog() {
        DialogUtils.createLateFeeDialog(this, "滞纳金说明", "根据《中华人民共和国道" +
                "路交通安全法》108条:当事人应当自收到行政处罚决定书" +
                "之日起15日内，到指定的银行缴纳罚款。\n" +
                "109条：到期不缴纳罚款的，每日按罚款数额的3%加处罚款；\n" +
                "\n" +
                "*滞纳金总额不会超过罚款本金的100%。\n" +
                "*没有去开罚单的“电子警察”记录不会产生滞纳金。");
    }

    @Override
    public void showProgress() {
        showDialogLoading();
    }

    @Override
    public void hideProgress() {
        hideDialogLoading();
    }

    public void loadFaildProgress() {
        hideDialogLoading();
    }

}
