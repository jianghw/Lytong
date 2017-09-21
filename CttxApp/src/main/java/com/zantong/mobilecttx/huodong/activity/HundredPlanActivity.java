package com.zantong.mobilecttx.huodong.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.tzly.annual.base.bean.BaseResult;
import com.zantong.mobilecttx.car.activity.ManageCarActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.huodong.bean.ActivitySignNum;
import com.zantong.mobilecttx.huodong.dto.HundredPlanDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.activity.SettingActivity;
import cn.qqtheme.framework.util.ui.DensityUtils;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.widght.DanceWageTimer;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ToastUtils;

/**
 * 活动报名
 */
public class HundredPlanActivity extends BaseMvpActivity {

    @Bind(R.id.hundred_plan_count)
    TextView mCountTv;
    @Bind(R.id.img_hundred_plan_banner)
    ImageView mBanner;
    @Bind(R.id.hundred_plan_selcar)
    TextView mSelCar;
    @Bind(R.id.hundred_plan_push_btn)
    TextView mPush;
    @Bind(R.id.hundred_plan_sign_car)
    TextView mCar;
    @Bind(R.id.hundred_plan_unsign_layout)
    View mUnSignLayout;//未报名
    @Bind(R.id.hundred_plan_sign_layout)
    View mSignLayout;//已报名
    @Bind(R.id.hundred_plan_addcar)
    TextView mAddCar;//添加车辆
    @Bind(R.id.hundred_plan_rules)
    TextView mRules;//活动规则
    @Bind(R.id.hundred_plan_query_violation_layout)
    View mQueryViolationLayout;//查违章
    @Bind(R.id.hundred_plan_query_violation)
    TextView mQueryViolation;

    private int signCount;
    public static boolean isSelCar;//是否选择车辆

    DanceWageTimer myDanceTimer;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_hundred_plan;
    }


    @Override
    public void initView() {
        setTitleText("百日无违章");
        setEnsureText("分享");
        mRules.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mAddCar.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mPush.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mQueryViolation.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        int screenWidth = DensityUtils.getScreenWidth(this);
        ViewGroup.LayoutParams lp = mBanner.getLayoutParams();
        lp.width = screenWidth;
        lp.height = screenWidth * 4 / 9;//15 / 36
        mBanner.setLayoutParams(lp);

    }

    @Override
    public void initData() {
//        getSignStatus();
        getSignNum();
        if (SPUtils.getInstance().getSignStatus()) {
            mUnSignLayout.setVisibility(View.GONE);
            mSignLayout.setVisibility(View.VISIBLE);
            mQueryViolationLayout.setVisibility(View.VISIBLE);
            mCar.setText("报名车辆：" + SPUtils.getInstance().getSignCarPlate());
        } else {
            MobclickAgent.onEvent(this, Config.getUMengID(19));
            mUnSignLayout.setVisibility(View.VISIBLE);
            mSignLayout.setVisibility(View.GONE);
            mQueryViolationLayout.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.hundred_plan_addcar, R.id.hundred_plan_selcar, R.id.hundred_plan_commit, R.id.hundred_plan_push_btn, R.id.hundred_plan_rules,
            R.id.hundred_plan_query_violation})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hundred_plan_addcar:
                Act.getInstance().gotoIntentLogin(this, ManageCarActivity.class);
                finish();
                break;
            case R.id.hundred_plan_selcar:
                DialogUtils.createCarsDialog(this, mSelCar);
                break;
            case R.id.hundred_plan_commit:
                MobclickAgent.onEvent(this, Config.getUMengID(20));
                if (!isSelCar) {
                    ToastUtils.showShort(this, "请先选择您要报名的车辆");
                    return;
                }

                HundredPlanDTO dto = new HundredPlanDTO();
                dto.setUsrnum(PublicData.getInstance().userID);
                dto.setPhoneNum(PublicData.getInstance().mLoginInfoBean.getPhoenum());
                dto.setPlateNo(mSelCar.getText().toString());

                CarApiClient.commitHundredPlan(this, dto, new CallBack<BaseResult>() {
                    @Override
                    public void onSuccess(BaseResult result) {
                        if (result.getResponseCode() == 2000) {
                            signCount++ ;
                            mCar.setText(mSelCar.getText().toString());
                            mUnSignLayout.setVisibility(View.GONE);
                            mSignLayout.setVisibility(View.VISIBLE);
                        } else {
                            mUnSignLayout.setVisibility(View.GONE);
                            mSignLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
                break;
            case R.id.hundred_plan_push_btn:
                Act.getInstance().gotoIntent(this, SettingActivity.class);
                break;
            case R.id.hundred_plan_rules:
                Act.getInstance().gotoIntent(this, HundredAgreementActivity.class);
                break;
            case R.id.hundred_plan_query_violation:

                break;
        }
    }

    /**
     * 获取报名数量
     */
    private void getSignNum() {
        CarApiClient.getSignNum(this, new CallBack<ActivitySignNum>() {
            @Override
            public void onSuccess(ActivitySignNum result) {
                if (result.getResponseCode() == 2000) {
                    try {
                        signCount = Integer.valueOf(result.getData().getActivityCount());
                        myDanceTimer = new DanceWageTimer(2000, 50, mCountTv, signCount);
                        myDanceTimer.start();
                    } catch (Exception e) {

                    }

                }
            }
        });

    }

    @Override
    protected void baseGoEnsure() {
        super.baseGoEnsure();
        new DialogMgr(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(0);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(1);
            }
        });

    }

    /**
     * 微信分享
     *
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    private void wechatShare(int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_ID, true);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.showShort(this, "您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if (PublicData.getInstance().loginFlag) {
            webpage.webpageUrl = "http://d.eqxiu.com/s/um7rznLE";
        } else {
            webpage.webpageUrl = "http://d.eqxiu.com/s/um7rznLE";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "不违章就送油";
        msg.description = "我是第" + signCount + "位参加\"百日无违章，共赢百吨油\"的活动的人。文明行车从我做起";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sharelogo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
