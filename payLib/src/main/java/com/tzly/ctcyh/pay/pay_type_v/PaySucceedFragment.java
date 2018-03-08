package com.tzly.ctcyh.pay.pay_type_v;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tzly.ctcyh.java.response.coupon.CouponInfoResponse;
import com.tzly.ctcyh.pay.R;
import com.tzly.ctcyh.pay.data_m.InjectionRepository;
import com.tzly.ctcyh.pay.global.PayGlobal;
import com.tzly.ctcyh.pay.pay_type_p.IPaySucceedContract;
import com.tzly.ctcyh.pay.pay_type_p.PaySucceedPresenter;
import com.tzly.ctcyh.pay.router.PayRouter;
import com.tzly.ctcyh.router.base.AbstractBaseFragment;
import com.tzly.ctcyh.router.custom.dialog.DialogUtils;
import com.tzly.ctcyh.router.custom.dialog.WeiXinDialogFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.animation.PropertyUtils;

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
    /**
     * 图片地址
     */
    private String imageUrl;

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

        PropertyUtils.shakeView(mBtnShape, 1000);
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        if (vId == R.id.btn_shape) {
            toShareBitmap();
        } else if (vId == R.id.tv_home) {//关闭页面
            PayRouter.gotoMainActivity(getContext(), 1);
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

            String goodsTitle = getGoodsType().equals("2") ?
                    "违章缴费" : getGoodsType().equals("6") ?
                    "年检" : "未知商品";
            String format = getResources().getString(R.string.pay_succeed_title);
            String title = String.format(format, goodsTitle);
            mTvTitle.setText(title);

            String name = bean.getCouponName();
            mTvCoupon.setText(name);

            imageUrl = infoResponse.getData().getImage();
        }
    }

    /**
     * 下载分享的图片
     */
    private void toShareBitmap() {
        ImageLoader.getInstance().loadImage(imageUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                toastShort("加载图片失败,请重新试一试");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                bitmapFactory(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    private void bitmapFactory(Bitmap bitmap) {
        //TODO 合成图片
        WeiXinDialogFragment fragment = WeiXinDialogFragment.newInstance(bitmap);
        DialogUtils.showDialog(getActivity(), fragment, "wechat_dialog");
    }

    public static PaySucceedFragment newInstance(String goodsType) {
        PaySucceedFragment f = new PaySucceedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PayGlobal.putExtra.web_goods_type_extra, goodsType);
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
    public String getGoodsType() {
        return getArguments().getString(PayGlobal.putExtra.web_goods_type_extra);
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
