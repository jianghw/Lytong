package com.zantong.mobilecttx.weizhang.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.contract.ModelView;
import com.zantong.mobilecttx.presenter.ViolationDetailsPresenterImp;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.global.JxGlobal;
import cn.qqtheme.framework.util.ToastUtils;

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
    @Bind(R.id.violation_time_text)
    TextView mViolationTimeText;
    @Bind(R.id.violation_state_title)
    TextView mViolationStateTitle;
    @Bind(R.id.violation_state_text)
    TextView mViolationStateText;
    @Bind(R.id.violation_money_title)
    TextView mViolationMoneyTitle;
    @Bind(R.id.violation_money_text)
    TextView mViolationMoneyText;
    @Bind(R.id.violation_money_zhinajin)
    TextView mViolationMoneyZhinajin;
    @Bind(R.id.detail_zhinajin_img)
    ImageView mDetailZhinajinImg;
    @Bind(R.id.violation_money_zhinajin_text)
    TextView mViolationMoneyZhinajinText;
    @Bind(R.id.violation_points_title)
    TextView mViolationPointsTitle;
    @Bind(R.id.violation_points_text)
    TextView mViolationPointsText;
    @Bind(R.id.violation_pay_title)
    TextView mViolationPayTitle;
    @Bind(R.id.violation_pay_text)
    TextView mViolationPayText;
    @Bind(R.id.violation_pay_rl)
    RelativeLayout mViolationPayRl;
    @Bind(R.id.viloation_detail_commit_desc)
    TextView mDescTextmDescText;
    @Bind(R.id.next_btn)
    Button mNextBtn;
    @Bind(R.id.tv_carNum)
    TextView mCarNum;

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

        ViolationDetailsBean.RspInfoBean mRspInfo = violationDetailsBean.getRspInfo();
        String carNum = Des3.decode(mRspInfo.getCarnum());
        mCarNum.setText(carNum);

        mViolationLocationText.setText(mRspInfo.getViolationplace());
        mViolationContentText.setText(mRspInfo.getViolationtype());

        DateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
        DateFormat formatTime = new SimpleDateFormat("HHmm", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat timeDate = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);

        Date dateDate;
        Date dateTime;
        String dateString;
        String timeString;

        try {
            dateDate = formatDate.parse(mRspInfo.getViolationdate());
            dateTime = formatTime.parse(mRspInfo.getViolationtime());
            dateString = yearDate.format(dateDate);
            timeString = timeDate.format(dateTime);
            mViolationTimeText.setText(dateString + " " + timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ("1".equals(MemoryData.getInstance().mHashMap.get("mRes"))) {
            mViolationPayRl.setVisibility(View.VISIBLE);
            try {
                dateDate = formatDate.parse(mRspInfo.getPaydate());
                dateTime = formatTime.parse(mRspInfo.getPaytime());
                dateString = yearDate.format(dateDate);
                timeString = timeDate.format(dateTime);
                mViolationPayText.setText(dateString + " " + timeString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            mViolationPayRl.setVisibility(View.GONE);
        }


        if ("0".equals(mRspInfo.getProcessste())
                || "2".equals(mRspInfo.getProcessste())) {
            mViolationStateText.setText("未缴费");
        } else {
            mViolationStateText.setText("已缴费");
        }

        try {
            mViolationMoneyText.setText(StringUtils.getPriceString(mRspInfo.getViolationamt()) + "元");
            mViolationMoneyZhinajin.setText(StringUtils.getPriceString(mRspInfo.getZhinajin()) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ("0".equals(mRspInfo.getViolationcent())) {
            mViolationPointsText.setText("没有扣分");
        } else {
            mViolationPointsText.setText(mRspInfo.getViolationcent() + "分");
        }
        String violationNumber = Des3.decode(mRspInfo.getCarnum());
        for (int i = 0; i < MemoryData.getInstance().payData.size(); i++) {
            if (violationNumber.equals(MemoryData.getInstance().payData.get(i).getCarnum())) {
                isPagCar = true;
                break;
            } else {
                isPagCar = false;
            }
        }
        String bitNumber = mViolationnum.substring(6, 7);

        if ("0".equals(mRspInfo.getProcessste())
                || "2".equals(mRspInfo.getProcessste())) {//未交费

            if ("1".equals(bitNumber) || "2".equals(bitNumber)) {//是否处罚决定书
                mNextBtn.setEnabled(true);
                mNextBtn.setText("违章缴费");
                mCommitType = 0;
            } else if (Tools.isStrEmpty(MemoryData.getInstance().filenum)) {
                MobclickAgent.onEvent(this, Config.getUMengID(11));
                mDescTextmDescText.setVisibility(View.VISIBLE);
                mDescTextmDescText.setText("您还未绑定畅通卡，违章缴费需要使用畅通卡");
                mNextBtn.setText("绑定畅通卡");
                mCommitType = 1;
            } else if (isPagCar || MemoryData.getInstance().payData.size() < 2) {
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
                MemoryData.getInstance().mHashMap.clear();
                if (mDetailsBean != null) {
                    ViolationDetailsBean.RspInfoBean rspInfo = mDetailsBean.getRspInfo();
                    ViolationBean violationBean = new ViolationBean();
                    violationBean.setCarnum(rspInfo.getCarnum());
                    violationBean.setViolationamt(rspInfo.getViolationamt());
                    violationBean.setViolationdate(rspInfo.getViolationdate());
                    violationBean.setViolationnum(rspInfo.getViolationnum());
                    showPayFragment(violationBean);
                } else {
                    ToastUtils.toastShort("出现未知错误，mDetailsBean为null");
                }
                break;
            case 1://绑卡
                Act.getInstance().gotoIntentLogin(this, MyCardActivity.class);
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

        mDescTextmDescText.setVisibility(View.VISIBLE);
        mDescTextmDescText.setText("获取信息失败，请退出当前页面检查违章单编号");
        mNextBtn.setVisibility(View.GONE);
    }

}
