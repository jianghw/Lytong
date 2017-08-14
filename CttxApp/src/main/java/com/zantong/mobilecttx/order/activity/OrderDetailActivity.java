package com.zantong.mobilecttx.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseJxActivity;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.contract.IOrderDetailContract;
import com.zantong.mobilecttx.order.bean.OrderDetailBean;
import com.zantong.mobilecttx.order.bean.OrderDetailResult;
import com.zantong.mobilecttx.presenter.order.OrderDetailPresenter;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.text.DecimalFormat;

import cn.qqtheme.framework.global.GlobalConstant;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 订单详情页面
 */
public class OrderDetailActivity extends BaseJxActivity
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

    @Override
    protected void bundleIntent(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mOrderId = intent.getStringExtra(GlobalConstant.putExtra.web_order_id_extra);
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_order_detail;
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

        int status = bean.getOrderStatus();
        changeTextColorByStatus(status, mTvPayStatus);
        mTvContent.setText(bean.getGoodsName());
        mTvSupplier.setText(bean.getMerchantName());
        mTvOrderNum.setText(bean.getOrderId());
        mTvDate.setText(bean.getCreateDate());
        mTvPayType.setText(bean.getPayType() == 0 ? "牡丹畅通卡" : "其他银行");

        mTvContentBottom.setText(bean.getDetail());
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
