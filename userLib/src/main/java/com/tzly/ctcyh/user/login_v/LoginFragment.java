package com.tzly.ctcyh.user.login_v;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.KeyboardUtils;
import com.tzly.ctcyh.router.util.RegexUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.primission.PermissionFail;
import com.tzly.ctcyh.router.util.primission.PermissionGen;
import com.tzly.ctcyh.router.util.primission.PermissionSuccess;
import com.tzly.ctcyh.user.R;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.custom.CustomCharKeyBoard;
import com.tzly.ctcyh.user.custom.CustomNumKeyBoard;
import com.tzly.ctcyh.user.data_m.InjectionRepository;
import com.tzly.ctcyh.user.global.UserGlobal;
import com.tzly.ctcyh.user.login_p.ILoginContract;
import com.tzly.ctcyh.user.login_p.LoginPresenter;
import com.tzly.ctcyh.user.router.UserRouter;

import java.util.ArrayList;

import static com.tzly.ctcyh.router.util.ToastUtils.toastLong;

/**
 * 用户登录
 */
public class LoginFragment extends RefreshFragment
        implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener,
        ILoginContract.ILoginView {
    /**
     * 请输入您的手机号
     */
    private EditText mEdtPhone;
    /**
     * 请输入密码
     */
    private EditText mEdtCode;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 忘记密码?
     */
    private TextView mTvForgetPw;
    private CustomNumKeyBoard mNumKeyboard;
    private CustomCharKeyBoard mCharKeyboard;

    ArrayList<TextView> mListNum;
    ArrayList<TextView> mListChar;
    private ILoginContract.ILoginPresenter mPresenter;
    private boolean isDaxie;

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

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int fragmentView() {
        return R.layout.user_fragment_login;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        LoginPresenter presenter = new LoginPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
        //获取设备号
        takePhoneIMEI();
    }

    public void initView(View childView) {
        mEdtPhone = (EditText) childView.findViewById(R.id.edt_pass);
        mEdtCode = (EditText) childView.findViewById(R.id.edt_word);
        mBtnLogin = (Button) childView.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPw = (TextView) childView.findViewById(R.id.tv_forget_pw);
        mTvForgetPw.setOnClickListener(this);
        mNumKeyboard = (CustomNumKeyBoard) childView.findViewById(R.id.num_keyboard);
        mCharKeyboard = (CustomCharKeyBoard) childView.findViewById(R.id.char_keyboard);
    }

    /**
     * 手机设备id获取
     */
    public void takePhoneIMEI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PermissionGen.PER_REQUEST_CODE,
                    new String[]{Manifest.permission.READ_PHONE_STATE}
            );
        } else {
            getDeviceId();
        }
    }

    @Override
    protected void loadingFirstData() {
        mListNum = mNumKeyboard.getRandomList();
        mListChar = mCharKeyboard.getRandomList();

        for (int i = 0; i < mListNum.size(); i++) {
            mListNum.get(i).setOnClickListener(this);
        }
        for (int i = 0; i < mListChar.size(); i++) {
            mListChar.get(i).setOnClickListener(this);
        }

        initTextListener();
    }

    @Override
    protected void responseData(Object response) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        hitCustomKeyboard();
        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 输入监控
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initTextListener() {
        // 对输入框增加触摸事件，让其显示自定义输入框
        mEdtPhone.setOnTouchListener(this);
        mEdtCode.setOnTouchListener(this);

        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEdtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mNumKeyboard.getChangeAbcView().setOnClickListener(this);
        mNumKeyboard.getNumDelView().setOnClickListener(this);
        mNumKeyboard.getNumDelView().setOnLongClickListener(this);
        mNumKeyboard.getNumFinishView().setOnClickListener(this);
        mNumKeyboard.getNumHideView().setOnClickListener(this);

        mCharKeyboard.getCharDelView().setOnClickListener(this);
        mCharKeyboard.getCharDelView().setOnLongClickListener(this);
        mCharKeyboard.getCharFinishView().setOnClickListener(this);
        mCharKeyboard.getCharTabView().setOnClickListener(this);
        mCharKeyboard.getChangeNumView().setOnClickListener(this);
        mCharKeyboard.getCharHideView().setOnClickListener(this);

        mEdtPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {//下一步
                    KeyboardUtils.hideSoftInput(mEdtPhone);
                    mEdtCode.requestFocus();
                    numCustomKeyboard();
                }
                return true;
            }
        });
    }

    private void getDeviceId() {
        if (mPresenter != null) mPresenter.initPhoneDeviceId();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PermissionGen.PER_REQUEST_CODE)
    public void doPermissionIMEISuccess() {
        getDeviceId();
    }

    @PermissionFail(requestCode = PermissionGen.PER_REQUEST_CODE)
    public void doPermissionIMEIFail() {
        toastShort("手机识别码权限被拒绝，请手机设置中打开");
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            validationSubmitData();
            return;
        }
        if (view.getId() == R.id.tv_forget_pw) {
            gotoResetActivity();
            return;
        }

        Editable editable = mEdtCode.getText();
        int start = mEdtCode.getSelectionStart();

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
        if (view == mNumKeyboard.getChangeAbcView()) {
            mNumKeyboard.setVisibility(View.GONE);
            mCharKeyboard.setVisibility(View.VISIBLE);
        }
        //删除
        if (view == mNumKeyboard.getNumDelView() || view == mCharKeyboard.getCharDelView()) {
            if (editable != null && editable.length() > 0) {
                if (start > 0) editable.delete(start - 1, start);//开始，结束位置
            }
        }
        //完成
        if (view == mNumKeyboard.getNumFinishView() || view == mCharKeyboard.getCharFinishView()
                || view == mNumKeyboard.getNumHideView() || view == mCharKeyboard.getCharHideView()) {
            mNumKeyboard.setVisibility(View.GONE);
            mCharKeyboard.setVisibility(View.GONE);
        }

        //切换为数字输入
        if (view == mCharKeyboard.getChangeNumView()) {
            mCharKeyboard.setVisibility(View.GONE);
            mNumKeyboard.setVisibility(View.VISIBLE);
        }

        //切换大小写输入
        if (view == mCharKeyboard.getCharTabView()) {
            if (isDaxie) {
                isDaxie = false;
                mCharKeyboard.getCharTabViewImg().setBackgroundResource(R.mipmap.user_ic_char_lower);
                mCharKeyboard.changeLower();
            } else {
                isDaxie = true;
                mCharKeyboard.getCharTabViewImg().setBackgroundResource(R.mipmap.user_ic_char_upper);
                mCharKeyboard.changeUpper();
            }
        }
    }

    /**
     * 设置密码页面
     */
    private void gotoResetActivity() {
        UserRouter.gotoCodeActivity(getContext(), UserGlobal.Host.code_pw_host);
    }

    /**
     * 验证数据
     */
    private void validationSubmitData() {
        hitCustomKeyboard();
        KeyboardUtils.hideSoftInput(getActivity());

        String userPhone = getUserPhone();
        if (TextUtils.isEmpty(userPhone) ||
                !RegexUtils.isMobileSimple(userPhone)) {
            toastLong("请输入正确的手机号码");
            return;
        }
        String userPassword = getUserPassword();
        if (TextUtils.isEmpty(userPassword) ||
                !RegexUtils.isMatch("^[A-Za-z0-9]{6,20}$", userPassword)) {
            toastLong("请输入符合规则的密码");
            return;
        }
        if (mPresenter != null) mPresenter.userLogin();
    }

    /**
     * 自定义键盘收起
     */
    private void hitCustomKeyboard() {
        mNumKeyboard.setVisibility(View.GONE);
        mCharKeyboard.setVisibility(View.GONE);
    }

    private void numCustomKeyboard() {
        mCharKeyboard.setVisibility(View.GONE);
        mNumKeyboard.setVisibility(View.VISIBLE);
    }

    private void charCustomKeyboard() {
        mNumKeyboard.setVisibility(View.GONE);
        mCharKeyboard.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mNumKeyboard.getNumDelView()
                || view == mCharKeyboard.getCharDelView()) {
            mEdtCode.setText("");
        }
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.edt_word) {//密码
            KeyboardUtils.hideSoftInput(mEdtPhone);
            mEdtCode.requestFocus();
            numCustomKeyboard();
        } else if (v.getId() == R.id.edt_pass) {//手机号码
            hitCustomKeyboard();
            KeyboardUtils.showSoftInput(mEdtPhone);
        }
        return true;
    }

    @Override
    public void setPresenter(ILoginContract.ILoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void userLoginError(String message) {
        toastShort(message);
    }

    @Override
    public String getUserPhone() {
        return mEdtPhone.getText().toString().trim();
    }

    @Override
    public String getUserPassword() {
        return mEdtCode.getText().toString().trim();
    }

    @Override
    public void userLoginSucceed(LoginResponse loginInfoBean) {
        toastShort("银行服务器登录成功!");
    }

    @Override
    public void registerSucceed() {
        UserRouter.loginFilenumDialog(getActivity());
    }

    @Override
    public void registerError(String message) {
        toastShort("服务器数据同步失败!请重新登录" + message);
    }

    /**
     * 自动化操作 登录
     */
    public void automaticOperation(String mExtraPhone, String mExtraPassword) {
        if (mEdtPhone != null) mEdtPhone.setText(mExtraPhone);
        if (mEdtCode != null) mEdtCode.setText(mExtraPassword);

        if (!TextUtils.isEmpty(mExtraPhone)
                && !TextUtils.isEmpty(mExtraPassword) && mPresenter != null)
            validationSubmitData();
    }
}