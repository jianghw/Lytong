package com.tzly.ctcyh.pay.pay_type_v;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.base.AbstractBaseFragment;
import com.tzly.ctcyh.router.custom.animation.PropertyUtils;
import com.tzly.ctcyh.router.custom.dialog.DialogUtils;
import com.tzly.ctcyh.router.custom.dialog.WeiXinDialogFragment;
import com.tzly.ctcyh.router.util.Utils;

import static com.tzly.ctcyh.pay.pay_type_v.PaySucceedFragment.WechatBroadcastReceiver.WECHAT_ACTION;


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
    public IPaySucceedContract.IPaySucceedPresenter presenter;
    /**
     * 图片地址
     */
    private String imageUrl;
    private ImageView mTvImg;
    private WechatBroadcastReceiver mBroadcastReceiver;

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

        //推送广播核心部分代码：
        mBroadcastReceiver = new WechatBroadcastReceiver();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction(WECHAT_ACTION);
        getActivity().registerReceiver(mBroadcastReceiver, itFilter);

        PaySucceedPresenter mPresenter = new PaySucceedPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);

        PropertyUtils.shakeView(mBtnShape, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        getActivity().unregisterReceiver(mBroadcastReceiver);
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

            String goodsTitle = getGoodsType().equals("2") ? "违章缴费"
                    : getGoodsType().equals("6") ? "年检"
                    : getGoodsType().equals("15") ? "加油卡" : "未知商品";

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
        String host = BuildConfig.isDeta ? "h5dev" : "h5";
        //TODO 合成图片
        String codeUrl = getGoodsType().equals("2")
                ? "http://" + host + ".liyingtong.com/share/weizhang.html?userId=" + PayRouter.getUserID() + "&type=" + 2 + "payStatus=1&source=1"
                : getGoodsType().equals("6")
                ? "http://" + host + ".liyingtong.com/share/nianjian.html?userId=" + PayRouter.getUserID() + "&type=" + 6 + "payStatus=1&source=1"
                : getGoodsType().equals("15")
                ? "http://admin.liyingtong.com/wxproxy.php?appid=wx6f090722facc7bf1&redirect_uri=http%3a%2f%2f"+host+".liyingtong.com%2fwechat%2fbuyCard.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"
                : "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";

        Bitmap logio = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_global_app);
        if (presenter != null) presenter.mergeBitmap(bitmap, codeUrl, logio);
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

    @Override
    public void mergeSucceed(Bitmap bitmap) {
        WeiXinDialogFragment fragment = WeiXinDialogFragment.newInstance(bitmap);
        DialogUtils.showDialog(getActivity(), fragment, "wechat_dialog");
    }

    class WechatBroadcastReceiver extends BroadcastReceiver {

        public final static String WECHAT_ACTION = "com.tzly.ctcyh.pay.pay_type_v.PaySucceedActivity";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WECHAT_ACTION)) {
                if (presenter != null) presenter.shareUser();
            }
        }
    }
}
