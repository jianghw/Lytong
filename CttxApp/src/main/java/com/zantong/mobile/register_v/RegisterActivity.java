package com.zantong.mobile.register_v;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tzly.annual.base.bean.Result;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.BuildConfig;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.base.activity.BaseMvpActivity;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.contract.IOrderView;
import com.zantong.mobile.presenter.OrderPresenter;
import com.zantong.mobile.user.bean.VcodeResult;
import com.zantong.mobile.user.dto.VcodeDTO;
import com.zantong.mobile.utils.ValidateUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class RegisterActivity extends BaseMvpActivity<IOrderView, OrderPresenter> {

    @Bind(R.id.register_next_btn)
    Button mNext;
    @Bind(R.id.register_vcode_btn)
    Button mVcodeBtn;
    @Bind(R.id.register_del)
    ImageView mDel;
    @Bind(R.id.register_vcode)
    EditText mVCode;
    @Bind(R.id.register_phone)
    EditText mPhone;

    private Timer timer;
    private int iTime = -1;
    private boolean codeFlag = false;
    private boolean phoneFlag = false;

    String onlyflag = "";

    //輸入的手机号
    String phone;

    @Override
    public void initView() {
        setTitleText("注册");
        init();
    }

    @Override
    public void initData() {
        mNext.setOnClickListener(this);
        mVcodeBtn.setOnClickListener(this);
        mDel.setOnClickListener(this);
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_register;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_vcode_btn:
                getVerifyCode();
                break;
            case R.id.register_del:
                mPhone.setText("");
                break;

            case R.id.register_next_btn:
                checkVerifyCode();
                break;
        }
    }

    private void init() {

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        iTime--;
                        mVcodeBtn.setEnabled(false);
                        mVcodeBtn.setText(iTime + " s");
                        break;
                    case 1:
                        mVcodeBtn.setEnabled(true);
                        mVcodeBtn.setText("重发验证码");
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

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mDel.setVisibility(View.VISIBLE);
                } else {
                    mDel.setVisibility(View.GONE);

                }
                if (ValidateUtils.isMobile(s.toString()) && (iTime == 0 || iTime == -1)) {
                    mVcodeBtn.setEnabled(true);
                    phoneFlag = true;
                } else {
                    mVcodeBtn.setEnabled(false);
                    phoneFlag = false;
                }
                if (codeFlag && phoneFlag) {
                    mNext.setEnabled(true);
                } else {
                    mNext.setEnabled(true);
                }
            }
        });

        mVCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    codeFlag = true;
                } else {
                    codeFlag = false;
                }
                if (codeFlag && phoneFlag) {
                    mNext.setEnabled(true);
                } else {
                    mNext.setEnabled(true);
                }
            }
        });
    }


    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        iTime = MemoryData.getInstance().smCtrlTime;
        if (iTime > 0) {
            mVcodeBtn.setEnabled(false);
            mVcodeBtn.setText("60s");
        }
        VcodeDTO dto = new VcodeDTO();
        dto.setPhoenum(mPhone.getText().toString());
        dto.setSmsscene("001");
        UserApiClient.getVerifyCode(this, dto, new CallBack<VcodeResult>() {
            @Override
            public void onSuccess(VcodeResult result) {

                if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                    if ("0".equals(result.getRspInfo().getRgstste())) {
                        onlyflag = result.getRspInfo().getOnlyflag();
                    } else {
                        iTime = 0;
                        ToastUtils.toastShort("该用户已注册");
                        mPhone.setText("");
                    }
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                iTime = 0;
            }
        });
    }

    /**
     * 校验验证码
     */
    private void checkVerifyCode() {
        phone = mPhone.getText().toString();
        if (BuildConfig.LOG_DEBUG) {
            Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
            intent.putExtra(Register2Activity.RES_CODE, 0);
            intent.putExtra(Register2Activity.PHONE, phone);
            startActivity(intent);
            finish();
        } else {
            onCheckVerifyCode();
        }
    }

    private void onCheckVerifyCode() {
        String vcode = mVCode.getText().toString();
        if (TextUtils.isEmpty(vcode)) {
            ToastUtils.toastShort("验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(onlyflag)) {
            ToastUtils.toastShort("请先获取验证码");
            return;
        }
        VcodeDTO dto = new VcodeDTO();
        dto.setSmsscene("001");
        dto.setPhoenum(phone);
        dto.setCaptcha(vcode);
        dto.setOnlyflag(onlyflag);
        showDialogLoading();
        UserApiClient.checkVerifyCode(this, dto, new CallBack<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        hideDialogLoading();
                        if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                            Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                            intent.putExtra(Register2Activity.RES_CODE, 0);
                            intent.putExtra(Register2Activity.PHONE, phone);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(String errorCode, String msg) {
                        hideDialogLoading();
                    }
                }
        );
    }
}