package com.tzly.ctcyh.pay.pay_type_v;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.pay_type_p.IPaySucceedContract;
import com.tzly.ctcyh.pay.pay_type_p.PaySucceedPresenter;
import com.tzly.ctcyh.router.base.AbstractBaseFragment;
import com.tzly.ctcyh.router.custom.dialog.WeiXinDialogFragment;
import com.tzly.ctcyh.router.util.Utils;

/**
 * 支付成功
 */
public class PaySucceedFragment extends AbstractBaseFragment
        implements View.OnClickListener, IPaySucceedContract.IPaySucceedView {


    /**
     * pay_succeed_title
     */
    private TextView mTvTitle;
    private TextView mTvCoupon;
    private TextView mTvTip;
    private RelativeLayout mLayoutCenter;
    /**
     * 立即分享
     */
    private Button mBtnShape;
    private TextView mTvHome;
    private IPaySucceedContract.IPaySucceedPresenter presenter;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int contentView() {
        return R.layout.pay_fragment_pay_succeed;
    }

    @Override
    protected void bindContent(View contentView) {
        initView(contentView);

        PaySucceedPresenter mPresenter = new PaySucceedPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        if (vId == R.id.btn_shape) {
            WeiXinDialogFragment fragment = WeiXinDialogFragment.newInstance();
            fragment.showDialog(fragment);
        } else if (vId == R.id.tv_home) {//关闭页面

        }
    }

    @Override
    protected void loadingFirstData() {
        if (presenter != null) presenter.getCouponInfo();
    }

    @Override
    protected void clickRefreshData() {
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof CouponInfoResponse) {
            CouponInfoResponse infoResponse = (CouponInfoResponse) response;
            CouponInfoResponse.DataBean.CouponBean bean = infoResponse.getData().getCoupon();

            String format = getResources().getString(R.string.pay_succeed_title);
            String title = String.format(format, bean.getCouponContent());
            mTvTitle.setText(title);

            String name = bean.getCouponName();
            mTvCoupon.setText(name);

        }
    }

    public static PaySucceedFragment newInstance(String extraOrder) {
        PaySucceedFragment f = new PaySucceedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_orderId_extra, extraOrder);
        f.setArguments(bundle);
        return f;
    }


    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mTvTip = (TextView) view.findViewById(R.id.tv_tip);
        mLayoutCenter = (RelativeLayout) view.findViewById(R.id.layout_center);
        mBtnShape = (Button) view.findViewById(R.id.btn_shape);
        mBtnShape.setOnClickListener(this);
        mTvHome = (TextView) view.findViewById(R.id.tv_home);
        mTvHome.setOnClickListener(this);
    }

    @Override
    public void setPresenter(IPaySucceedContract.IPaySucceedPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String getOrderId() {
        return getArguments().getString(PayGlobal.putExtra.web_orderId_extra);
    }

    @Override
    public void couponInfoError(String message) {
        toastShort(message);
    }

    @Override
    public void couponInfoSucceed(CouponInfoResponse response) {
        responseData(response);
    }
}
