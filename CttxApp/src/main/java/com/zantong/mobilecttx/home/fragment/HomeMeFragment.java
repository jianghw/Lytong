package com.zantong.mobilecttx.home.fragment;

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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.car.activity.CarManageGroupActivity;
import com.zantong.mobilecttx.card.activity.CardHomeActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.common.activity.CommonProblemActivity;
import com.zantong.mobilecttx.home.activity.HomeMainActivity;
import com.zantong.mobilecttx.interf.IHomeMeFtyContract;
import com.zantong.mobilecttx.order.activity.OrderParentActivity;
import com.zantong.mobilecttx.presenter.home.HomeMeFtyPresenter;
import com.zantong.mobilecttx.share.activity.ShareParentActivity;
import com.zantong.mobilecttx.user.activity.AboutActivity;
import com.zantong.mobilecttx.user.activity.CouponActivity;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.activity.SettingActivity;
import com.zantong.mobilecttx.user.activity.UserInfoUpdate;
import com.zantong.mobilecttx.user.bean.CouponFragmentBean;
import com.zantong.mobilecttx.user.bean.CouponFragmentLBean;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageCountBean;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationHistoryAcitvity;

import java.io.File;
import java.util.List;

import cn.qqtheme.framework.util.AppUtils;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.ToastUtils;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

/**
 * 新个人页面
 */
public class HomeMeFragment extends BaseRefreshJxFragment
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
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_home_me;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);

        HomeMeFtyPresenter mPresenter = new HomeMeFtyPresenter(
                Injection.provideRepository(getActivity().getApplicationContext()), this);
    }

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
        if (mPresenter != null) mPresenter.getCouponCount();
        if (mPresenter != null) mPresenter.getUnReadMsgCount();

        //畅通卡
        boolean isUnBound = TextUtils.isEmpty(PublicData.getInstance().filenum);
//        StringBuffer sb = new StringBuffer();
//        if (isUnBound)
//            sb.append("<font color=\"#b3b3b3\">");
//        else
//            sb.append("<font color=\"#f3362b\">");
//        sb.append(isUnBound ? 0 : 1);
//        sb.append("</font>");
//        sb.append("&#160;");
//        sb.append("张牡丹畅通卡");
//        mTvCard.setText(Html.fromHtml(sb.toString()));
        mTvCard.setText(isUnBound ? "未绑定牡丹畅通卡" : "已绑定牡丹畅通卡");
        mTvCard.setTextColor(isUnBound
                ? getResources().getColor(R.color.colorTvGray_b2)
                : getResources().getColor(R.color.colorTvBlack_4d));

        //车辆
        StringBuffer stringBuffer = new StringBuffer();
        if (PublicData.getInstance().mCarNum == 0)
            stringBuffer.append("<font color=\"#b3b3b3\">");
        else
            stringBuffer.append("<font color=\"#f3362b\">");
        stringBuffer.append(PublicData.getInstance().mCarNum);
        stringBuffer.append("</font>");
        stringBuffer.append("&#160;");
        stringBuffer.append("车辆");
        mTvCar.setText(Html.fromHtml(stringBuffer.toString()));

        if (PublicData.getInstance().loginFlag) {
            LoginInfoBean.RspInfoBean infoBean = PublicData.getInstance().mLoginInfoBean;

            if (infoBean == null || TextUtils.isEmpty(infoBean.getPortrait())) {
                getHeadImageFile();
            } else {
                ImageLoader.getInstance().displayImage(
                        infoBean.getPortrait(),
                        mImgHead,
                        ImageOptions.getAvatarOptions()
                );
            }

            if (infoBean != null) {
                if (!Tools.isStrEmpty(infoBean.getNickname())) {
                    mTvLogin.setText(infoBean.getNickname());
                } else {
                    String phoenum = infoBean.getPhoenum();
                    if (!TextUtils.isEmpty(phoenum) && phoenum.length() >= 7)
                        mTvLogin.setText(infoBean.getPhoenum().substring(7));
                }
            }
        } else {
//            StringBuffer buffer = new StringBuffer();
//            buffer.append("<font color=\"#b3b3b3\">");
//            buffer.append(0);
//            buffer.append("</font>");
//            buffer.append("&#160;");
//            buffer.append("张牡丹畅通卡");
//            mTvCard.setText(Html.fromHtml(buffer.toString()));

            mTvCard.setText("未绑定牡丹畅通卡");
            mTvCard.setTextColor(getResources().getColor(R.color.colorTvGray_b2));

            mTvLogin.setText("您还未登录");
            mImgHead.setImageResource(R.mipmap.portrait);
        }
        mTvToolbar.setText(mTvLogin.getText().toString());
    }

    /**
     * 头像
     */
    private File getHeadImageFile() {
        String ImgPath = FileUtils.photoImagePath(getActivity().getApplicationContext(), FileUtils.CROP_DIR);

        File mCropFile = new File(ImgPath);
        if (!mCropFile.exists()) {
//            ToastUtils.toastShort("头像图片未生成或丢失");
            return null;
        }
        Uri outputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            outputUri = getUriForFileByN(mCropFile);
        } else {
            outputUri = Uri.fromFile(mCropFile);
        }

        Bitmap bitmap = FileUtils.decodeUriAsBitmap(outputUri, getActivity().getApplicationContext());
        if (bitmap != null) mImgHead.setImageBitmap(bitmap);

        return mCropFile;
    }

    private Uri getUriForFileByN(File mCameraFile) {
        try {
            return FileProvider.getUriForFile(getActivity().getApplicationContext(),
                    getApplication().getPackageName() + ".fileprovider", mCameraFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.fromFile(mCameraFile);
        }
    }

    @Override
    protected void DestroyViewAndThing() {
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
                Act.getInstance().lauchIntentToLogin(getActivity(), SettingActivity.class);
                break;
            case R.id.img_head:
                Act.getInstance().lauchIntentToLogin(getActivity(), UserInfoUpdate.class);
                break;
            case R.id.lay_order://我的订单
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(30));
                Act.getInstance().lauchIntentToLogin(getActivity(), OrderParentActivity.class);
                break;
            case R.id.tv_card://我的畅通卡
                if (Tools.isStrEmpty(PublicData.getInstance().filenum))
                    Act.getInstance().lauchIntentToLogin(getActivity(), CardHomeActivity.class);
                else
                    Act.getInstance().lauchIntentToLogin(getActivity(), MyCardActivity.class);
                break;
            case R.id.tv_car:
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(28));
                Act.getInstance().gotoIntent(getActivity(), CarManageGroupActivity.class);
                break;
            case R.id.tv_coupon:
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(27));
                Act.getInstance().lauchIntentToLogin(getActivity(), CouponActivity.class);
                break;
            case R.id.lay_query://违章缴费查询
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(34));
                Act.getInstance().lauchIntentToLogin(getActivity(), ViolationHistoryAcitvity.class);
                break;
            case R.id.lay_msg://消息
                MobclickAgent.onEvent(ContextUtils.getContext(), Config.getUMengID(24));
                Act.getInstance().lauchIntentToLogin(getActivity(), MegTypeActivity.class);
                break;
            case R.id.lay_recommend://推荐送豪礼
                Act.getInstance().lauchIntentToLogin(getActivity(), ShareParentActivity.class);
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
            case R.id.about_advertising:
                PublicData.getInstance().webviewUrl = "file:///android_asset/bindcard_agreement.html";
                PublicData.getInstance().webviewTitle = "隐私声明";
                Act.getInstance().gotoIntent(getActivity(), BrowserActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 优惠券
     */
    @Override
    public void getCouponCountSucceed(CouponFragmentResult result) {
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
    public void countMessageDetailSucceed(MessageCountResult result) {
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

    public void setMessageListener(HomeMainActivity.MessageListener messageListener) {
        mHomeListener = messageListener;
    }
}
