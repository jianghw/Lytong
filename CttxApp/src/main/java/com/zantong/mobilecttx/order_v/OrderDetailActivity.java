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
import com.tzly.ctcyh.router.base.JxBaseActivity;
import com.tzly.ctcyh.router.util.FormatUtils;
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
 * 订单详情页面
 */
public class OrderDetailActivity extends JxBaseActivity
        implements View.OnClickListener, IOrderDetailContract.IOrderDetailView {

    private TextView mTvPrice;
    private LinearLayout mLayPrice;
    private TextView mTvPayStatus;
    /**
     * 商品内容
     */
    private TextView mTvContentTitle;
    private TextView mTvContent;
    /**
     * 供应商
     */
    private TextView mTvSupplierTitle;
    private TextView mTvSupplier;
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
    private TextView mTvContentBottom;
    /**
     * 对订单有疑问?
     */
    private TextView mTvQuery;

    private IOrderDetailContract.IOrderDetailPresenter mPresenter;
    private String mOrderId;

    private RelativeLayout mRyName;
    private TextView mTvName;
    private RelativeLayout mRyPhone;
    private TextView mTvPhone;
    private RelativeLayout mRyArea;
    private TextView mTvArea;
    private RelativeLayout mRyAddress;
    private TextView mTvAddress;
    private TextView mTvXiugai;
    private TextView mTvTuiKuan;
    private TextView mTvCuidan;
    private TextView mTvWuliu;

    private TextView mPayConsignee;
    private TextView mPayAddress;
    private TextView mPayPick;
    private TextView mPayRemark;
    private TextView mUserName;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RichText.clear(this);
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && intent.hasExtra(MainGlobal.putExtra.web_order_id_extra))
                mOrderId = bundle.getString(MainGlobal.putExtra.web_order_id_extra);
        }
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("订单详情");

        initView(childView);

        OrderDetailPresenter presenter = new OrderDetailPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @Override
    protected void initContentData() {

        RichText.initCacheDir(this);
        if (mPresenter != null) mPresenter.getOrderDetail();

        if (mPresenter != null) mPresenter.getUserOrderInfo();
    }

    public void initView(View view) {
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mLayPrice = (LinearLayout) view.findViewById(R.id.lay_price);
        mTvPayStatus = (TextView) view.findViewById(R.id.tv_pay_status);
        mTvContentTitle = (TextView) view.findViewById(R.id.tv_content_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvSupplierTitle = (TextView) view.findViewById(R.id.tv_supplier_title);
        mTvSupplier = (TextView) view.findViewById(R.id.tv_supplier);
        mTvOrderNumTitle = (TextView) view.findViewById(R.id.tv_order_num_title);
        mTvOrderNum = (TextView) view.findViewById(R.id.tv_order_num);
        mTvDateTitle = (TextView) view.findViewById(R.id.tv_date_title);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvPayTypeTitle = (TextView) view.findViewById(R.id.tv_pay_type_title);
        mTvPayType = (TextView) view.findViewById(R.id.tv_pay_type);
        mTvContentBottom = (TextView) view.findViewById(R.id.tv_content_bottom);

        mRyName = (RelativeLayout) view.findViewById(R.id.ry_name);
        mTvName = (TextView) view.findViewById(R.id.tv_pay_name);
        mRyPhone = (RelativeLayout) view.findViewById(R.id.ry_phone);
        mTvPhone = (TextView) view.findViewById(R.id.tv_pay_phone);
        mRyArea = (RelativeLayout) view.findViewById(R.id.ry_area);
        mTvArea = (TextView) view.findViewById(R.id.tv_pay_area);
        mRyAddress = (RelativeLayout) view.findViewById(R.id.ry_address);
        mTvAddress = (TextView) view.findViewById(R.id.tv_pay_address);

        mUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mPayConsignee = (TextView) view.findViewById(R.id.tv_pay_consignee);
        mPayAddress = (TextView) view.findViewById(R.id.tv_pay_ce_address);
        mPayPick = (TextView) view.findViewById(R.id.tv_pay_pick_up);
        mPayRemark = (TextView) view.findViewById(R.id.tv_pay_remark);

        mTvCuidan = (TextView) view.findViewById(R.id.tv_pay_cuid);
        mTvCuidan.setOnClickListener(this);
        mTvWuliu = (TextView) view.findViewById(R.id.tv_pay_wuliu);
        mTvWuliu.setOnClickListener(this);

        mTvXiugai = (TextView) view.findViewById(R.id.tv_xiugai);
        mTvXiugai.setOnClickListener(this);
        mTvTuiKuan = (TextView) view.findViewById(R.id.tv_tuikuan);
        mTvTuiKuan.setOnClickListener(this);
        mTvQuery = (TextView) view.findViewById(R.id.tv_query);
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
        toastShore(message);
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

        int status = bean.getOrderStatus();
        changeTextColorByStatus(status, mTvPayStatus);
        mTvContent.setText(bean.getGoodsName());
        mTvSupplier.setText(bean.getMerchantName());
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

        String name = bean.getName();
        mTvName.setText(name);
        mRyName.setVisibility(TextUtils.isEmpty(name) ? View.GONE : View.VISIBLE);
        String phone = bean.getPhone();
        mTvPhone.setText(phone);
        mRyPhone.setVisibility(TextUtils.isEmpty(phone) ? View.GONE : View.VISIBLE);
        String sheng = bean.getSheng();
        mTvArea.setText(sheng + "/" + FormatUtils.textForNull(bean.getShi()) + "/" + FormatUtils.textForNull(bean.getXian()));
        mRyArea.setVisibility(TextUtils.isEmpty(sheng) ? View.GONE : View.VISIBLE);
        String address = bean.getAddressDetail();
        mTvAddress.setText(address);
        mRyAddress.setVisibility(TextUtils.isEmpty(address) ? View.GONE : View.VISIBLE);

        String beanDetail = bean.getDetail();
        //        mTvContentBottom.setClickHtml(beanDetail,
        //                new HtmlHttpImageGetter(mTvContentBottom),
        //                new IHtmlTextClick() {
        //                    @Override
        //                    public void clickLine(String url) {
        //                        gotoHtml(url);
        //                    }
        //                });

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

    /**
     * 催单,退款
     */
    @Override
    public String getChannel() {
        return "1";
    }

    @Override
    public void infoError(String message) {
        toastShore(message);
    }

    @Override
    public void infoSucceed(OrderRefundResponse result) {

    }

    @Override
    public void UserOrderInfoError(String message) {
        toastShore(message);
    }

    @Override
    public void UserOrderInfoSucceed(OrderInfoResponse result) {
        OrderInfoResponse.DataBean resultData = result.getData();
        if (resultData == null) return;

        try {
            mUserName.setText(resultData.getName());
            mPayConsignee.setText(resultData.getPhone());
            mPayAddress.setText(resultData.getSheng() + "/" + resultData.getShi() + "/" + resultData.getXian());
            mPayPick.setText(resultData.getAddressDetail());
            mPayRemark.setText(resultData.getSupplement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
