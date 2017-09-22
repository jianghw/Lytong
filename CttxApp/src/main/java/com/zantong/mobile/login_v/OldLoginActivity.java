package com.zantong.mobile.login_v;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.tzly.annual.base.JxBaseActivity;
import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.util.LogUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.primission.PermissionFail;
import com.tzly.annual.base.util.primission.PermissionGen;
import com.tzly.annual.base.util.primission.PermissionSuccess;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.CarApiClient;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.base.bean.Result;
import com.zantong.mobile.car.dto.CarInfoDTO;
import com.zantong.mobile.car.dto.UserCarsDTO;
import com.zantong.mobile.card.dto.BindCarDTO;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.contract.LoginPhoneView;
import com.zantong.mobile.eventbus.GetUserEvent;
import com.zantong.mobile.presenter.LoginPhonePresenterImp;
import com.zantong.mobile.user.activity.RegisterActivity;
import com.zantong.mobile.user.activity.ResetActivity;
import com.zantong.mobile.user.bean.LoginInfoBean;
import com.zantong.mobile.user.bean.UserCarsResult;
import com.zantong.mobile.user.dto.LiYingRegDTO;
import com.zantong.mobile.utils.AccountRememberCtrl;
import com.zantong.mobile.utils.DialogMgr;
import com.zantong.mobile.utils.SPUtils;
import com.zantong.mobile.utils.ScreenManager;
import com.zantong.mobile.utils.StateBarSetting;
import com.zantong.mobile.utils.SystemBarTintManager;
import com.zantong.mobile.utils.TitleSetting;
import com.zantong.mobile.utils.Tools;
import com.zantong.mobile.utils.ValidateUtils;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.utils.rsa.Des3;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.utils.xmlparser.SHATools;
import com.zantong.mobile.widght.CustomCharKeyBoard;
import com.zantong.mobile.widght.CustomNumKeyBoard;

import org.greenrobot.eventbus.EventBus;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆界面
 */
public class OldLoginActivity extends JxBaseActivity implements
        LoginPhoneView, View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {

    @Bind(R.id.title_kong)
    RelativeLayout titleKong;
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.linear_title)
    LinearLayout linearTitle;
    @Bind(R.id.titleTv)
    TextView titleTv;
    @Bind(R.id.btn_right)
    Button btnRight;
    @Bind(R.id.image_right)
    ImageView imageRight;
    @Bind(R.id.rl_image_right)
    RelativeLayout rlImageRight;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.title)
    RelativeLayout title;

    @Bind(R.id.phone_number_edittext)
    RelativeLayout phoneNumberEdittext;
    @Bind(R.id.underline)
    TextView underline;
    @Bind(R.id.all_rela)
    RelativeLayout allRela;
    @Bind(R.id.btn_login)
    Button loginBtn;
    @Bind(R.id.subtitle)
    TextView subtitle;
    @Bind(R.id.btn_number)
    Button btnNumber;
    @Bind(R.id.image_delete)
    ImageView imageDelete;
    @Bind(R.id.edt_phone)
    EditText edit_phone_number;
    @Bind(R.id.edt_code)
    EditText edit_code_number;

    private LoginPhonePresenterImp mLoginPhonePresenterImp;

    private boolean codeFlag = false;
    private boolean phoneFlag = false;
    private Timer timer;
    private int iTime = -1;

    @Bind(R.id.num_keyboard)
    CustomNumKeyBoard mNumKeyBoard;
    @Bind(R.id.char_keyboard)
    CustomCharKeyBoard mCharKeyBoard;

    boolean isDaxie;

    ArrayList<TextView> mListNum;
    ArrayList<TextView> mListChar;

    protected int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindContentView(View childView) {

    }

    @Override
    protected void initContentData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mLoginPhonePresenterImp = new LoginPhonePresenterImp(this);
        StateBarSetting.settingBar(this);
        TitleSetting.getInstance().initTitle(this, "登录", 0, "取消", "欢迎加入畅通车友会", "注册");

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.appmain);//通知栏所需颜色
        ScreenManager.pushActivity(this);

        init();
        takePhoneIMEI();

        mListNum = mNumKeyBoard.getRandomList();
        mListChar = mCharKeyBoard.getRandomList();

        for (int i = 0; i < mListNum.size(); i++) {
            mListNum.get(i).setOnClickListener(this);
        }
        for (int i = 0; i < mListChar.size(); i++) {
            mListChar.get(i).setOnClickListener(this);
        }

        edit_phone_number.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框
        edit_code_number.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框

        mNumKeyBoard.getChangeAbcView().setOnClickListener(this);
        mNumKeyBoard.getNumDelView().setOnClickListener(this);
        mNumKeyBoard.getNumDelView().setOnLongClickListener(this);
        mNumKeyBoard.getNumFinishView().setOnClickListener(this);
        mNumKeyBoard.getNumHideView().setOnClickListener(this);

        mCharKeyBoard.getCharDelView().setOnClickListener(this);
        mCharKeyBoard.getCharDelView().setOnLongClickListener(this);
        mCharKeyBoard.getCharFinishView().setOnClickListener(this);
        mCharKeyBoard.getCharTabView().setOnClickListener(this);
        mCharKeyBoard.getChangeNumView().setOnClickListener(this);
        mCharKeyBoard.getCharHideView().setOnClickListener(this);

        edit_phone_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    edit_code_number.requestFocus();
                    hideInputType();
                }
                return false;
            }
        });
    }

    private void init() {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        iTime--;
                        btnNumber.setEnabled(false);
                        btnNumber.setText(iTime + " s");
                        break;
                    case 1:
                        if (phoneFlag) {
                            btnNumber.setEnabled(true);
                        } else {
                            btnNumber.setEnabled(false);
                        }
                        btnNumber.setText("重发验证码");
                        break;
                }
                super.handleMessage(msg);
            }
        };
        final TimerTask task = new TimerTask() {
            public void run() {
                Message message = new Message();
                if (iTime > 0)
                    message.what = 2;
                else if (iTime == 0)
                    message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer = new Timer(true);
        timer.schedule(task, 0, 1000);

        edit_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    imageDelete.setVisibility(View.VISIBLE);
                } else {
                    imageDelete.setVisibility(View.GONE);

                }
                if (11 == s.length() && (iTime == 0 || iTime == -1)) {
                    btnNumber.setEnabled(true);
                    phoneFlag = true;
                } else {
                    btnNumber.setEnabled(false);
                    phoneFlag = false;
                }
                if (codeFlag && phoneFlag) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
            }
        });

        edit_code_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidateUtils.checkPwd(s.toString())) {
                    codeFlag = true;
                } else {
                    codeFlag = false;
                }
                if (codeFlag && phoneFlag) {
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setEnabled(false);
                }
            }
        });
    }

    public HashMap<String, String> mapData() {
        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("phoenum", edit_phone_number.getText().toString());
        mHashMap.put("pswd", edit_code_number.getText().toString());
        return mHashMap;
    }

    public void setCodeTime() {
        iTime = PublicData.getInstance().smCtrlTime;
        if (iTime > 0) {
            btnNumber.setEnabled(false);
            btnNumber.setText("60s");
        }
    }

    @OnClick({R.id.btn_number, R.id.btn_login, R.id.text_right, R.id.image_delete, R.id.tv_back, R.id.tv_forget_pw})
    public void onClick(View view) {
        Editable editable = edit_code_number.getText();
        int start = edit_code_number.getSelectionStart();

        for (int i = 0; i < mListNum.size(); i++) {
            if (view == mListNum.get(i)) {
                editable.insert(start, mListNum.get(i).getText().toString());
            }
        }
        for (int i = 0; i < mListChar.size(); i++) {
            if (view == mListChar.get(i)) {
                editable.insert(start, mListChar.get(i).getText().toString());
            }
        }
        //切换为字母输入
        if (view == mNumKeyBoard.getChangeAbcView()) {
            mCharKeyBoard.setVisibility(View.VISIBLE);
            mNumKeyBoard.setVisibility(View.GONE);
        }
        //删除
        if (view == mNumKeyBoard.getNumDelView() || view == mCharKeyBoard.getCharDelView()) {
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);//开始，结束位置
                }
            }
        }
        //完成
        if (view == mNumKeyBoard.getNumFinishView() || view == mCharKeyBoard.getCharFinishView() ||
                view == mNumKeyBoard.getNumHideView() || view == mCharKeyBoard.getCharHideView()) {
            mNumKeyBoard.setVisibility(View.GONE);
            mCharKeyBoard.setVisibility(View.GONE);
        }

        //切换为数字输入
        if (view == mCharKeyBoard.getChangeNumView()) {
            mCharKeyBoard.setVisibility(View.GONE);
            mNumKeyBoard.setVisibility(View.VISIBLE);
        }
        //切换大小写输入
        if (view == mCharKeyBoard.getCharTabView()) {
            if (isDaxie) {
                isDaxie = false;
                mCharKeyBoard.getCharTabViewImg().setBackgroundResource(R.mipmap.icon_xiaoxie_btn);
                mCharKeyBoard.changeLower();
            } else {
                isDaxie = true;
                mCharKeyBoard.getCharTabViewImg().setBackgroundResource(R.mipmap.icon_daxie_btn);
                mCharKeyBoard.changeUpper();
            }
        }
        switch (view.getId()) {
            case R.id.btn_number:
                mLoginPhonePresenterImp.loadView(1);
                break;
            case R.id.btn_login:
                mNumKeyBoard.setVisibility(View.GONE);
                mCharKeyBoard.setVisibility(View.GONE);
                mLoginPhonePresenterImp.loadView(2);
                break;
            case R.id.text_right:
                Act.getInstance().lauchIntent(OldLoginActivity.this, RegisterActivity.class);
                break;
            case R.id.tv_forget_pw:
                Act.getInstance().lauchIntent(OldLoginActivity.this, ResetActivity.class);
                break;

            case R.id.image_delete:
                edit_phone_number.setText("");
                break;
            case R.id.tv_back:
                hideInputManager();
                break;
        }
    }

    //判断是否隐藏输入法键盘
    private void hideInputManager() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void showProgress() {
        loginBtn.setText("正在验证...");
        loginBtn.setClickable(false);
    }

    @Override
    public void addLoginInfo(LoginInfoBean mLoginInfoBean) {

        Intent intent = new Intent();
        intent.putExtra("back", "wode");
        OldLoginActivity.this.setResult(1, intent);

        EventBus.getDefault().post(new GetUserEvent(true));
        SPUtils.getInstance().setUserPwd(mapData().get("pswd"));

        liyingreg(mLoginInfoBean.getRspInfo().getUsrid());

        setJiaoYiDaiMa(Des3.decode(mLoginInfoBean.getRspInfo().getFilenum()));

        if (!"0".equals(AccountRememberCtrl.getLoginAD(OldLoginActivity.this)) && Tools.isStrEmpty(PublicData.getInstance().filenum)) {
            new DialogMgr(OldLoginActivity.this,
                    "登录成功", "畅通车友会欢迎您，赶快去注册您的牡丹卡吧！", "添加畅通卡", "继续",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            InputMethodManager imm = (InputMethodManager) OldLoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm.isActive() && OldLoginActivity.this.getCurrentFocus() != null) {
                                if (OldLoginActivity.this.getCurrentFocus().getWindowToken() != null) {
                                    imm.hideSoftInputFromWindow(OldLoginActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            }
                            finish();
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (PublicData.getInstance().className != null) {
                                Act.getInstance().lauchIntent(OldLoginActivity.this, PublicData.getInstance().className);
                            }
                            finish();
                        }
                    },
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AccountRememberCtrl.saveLoginAD(OldLoginActivity.this, "0");
                            hideInputType();
                            finish();
                        }
                    });
        } else {
            ScreenManager.popActivity();
        }
    }

    /**
     * 交易代码
     */
    private void setJiaoYiDaiMa(String strFileNum) {
        if (!TextUtils.isEmpty(strFileNum)) {
            UserApiClient.setJiaoYiDaiMa(this, strFileNum, new CallBack<Result>() {
                @Override
                public void onSuccess(Result result) {
                    if (result.getSYS_HEAD().getReturnCode().equals("000000")) {
                    }
                }
            });
        }
    }

    private void liyingreg(String userId) {
        try {
            String phone = RSAUtils.strByEncryptionLiYing(mapData().get("phoenum"), true);
            SHATools sha = new SHATools();
            String pwd = RSAUtils.strByEncryptionLiYing(SHATools.hexString(sha.eccryptSHA1(mapData().get("pswd"))), true);
            LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
            liYingRegDTO.setPhoenum(phone);
            liYingRegDTO.setPswd(pwd);
            liYingRegDTO.setUsrid(RSAUtils.strByEncryptionLiYing(userId, true));

            CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {
//                    getUserCarInfo();
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgress() {
        loginBtn.setText("开始");
        loginBtn.setClickable(true);
    }

    /**
     * target 目标类型，1：本设备； 2：本设备绑定账号； 3：别名
     * tags 标签（数组输入）
     * alias 别名（仅当target = 3时生效）
     * callback 回调
     */
    private void alicloud() {
        PushServiceFactory.getCloudPushService().bindTag(3, getTagArr(), "123456", new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtils.i("绑定标签到别名成功.");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.i("绑定标签到别名失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
            }
        });
        PushServiceFactory.getCloudPushService().bindAccount("1234", new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtils.i("绑定账号成功");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.i("绑定账号失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
            }
        });
    }

    private String[] getTagArr() {
        String tagStr = "123456";
        String[] tagArr = null;
        if (tagStr != null && tagStr.length() > 0) {
            tagArr = tagStr.split(" ");
        } else {
            LogUtils.i("请按照格式输入标签.");
        }
        return tagArr;
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mNumKeyBoard.getNumDelView() || view == mCharKeyBoard.getCharDelView()) {
            edit_code_number.setText("");
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.edt_code) {
            mNumKeyBoard.setVisibility(View.VISIBLE);
//            edit_code_number.setInputType(InputType.TYPE_CLASS_NUMBER);
            edit_code_number.requestFocus();
            hideInputType();
        } else if (v.getId() == R.id.edt_phone) {
            edit_phone_number.requestFocus();
//            edit_phone_number.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mNumKeyBoard.setVisibility(View.GONE);
            mCharKeyBoard.setVisibility(View.GONE);
            showInputType();
        }
        return true;
    }

    /**
     * 判断当前系统版本，选择使用何种方式隐藏默认键盘
     */
    private void hideInputType() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(edit_code_number.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 显示默认键盘
     */
    private void showInputType() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit_phone_number, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取车辆数
     */
    private void getUserCarInfo() {
        UserCarsDTO dto = new UserCarsDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getCarInfo(OldLoginActivity.this, dto, new CallBack<UserCarsResult>() {
            @Override
            public void onSuccess(UserCarsResult result) {
                if (result.getRspInfo().getUserCarsInfo().size() <= 0) {
                    commitCarInfoToNewServer();
                }
            }
        });
    }

    //提交新服务器
    private void commitCarInfoToNewServer() {
        List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
        for (CarInfoDTO info : list) {
            LogUtils.i("-----" + info.getCarnumtype());
            BindCarDTO dto = new BindCarDTO();
            dto.setPlateNo(info.getCarnum());
            dto.setEngineNo(info.getEnginenum());
            dto.setUsrnum(PublicData.getInstance().userID);
            dto.setFileNum(PublicData.getInstance().filenum);
            dto.setVehicleType(String.valueOf(Integer.valueOf(info.getCarnumtype()) + 1));
            CarApiClient.commitCar(this, dto, new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {

                }
            });
        }
    }

    public void takePhoneIMEI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 100,
                    new String[]{Manifest.permission.READ_PHONE_STATE}
            );
        } else {
            PublicData.getInstance().imei = Tools.getIMEI();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doPermissionIMEISuccess() {
        PublicData.getInstance().imei = Tools.getIMEI();
    }

    @PermissionFail(requestCode = 100)
    public void doPermissionIMEIFail() {
        ToastUtils.toastShort("手机识别码权限被拒绝，请手机设置中打开");
    }
}
