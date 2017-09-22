package com.zantong.mobile.user.activity;

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

import com.zantong.mobile.common.Config;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.UserApiClient;
import com.zantong.mobile.base.activity.BaseMvpActivity;
import com.zantong.mobile.base.bean.Result;
import com.zantong.mobile.user.bean.VcodeResult;
import com.zantong.mobile.user.dto.VcodeDTO;
import com.zantong.mobile.presenter.OrderPresenter;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.utils.ValidateUtils;
import com.zantong.mobile.contract.IOrderView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

/**
 * 重置密码
 * @author Sandy
 * create at 16/6/21 下午3:54
 */
public class ResetActivity extends BaseMvpActivity<IOrderView,OrderPresenter> {

    @Bind(R.id.reset_next_btn)
    Button mNext;
    @Bind(R.id.reset_vcode_btn)
    Button mVcodeBtn;
    @Bind(R.id.reset_del)
    ImageView mDel;
    @Bind(R.id.reset_vcode)
    EditText mVCode;
    @Bind(R.id.reset_phone)
    EditText mPhone;

    private Timer timer;
    private int iTime=-1;
    private boolean codeFlag = false;
    private boolean phoneFlag = false;

    String onlyflag = "";

    //輸入的手机号
    String phone;

    @Override
    public void initView() {
        setTitleText("重置密码");
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
        return R.layout.mine_reset;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.reset_vcode_btn:
                getVerifyCode();
                break;
            case R.id.reset_del:
                mPhone.setText("");
                break;
            case R.id.reset_next_btn:
                checkVerifyCode();
                break;
        }
    }

    private void init(){

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        iTime--;
                        mVcodeBtn.setEnabled(false);
                        mVcodeBtn.setText(iTime+" s");
                        break;
                    case 1:
                        mVcodeBtn.setEnabled(true);
                        mVcodeBtn.setText("重发验证码");
                        break;
                }
//				}

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
                if(s.length() > 0){
                    mDel.setVisibility(View.VISIBLE);
                }else{
                    mDel.setVisibility(View.GONE);

                }
                if(ValidateUtils.isMobile(s.toString()) && (iTime == 0 || iTime == -1)){
                    mVcodeBtn.setEnabled(true);
                    phoneFlag = true;
                }else{
                    mVcodeBtn.setEnabled(false);
                    phoneFlag = false;
                }
                if(codeFlag && phoneFlag){
                    mNext.setEnabled(true);
                }else{
                    mNext.setEnabled(false);
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
                    mNext.setEnabled(false);
                }
            }
        });
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        iTime = PublicData.getInstance().smCtrlTime;
        if (iTime > 0) {
            mVcodeBtn.setEnabled(false);
            mVcodeBtn.setText("60s");
        }
        VcodeDTO dto = new VcodeDTO();
        dto.setPhoenum(mPhone.getText().toString());
        dto.setSmsscene("001");
        UserApiClient.getVerifyCode2(this, dto, new CallBack<VcodeResult>() {
            @Override
            public void onSuccess(VcodeResult result) {
                if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                    if ("1".equals(result.getRspInfo().getRgstste())) {
                        onlyflag = result.getRspInfo().getOnlyflag();
                    } else {
                        iTime = 0;
                        ToastUtils.showShort(ResetActivity.this, "该用户暂未注册");
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
//        Intent intent = new Intent(ResetActivity.this, Register2Activity.class);
//        intent.putExtra(Register2Activity.RES_CODE, 1);
//        intent.putExtra(Register2Activity.PHONE, phone);
//        startActivity(intent);
//        finish();

        String vcode = mVCode.getText().toString();
        if (TextUtils.isEmpty(vcode)) {
            ToastUtils.showShort(this, "验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(onlyflag)) {
            ToastUtils.showShort(this, "onlyflag字段不能为空");
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
                        Intent intent = new Intent(ResetActivity.this, Register2Activity.class);
                        intent.putExtra(Register2Activity.RES_CODE, 1);
                        intent.putExtra(Register2Activity.PHONE, phone);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        );
    }
}