package com.zantong.mobilecttx.home_v;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jianghw.multi.state.layout.MultiState;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.util.AppUtils;
import com.tzly.ctcyh.router.util.FileUtils;
import com.tzly.ctcyh.router.util.SPUtils;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.bean.ValidCountBean;
import com.zantong.mobilecttx.base.bean.ValidCountResponse;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.card.activity.UnblockedCardActivity;
import com.zantong.mobilecttx.common.activity.CommonProblemActivity;
import com.zantong.mobilecttx.home.bean.DriverCoachResponse;
import com.zantong.mobilecttx.home_p.HomeMeFtyPresenter;
import com.zantong.mobilecttx.home_p.IHomeMeFtyContract;
import com.zantong.mobilecttx.order.activity.MyOrderActivity;
import com.zantong.mobilecttx.payment_v.LicenseGradeActivity;
import com.zantong.mobilecttx.payment_v.PaymentActivity;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.share_v.ShareParentActivity;
import com.zantong.mobilecttx.user.activity.AboutActivity;
import com.zantong.mobilecttx.msg_v.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.activity.SettingActivity;
import com.zantong.mobilecttx.user.activity.UserInfoUpdate;
import com.zantong.mobilecttx.user.bean.MessageCountBean;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;

import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.io.File;

import static com.tencent.bugly.beta.tinker.TinkerManager.getApplication;

/**
 * 新个人页面
 */
public class HomeMeFragment extends RefreshFragment
        implements View.OnClickListener, IHomeMeFtyContract.IHomeMeFtyView {
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public static HomeMeFragment newInstance() {
        return new HomeMeFragment();
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int fragmentView() {
        return R.layout.fragment_home_me;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        HomeMeFtyPresenter mPresenter = new HomeMeFtyPresenter(
                Injection.provideRepository(getContext()), this);

        versionUpgrade();
    }

    /**
     * 版本检查
     */
    private void versionUpgrade() {
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

    @Override
    public void setPresenter(IHomeMeFtyContract.IHomeMeFtyPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 数据加载
     */
    @Override
    protected void loadingFirstData() {
    }

    /**
     * 手动刷新动作
     */
    protected void onRefreshData() {
        initDataRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();

        initDataRefresh();
    }

    /**
     * 啊不用此方法 不能出现未加载状态页面
     */
    @Override
    protected void responseData(Object response) {
    }

    private void initDataRefresh() {
        if (mPresenter != null && !TextUtils.isEmpty(mPresenter.initUserPhone())) {
            mPresenter.getDriverCoach();
        } else {
            if (mLayDriverOrder != null)
                mLayDriverOrder.setVisibility(View.GONE);
        }

        if (mPresenter != null && MainRouter.isUserLogin()) {
            mPresenter.getValidCount();
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
        String nickName = MainRouter.getUserNickname();
        if (!TextUtils.isEmpty(nickName)) {
            mTvLogin.setText(nickName);
        } else if (!TextUtils.isEmpty(phone) && phone.length() >= 7) {
            mTvLogin.setText(phone.substring(7));
        } else {
            mTvCard.setText("未绑定牡丹畅通卡");
            mTvCard.setTextColor(getResources().getColor(R.color.colorTvGray_b2));

            mImgHead.setImageResource(R.mipmap.portrait);
            mTvLogin.setText("您还未登录");
        }
    }

    /**
     * 是否隐藏
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
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

    public void initView(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mImgSetting = (ImageView) view.findViewById(R.id.img_setting);
        mImgSetting.setOnClickListener(this);
        mImgHead = (ImageView) view.findViewById(R.id.img_head);
        mImgHead.setOnClickListener(this);
        mTvLogin = (TextView) view.findViewById(R.id.tv_login);

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
        //        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
        //            @Override
        //            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //                int scrollRangle = appBarLayout.getTotalScrollRange();
        //                //初始verticalOffset为0，不能参与计算。
        //                if (verticalOffset == 0) {
        //                    mTvToolbar.setAlpha(0.0f);
        //                } else {//保留一位小数
        //                    float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
        //                    mTvToolbar.setAlpha(alpha);
        //                }
        //            }
        //        });
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
                MainRouter.gotoCouponStatusActivity(getActivity());
                break;
            case R.id.lay_query://违章缴费查询
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(34));
                licenseCheckGrade();
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

    protected void licenseCheckGrade() {
//        LicenseFileNumDTO bean = SPUtils.getInstance().getLicenseFileNumDTO();
        String grade = SPUtils.instance().getString(SPUtils.USER_GRADE);

        if (!TextUtils.isEmpty(grade) || (!TextUtils.isEmpty(MainRouter.getUserFilenum())
                && !TextUtils.isEmpty(MainRouter.getUserGetdate()))) {

            LicenseFileNumDTO fromJson = new Gson().fromJson(grade, LicenseFileNumDTO.class);

        }

//        if (!TextUtils.isEmpty(MainRouter.getUserFilenum())
//                && !TextUtils.isEmpty(MainRouter.getUserGetdate()) || bean != null) {
//
//            LicenseFileNumDTO loginBean = new LicenseFileNumDTO();
//            loginBean.setFilenum(MainRouter.getUserFilenum());
//            loginBean.setStrtdt(MainRouter.getUserGetdate());
//
//            Intent intent = new Intent(getActivity(), PaymentActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(LicenseGradeActivity.KEY_BUNDLE, bean != null ? bean : loginBean);
//            intent.putExtras(bundle);
//            startActivity(intent);
//        } else {
//            MainRouter.gotoLicenseGradeActivity(getActivity());
//        }
    }

    /**
     * 未读消息
     */
    public void unMessageCount(int position, int number) {
        //未读消息
        if (mImgMsg != null) mImgMsg.setVisibility(number <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * 未读消息
     */
    @Override
    public void countMessageDetailSucceed(MessageCountResponse result) {
        MessageCountBean resultData = result.getData();
        if (mImgMsg != null) mImgMsg.setVisibility(
                resultData != null && resultData.getCount() > 0 ? View.VISIBLE : View.GONE);
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


    /**
     * 优惠券
     */
    @Override
    public void validCountError(String message) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<font color=\"#b3b3b3\">");
        stringBuffer.append(0);
        stringBuffer.append("</font>");
        stringBuffer.append("&#160;");
        stringBuffer.append("张可用券");
        mTvCoupon.setText(Html.fromHtml(stringBuffer.toString()));

        ToastUtils.toastShort(message);
    }

    @Override
    public void validCountSucceed(ValidCountResponse result) {
        ValidCountBean resultData = result.getData();
        StringBuffer stringBuffer = new StringBuffer();

        int count = 0;
        if (resultData == null) {
            stringBuffer.append("<font color=\"#b3b3b3\">");
        } else {
            count = resultData.getValidCount();
            if (count == 0)
                stringBuffer.append("<font color=\"#b3b3b3\">");
            else
                stringBuffer.append("<font color=\"#f3362b\">");
        }

        stringBuffer.append(count);

        stringBuffer.append("</font>");
        stringBuffer.append("&#160;");
        stringBuffer.append("张可用券");
        mTvCoupon.setText(Html.fromHtml(stringBuffer.toString()));
    }
}
