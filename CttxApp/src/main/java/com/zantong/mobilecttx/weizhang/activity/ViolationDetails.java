package com.zantong.mobilecttx.weizhang.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.car.activity.CarManageActivity;
import com.zantong.mobilecttx.card.activity.ChangTongCard;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.activity.CaptureActivity;
import com.zantong.mobilecttx.interf.ModelView;
import com.zantong.mobilecttx.presenter.ViolationDetailsPresenterImp;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.StateBarSetting;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.TitleSetting;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.popwindow.SurePayPopupWindows;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;
import com.zantong.mobilecttx.weizhang.fragment.PayTypeFragment;
import com.zantong.mobilecttx.weizhang.fragment.SurePayFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.util.log.LogUtils;
import cn.qqtheme.framework.util.primission.PermissionFail;
import cn.qqtheme.framework.util.primission.PermissionGen;
import cn.qqtheme.framework.util.primission.PermissionSuccess;

public class ViolationDetails extends FragmentActivity implements ModelView {
    @Bind(R.id.next_btn)
    Button nextBtn;
    //    @Bind(R.id.violation_state_text)
//    TextView violationStateText;
    @Bind(R.id.violation_location_text)
    TextView violation_location_text;
    @Bind(R.id.violation_content_text)
    TextView violation_content_text;
    @Bind(R.id.violation_time_text)
    TextView violation_time_text;
    @Bind(R.id.violation_pay_text)
    TextView violation_pay_text;
    @Bind(R.id.violation_state_text)
    TextView violation_state_text;
    @Bind(R.id.violation_money_text)
    TextView violation_money_text;
    @Bind(R.id.violation_money_zhinajin_text)
    TextView violation_money_zhinajin_text;
    @Bind(R.id.violation_points_text)
    TextView violation_points_text;
    @Bind(R.id.loading_content)
    RelativeLayout loading_content;
    @Bind(R.id.loading_faild_content)
    RelativeLayout loading_faild_content;
    @Bind(R.id.violation_pay_rl)
    RelativeLayout violation_pay_rl;
    @Bind(R.id.content_all)
    ScrollView content_all;
    @Bind(R.id.loading_image)
    ImageView loading_image;
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.viloation_detail_commit_desc)
    TextView mDesc;
    private SurePayPopupWindows mSurePayPopupWindows;
    private SurePayPopupWindows mSurePayPopupWindows2;
    private SurePayFragment mSurePayFragment;
    private PayTypeFragment mPayTypeFragment;
    private FragmentManager mFragmentManager;
    private int indexFlag = 0;//标志当前fragment
    private int payType = 1;
    private ViolationDetailsPresenterImp mViolationDetailsPresenterImp;
    private ViolationDetailsBean mViolationDetailsBean;
    private RotateAnimation refreshingAnimation;
    private boolean isPagCar = false;

    private int mCommitType; //0去缴费 1去绑卡 2去改绑车辆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_details);
        ButterKnife.bind(this);
        if ("1".equals(PublicData.getInstance().mHashMap.get("mRes"))) {
            TitleSetting.getInstance().initTitle(this, "缴费详情", R.mipmap.back_btn_image, "返回", null, null);
        } else {
            TitleSetting.getInstance().initTitle(this, "违章详情", R.mipmap.back_btn_image, "返回", null, null);
        }
        StateBarSetting.settingBar(this);
        mViolationDetailsPresenterImp = new ViolationDetailsPresenterImp(this);

        mSurePayFragment = new SurePayFragment();
        mPayTypeFragment = new PayTypeFragment();
        mFragmentManager = getSupportFragmentManager();
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                this, R.anim.rotating_anim);
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        Act.getInstance().lauchIntent(this, CaptureActivity.class);
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        ToastUtils.showShort(this, "您已关闭摄像头权限");
    }

    private void init() {
        mViolationDetailsBean = (ViolationDetailsBean) PublicData.getInstance().mHashMap.get("ViolationDetails");
        if (null != mViolationDetailsBean) {
            initText();
        } else {
            mViolationDetailsPresenterImp.loadView(1);
        }

    }

    private void initText() {
        violation_location_text.setText(mViolationDetailsBean.getRspInfo().getViolationplace());
        violation_content_text.setText(mViolationDetailsBean.getRspInfo().getViolationtype());
        DateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        DateFormat formatTime = new SimpleDateFormat("HHmm");
        SimpleDateFormat yearDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeDate = new SimpleDateFormat("HH:mm");
        Date dateDate;
        Date dateTime;
        String date;
        String time;
        try {
            dateDate = formatDate.parse(mViolationDetailsBean.getRspInfo().getViolationdate());
            dateTime = formatTime.parse(mViolationDetailsBean.getRspInfo().getViolationtime());
            date = yearDate.format(dateDate);
            time = timeDate.format(dateTime);
            violation_time_text.setText(date + " " + time);
            if ("1".equals(PublicData.getInstance().mHashMap.get("mRes"))) {
                violation_pay_rl.setVisibility(View.VISIBLE);
                dateDate = formatDate.parse(mViolationDetailsBean.getRspInfo().getPaydate());
                dateTime = formatTime.parse(mViolationDetailsBean.getRspInfo().getPaytime());
                date = yearDate.format(dateDate);
                time = timeDate.format(dateTime);
                violation_pay_text.setText(date + " " + time);
            } else {
                violation_pay_rl.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        violation_time_text.setText(mViolationDetailsBean.getRspInfo().getViolationtime());
        if ("0".equals(mViolationDetailsBean.getRspInfo().getProcessste())) {
            violation_state_text.setText("未缴费");
//            violation_state_text.setText("未处理");
        } else {
            violation_state_text.setText("已缴费");
        }
        try {
            violation_money_text.setText(StringUtils.getPriceString(mViolationDetailsBean.getRspInfo().getViolationamt()) + "元");
            violation_money_zhinajin_text.setText(StringUtils.getPriceString(mViolationDetailsBean.getRspInfo().getZhinajin()) + "元");
            violation_money_text.setText(AmountUtils.changeF2Y(mViolationDetailsBean.getRspInfo().getViolationamt())+"元");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("0".equals(mViolationDetailsBean.getRspInfo().getViolationcent())) {
            violation_points_text.setText("没有扣分");
        } else {
            violation_points_text.setText(mViolationDetailsBean.getRspInfo().getViolationcent() + "分");
        }
        String carNum = Des3.decode(mViolationDetailsBean.getRspInfo().getCarnum());
        String violationNumber = carNum;
        for (int i = 0; i < PublicData.getInstance().payData.size(); i++) {
            if (violationNumber.equals(PublicData.getInstance().payData.get(i).getCarnum())) {
                isPagCar = true;
                break;
            } else {
                isPagCar = false;
            }
        }
        String bitNumber = ((String) PublicData.getInstance().mHashMap.get("ViolationDetailsStr")).substring(6, 7);
        LogUtils.i("bitNumber:"+bitNumber);
        if ("0".equals(mViolationDetailsBean.getRspInfo().getProcessste())) {//未交费
            if ("1".equals(bitNumber) || "2".equals(bitNumber)) {//是否处罚决定书
                nextBtn.setEnabled(true);
                nextBtn.setText("违章缴费");
                mCommitType = 0;
            } else {
                if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {
                    MobclickAgent.onEvent(this, Config.getUMengID(11));
                    mDesc.setVisibility(View.VISIBLE);
                    mDesc.setText("您还未绑定畅通卡，违章缴费需要使用畅通卡");
                    nextBtn.setText("绑定畅通卡");
                    mCommitType = 1;
                } else {
                    LogUtils.i("boolean:"+isPagCar+"-----payDataSize:"+PublicData.getInstance().payData.size());
                    if (isPagCar || PublicData.getInstance().payData.size() < 2) {
                        nextBtn.setEnabled(true);
                        nextBtn.setText("违章缴费");
                        mCommitType = 0;
                    } else {
                        mDesc.setVisibility(View.VISIBLE);
                        mDesc.setText("您的绑定车辆已满，缴费需要更改绑定设置");
                        nextBtn.setText("车辆改绑");
                        mCommitType = 2;
                    }
                }

            }
        } else {//已缴费
            nextBtn.setVisibility(View.GONE);
            mDesc.setVisibility(View.GONE);
        }
    }


    public HashMap<String, String> mapData() {
        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("violationnum", (String) PublicData.getInstance().mHashMap.get("ViolationDetailsStr"));
        if (mViolationDetailsBean != null) {
            mHashMap.put("violationamt", mViolationDetailsBean.getRspInfo().getViolationamt()); //总计
            mHashMap.put("benjin", mViolationDetailsBean.getRspInfo().getViolationmoney());    //本金
            mHashMap.put("zhinajin", mViolationDetailsBean.getRspInfo().getZhinajin());  //滞纳金
        }
        return mHashMap;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if(getSupportFragmentManager().getBackStackEntryCount()<=0){
//                exit();
//            }else{
//                return super.onKeyDown(keyCode, event);
//            }
            switch (indexFlag) {
                case 0:
//                    ScreenManager.popActivity();
                    return super.onKeyDown(keyCode, event);
                case 1:
                    indexFlag = 0;
                    StateBarSetting.settingBar(this);
                    mFragmentManager.beginTransaction().remove(getSurePayFragment()).commit();
                    break;
                case 2:
                    indexFlag = 1;
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.left_to_right_enter, R.anim.right_to_left_out)
                            .replace(R.id.sure_pay_frame, getSurePayFragment()).commit();
                    break;
            }
            return false;
        }
        return true;
    }

    @OnClick({R.id.next_btn, R.id.loading_faild_content, R.id.tv_back, R.id.detail_zhinajin_img})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                switch (mCommitType) {
                    case 0:
                        StateBarSetting.settingBar(this, R.color.surePayPopupBackground);
                        indexFlag = 1;
                        mFragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.down_to_up, R.anim.left_to_right)
                                .replace(R.id.sure_pay_frame, mSurePayFragment).commit();
                        break;
                    case 1://绑卡
                        if (PublicData.getInstance().loginFlag){
                            Act.getInstance().gotoIntent(this, ChangTongCard.class);
                        }else{
                            Act.getInstance().gotoIntent(this, LoginActivity.class);
                        }
                        break;
                    case 2:
                        Act.getInstance().gotoIntent(this, CarManageActivity.class);
                        break;
                }
                break;
            case R.id.loading_faild_content:
                mViolationDetailsPresenterImp.loadView(1);
                break;
            case R.id.detail_zhinajin_img:
                lateFeeDialog();
                break;
        }
    }

    /**
     * 滞纳金dialog
     */
    private void lateFeeDialog(){
        DialogUtils.createLateFeeDialog(this, "滞纳金说明", "根据《中华人民共和国道" +
                "路交通安全法》108条:当事人应当自收到行政处罚决定书" +
                "之日起15日内，到指定的银行缴纳罚款。\n" +
                "109条：到期不缴纳罚款的，每日按罚款数额的3%加处罚款；\n" +
                "\n" +
                "*滞纳金总额不会超过罚款本金的100%。\n" +
                "*没有去开罚单的“电子警察”记录不会产生滞纳金。");
    }

    public FragmentManager getSurePayFragmentManager() {
        return mFragmentManager;
    }

    public SurePayFragment getSurePayFragment() {
        return mSurePayFragment;
    }

    public PayTypeFragment getPayTypeFragment() {
        return mPayTypeFragment;
    }

    public void setTitleColor() {
        StateBarSetting.settingBar(this);
    }

    public void setIndexFlag(int indexFlag) {
        this.indexFlag = indexFlag;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayType() {
        return payType;
    }

    @Override
    public void showProgress() {
        loading_content.setVisibility(View.VISIBLE);
        loading_faild_content.setVisibility(View.GONE);
        content_all.setVisibility(View.GONE);
        loading_image.startAnimation(refreshingAnimation);
    }

    @Override
    public void updateView(Object object, int index) {
        mViolationDetailsBean = (ViolationDetailsBean) object;
        if (null != mViolationDetailsBean) {
            initText();
        }
    }

    @Override
    public void hideProgress() {
        loading_content.setVisibility(View.GONE);
        loading_faild_content.setVisibility(View.GONE);
        content_all.setVisibility(View.VISIBLE);
        loading_image.clearAnimation();
    }

    public void loadFaildProgress() {
        loading_content.setVisibility(View.GONE);
        loading_faild_content.setVisibility(View.VISIBLE);
        content_all.setVisibility(View.GONE);
        loading_image.clearAnimation();
    }

}
