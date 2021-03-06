package com.zantong.mobilecttx.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderDetailResult;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResult;
import com.zantong.mobilecttx.common.activity.BrowserForPayActivity;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.eventbus.DrivingCancelEvent;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.HashUtils;
import com.zantong.mobilecttx.utils.PullToRefreshLayout;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.Bind;

/**
 * 代驾订单详情页面
 * Created by zhoujie on 2017/2/21.
 */

public class DODetailActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.order_detail_date_being)
    TextView mDate;
    @Bind(R.id.order_detail_address_being)
    TextView mAddress;
    @Bind(R.id.order_detail_clean_being)
    TextView mClean;
    @Bind(R.id.order_detail_pay)
    TextView mPay;
    @Bind(R.id.daijia_order_detail_state)
    TextView mState;
    @Bind(R.id.driving_order_layout)
    PullToRefreshLayout mDrivingOrderLayout;
    @Bind(R.id.order_detail_driver_layout)
    View mDriverLayout;
    @Bind(R.id.order_detail_mileage_layout)
    View mMileageLayout;
    @Bind(R.id.order_detail_price_layout)
    View mPriceLayout;
    @Bind(R.id.order_detail_driver_phone)
    TextView mDriverPhone;
    @Bind(R.id.order_detail_price)
    TextView mPrice;
    @Bind(R.id.order_detail_mileage)
    TextView mMileage;
    @Bind(R.id.base_loading_failed_layout)
    View mFailedLayout;
    @Bind(R.id.base_loading_failed_refresh)
    View mRefreshLayout;

    private String mOrderId;
    private String mOrderPrice = "1";

    @Override
    public void initView() {
        mRefreshLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrderDetail();
            }
        });
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanOrderDialog();
            }
        });
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.createRechargeDialog(DODetailActivity.this, mOrderId,
                        mPrice.getText().toString(), "",new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                int orderPrice = 0;
                                try {
                                    orderPrice = Integer.valueOf((int) (Double.valueOf(mOrderPrice) * 100));
                                } catch (Exception e) {

                                }
                                String payUrl = "http://139.196.183.121:8081/payment/payForWapb2cPay?orderid=" +
                                        mOrderId + "&amount=" +
                                        orderPrice + "&payType=0";
                                CarApiClient.getPayOrderSn(DODetailActivity.this, payUrl, new CallBack<PayOrderResult>() {
                                    @Override
                                    public void onSuccess(PayOrderResult result) {
                                        if (result.getResponseCode() == 2000) {
                                            PublicData.getInstance().webviewTitle = "支付";
                                            PublicData.getInstance().webviewUrl = result.getData();
                                            Act.getInstance().lauchIntentToLogin(DODetailActivity.this, BrowserForPayActivity.class);
                                        }
                                    }
                                });
                            }
                        });
            }
        });
    }

    @Override
    public void initData() {
        setTitleText("代驾订单");
        initRefreshView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_d_o_detail_being;
    }


    /**
     * 获取代驾中的订单详情
     */
    private void getOrderDetail() {
        DaiJiaOrderDetailDTO dto = new DaiJiaOrderDetailDTO();
        dto.setUsrId(RSAUtils.strByEncryption(PublicData.getInstance().userID, true));
        Intent intent = getIntent();
        mOrderId = intent.getStringExtra(Act.ACT_PARAM);
        dto.setOrderId(mOrderId);
        String time = "1488253689";
        try {
            time = StringUtils.getTimeToStr();
        } catch (Exception e) {

        }
        dto.setTime(time);
        showDialogLoading();
        CarApiClient.getDaiJiaOrderDetail(this, dto, new CallBack<DaiJiaOrderDetailResult>() {
            @Override
            public void onSuccess(DaiJiaOrderDetailResult result) {
                hideDialogLoading();
                mDrivingOrderLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (result.getResponseCode() == 2000) {
                    mDrivingOrderLayout.setVisibility(View.VISIBLE);
                    mFailedLayout.setVisibility(View.GONE);
                    String state = result.getData().getOrderStatus();
                    mState.setText(state);
                    mOrderPrice = result.getData().getAmount();
                    if (state.contains("派单中")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mState.setTextColor(R.color.red);
                    } else if (state.contains("司机途中")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mState.setTextColor(R.color.red);
                    } else if (state.contains("司机等待")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mState.setTextColor(R.color.red);
                    } else if (state.contains("代驾中")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.red);
                    } else if (state.contains("代驾完成")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.gray_99);
                    } else if (state.contains("已预约")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.red);
                    } else if (state.contains("支付完成")) {
                        mMileageLayout.setVisibility(View.VISIBLE);
                        mPriceLayout.setVisibility(View.VISIBLE);
                        mDriverLayout.setVisibility(View.VISIBLE);
                        mPrice.setText(result.getData().getAmount());
                        mMileage.setText(result.getData().getDistance());
                        mDriverPhone.setText(result.getData().getDriverMobile());
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.gray_99);
                    } else if (state.contains("已取消")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.gray_99);
                    }

                    mDate.setText(result.getData().getCreateTime());
                    mAddress.setText(result.getData().getAddress());
                } else {
                    mDrivingOrderLayout.setVisibility(View.GONE);
                    mFailedLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
                mDrivingOrderLayout.setVisibility(View.GONE);
                mFailedLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 初始化下拉刷新界面控件
     */
    private void initRefreshView() {
        mDrivingOrderLayout.setPullUpEnable(false);
        mDrivingOrderLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getOrderDetail();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
    }

    /**
     * 取消订单dialog
     */
    private void cleanOrderDialog() {
        DialogUtils.telDialog(this, "提示", "您确定取消订单吗？", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaiJiaOrderDetailDTO dto = new DaiJiaOrderDetailDTO();
                String time = "1488253689";
                try {
                    time = StringUtils.getTimeToStr();
                } catch (Exception e) {

                }
                dto.setTime(time);
                dto.setOrderId(mOrderId);
                dto.setUsrId(RSAUtils.strByEncryption(PublicData.getInstance().userID, true));
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("time", time);
                hashMap.put("orderId", mOrderId);
                hashMap.put("usrId", PublicData.getInstance().userID);
                dto.setHash(HashUtils.getSignature(hashMap));
                CarApiClient.cancelDaiJiaOrderDetail(DODetailActivity.this, dto, new CallBack<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        if (result.getResponseCode() == 2000) {
                            mState.setText("已取消");
                            mState.setTextColor(R.color.gray_99);
                            mClean.setVisibility(View.GONE);
                            EventBus.getDefault().post(new DrivingCancelEvent(true));
                        }
                    }

                    @Override
                    public void onError(String errorCode, String msg) {
                        super.onError(errorCode, msg);
                    }
                });
            }
        });
    }
}