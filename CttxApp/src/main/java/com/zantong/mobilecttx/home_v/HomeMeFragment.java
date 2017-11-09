package com.zantong.mobilecttx.home_v;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.ctcyh.router.base.JxBaseRefreshFragment;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.card.activity.UnblockedCardActivity;
import com.zantong.mobilecttx.common.activity.CommonProblemActivity;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.home_p.HomeMeFtyPresenter;
import com.zantong.mobilecttx.home_p.IHomeMeFtyContract;
import com.zantong.mobilecttx.order.activity.MyOrderActivity;
import com.zantong.mobilecttx.order.bean.CouponFragmentBean;
import com.zantong.mobilecttx.order.bean.CouponFragmentLBean;
import com.zantong.mobilecttx.order.bean.CouponFragmentResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.user.activity.AboutActivity;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.activity.SettingActivity;
import com.zantong.mobilecttx.user.activity.UserInfoUpdate;
import com.zantong.mobilecttx.user.bean.MessageCountBean;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationHistoryAcitvity;

import java.io.File;
import java.util.List;

import cn.qqtheme.framework.util.AppUtils;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.image.ImageLoadUtils;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

/**
 * 新个人页面
 */
public class HomeMeFragment extends JxBaseRefreshFragment
        implements View.OnClickListener, IHomeMeFtyContract.IHomeMeFtyView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /**
     * 个人中心
     */
    private TextView mTvTitle;
    private ImageView mImgSetting;
    private ImageView mImgHead;
    /**
     * 未登录
     */
    private TextView mTvLogin;
    private TextView mTvToolbar;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppbar;
    /**
     * 1张牡丹畅通卡
     */
    private TextView mTvCard;
    /**
     * 1辆车
     */
    private TextView mTvCar;
    /**
     * 1张优惠劵
     */
    private TextView mTvCoupon;
    private RelativeLayout mLayOrder;
    private RelativeLayout mLayDriverOrder;
    private RelativeLayout mLayQuery;
    private RelativeLayout mLayMsg;
    private ImageView mImgMsg;
    private RelativeLayout mLayRecommend;
    private RelativeLayout mLayProblem;
    private RelativeLayout mLayService;
    private RelativeLayout mAboutUs;
    /**
     * 版本更新
     */
    private TextView mTvUpdateTitle;
    private TextView mTvUpdate;
    private ImageView mImgUpdate;
    private RelativeLayout mLayUpdate;
    private RelativeLayout mAboutAdvertising;
    /**
     * mPresenter
     */
    private IHomeMeFtyContract.IHomeMeFtyPresenter mPresenter;
    private HomeMainActivity.MessageListener mHomeListener;

    public static HomeMeFragment newInstance() {
        return new HomeMeFragment();
    }

    public static HomeMeFragment newInstance(String param1, String param2) {
        HomeMeFragment fragment = new HomeMeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    protected boolean isRefresh() {
        return false;
    }

    @Override
    protected int initFragmentView() {
        return R.layout.fragment_home_me;
    }

    @Override
    protected void bindFragmentView(View view) {
        initView(view);

        HomeMeFtyPresenter mPresenter = new HomeMeFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

    @Override
    protected void onRefreshData() {}

    @Override
    protected void onLoadMoreData() {}

    @Override
    public void setPresenter(IHomeMeFtyContract.IHomeMeFtyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstDataVisible() {
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        int appCode = AppUtils.getAppVersionCode();
        int mVersionCode = appCode;
        String mVersionName = AppUtils.getAppVersionName();
        if (upgradeInfo != null) {
            mVersionCode = upgradeInfo.versionCode;
            mVersionName = upgradeInfo.versionName;
        }
        mTvUpdate.setText(appCode >= mVersionCode
                ? "当前已为最新版本" : "请更新最新版本v" + mVersionName);

        if (appCode < mVersionCode) {
            Drawable nav_up = getResources().getDrawable(R.mipmap.icon_dot_sel);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            mTvUpdateTitle.setCompoundDrawables(null, null, nav_up, null);
        }
    }

    /**
     * 会多次刷新数据 ^3^
     */
    @Override
    public void onResume() {
        super.onResume();

        initDataRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 是否隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private void initDataRefresh() {
        if (mPresenter != null && !TextUtils.isEmpty(mPresenter.initUserPhone())) {
            mPresenter.getDriverCoach();
        } else {
            mLayDriverOrder.setVisibility(View.GONE);
        }

        if (mPresenter != null && MainRouter.isUserLogin()) {
            mPresenter.getCouponCount();
            mPresenter.getUnReadMsgCount();
        }

        //畅通卡
        boolean isUnBound = TextUtils.isEmpty(MainRouter.getUserFilenum());

        mTvCard.setText(isUnBound ? "未绑定牡丹畅通卡" : "已绑定牡丹畅通卡");
        mTvCard.setTextColor(isUnBound
                ? getResources().getColor(R.color.colorTvGray_b2)
                : getResources().getColor(R.color.colorTvBlack_4d));

        //车辆
        StringBuffer stringBuffer = new StringBuffer();
        if (LoginData.getInstance().mCarNum == 0)
            stringBuffer.append("<font color=\"#b3b3b3\">");
        else
            stringBuffer.append("<font color=\"#f3362b\">");
        stringBuffer.append(LoginData.getInstance().mCarNum);
        stringBuffer.append("</font>");
        stringBuffer.append("&#160;");
        stringBuffer.append("车辆");
        mTvCar.setText(Html.fromHtml(stringBuffer.toString()));

        //优惠劵
        StringBuffer buffer = new StringBuffer();
        buffer.append("<font color=\"#b3b3b3\">");
        buffer.append(0);
        buffer.append("</font>");
        buffer.append("&#160;");
        buffer.append("张优惠券");
        mTvCoupon.setText(Html.fromHtml(buffer.toString()));

        File file = getHeadImageFile(mImgHead);
        if (file == null && MainRouter.isUserLogin()) {
            ImageLoadUtils.loadHead(MainRouter.getUserPortrait(), mImgHead);
        }

        String phone = MainRouter.getUserPhoenum();
        if (!Tools.isStrEmpty(MainRouter.getUserNickname())) {
            mTvLogin.setText(MainRouter.getUserNickname());
        } else if (!TextUtils.isEmpty(phone) && phone.length() >= 7) {
            mTvLogin.setText(phone.substring(7));
        } else {
            mTvCard.setText("未绑定牡丹畅通卡");
            mTvCard.setTextColor(getResources().getColor(R.color.colorTvGray_b2));

            mImgHead.setImageResource(R.mipmap.portrait);
            mTvLogin.setText("您还未登录");
        }

        mTvToolbar.setText(mTvLogin.getText().toString());
    }

    /**
     * 头像
     */
    private File getHeadImageFile(ImageView userImage) {
        String ImgPath = FileUtils.photoImagePath(Utils.getContext(), FileUtils.CROP_DIR);

        File mCropFile = new File(ImgPath);
        if (!mCropFile.exists()) {
            return null;
        }
        Uri outputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            outputUri = getUriForFileByN(mCropFile);
        } else {
            outputUri = Uri.fromFile(mCropFile);
        }

        Bitmap bitmap = FileUtils.decodeUriAsBitmap(outputUri, Utils.getContext());
        if (bitmap != null) userImage.setImageBitmap(bitmap);

        return mCropFile;
    }

    private Uri getUriForFileByN(File mCameraFile) {
        try {
            return FileProvider.getUriForFile(Utils.getContext(),
                    getApplication().getPackageName() + ".fileprovider", mCameraFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.fromFile(mCameraFile);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
    }


    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgSetting = (ImageView) view.findViewById(R.id.img_setting);
        mImgSetting.setOnClickListener(this);
        mImgHead = (ImageView) view.findViewById(R.id.img_head);
        mImgHead.setOnClickListener(this);
        mTvLogin = (TextView) view.findViewById(R.id.tv_login);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mTvToolbar = (TextView) view.findViewById(R.id.tv_toolbar);

        mCollapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        mAppbar = (AppBarLayout) view.findViewById(R.id.appbar);
        mTvCard = (TextView) view.findViewById(R.id.tv_card);
        mTvCard.setOnClickListener(this);
        mTvCar = (TextView) view.findViewById(R.id.tv_car);
        mTvCar.setOnClickListener(this);
        mTvCoupon = (TextView) view.findViewById(R.id.tv_coupon);
        mTvCoupon.setOnClickListener(this);
        mLayOrder = (RelativeLayout) view.findViewById(R.id.lay_order);
        mLayOrder.setOnClickListener(this);
        mLayDriverOrder = (RelativeLayout) view.findViewById(R.id.lay_driver_order);
        mLayDriverOrder.setOnClickListener(this);
        mLayQuery = (RelativeLayout) view.findViewById(R.id.lay_query);
        mLayQuery.setOnClickListener(this);
        mLayMsg = (RelativeLayout) view.findViewById(R.id.lay_msg);
        mLayMsg.setOnClickListener(this);
        mImgMsg = (ImageView) view.findViewById(R.id.img_msg);
        mLayRecommend = (RelativeLayout) view.findViewById(R.id.lay_recommend);
        mLayRecommend.setOnClickListener(this);
        mLayProblem = (RelativeLayout) view.findViewById(R.id.lay_problem);
        mLayProblem.setOnClickListener(this);
        mLayService = (RelativeLayout) view.findViewById(R.id.lay_service);
        mLayService.setOnClickListener(this);
        mAboutUs = (RelativeLayout) view.findViewById(R.id.about_us);
        mAboutUs.setOnClickListener(this);
        mTvUpdateTitle = (TextView) view.findViewById(R.id.tv_update_title);
        mTvUpdate = (TextView) view.findViewById(R.id.tv_update);
        mImgUpdate = (ImageView) view.findViewById(R.id.img_update);
        mLayUpdate = (RelativeLayout) view.findViewById(R.id.lay_update);
        mLayUpdate.setOnClickListener(this);
        mAboutAdvertising = (RelativeLayout) view.findViewById(R.id.about_advertising);
        mAboutAdvertising.setOnClickListener(this);

        //动态调整标题透明度
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRangle = appBarLayout.getTotalScrollRange();
                //初始verticalOffset为0，不能参与计算。
                if (verticalOffset == 0) {
                    mTvToolbar.setAlpha(0.0f);
                } else {//保留一位小数
                    float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
                    mTvToolbar.setAlpha(alpha);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_setting://设置
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(26));
                Act.getInstance().gotoIntentLogin(getActivity(), SettingActivity.class);
                break;
            case R.id.img_head:
                Act.getInstance().gotoIntentLogin(getActivity(), UserInfoUpdate.class);
                break;
            case R.id.lay_order://我的订单
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(30));
                Act.getInstance().gotoIntentLogin(getActivity(), MyOrderActivity.class);
                break;
            case R.id.lay_driver_order://司机订单
                MainRouter.gotoHtmlActivity(getActivity(),
                        "司机订单", BuildConfig.App_Url
                                ? "http://dev.liyingtong.com/h5/driver/index.html"
                                : "http://api2.liyingtong.com/h5/driver/index.html");
                break;
            case R.id.tv_card://我的畅通卡
                if (TextUtils.isEmpty(MainRouter.getUserFilenum()))
                    Act.getInstance().gotoIntentLogin(getActivity(), UnblockedCardActivity.class);
                else
                    Act.getInstance().gotoIntentLogin(getActivity(), MyCardActivity.class);
                break;
            case R.id.tv_car:
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(28));
                Act.getInstance().gotoIntentLogin(getActivity(), ManageCarActivity.class);
                break;
            case R.id.tv_coupon:
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(27));
                MainRouter.gotoCouponActivity(getActivity());
                break;
            case R.id.lay_query://违章缴费查询
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(34));
                Act.getInstance().gotoIntentLogin(getActivity(), ViolationHistoryAcitvity.class);
                break;
            case R.id.lay_msg://消息
                MobclickAgent.onEvent(Utils.getContext(), Config.getUMengID(24));
                Act.getInstance().gotoIntentLogin(getActivity(), MegTypeActivity.class);
                break;
            case R.id.lay_recommend://推荐送豪礼
                Act.getInstance().gotoIntentLogin(getActivity(), ShareParentActivity.class);
                break;
            case R.id.lay_problem://常见问题
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(32));
                Act.getInstance().gotoIntent(getActivity(), CommonProblemActivity.class);
                break;
            case R.id.lay_service://联系客服
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(33));
                Act.getInstance().gotoIntent(getActivity(), ProblemFeedbackActivity.class);
                break;
            case R.id.about_us://关于我们
                Act.getInstance().gotoIntent(getActivity(), AboutActivity.class);
                break;
            case R.id.lay_update:
                Beta.checkUpgrade();
                break;
            case R.id.about_advertising://隐私说明
                MainRouter.gotoHtmlActivity(getActivity(),
                        "隐私声明", "file:///android_asset/bindcard_agreement.html");
                break;
            default:
                break;
        }
    }

    /**
     * 优惠券
     */
    @Override
    public void getCouponCountSucceed(CouponFragmentResponse result) {
        CouponFragmentLBean resultData = result.getData();
        StringBuffer stringBuffer = new StringBuffer();
        List<CouponFragmentBean> couponList = null;

        if (resultData == null) {
            stringBuffer.append("<font color=\"#b3b3b3\">");
        } else {
            couponList = resultData.getCouponList();
            if (couponList == null || couponList.size() == 0)
                stringBuffer.append("<font color=\"#b3b3b3\">");
            else
                stringBuffer.append("<font color=\"#f3362b\">");
        }

        stringBuffer.append(couponList != null ? couponList.size() : 0);

        stringBuffer.append("</font>");
        stringBuffer.append("&#160;");
        stringBuffer.append("张优惠券");
        mTvCoupon.setText(Html.fromHtml(stringBuffer.toString()));
    }

    @Override
    public void getCouponCountError(String responseDesc) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<font color=\"#b3b3b3\">");
        stringBuffer.append(0);
        stringBuffer.append("</font>");
        stringBuffer.append("&#160;");
        stringBuffer.append("张优惠券");
        mTvCoupon.setText(Html.fromHtml(stringBuffer.toString()));

        ToastUtils.toastShort(responseDesc);
    }

    /**
     * 未读消息
     */
    @Override
    public void countMessageDetailSucceed(MessageCountResponse result) {
        MessageCountBean resultData = result.getData();
        if (mImgMsg != null) mImgMsg.setVisibility(
                resultData != null && resultData.getCount() > 0 ? View.VISIBLE : View.GONE);

        if (mHomeListener != null)
            mHomeListener.setTipOfNumber(2, resultData != null ? resultData.getCount() : 0);
    }

    @Override
    public void countMessageDetailError(String responseDesc) {
        if (mImgMsg != null) mImgMsg.setVisibility(View.GONE);
        ToastUtils.toastShort(responseDesc);
    }

    /**
     * 13.判断是否为司机 错误
     */
    @Override
    public void driverCoachError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void driverCoachSucceed(DriverCoachResponse result) {
        mLayDriverOrder.setVisibility(result.getData() ? View.VISIBLE : View.GONE);
    }

    public void setMessageListener(HomeMainActivity.MessageListener messageListener) {
        mHomeListener = messageListener;
    }
}