package com.zantong.mobilecttx.home.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;
import com.zantong.mobilecttx.car.activity.CarManageGroupActivity;
import com.zantong.mobilecttx.card.activity.CardHomeActivity;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.common.activity.CommonProblemActivity;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.activity.AboutActivity;
import com.zantong.mobilecttx.user.activity.CouponActivity;
import com.zantong.mobilecttx.user.activity.GetBonusActivity;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.OrderActivity;
import com.zantong.mobilecttx.user.activity.OrderRechargeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.activity.UserInfoUpdate;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.ViolationHistoryAcitvity;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.AppUtils;
import cn.qqtheme.framework.util.ToastUtils;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 个人页面
 */
public class MeFragment extends BaseRefreshJxFragment {

    @Bind(R.id.user_head_image)
    ImageView userHeadImage;

    @Bind(R.id.user_name_text)
    TextView userNameText;

    @Bind(R.id.mine_changtong_notice_text)
    TextView mine_changtong_notice_text;

    @Bind(R.id.mine_manage_vechilse_notice)
    TextView mine_manage_vechilse_notice;

    @Bind(R.id.mine_card_status)
    TextView mCardStatus;
    //消息数量
    @Bind(R.id.mine_meg_count)
    TextView mMegCount;
    //优惠券
    @Bind(R.id.mine_youhuijuan_tv)
    TextView mYouHui;

    @Bind(R.id.tv_update_title)
    TextView mTvUpdateTitle;
    @Bind(R.id.tv_update)
    TextView mTvUpdate;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
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

    @Override
    protected void onRefreshData() {
        onFirstDataVisible();
    }

    /**
     * 初始化布局
     */
    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.mine_view;
    }

    /**
     * 使用ButterKnife 实现子类的使用
     */
    protected boolean isNeedKnife() {
        return true;
    }

    @Override
    protected void initFragmentView(View view) {

    }

    @Override
    protected void onFirstDataVisible() {
        initDefaultData();

        updateVersion();
    }

    /**
     * 更新比价 注会终止方法执行
     */
    private boolean updateVersion() {
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

        if (appCode >= mVersionCode) return true;
        Drawable nav_up = getResources().getDrawable(R.mipmap.icon_dot_sel);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        mTvUpdateTitle.setCompoundDrawables(null, null, nav_up, null);
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void initDefaultData() {
        mCardStatus.setText(TextUtils.isEmpty(PublicData.getInstance().filenum) ? "未绑卡" : "已绑卡");
        mine_manage_vechilse_notice.setText(PublicData.getInstance().mCarNum + "辆车");

        if (PublicData.getInstance().loginFlag) {
            LoginInfoBean.RspInfoBean mLoginInfoBean = PublicData.getInstance().mLoginInfoBean;
            if (!Tools.isStrEmpty(mLoginInfoBean.getNickname())) {
                userNameText.setText(mLoginInfoBean.getNickname());
            } else {
                userNameText.setText(mLoginInfoBean.getPhoenum().substring(7));
            }

            ImageLoader.getInstance().displayImage(
                    PublicData.getInstance().mLoginInfoBean.getPortrait(),
                    userHeadImage,
                    ImageOptions.getAvatarOptions()
            );
        } else {
            userNameText.setText("您还未登录");
            userHeadImage.setImageResource(R.mipmap.icon_portrai);
        }
        mine_changtong_notice_text.setText(
                !Tools.isStrEmpty(PublicData.getInstance().filenum)
                        && PublicData.getInstance().loginFlag ? "已绑定" : "未绑定");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PublicData.getInstance().loginFlag) {
            getCouponCount();
            getUnReadMsgCount();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void DestroyViewAndThing() {

    }

    /**
     * 未读消息数量
     */
    private void getUnReadMsgCount() {
        BaseDTO dto = new BaseDTO();
        dto.setUsrId(RSAUtils.strByEncryption(
                getActivity().getApplicationContext(), PublicData.getInstance().userID, true));
        CarApiClient.getUnReadMsgCount(getActivity().getApplicationContext(), dto, new CallBack<MessageCountResult>() {
            @Override
            public void onSuccess(MessageCountResult result) {
                if (result.getResponseCode() == 2000) {
                    mMegCount.setText(String.valueOf(result.getData().getCount()));
                }
            }
        });
    }

    /**
     * 优惠券数量
     */
    private void getCouponCount() {
        final Dialog showLoading = DialogUtils.showLoading(getActivity());
        RepositoryManager repositoryManager = Injection.provideRepository(getActivity().getApplicationContext());
        Subscription subscription = repositoryManager.usrCouponInfo(PublicData.getInstance().userID, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CouponFragmentResult>() {
                    @Override
                    public void doCompleted() {
                        showLoading.dismiss();
                    }

                    @Override
                    public void doError(Throwable e) {
                        showLoading.dismiss();
                    }

                    @Override
                    public void doNext(CouponFragmentResult result) {
                        if (result.getResponseCode() == 2000) {
                            if (result.getData() != null) {
                                mYouHui.setText(String.valueOf(result.getData().getCouponList().size()));
                            } else {
                                ToastUtils.toasetShort(result.getResponseDesc());
                            }
                        }
                    }
                });
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.common_problem, R.id.mine_order, R.id.mine_pay_order, R.id.mine_info_rl,
            R.id.mine_tools_part1, R.id.mine_manage_vechilse, R.id.mine_manage_weizhang_history,
            R.id.invite_red_packet, R.id.problem_feedback, R.id.about_us,
            R.id.mine_meg_layout, R.id.mine_ctk_layout, R.id.mine_youhuijian_layout,
            R.id.about_update, R.id.about_advertising})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_info_rl:  //用户信息
                Act.getInstance().lauchIntentToLogin(getActivity(), UserInfoUpdate.class);
                break;
            case R.id.mine_tools_part1:  //畅通卡
                if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {
                    Act.getInstance().lauchIntentToLogin(getActivity(), CardHomeActivity.class);
                } else {
                    Act.getInstance().lauchIntentToLogin(getActivity(), MyCardActivity.class);
                }
                break;
            case R.id.mine_manage_vechilse:  //车辆管理
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(28));
                Act.getInstance().gotoIntent(getActivity(), CarManageGroupActivity.class);
                break;
            case R.id.mine_manage_weizhang_history:  //违章缴费记录
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(34));
                Act.getInstance().lauchIntentToLogin(getActivity(), ViolationHistoryAcitvity.class);
                break;
            case R.id.mine_order:  //保险订单
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(29));
                Act.getInstance().lauchIntentToLogin(getActivity(), OrderActivity.class);
                break;
            case R.id.mine_pay_order:  //充值支付订单
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(30));
                Act.getInstance().lauchIntentToLogin(getActivity(), OrderRechargeActivity.class);
                break;
            case R.id.common_problem:  //常见问题
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(32));
                Act.getInstance().gotoIntent(getActivity(), CommonProblemActivity.class);
                break;
            case R.id.invite_red_packet:  //推荐领积分页面
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(31));
                Act.getInstance().lauchIntentToLogin(getActivity(), GetBonusActivity.class);
                break;
            case R.id.problem_feedback:  //问题反馈
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(33));
                Act.getInstance().gotoIntent(getActivity(), ProblemFeedbackActivity.class);
                break;
            case R.id.about_us:  //关于我们
                Act.getInstance().gotoIntent(getActivity(), AboutActivity.class);
                break;
            case R.id.mine_meg_layout:  //消息
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(24));
                Act.getInstance().lauchIntentToLogin(getActivity(), MegTypeActivity.class);
                break;
            case R.id.mine_ctk_layout:  //畅通卡
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(25));
                if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {
                    Act.getInstance().lauchIntentToLogin(getActivity(), CardHomeActivity.class);
                } else {
                    Act.getInstance().lauchIntentToLogin(getActivity(), MyCardActivity.class);
                }
                break;
            case R.id.mine_youhuijian_layout:  //优惠券
                MobclickAgent.onEvent(getActivity(), Config.getUMengID(27));
                Act.getInstance().lauchIntentToLogin(getActivity(), CouponActivity.class);
                break;
            case R.id.about_update://版本更新
                Beta.checkUpgrade();
                break;
            case R.id.about_advertising://用户隐私
                PublicData.getInstance().webviewUrl = "file:///android_asset/bindcard_agreement.html";
                PublicData.getInstance().webviewTitle = "隐私声明";
                Act.getInstance().gotoIntent(getActivity(), BrowserActivity.class);
                break;
            default:
                break;
        }
    }
}
