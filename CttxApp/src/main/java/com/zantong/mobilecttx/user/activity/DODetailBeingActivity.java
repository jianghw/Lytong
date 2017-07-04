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
import com.zantong.mobilecttx.daijia.dto.DaiJiaOrderDetailDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.HashUtils;
import com.zantong.mobilecttx.utils.PullToRefreshLayout;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.daijia.activity.DrivingOrderActivity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 代驾派单中详情页面
 * Created by zhoujie on 2017/2/21.
 */

public class DODetailBeingActivity extends BaseMvpActivity<IBaseView, HelpPresenter> {

    @Bind(R.id.order_detail_date_being)
    TextView mDate;
    @Bind(R.id.order_detail_address_being)
    TextView mAddress;
    @Bind(R.id.order_detail_clean_being)
    TextView mClean;
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


    @Override
    public void initView() {
        setTitleText("当前代驾订单");
    }

    @Override
    public void initData() {
        initRefreshView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
    }

    @OnClick(R.id.order_detail_clean_being)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_detail_clean_being:
                cleanOrderDialog();
                break;
        }
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
                Intent intent = getIntent();
                String orderId = intent.getStringExtra("param");
                dto.setOrderId(orderId);
                dto.setUsrId(RSAUtils.strByEncryptionLiYing(PublicData.getInstance().userID, true));
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("time",time);
                hashMap.put("orderId",orderId);
                hashMap.put("usrId",PublicData.getInstance().userID);
                dto.setHash(HashUtils.getSignature(hashMap));
                CarApiClient.cancelDaiJiaOrderDetail(DODetailBeingActivity.this, dto, new CallBack<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        if (result.getResponseCode() == 2000) {
                            mState.setText("已取消");
                            mState.setTextColor(R.color.gray_cc);
                            mClean.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
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
        String time = "1488253689";
        try {
            time = StringUtils.getTimeToStr();
        } catch (Exception e) {

        }
        DaiJiaOrderDetailDTO dto = new DaiJiaOrderDetailDTO();
        Intent intent = getIntent();
        String orderId = intent.getStringExtra(Act.ACT_PARAM);
        dto.setOrderId(orderId);
        dto.setTime(time);
        dto.setUsrId(RSAUtils.strByEncryptionLiYing(PublicData.getInstance().userID, true));
        CarApiClient.getDaiJiaOrderDetail(this, dto, new CallBack<DaiJiaOrderDetailResult>() {
            @Override
            public void onSuccess(DaiJiaOrderDetailResult result) {
                mDrivingOrderLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (result.getResponseCode() == 2000) {
                    String state = result.getData().getOrderStatus();
                    mState.setText(state);
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
                        mState.setTextColor(R.color.gray_cc);
                    } else if (state.contains("已预约")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.red);
                    }else if (state.contains("支付完成")) {
                        mMileageLayout.setVisibility(View.VISIBLE);
                        mPriceLayout.setVisibility(View.VISIBLE);
                        mDriverLayout.setVisibility(View.VISIBLE);
                        mPrice.setText(result.getData().getAmount());
                        mMileage.setText(result.getData().getDistance());
                        mDriverPhone.setText(result.getData().getDriverMobile());
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.gray_cc);
                    }else if (state.contains("已取消")) {
                        mMileageLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mDriverLayout.setVisibility(View.GONE);
                        mClean.setVisibility(View.GONE);
                        mState.setTextColor(R.color.gray_cc);
                    }
                    mDate.setText(result.getData().getCreateTime());
                    mAddress.setText(result.getData().getAddress());
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
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

    @Override
    protected void baseGoBack() {
        Act.getInstance().gotoIntent(this, DrivingOrderActivity.class);
        finish();
    }
}