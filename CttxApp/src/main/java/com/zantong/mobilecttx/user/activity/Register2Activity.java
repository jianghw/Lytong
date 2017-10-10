package com.zantong.mobilecttx.user.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import cn.qqtheme.framework.bean.BaseResponse;
import cn.qqtheme.framework.bean.BankResponse;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.card.activity.UnblockedCardActivity;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.eventbus.CarInfoEvent;
import com.zantong.mobilecttx.home.activity.HomeMainActivity;
import com.zantong.mobilecttx.contract.IOrderView;
import com.zantong.mobilecttx.presenter.OrderPresenter;
import com.zantong.mobilecttx.user.bean.LoginResult;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.user.dto.RegisterDTO;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ScreenManager;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;
import com.zantong.mobilecttx.widght.CustomCharKeyBoard;
import com.zantong.mobilecttx.widght.CustomNumKeyBoard;

import org.greenrobot.eventbus.EventBus;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;

public class Register2Activity extends BaseMvpActivity<IOrderView, OrderPresenter> implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {

    @Bind(R.id.register_commit_btn)
    Button mCommit;
    @Bind(R.id.register_pwd)
    EditText mPwd;
    @Bind(R.id.register_repwd)
    EditText mPwd2;
    @Bind(R.id.mine_register_numkeyboard)
    CustomNumKeyBoard mNumKeyBoard;
    @Bind(R.id.mine_register_charkeyboard)
    CustomCharKeyBoard mCharKeyBoard;

    ArrayList<TextView> mListNum;
    ArrayList<TextView> mListChar;
    boolean isDaxie;
    int mFoucsPos = 1;//0 ：第一个输入框获取焦点 1： 第二个输入框获取焦点

    public static final String RES_CODE = "resCode";
    public static final String PHONE = "phone";
    int res = 0;

    @Override
    public void initView() {
        mPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 5) {
                    mCommit.setEnabled(true);
                } else {
                    mCommit.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ScreenManager.pushActivity(this);

        mListNum = mNumKeyBoard.getRandomList();
        mListChar = mCharKeyBoard.getRandomList();
        for (int i = 0; i < mListNum.size(); i++) {
            mListNum.get(i).setOnClickListener(this);
        }
        for (int i = 0; i < mListChar.size(); i++) {
            mListChar.get(i).setOnClickListener(this);
        }


        mPwd.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框
        mPwd2.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框

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

        mPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    mFoucsPos = 0;
                    hideInputType();
                    mCharKeyBoard.setVisibility(View.GONE);
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    mPwd.requestFocus();
                    mPwd.setCursorVisible(true);
                }
                return false;
            }
        });

    }

    @Override
    public void initData() {
        mCommit.setOnClickListener(this);

        res = getIntent().getIntExtra(RES_CODE, 0);

        setTitleText(res == 0 ? "注册" : "重置密码");
        mCommit.setText(res == 0 ? "注册" : "重置密码");


    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_register2;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        Editable editable;
        int start = 0;
        if (mFoucsPos == 0) {
            editable = mPwd.getText();
            start = mPwd.getSelectionStart();
        } else {
            editable = mPwd2.getText();
            start = mPwd2.getSelectionStart();
        }

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
            case R.id.register_commit_btn:
                mNumKeyBoard.setVisibility(View.GONE);
                mCharKeyBoard.setVisibility(View.GONE);
                final String pwd = mPwd.getText().toString().replace(" ", "");
                String pwd2 = mPwd2.getText().toString().replace(" ", "");
                if (pwd.length() < 6 || pwd.length() > 20) {
                    ToastUtils.toastShort("密码位数不对");
                    return;
                } else if (!ValidateUtils.checkPwd(pwd)) {
                    ToastUtils.toastShort("密码存在非法字符");
                    return;
                } else if (ValidateUtils.checkNum(pwd)) {
                    ToastUtils.toastShort("密码不能为纯数字");
                    return;
                } else if (ValidateUtils.checkChar(pwd)) {
                    ToastUtils.toastShort("密码不能为纯字母");
                    return;
                } else if (!pwd.equals(pwd2)) {
                    ToastUtils.toastShort("两次输入的密码不一致");
                    return;
                }
                SPUtils.getInstance().setUserPwd(pwd);
                if (res == 0) {
                    final RegisterDTO dto = new RegisterDTO();
                    String phone = RSAUtils.strByEncryption(getIntent().getStringExtra(PHONE), true);
                    dto.setToken(RSAUtils.strByEncryption(MemoryData.getInstance().deviceId, true));
                    dto.setPhoenum(phone);
                    if ("".equals(MemoryData.getInstance().imei)) {
                        dto.setDevicetoken("1234567890");
                    } else {
                        dto.setDevicetoken(MemoryData.getInstance().imei);
                    }
                    LogUtils.i("token:" + dto.getDevicetoken());
                    try {
                        SHATools sha = new SHATools();
                        String password = SHATools.hexString(sha.eccryptSHA1(pwd));
                        password = RSAUtils.strByEncryption(password, true);
                        dto.setPswd(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    showDialogLoading();
                    UserApiClient.register(this, dto, new CallBack<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult result) {
                            hideDialogLoading();
                            if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                                liyingreg(pwd, result.getRspInfo().getUsrid());
                                SPUtils.getInstance().setLoginInfoBean(result.getRspInfo());
                                result.getRspInfo().setFilenum(Des3.decode(result.getRspInfo().getFilenum()));
                                result.getRspInfo().setPhoenum(Des3.decode(result.getRspInfo().getPhoenum()));
                                result.getRspInfo().setCtfnum(Des3.decode(result.getRspInfo().getCtfnum()));
                                UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERPD, mPwd.getText().toString());
                                UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERDEVICE, MemoryData.getInstance().imei);
                                UserInfoRememberCtrl.saveObject(result.getRspInfo());

                                LogUtils.i("usrid:" + result.getRspInfo().getUsrid());
                                MemoryData.getInstance().mLoginInfoBean = result.getRspInfo();
                                MemoryData.getInstance().userID = result.getRspInfo().getUsrid();
                                MemoryData.getInstance().filenum = result.getRspInfo().getFilenum();

                                MemoryData.getInstance().loginFlag = true;
                                commitLocalCar();
                                commitIllegalHistory();

                                new DialogMgr(Register2Activity.this,
                                        "登录成功！",
                                        "欢迎你加入畅通车友会\n赶快去添加你的牡丹畅通卡吧！",
                                        "添加畅通卡",
                                        "继续",
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Act.getInstance().lauchIntent(Register2Activity.this, UnblockedCardActivity.class);
                                                ScreenManager.getScreenManager().specialMethod();
                                            }
                                        },
                                        new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                ScreenManager.getScreenManager().popAllActivityExceptOne(HomeMainActivity.class);
                                            }
                                        },
                                        new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                AccountRememberCtrl.saveLoginAD(Register2Activity.this, "0");
                                                ScreenManager.getScreenManager().popAllActivityExceptOne(HomeMainActivity.class);
                                            }
                                        });
                                EventBus.getDefault().post(new CarInfoEvent(true));
                            } else {
                                ToastUtils.toastShort(result.getSYS_HEAD().getReturnMessage());
                            }
                        }

                        @Override
                        public void onError(String errorCode, String msg) {
                            super.onError(errorCode, msg);
                            hideDialogLoading();
                        }
                    });
                } else {
                    RegisterDTO dto = new RegisterDTO();
                    String phone = RSAUtils.strByEncryption(getIntent().getStringExtra(PHONE), true);
                    dto.setPhoenum(phone);
                    try {
                        SHATools sha = new SHATools();
                        String password = SHATools.hexString(sha.eccryptSHA1(pwd));
                        password = RSAUtils.strByEncryption(password, true);
                        dto.setPswd(password);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    showDialogLoading();
                    UserApiClient.reset(ContextUtils.getContext(), dto, new CallBack<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult result) {
                            hideDialogLoading();
                            if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                                ToastUtils.toastShort("密码已重置");
                                finish();
                            }
                        }

                        @Override
                        public void onError(String errorCode, String msg) {
                            super.onError(errorCode, msg);
                            hideDialogLoading();
                        }
                    });

                }
                break;
        }
    }

    private void liyingreg(String pwd, String userId) {
        try {
            SHATools sha = new SHATools();
            String password = SHATools.hexString(sha.eccryptSHA1(pwd));
            LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
            liYingRegDTO.setPhoenum(RSAUtils.strByEncryptionLiYing(getIntent().getStringExtra(PHONE), true));
            liYingRegDTO.setPswd(RSAUtils.strByEncryptionLiYing(password, true));
            liYingRegDTO.setUsrid(RSAUtils.strByEncryptionLiYing(userId, true));
            CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void commitLocalCar() {
        List<CarInfoDTO> list = SPUtils.getInstance().getCarsInfo();
        if (null == list && list.size() <= 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            final int index = i;
            CarInfoDTO carInfoDTO = list.get(i);
            carInfoDTO.setUsrid(MemoryData.getInstance().userID);
            carInfoDTO.setIspaycar("0");
            carInfoDTO.setDefaultflag("1");
            carInfoDTO.setInspectflag("0");
            carInfoDTO.setViolationflag("0");
            carInfoDTO.setCarmodel("");
            carInfoDTO.setInspectdate("");
            UserApiClient.addCarInfo(getApplicationContext(), carInfoDTO, new CallBack<BankResponse>() {
                @Override
                public void onSuccess(BankResponse bankResponse) {
                    EventBus.getDefault().post(new CarInfoEvent(true));
                    SPUtils.getInstance().getCarsInfo().remove(index);
                }
            });
        }
        for (int i = 0; i < list.size(); i++) {
            CarInfoDTO carInfoDTO = list.get(i);
            BindCarDTO params = new BindCarDTO();
            params.setPlateNo(carInfoDTO.getCarnum());
            params.setFileNum("");
            params.setVehicleType(carInfoDTO.getCarnumtype());
            params.setAddress("");
            params.setUseCharacter("");
            params.setCarModel(carInfoDTO.getCarmodel());
            params.setVin("");
            params.setEngineNo(carInfoDTO.getEnginenum());
            params.setRegisterDate(carInfoDTO.getInspectdate());
            params.setIssueDate("");
            params.setUsrnum(MemoryData.getInstance().userID);
            CarApiClient.commitCar(getApplicationContext(), params, new CallBack<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse result) {
                }
            });
        }
    }

    private void commitIllegalHistory() {
        try {
            LinkedList<QueryHistoryBean.QueryCarBean> mDatas = MemoryData.getInstance().mQueryHistoryBean.getQueryCar();
            if (null == mDatas && mDatas.size() <= 0) {
                return;
            }
            for (int i = 0; i < mDatas.size(); i++) {
                QueryHistoryBean.QueryCarBean queryCarBean = mDatas.get(i);
                BindCarDTO params = new BindCarDTO();
                params.setPlateNo(queryCarBean.getCarNumber());
                params.setFileNum("");
                params.setVehicleType(queryCarBean.getCarnumtype());
                params.setAddress("");
                params.setUseCharacter("");
                params.setCarModel("");
                params.setVin("");
                params.setEngineNo(queryCarBean.getEngineNumber());
                params.setRegisterDate("");
                params.setIssueDate("");
                params.setUsrnum(MemoryData.getInstance().userID);
                CarApiClient.commitCar(getApplicationContext(), params, new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mNumKeyBoard.getNumDelView() || view == mCharKeyBoard.getCharDelView()) {
            if (mFoucsPos == 0) {
                mPwd.setText("");
            } else {
                mPwd2.setText("");
            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (v.getId() == R.id.register_pwd && mFoucsPos == 1) {
                    mFoucsPos = 0;
                    hideInputType();
                    mCharKeyBoard.setVisibility(View.GONE);
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    mPwd.requestFocus();
                    mPwd.setCursorVisible(true);
                } else if (v.getId() == R.id.register_repwd && mFoucsPos == 0) {
                    mFoucsPos = 1;
                    hideInputType();
                    mCharKeyBoard.setVisibility(View.GONE);
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    mPwd2.requestFocus();
                    mPwd2.setCursorVisible(true);
                }
                break;
        }
        return true;
    }

    /**
     * 判断当前系统版本，选择使用何种方式隐藏默认键盘
     */
    private void hideInputType() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mFoucsPos == 0) {
            imm.hideSoftInputFromWindow(mPwd.getWindowToken(), 0); //强制隐藏键盘
        } else {
            imm.hideSoftInputFromWindow(mPwd2.getWindowToken(), 0); //强制隐藏键盘
        }
    }
}