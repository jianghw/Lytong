package com.zantong.mobilecttx.order_v;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.java.response.order.OrderInfoResponse;
import com.tzly.ctcyh.java.response.order.OrderRefundResponse;
import com.tzly.ctcyh.router.base.AbstractBaseActivity;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.order.bean.OrderDetailBean;
import com.zantong.mobilecttx.order.bean.OrderDetailResponse;
import com.zantong.mobilecttx.order_p.IOrderDetailContract;
import com.zantong.mobilecttx.order_p.OrderDetailPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 年检订单详情页面
 */
public class AnnualDetailActivity extends AbstractBaseActivity
        implements View.OnClickListener, IOrderDetailContract.IOrderDetailView {

    private IOrderDetailContract.IOrderDetailPresenter mPresenter;
    private String mOrderId;

    private TextView mTvPrice;
    private RelativeLayout mLayPrice;
    /**
     * 已付款
     */
    private TextView mTvPaid1;
    private TextView mLinePaid2;
    /**
     * 已取件
     */
    private TextView mTvTaken20;
    private TextView mLineTaken21;
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

    private TextView mTvXiugai;
    private TextView mTvTuiKuan;
    private TextView mTvCuidan;
    private TextView mTvWuliu;

    private TextView mPayConsignee;
    private TextView mPayAddress;
    private TextView mPayPick;
    private TextView mPayRemark;
    private TextView mUserName;

    private RelativeLayout mLayUserName;
    private RelativeLayout mLayAddress;
    private RelativeLayout mLayTime;
    private TextView mPickTitle;
    private LinearLayout mLayOther;

    private String mBackExpressNo;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RichText.clear(this);
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_annual_order_detail;
    }

    @Override
    protected void bundleIntent(Intent intent) {
        if (intent != null && intent.hasExtra(MainGlobal.putExtra.web_order_id_extra)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
                mOrderId = bundle.getString(MainGlobal.putExtra.web_order_id_extra);
        }
    }

    @Override
    protected void bindFragment() {
        RichText.initCacheDir(this);

        titleContent("订单详情");

        initView();
        OrderDetailPresenter presenter = new OrderDetailPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @Override
    protected void initContentData() {
        if (mPresenter != null) mPresenter.getOrderDetail();
    }

    public void initView() {
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mLayPrice = (RelativeLayout) findViewById(R.id.lay_price);

        mTvPaid1 = (TextView) findViewById(R.id.tv_paid_1);
        mLinePaid2 = (TextView) findViewById(R.id.line_paid_2);
        mTvTaken20 = (TextView) findViewById(R.id.tv_taken_20);
        mLineTaken21 = (TextView) findViewById(R.id.line_taken_21);
        mLineLeft5 = (TextView) findViewById(R.id.line_left_5);
        mLine6 = (TextView) findViewById(R.id.line_6);
        mTvComplete7 = (TextView) findViewById(R.id.tv_complete_7);
        mLineLeft8 = (TextView) findViewById(R.id.line_left_8);
        mLineLeft9 = (TextView) findViewById(R.id.line_left_9);
        mLineRight10 = (TextView) findViewById(R.id.line_right_10);
        mLineRight11 = (TextView) findViewById(R.id.line_right_11);
        mTvUnComplete12 = (TextView) findViewById(R.id.tv_unComplete_12);
        mLineRight13 = (TextView) findViewById(R.id.line_right_13);
        mTvCompleting14 = (TextView) findViewById(R.id.tv_completing_14);
        mLineRight15 = (TextView) findViewById(R.id.line_right_15);
        mLineRight16 = (TextView) findViewById(R.id.line_right_16);
        mLineDown17 = (TextView) findViewById(R.id.line_down_17);
        mTvCommission18 = (TextView) findViewById(R.id.tv_commission_18);
        mLineCommission19 = (TextView) findViewById(R.id.line_commission_19);
        mTvFinish = (TextView) findViewById(R.id.tv_finish);

        mTvContentTitle = (TextView) findViewById(R.id.tv_content_title);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvOrderNumTitle = (TextView) findViewById(R.id.tv_order_num_title);
        mTvOrderNum = (TextView) findViewById(R.id.tv_order_num);
        mTvDateTitle = (TextView) findViewById(R.id.tv_date_title);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvPayTypeTitle = (TextView) findViewById(R.id.tv_pay_type_title);
        mTvPayType = (TextView) findViewById(R.id.tv_pay_type);
        mTvSendOffTitle = (TextView) findViewById(R.id.tv_send_off_title);
        mTvSendOff = (TextView) findViewById(R.id.tv_send_off);
        mTvSendBackTitle = (TextView) findViewById(R.id.tv_send_back_title);
        mTvSendBack = (TextView) findViewById(R.id.tv_send_back);
        mTvContentBottom = (TextView) findViewById(R.id.tv_content_bottom);

        mLayOther = (LinearLayout) findViewById(R.id.lay_other);
        mLayUserName = (RelativeLayout) findViewById(R.id.ry_consignee);
        mUserName = (TextView) findViewById(R.id.tv_user_name);
        mPayConsignee = (TextView) findViewById(R.id.tv_pay_consignee);
        mLayAddress = (RelativeLayout) findViewById(R.id.ry_ce_address);
        mPayAddress = (TextView) findViewById(R.id.tv_pay_ce_address);
        mLayTime = (RelativeLayout) findViewById(R.id.ry_pick_up);
        mPickTitle = (TextView) findViewById(R.id.tv_pay_pick_up_title);
        mPayPick = (TextView) findViewById(R.id.tv_pay_pick_up);
        mPayRemark = (TextView) findViewById(R.id.tv_pay_remark);


        mTvCuidan = (TextView) findViewById(R.id.tv_pay_cuid);
        mTvCuidan.setOnClickListener(this);
        mTvWuliu = (TextView) findViewById(R.id.tv_pay_wuliu);
        mTvWuliu.setOnClickListener(this);

        mTvXiugai = (TextView) findViewById(R.id.tv_xiugai);
        mTvXiugai.setOnClickListener(this);
        mTvTuiKuan = (TextView) findViewById(R.id.tv_tuikuan);
        mTvTuiKuan.setOnClickListener(this);
        mTvQuery = (TextView) findViewById(R.id.tv_query);
        mTvQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query://客服
                Act.getInstance().gotoIntent(this, ProblemFeedbackActivity.class);
                break;
            case R.id.tv_xiugai://修改
                MainRouter.gotoAmendOrderActivity(this, mOrderId);
                break;
            case R.id.tv_tuikuan://退钱
                MainRouter.gotoOrderRefundActivity(this, mOrderId);
                break;
            case R.id.tv_pay_cuid://催单
                if (mPresenter != null) mPresenter.info();
                break;
            case R.id.tv_pay_wuliu://物流
                String url = "http://www.sf-express.com/cn/sc/dynamic_function/waybill/#search/bill-number/" + mBackExpressNo;
                MainRouter.gotoWebHtmlActivity(this, "物流信息", url);
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
    public void getOrderDetailError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void getOrderDetailSucceed(OrderDetailResponse result) {
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
        int type = bean.getPayType();
        String payType;
        if (type == 1) {
            payType = "工行卡支付";
        } else if (type == 2) {
            payType = "银联卡支付";
        } else if (type == 3) {
            payType = "支付宝支付";
        } else if (type == 4) {
            payType = "微信支付";
        } else {
            payType = "其他支付";
        }
        mTvPayType.setText(payType);
        mTvSendOff.setText(bean.getSendOffExpress());
        mTvSendBack.setText(bean.getSendBackExpress());

        String beanDetail = bean.getDetail();

       /* mTvContentBottom.setClickHtml(beanDetail,
                new HtmlHttpImageGetter(mTvContentBottom),
                new IHtmlTextClick() {
                    @Override
                    public void clickLine(String url) {
                        gotoHtml(url);
                    }
                });*/

        RichText.fromHtml(beanDetail) // 数据源
                .autoFix(true) // 是否自动修复，默认true
                .autoPlay(true) // gif图片是否自动播放
                .showBorder(false) // 是否显示图片边框
                .scaleType(ImageHolder.ScaleType.fit_xy) // 图片缩放方式
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
                .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
                .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
                .urlClick(new OnUrlClickListener() {// 设置链接点击回调
                    @Override
                    public boolean urlClicked(String url) {
                        gotoHtml(url);
                        return false;
                    }
                })
                .bind(this) // 绑定richText对象到某个object上，方便后面的清理
                .into(mTvContentBottom); // 设置目标TextView

        int status = bean.getOrderStatus();
        boolean visible = status != 0 && status != 2;
        mTvCuidan.setVisibility(visible ? View.VISIBLE : View.GONE);

        mTvTuiKuan.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (mPresenter != null && visible) mPresenter.getUserOrderInfo();

        mBackExpressNo = bean.getBackExpressNo();
        if (!TextUtils.isEmpty(mBackExpressNo)) {
            mTvCuidan.setVisibility(View.GONE);
            mTvWuliu.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(bean.getError())) {
            mTvTaken20.setTextColor(getResources().getColor(R.color.colorTvRed_f33));
            mTvTaken20.setText(bean.getError()+"详询4008216158");
        }
    }


    private void gotoHtml(String tableHtml) {
        MainRouter.gotoWebHtmlActivity(this, "Web页面", tableHtml);
    }

    private void gotoBrowser(String beanDetail, TextView tvContent) {
        if (!TextUtils.isEmpty(beanDetail) && beanDetail.contains("target=\"_self\"")) {
            customDisplaysHyperlinks(beanDetail, tvContent);
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
        MainRouter.gotoWebHtmlActivity(this, title, contentHrefLine);
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
     * <p>
     * 0 未支付
     * 1 已支付
     * 2 已取消
     * 3 进行中
     * 4 已完成
     * 5 已取件
     * 6 证件齐全
     * 7 证件不全
     * 8 证件不全中
     * 9 代办成功资料送达中（14转到）
     * 10 代办成功资料送达中（15 转到）
     * 11 订单完成（9 转到）
     * 12 订单完成（10 转到）
     * 13 取件中
     * 14 办理中(6转到)
     * 15 办理中(8转到)
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
                colorText_14();
                colorText_9();
                break;
            case 10:
                colorText_1();
                colorText_5();
                colorText_7_green();
                colorText_8_green();
                colorText_15();
                colorText_10();
                break;
            case 11:
                colorText_1();
                colorText_5();
                colorText_6();
                colorText_14();
                colorText_9();
                colorText_1112();
                break;
            case 12:
                colorText_1();
                colorText_5();
                colorText_7_green();
                colorText_8_green();
                colorText_15();
                colorText_10();
                colorText_1112();
                break;
            case 14:
                colorText_1();
                colorText_5();
                colorText_6();
                colorText_14();
                break;
            case 15:
                colorText_1();
                colorText_5();
                colorText_7_green();
                colorText_8_green();
                colorText_15();
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
    }

    private void colorText_14() {
        mLineLeft8.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineLeft9.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineDown17.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvTaken20.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_15() {
        mLineRight15.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineRight16.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineDown17.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvTaken20.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_6() {
        mLineTaken21.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineLeft5.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLine6.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvComplete7.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_7_green() {
        mLineTaken21.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineRight10.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mLineRight11.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvUnComplete12.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_7_red() {
        mLineTaken21.setBackgroundColor(getResources().getColor(R.color.colorTvRed_f33));
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
        mLineTaken21.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
        mTvCommission18.setTextColor(getResources().getColor(R.color.colorTvGreen_80));
    }

    private void colorText_10() {
        mLineTaken21.setBackgroundColor(getResources().getColor(R.color.colorTvGreen_80));
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

    @Override
    public String getChannel() {
        return "1";
    }

    /**
     * 退单
     */
    @Override
    public void infoError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void infoSucceed(OrderRefundResponse result) {
        ToastUtils.toastShort(result.getResponseDesc());
    }

    @Override
    public void UserOrderInfoError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void UserOrderInfoSucceed(OrderInfoResponse result) {
        OrderInfoResponse.DataBean resultData = result.getData();

        mLayOther.setVisibility(resultData == null ? View.GONE : View.VISIBLE);
        if (resultData == null) return;

        String name = resultData.getName();
        mLayUserName.setVisibility(TextUtils.isEmpty(name) ? View.GONE : View.VISIBLE);
        mUserName.setText(name);

        String phone = resultData.getPhone();
        mPayConsignee.setVisibility(TextUtils.isEmpty(phone) ? View.GONE : View.VISIBLE);
        mPayConsignee.setText(phone);

        String sheng = resultData.getSheng();
        mLayAddress.setVisibility(TextUtils.isEmpty(sheng) ? View.GONE : View.VISIBLE);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(sheng)) sb.append(sheng);
        String shi = resultData.getShi();
        if (!TextUtils.isEmpty(shi)) sb.append("/").append(shi);
        String xian = resultData.getXian();
        if (!TextUtils.isEmpty(xian)) sb.append("/").append(xian);
        mPayAddress.setText(sb.toString());

        String bespeakDate = resultData.getBespeakDate();
        String expressTime = resultData.getExpressTime();
        boolean viTime = !TextUtils.isEmpty(bespeakDate) || !TextUtils.isEmpty(expressTime);
        mLayTime.setVisibility(viTime ? View.VISIBLE : View.GONE);
        mPickTitle.setText(!TextUtils.isEmpty(bespeakDate) ? "预约时间" : "取件时间");
        mPayPick.setText(!TextUtils.isEmpty(bespeakDate) ? bespeakDate : expressTime);

        mPayRemark.setText(resultData.getSupplement());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainGlobal.requestCode.order_detail_amend &&
                resultCode == MainGlobal.resultCode.amend_order_detail) {

            initContentData();
            if (mPresenter != null) mPresenter.getUserOrderInfo();
        }
    }
}
