package com.tzly.ctcyh.user.pw_v;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.RegexUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.user.BuildConfig;
import com.tzly.ctcyh.user.R;
import com.tzly.ctcyh.user.bean.BankResponse;
import com.tzly.ctcyh.user.bean.response.VCodeResponse;
import com.tzly.ctcyh.user.data_m.InjectionRepository;
import com.tzly.ctcyh.user.global.UserGlobal;
import com.tzly.ctcyh.user.pw_p.ISendCodeContract;
import com.tzly.ctcyh.user.pw_p.SendCodePresenter;
import com.tzly.ctcyh.user.router.UserRouter;

/**
 * Created by jianghw on 2017/12/8.
 * Description:
 * Update by:
 * Update day:
 */

public class SendCodeFragment extends RefreshFragment
        implements View.OnClickListener, ISendCodeContract.ISendCodeView {
    /**
     * 请输入您的手机号码
     */
    private EditText mPhone;
    /**
     * 获取验证码
     */
    private Button mVcodeBtn;
    private TextView mUnderline;
    /**
     * 请输入验证码
     */
    private EditText mVCode;
    /**
     * 下一步
     */
    private Button mNext;
    private ISendCodeContract.ISendCodePresenter mPresenter;
    /**
     * 标记
     */
    private String mOnlyflag;

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return false;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    public static SendCodeFragment newInstance(String extraHost) {
        SendCodeFragment f = new SendCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserGlobal.Host.login_code_host, extraHost);
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected int fragmentView() {
        return R.layout.user_fragment_send_code;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);
    }

    @Override
    protected void loadingFirstData() {
        SendCodePresenter presenter = new SendCodePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void responseData(Object response) {
    }

    public void initView(View view) {
        TextView tvTip = (TextView) view.findViewById(R.id.tv_tip);
        mPhone = (EditText) view.findViewById(R.id.reset_phone);
        mVcodeBtn = (Button) view.findViewById(R.id.reset_vcode_btn);
        mVcodeBtn.setOnClickListener(this);
        mUnderline = (TextView) view.findViewById(R.id.underline);
        mVCode = (EditText) view.findViewById(R.id.reset_vcode);
        mNext = (Button) view.findViewById(R.id.reset_next_btn);
        mNext.setOnClickListener(this);

        String host = getHostString();
        tvTip.setVisibility(host.equals(UserGlobal.Host.code_pw_host)
                ? View.VISIBLE : View.GONE);
    }

    private String getHostString() {
        return getArguments().getString(UserGlobal.Host.login_code_host);
    }

    public String getPhone() {
        return mPhone.getText().toString().trim();
    }

    public void setPhone(String phone) {
        mPhone.setText(phone);
    }

    public String getVCode() {
        return mVCode.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset_vcode_btn) {//码
            String phone = getPhone();
            if (TextUtils.isEmpty(phone) || !RegexUtils.isMobileSimple(phone)) {
                toastShort("请输入正确的手机号码");
            } else if (mPresenter != null) {
                boolean register = getHostString().equals(UserGlobal.Host.code_register_host);
                mPresenter.sendVerificationCode(register);
                mPresenter.startCountDown();
                mNext.setEnabled(true);
            }
        } else if (v.getId() == R.id.reset_next_btn) {//提交
            if (BuildConfig.App_Url) gotoRegister();
            else
                verificationData();
        }
    }

    /**
     * 验证数据
     */
    private void verificationData() {
        String phone = getPhone();
        String vCode = getVCode();
        if (TextUtils.isEmpty(phone) || !RegexUtils.isMobileSimple(phone)) {
            toastShort("请输入正确的手机号码");
        } else if (TextUtils.isEmpty(vCode)) {
            toastShort("请输入验证码");
        } else if (TextUtils.isEmpty(mOnlyflag)) {
            toastShort("请重新获取验证码");
        } else if (mPresenter != null) {
            mPresenter.v_p002_01();
        }
    }

    @Override
    public void setPresenter(ISendCodeContract.ISendCodePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void codeBtnEnable(boolean b) {
        if (mVcodeBtn != null) mVcodeBtn.setEnabled(b);
        if (b && mVcodeBtn != null) mVcodeBtn.setText("获取验证码");
    }

    /**
     * 验证码
     */
    @Override
    public void verificationCodeError(String message) {
        toastShort(message);
    }

    /**
     * 0 未注册  1已注册
     */
    @Override
    public void verificationCodeSucceed(VCodeResponse response) {
        String host = getHostString();
        String type = response.getRspInfo().getRgstste();
        if (host.equals(UserGlobal.Host.code_pw_host) && type.equals("0")) {//重设密码
            toastShort("该用户暂未注册");
        } else if (host.equals(UserGlobal.Host.code_register_host) && type.equals("1")) {
            toastShort("该用户已经注册");
        } else {
            toastShort("请求成功,等待验证短信");
            mOnlyflag = response.getRspInfo().getOnlyflag();
        }
    }

    /**
     * 倒计时
     */
    @Override
    public void countDownTextView(long l) {
        if (mVcodeBtn != null) mVcodeBtn.setText(l + " s");
    }

    @Override
    public String getCode() {
        return getVCode();
    }

    @Override
    public String getFlag() {
        return mOnlyflag;
    }

    @Override
    public void v_p002_01Succeed(BankResponse response) {
        toastShort("验证成功");

        gotoRegister();
    }

    private void gotoRegister() {
        UserRouter.gotoRegisterActivity(getContext(),
                getHostString(),
                getPhone());
    }

    @Override
    public void v_p002_01Error(String message) {
        toastShort(message);
    }
}
