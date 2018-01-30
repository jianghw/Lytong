package com.zantong.mobile.login_v;

import android.Manifest;
import android.content.Context;
import android.os.Build;
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
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.annual.base.JxBaseActivity;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.RegexUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.primission.PermissionFail;
import com.tzly.annual.base.util.primission.PermissionGen;
import com.tzly.annual.base.util.primission.PermissionSuccess;
import com.zantong.mobile.R;
import com.zantong.mobile.application.Injection;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.presenter.login_p.ILoginContract;
import com.zantong.mobile.presenter.login_p.LoginPresenter;
import com.zantong.mobile.register_v.RegisterActivity;
import com.zantong.mobile.reset_v.ResetActivity;
import com.zantong.mobile.utils.Tools;
import com.zantong.mobile.utils.jumptools.Act;
import com.zantong.mobile.widght.CustomCharKeyBoard;
import com.zantong.mobile.widght.CustomNumKeyBoard;

import java.util.ArrayList;

/**
 * 登陆界面
 */
public class LoginActivity extends JxBaseActivity implements
        View.OnTouchListener, View.OnClickListener, View.OnLongClickListener, ILoginContract.ILoginView {

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

    @Override
    public void onClick(View view) {
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
                mCharKeyboard.getCharTabViewImg().setBackgroundResource(R.mipmap.icon_xiaoxie_btn);
                mCharKeyboard.changeLower();
            } else {
                isDaxie = true;
                mCharKeyboard.getCharTabViewImg().setBackgroundResource(R.mipmap.icon_daxie_btn);
                mCharKeyboard.changeUpper();
            }
        }

        switch (view.getId()) {
            case R.id.btn_login:
                validationSubmitData();
                break;
            case R.id.tv_forget_pw:
                gotoResetActivity();
                break;
        }
    }

    private void validationSubmitData() {
        hitCustomKeyboard();
        String userPhone = getUserPhone();
        if (TextUtils.isEmpty(userPhone) || !RegexUtils.isMobileSimple(userPhone)) {
            ToastUtils.toastLong("请输入正确的手机号码");
            return;
        }
        String userPassword = getUserPassword();
        if (TextUtils.isEmpty(userPassword) || !RegexUtils.isMatch("^[A-Za-z0-9]{6,20}$", userPhone)) {
            ToastUtils.toastLong("请输入符合规则的密码");
            return;
        }
        if (mPresenter != null) mPresenter.userLogin();
    }

    private void gotoResetActivity() {
        Act.getInstance().lauchIntent(this, ResetActivity.class);
    }

    private void hitCustomKeyboard() {
        mNumKeyboard.setVisibility(View.GONE);
        mCharKeyboard.setVisibility(View.GONE);
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mNumKeyboard.getNumDelView() || view == mCharKeyboard.getCharDelView()) {
            mEdtCode.setText("");
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.edt_code) {
            hideInputType();
            mEdtCode.requestFocus();
            mNumKeyboard.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.edt_phone) {
            hitCustomKeyboard();
            mEdtPhone.requestFocus();
            showInputType();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) mPresenter.unSubscribe();
        hideInputType();
        hideInputManager();
        hitCustomKeyboard();
    }

    /**
     * 回退监听功能
     */
    protected void backClickListener() {
        finish();
    }

    /**
     * 页面关闭
     */
    protected void closeClickListener() {
        finish();
    }

    /**
     * 右边文字点击
     */
    protected void rightClickListener() {
        Act.getInstance().lauchIntent(this, RegisterActivity.class);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindContentView(View childView) {
        titleContent("欢迎加入熊猫小助手", "注册");
        initView(childView);

        LoginPresenter presenter = new LoginPresenter(
                Injection.provideRepository(ContextUtils.getContext()), this);
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected void initContentData() {
        mListNum = mNumKeyboard.getRandomList();
        mListChar = mCharKeyboard.getRandomList();

        for (int i = 0; i < mListNum.size(); i++) {
            mListNum.get(i).setOnClickListener(this);
        }
        for (int i = 0; i < mListChar.size(); i++) {
            mListChar.get(i).setOnClickListener(this);
        }

        initTextListener();
        takePhoneIMEI();

        mEdtPhone.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框
        mEdtCode.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框

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
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    mNumKeyboard.setVisibility(View.VISIBLE);
                    mEdtCode.requestFocus();
                    hideInputType();
                }
                return false;
            }
        });
    }

    public void takePhoneIMEI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, 100,
                    new String[]{Manifest.permission.READ_PHONE_STATE}
            );
        } else {
            MemoryData.getInstance().imei = Tools.getIMEI();
        }
    }

    private void initTextListener() {
        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mEdtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void initView(View childView) {
        mEdtPhone = (EditText) childView.findViewById(R.id.edt_phone);
        mEdtCode = (EditText) childView.findViewById(R.id.edt_code);
        mBtnLogin = (Button) childView.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mTvForgetPw = (TextView) childView.findViewById(R.id.tv_forget_pw);
        mTvForgetPw.setOnClickListener(this);
        mNumKeyboard = (CustomNumKeyBoard) childView.findViewById(R.id.num_keyboard);
        mCharKeyboard = (CustomCharKeyBoard) childView.findViewById(R.id.char_keyboard);
    }

    /**
     * 显示默认键盘
     */
    private void showInputType() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(mEdtPhone, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 判断当前系统版本，选择使用何种方式隐藏默认键盘
     */
    private void hideInputType() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEdtCode.getWindowToken(), 0); //强制隐藏键盘
    }

    //判断是否隐藏输入法键盘
    private void hideInputManager() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive() && this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void doPermissionIMEISuccess() {
        MemoryData.getInstance().imei = Tools.getIMEI();
    }

    @PermissionFail(requestCode = 100)
    public void doPermissionIMEIFail() {
        ToastUtils.toastShort("手机识别码权限被拒绝，请手机设置中打开");
    }

    @Override
    public void setPresenter(ILoginContract.ILoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    @Override
    public void userLoginError(String message) {
        ToastUtils.toastShort(message);
        dismissLoadingDialog();
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
    public void userLoginSucceed() {
        ToastUtils.toastShort("登录成功!");

        if (MemoryData.getInstance().className != null) {
            Act.getInstance().lauchIntent(this, MemoryData.getInstance().className);
        }
    }

    @Override
    public void registerSucceed() {
        finish();
    }

    @Override
    public void registerError(String message) {
        ToastUtils.toastShort("数据同步失败," + message);
    }
}
