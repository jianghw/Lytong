package com.zantong.mobile.order.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.R;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.base.activity.BaseJxActivity;
import com.zantong.mobile.browser.BrowserHtmlActivity;
import com.zantong.mobile.contract.IOrderDetailContract;
import com.zantong.mobile.order.bean.OrderDetailBean;
import com.zantong.mobile.order.bean.OrderDetailResult;
import com.zantong.mobile.presenter.order.OrderDetailPresenter;
import com.zantong.mobile.user.activity.ProblemFeedbackActivity;
import com.zantong.mobile.utils.jumptools.Act;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 年检订单详情页面
 */
public class AnnualDetailActivity extends BaseJxActivity
        implements View.OnClickListener, IOrderDetailContract.IOrderDetailView {

    private IOrderDetailContract.IOrderDetailPresenter mPresenter;
    private String mOrderId;

    private TextView mTvPrice;
    private LinearLayout mLayPrice;
    /**
     * 已付款
     */
    private TextView mTvPaid1;
    private TextView mLinePaid2;
    /**
     * 已取件
     */
    private TextView mTvTaken3;
    private TextView mLineTaken4;
    private TextView mLineLeft5;
    private TextView mLine6;
    /**
     * 证件齐全
     */
    private TextView mTvComplete7;
    private TextView mLineLeft8;
    private TextView mLineLeft9;
    private TextView mLineRight10;
    private TextView mLineRight11;
    /**
     * 证件不全
     */
    private TextView mTvUnComplete12;
    private TextView mLineRight13;
    /**
     * 证件补全中
     */
    private TextView mTvCompleting14;
    private TextView mLineRight15;
    private TextView mLineRight16;
    private TextView mLineDown17;
    /**
     * 代办成功资料送达中
     */
    private TextView mTvCommission18;
    private TextView mLineCommission19;
    /**
     * 订单完成
     */
    private TextView mTvFinish;
    /**
     * 商品内容
     */
    private TextView mTvContentTitle;
    private TextView mTvContent;
    /**
     * 订单编号
     */
    private TextView mTvOrderNumTitle;
    private TextView mTvOrderNum;
    /**
     * 创建时间
     */
    private TextView mTvDateTitle;
    private TextView mTvDate;
    /**
     * 付款方式
     */
    private TextView mTvPayTypeTitle;
    private TextView mTvPayType;
    /**
     * 寄出快递单号
     */
    private TextView mTvSendOffTitle;
    /**
     * 寄回快递单号
     */
    private TextView mTvSendBackTitle;
    private TextView mTvContentBottom;
    /**
     * 对订单有疑问?
     */
    private TextView mTvQuery;
    private TextView mTvSendOff;
    private TextView mTvSendBack;

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mOrderId = intent.getStringExtra(JxGlobal.putExtra.web_order_id_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_annual_order_detail;
    }

    @Override
    protected void initFragmentView(View view) {
        initTitleContent("订单详情");

        OrderDetailPresenter presenter = new OrderDetailPresenter(
                Injection.provideRepository(getApplicationContext()), this);

        initView(view);
        if (mPresenter != null) mPresenter.getOrderDetail();
    }

    @Override
    protected void DestroyViewAndThing() {
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mLayPrice = (LinearLayout) view.findViewById(R.id.lay_price);

        mTvPaid1 = (TextView) view.findViewById(R.id.tv_paid_1);
        mLinePaid2 = (TextView) view.findViewById(R.id.line_paid_2);
        mTvTaken3 = (TextView) view.findViewById(R.id.tv_taken_3);
        mLineTaken4 = (TextView) view.findViewById(R.id.line_taken_4);
        mLineLeft5 = (TextView) view.findViewById(R.id.line_left_5);
        mLine6 = (TextView) view.findViewById(R.id.line_6);
        mTvComplete7 = (TextView) view.findViewById(R.id.tv_complete_7);
        mLineLeft8 = (TextView) view.findViewById(R.id.line_left_8);
        mLineLeft9 = (TextView) view.findViewById(R.id.line_left_9);
        mLineRight10 = (TextView) view.findViewById(R.id.line_right_10);
        mLineRight11 = (TextView) view.findViewById(R.id.line_right_11);
        mTvUnComplete12 = (TextView) view.findViewById(R.id.tv_unComplete_12);
        mLineRight13 = (TextView) view.findViewById(R.id.line_right_13);
        mTvCompleting14 = (TextView) view.findViewById(R.id.tv_completing_14);
        mLineRight15 = (TextView) view.findViewById(R.id.line_right_15);
        mLineRight16 = (TextView) view.findViewById(R.id.line_right_16);
        mLineDown17 = (TextView) view.findViewById(R.id.line_down_17);
        mTvCommission18 = (TextView) view.findViewById(R.id.tv_commission_18);
        mLineCommission19 = (TextView) view.findViewById(R.id.line_commission_19);
        mTvFinish = (TextView) view.findViewById(R.id.tv_finish);
        mTvContentTitle = (TextView) view.findViewById(R.id.tv_content_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvOrderNumTitle = (TextView) view.findViewById(R.id.tv_order_num_title);
        mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        mTvDateTitle = (TextView) view.findViewById(R.id.tv_date_title);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvPayTypeTitle = (TextView) view.findViewById(R.id.tv_pay_type_title);
        mTvPayType = (TextView) view.findViewById(R.id.tv_pay_type);
        mTvSendOffTitle = (TextView) view.findViewById(R.id.tv_send_off_title);
        mTvSendOff = (TextView) view.findViewById(R.id.tv_send_off);
        mTvSendBackTitle = (TextView) view.findViewById(R.id.tv_send_back_title);
        mTvSendBack = (TextView) view.findViewById(R.id.tv_send_back);
        mTvContentBottom = (TextView) view.findViewById(R.id.tv_content_bottom);
        mTvQuery = (TextView) view.findViewById(R.id.tv_query);
        mTvQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query:
                Act.getInstance().gotoIntent(this, ProblemFeedbackActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void setPresenter(IOrderDetailContract.IOrderDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    @Override
    public void getOrderDetailError(String message) {
        dismissLoadingDialog();
        ToastUtils.toastShort(message);
    }

    @Override
    public void getOrderDetailSucceed(OrderDetailResult result) {
        OrderDetailBean bean = result.getData();
        if (bean != null) {
            initDataByText(bean);
        }
    }

    /**
     * 订单状态,0未至付，1已支付,2取消或过期
     */
    private void initDataByText(OrderDetailBean bean) {
        float price = bean.getAmount();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(price);
        mTvPrice.setText(format);

        setupProcessState(bean);

        mTvContent.setText(bean.getGoodsName());
        mTvOrderNum.setText(bean.getOrderId());
        mTvDate.setText(bean.getCreateDate());
        mTvPayType.setText(bean.getPayType() == 0 ? "牡丹畅通卡" : "其他支付");
        mTvSendOff.setText(bean.getSendOffExpress());
        mTvSendBack.setText(bean.getSendBackExpress());

        String beanDetail = bean.getDetail();
        gotoBrowser(beanDetail, mTvContentBottom);
    }

    private void gotoBrowser(String beanDetail, TextView tvContent) {
        if (!TextUtils.isEmpty(beanDetail) && beanDetail.contains("target=\"_self\"")) {
            customDisplaysHyperlinks(beanDetail,tvContent);
        } else {
            CharSequence charSequence = Html.fromHtml(beanDetail);
            mTvContentBottom.setText(charSequence);
            mTvContentBottom.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接为可点击状态
        }
    }

    private void customDisplaysHyperlinks(String content, TextView tvContent) {
        Pattern pattern = Pattern.compile("<a href=\\\"(.*?)\\\" target=\\\"(.*?)\\\".*?>(.*?)<\\/a>");
        Matcher matcher = pattern.matcher(content);
        int start = 0, end = 0;
        while (matcher.find()) {
            start = matcher.start();
            end = matcher.end();
        }
        //超链接文本
        String lineString = content.substring(start, end);
        String contentString = matcherContent(lineString);
        final String contentHrefLine = matcherHrefLine(lineString);

        //显示的新文本
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(content.substring(0, start));
        stringBuilder.append(contentString);
        stringBuilder.append(content.substring(end, content.length()));
        String newContent = stringBuilder.toString();

        SpannableString spannableString = new SpannableString(newContent);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.parseColor("#ff33b5e5"));//设置超链接的颜色
                textPaint.setUnderlineText(true);
            }

            @Override
            public void onClick(View widget) {
                // 单击事件处理
                internalBrowser(contentHrefLine);
            }
        };
        spannableString.setSpan(span, start, start + contentString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvContent.setText(spannableString);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 跳转应用内部浏览器
     */
    private void internalBrowser(String contentHrefLine) {
        String title = "信息";
        Intent intent = new Intent();
        intent.putExtra(JxGlobal.putExtra.browser_title_extra, title);
        intent.putExtra(JxGlobal.putExtra.browser_url_extra, contentHrefLine);
        Act.getInstance().gotoLoginByIntent(this, BrowserHtmlActivity.class, intent);
    }

    /**
     * 超链接文本中的内容
     */
    public String matcherContent(String content) {
        Pattern patternStart = Pattern.compile("<a href=\\\"(.*?)\\\" target=\\\"(.*?)\\\".*?>");
        Matcher matcherStart = patternStart.matcher(content);
        int start = 0;
        while (matcherStart.find()) {
            start = matcherStart.end();
        }
        Pattern patternEnd = Pattern.compile("<\\/a>");
        Matcher matcherEnd = patternEnd.matcher(content);
        int end = 0;
        while (matcherEnd.find()) {
            end = matcherEnd.start();
        }
        return content.substring(start, end);
    }

    /**
     * 网址
     */
    public static String matcherHrefLine(String content) {
        Pattern patternStart = Pattern.compile("<a href=\\\"");
        Matcher matcherStart = patternStart.matcher(content);
        int start = 0;
        while (matcherStart.find()) {
            start = matcherStart.end();
        }
        Pattern patternEnd = Pattern.compile("\\\" target=\\\"(.*?)\\\".*?>");
        Matcher matcherEnd = patternEnd.matcher(content);
        int end = 0;
        while (matcherEnd.find()) {
            end = matcherEnd.start();
        }
        return content.substring(start, end);
    }

    /**
     * 5-已取件,6-证件齐全,7-证件不全,8-证件不全中,
     * 9-代办成功资料送达(6过来),
     * 10-代办成功资料送达(9过来),
     * 11-订单完成(6过来),12-订单完成(9过来)
     */
    private void setupProcessState(OrderDetailBean bean) {
        int status = bean.getOrderStatus();
        switch (status) {
            case 1:
                colorText_1();
                break;
            case 5:
                colorText_1();
                colorText_5();
                break;
            case 6:
                colorText_1();
                colorText_5();
                colorText_6();
                break;
            case 7:
                colorText_1();
                colorText_5();
                colorText_7_red();
                break;
            case 8:
                colorText_1();
                colorText_5();
                colorText_7_green();
                colorText_8_red();
                break;
            case 9:
                colorText_1();
                colorText_5();
                colorText_6();
                colorText_9();
                break;
            case 10:
                colorText_1();
                colorText_5();
                colorText_7_green();
                colorText_8_green();
                colorText_10();
                break;
            case 11:
                colorText_1();
                colorText_5();
                colorText_6();
                colorText_9();
                colorText_1112();
                break;
            case 12:
                colorText_1();
                colorText_5();
                colorText_7_green();
                colorText_8_green();
                colorText_10();
                colorText_1112();
                break;
            default:
                mTvPaid1.setTextColor(getResources().getColor(R.color.colorTvRed_f33));
                mTvPaid1.setText("未付款");
                break;
        }
    }

    private void colorText_1() {
        mTvPaid1.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvPaid1.setText("已付款");
    }

    private void colorText_5() {
        mLinePaid2.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvTaken3.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_6() {
        mLineTaken4.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineLeft5.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLine6.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvComplete7.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_7_green() {
        mLineTaken4.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineRight10.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineRight11.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvUnComplete12.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_7_red() {
        mLineTaken4.setBackgroundColor(getResources().getColor(R.color.colorTvRed_f33));
        mLineRight10.setBackgroundColor(getResources().getColor(R.color.colorTvRed_f33));
        mLineRight11.setBackgroundColor(getResources().getColor(R.color.colorTvRed_f33));
        mTvUnComplete12.setTextColor(getResources().getColor(R.color.colorTvRed_f33));
    }

    private void colorText_8_green() {
        mLineRight13.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvCompleting14.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_8_red() {
        mLineRight13.setBackgroundColor(getResources().getColor(R.color.colorTvRed_f33));
        mTvCompleting14.setTextColor(getResources().getColor(R.color.colorTvRed_f33));
    }

    private void colorText_9() {
        mLineLeft8.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineLeft9.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineDown17.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvCommission18.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_10() {
        mLineRight15.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineRight16.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineDown17.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvCommission18.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_1112() {
        mLineCommission19.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvFinish.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void changeTextColorByStatus(int status, TextView tvPayStatus) {
        switch (status) {
            case 0:
                tvPayStatus.setTextColor(getResources().getColor(R.color.colorTvOrange_ef));
                tvPayStatus.setText("待支付");
                break;
            case 1:
                tvPayStatus.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("已支付");
                break;
            case 3:
                tvPayStatus.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("进行中");
                break;
            case 4:
                tvPayStatus.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
                tvPayStatus.setText("已完成");
                break;
            case 2:
                tvPayStatus.setTextColor(getResources().getColor(R.color.colorTvBlack_b2));
                tvPayStatus.setText("已取消");
                break;
            default:
                tvPayStatus.setTextColor(getResources().getColor(R.color.colorTvBlack_b2));
                tvPayStatus.setText("未知状态");
                break;
        }
    }

    @Override
    public String getOrderId() {
        return mOrderId;
    }
}
