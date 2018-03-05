package com.zantong.mobilecttx.daijia.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.daijia.bean.DaiJiaOrderDetailResponse;
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.eventbus.DrivingCancelEvent;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.HashUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;
import com.zantong.mobilecttx.widght.refresh.OnPullListener;
import com.zantong.mobilecttx.widght.refresh.PullToRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.Bind;
import com.tzly.ctcyh.java.response.BaseResponse;
import com.tzly.ctcyh.router.global.JxGlobal;

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
                DialogUtils.createRechargeDialog(Utils.getContext(),
                        mOrderId,
                        mPrice.getText().toString(),
                        "",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                double orderPrice = Double.valueOf(mOrderPrice) ;
                                int price = (int) (orderPrice * 100);

                                String payUrl = "http://139.196.183.121:8081/payment/payForWapb2cPay?orderid=" + mOrderId
                                        + "&amount=" + price + "&payType=0";

                                CarApiClient.getPayOrderSn(Utils.getContext(), payUrl, new CallBack<PayOrderResponse>() {
                                    @Override
                                    public void onSuccess(PayOrderResponse result) {
                                        if (result.getResponseCode() == 2000) {;
                                            MainRouter.gotoWebHtmlActivity(DODetailActivity.this,"支付",result.getData());
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
        dto.setUsrId(MainRouter.getRASUserID());
        Intent intent = getIntent();
        mOrderId = intent.getStringExtra(JxGlobal.putExtra.common_extra);
        dto.setOrderId(mOrderId);
        String time = "1488253689";
        try {
            time = StringUtils.getTimeToStr();
        } catch (Exception e) {

        }
        dto.setTime(time);
        showDialogLoading();
        CarApiClient.getDaiJiaOrderDetail(Utils.getContext(), dto, new CallBack<DaiJiaOrderDetailResponse>() {
            @Override
            public void onSuccess(DaiJiaOrderDetailResponse result) {
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
        mDrivingOrderLayout.setOnPullListener(new OnPullListener() {
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
                dto.setUsrId(MainRouter.getRASUserID());
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("time", time);
                hashMap.put("orderId", mOrderId);
                hashMap.put("usrId", MainRouter.getUserID());
                dto.setHash(HashUtils.getSignature(hashMap));
                CarApiClient.cancelDaiJiaOrderDetail(DODetailActivity.this, dto, new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
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