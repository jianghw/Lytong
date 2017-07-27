package com.zantong.mobilecttx.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderDetailBean;
import com.zantong.mobilecttx.chongzhi.bean.RechargeOrderDetailResult;
import com.zantong.mobilecttx.common.activity.BrowserForPayActivity;
import com.zantong.mobilecttx.user.dto.CancelRechargeOrderDTO;
import com.zantong.mobilecttx.eventbus.OrderCancelEvent;
import com.zantong.mobilecttx.presenter.OrderPresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.interf.IOrderView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

public class OrderRechargeDetailActivity extends BaseMvpActivity<IOrderView, OrderPresenter> {

    public static final String RECHARGE_ORDER_ITEM = "rechargeorderItem";

    @Bind(R.id.mine_recharge_order_detail_title)//订单名称
            TextView mTitle;
    @Bind(R.id.mine_recharge_order_detail_ordertm)//创建时间
            TextView mTime;
    @Bind(R.id.mine_recharge_order_detail_content_status)//订单状态
            TextView mStatus;
    @Bind(R.id.mine_recharge_order_detail_content_img)//加油站图片
            ImageView mImg;
    @Bind(R.id.mine_recharge_order_detail_sn)//订单编号
            TextView mSn;
    @Bind(R.id.mine_recharge_order_detail_count)//订单数量
            TextView mCount;
    @Bind(R.id.mine_recharge_order_detail_price)//订单金额
            TextView mOrderAmount;
    @Bind(R.id.mine_recharge_order_detail_amount)//订单金额
            TextView mAmount;
    @Bind(R.id.mine_recharge_order_option_layout)//底部菜单布局
            View mOptionLayout;

    @Bind(R.id.mine_recharge_order_detail_cancel)//取消订单
            TextView mCancel;
    @Bind(R.id.mine_recharge_order_detail_pay)
    TextView mPay;
    @Bind(R.id.mine_recharge_order_detail_paystyle)
    TextView mPayStyle;
    @Bind(R.id.mine_recharge_order_detail_paytm)
    TextView mPayTm;

    RechargeOrderBean mOrderBean;

    @Override
    public void initView() {
        setTitleText("订单详情");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mOrderBean = (RechargeOrderBean) bundle.getSerializable(RECHARGE_ORDER_ITEM);

        CancelRechargeOrderDTO dto = new CancelRechargeOrderDTO();
        dto.setOrderId(mOrderBean.getOrderId());
        dto.setUserId(RSAUtils.strByEncryption(PublicData.getInstance().userID, true));
        CarApiClient.queryOrderDetail(this, dto, new CallBack<RechargeOrderDetailResult>() {
            @Override
            public void onSuccess(RechargeOrderDetailResult result) {
                if (result.getResponseCode() == 2000) {
                    RechargeOrderDetailBean data = result.getData();
                    mTitle.setText("1".equals(result.getData().getOilType()) ? "中石化直充" + data.getProdMoney() + "元" : "中石油直充" + data.getProdMoney() + "元");
                    mTime.setText("订单日期：" + data.getRechargeDate());
                    mSn.setText("订单编号：" + data.getOrderId());
                    mCount.setText("×" + data.getQuantity());
                    mOrderAmount.setText(data.getRechargeMoney());
                    mAmount.setText(data.getProdMoney());
                    mPayStyle.setText(data.getPayType().equals("0") ? "支付方式：畅通卡支付" : "支付方式：工行其他银行卡支付");
                    mPayTm.setText("支付时间：" + data.getPayDate());
                    mImg.setBackgroundResource("2".equals(data.getOilType()) ? R.mipmap.icon_zhongshiyou : R.mipmap.icon_zhongshihua);


                    // 0：待支付；1：支付失败；2：已支付（钱支付）；3：退款中；4：退款成功；5：取消
                    switch (data.getOrderStatus()) {
                        case 0:
                            mStatus.setText("未支付");
                            mStatus.setTextColor(getResources().getColor(R.color.red));
                            mOptionLayout.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            mStatus.setText("支付失败");
                            mStatus.setTextColor(getResources().getColor(R.color.red));
                            mOptionLayout.setVisibility(View.VISIBLE);
                            mPay.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            mStatus.setText("已完成");
                            mStatus.setTextColor(getResources().getColor(R.color.gray_99));
                            mOptionLayout.setVisibility(View.GONE);
                        case 3:
                            mStatus.setText("退款中");
                            mStatus.setTextColor(getResources().getColor(R.color.gray_99));
                            mOptionLayout.setVisibility(View.GONE);
                        case 4:
                            mStatus.setText("已退款");
                            mStatus.setTextColor(getResources().getColor(R.color.gray_99));
                            mOptionLayout.setVisibility(View.GONE);
                        case 5:
                            mStatus.setText("已取消");
                            mStatus.setTextColor(getResources().getColor(R.color.gray_99));
                            mOptionLayout.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_recharge_order_detail_activity;
    }

    @OnClick({R.id.mine_recharge_order_detail_cancel, R.id.mine_recharge_order_detail_pay})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_recharge_order_detail_cancel:
                DialogUtils.remindDialog(this, "温馨提醒", "您确定要取消该订单吗？", "取消", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelOrder();
                    }
                });

                break;
            case R.id.mine_recharge_order_detail_pay:
                DialogUtils.createRechargeDialog(this, mOrderBean.getOrderId(),
                        mOrderBean.getRechargeMoney(),mOrderBean.getPayType(), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int orderPrice = 0;
                                try {
                                    orderPrice = Integer.valueOf((int) (Double.valueOf(mOrderBean.getRechargeMoney()) * 100));
                                } catch (Exception e) {

                                }

                                String payUrl = "http://139.196.183.121:8081/payment/payForWapb2cPay?orderid=" +
                                        mOrderBean.getOrderId() + "&amount=" +
                                        orderPrice + "&payType=0";
                                CarApiClient.getPayOrderSn(OrderRechargeDetailActivity.this, payUrl, new CallBack<PayOrderResult>() {
                                    @Override
                                    public void onSuccess(PayOrderResult result) {
                                        if (result.getResponseCode() == 2000) {
                                            PublicData.getInstance().webviewTitle = "支付";
                                            PublicData.getInstance().webviewUrl = result.getData();
                                            Act.getInstance().gotoIntentLogin(OrderRechargeDetailActivity.this, BrowserForPayActivity.class);
                                        }
                                    }
                                });

                            }
                        });

                break;
        }
    }

    /**
     * 取消订单
     */

    private void cancelOrder() {
        CancelRechargeOrderDTO dto = new CancelRechargeOrderDTO();
        dto.setUserId(RSAUtils.strByEncryption(PublicData.getInstance().userID, true));
        dto.setOrderId(mOrderBean.getOrderId());
        CarApiClient.cancelOrder(this, dto, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
                if (result.getResponseCode() == 2000) {
                    ToastUtils.showShort(OrderRechargeDetailActivity.this, "已取消");
                    EventBus.getDefault().post(new OrderCancelEvent(true));
                    OrderRechargeDetailActivity.this.finish();
                }
            }
        });
    }
}
