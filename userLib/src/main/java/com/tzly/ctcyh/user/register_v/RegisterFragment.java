package com.tzly.ctcyh.user.register_v;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.util.RegexUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.user.R;
import com.tzly.ctcyh.user.bean.response.LoginResponse;
import com.tzly.ctcyh.user.custom.CustomCharKeyBoard;
import com.tzly.ctcyh.user.custom.CustomNumKeyBoard;
import com.tzly.ctcyh.user.data_m.InjectionRepository;
import com.tzly.ctcyh.user.global.UserGlobal;
import com.tzly.ctcyh.user.register_p.IRegisterContract;
import com.tzly.ctcyh.user.register_p.RegisterPresenter;
import com.tzly.ctcyh.user.router.UserRouter;

import java.util.ArrayList;

/**
 * Created by jianghw on 2017/12/8.
 * Description:
 * Update by:
 * Update day:
 */

public class RegisterFragment extends RefreshFragment
        implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener,
        IRegisterContract.IRegisterView {


    /**
     * 请输入6-20位密码(字母与数字组合)
     */
    private EditText mEdtPass;
    private TextView mUnderline;
    /**
     * 请再输入一遍
     */
    private EditText mEdtWord;

    /**
     * 注    册
     */
    private Button mBtnLogin;
    private CustomNumKeyBoard mNumKeyboard;
    private CustomCharKeyBoard mCharKeyboard;

    ArrayList<TextView> mListNum;
    ArrayList<TextView> mListChar;
    /**
     * 大小写
     */
    private boolean isDaxie;
    private IRegisterContract.IRegisterPresenter mPresenter;
    private int mFocusablePos;

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

    public static RegisterFragment newInstance(String extraHost, String extraPhone) {
        RegisterFragment f = new RegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserGlobal.Host.send_register_host, extraHost);
        bundle.putString(UserGlobal.putExtra.user_login_phone, extraPhone);
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected int fragmentView() {
        return R.layout.user_fragment_register;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        RegisterPresenter presenter = new RegisterPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);

        //获取设备号
        takePhoneIMEI();
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

    private void getDeviceId() {
        if (mPresenter != null) mPresenter.initPhoneDeviceId();
    }

    @SuppressLint("ClickableViewAccessibility")
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

        mEdtPass.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框
        mEdtWord.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框

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

        mEdtPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {//下一步
                    mEdtWord.requestFocus();
                    mFocusablePos = 1;
                    numCustomKeyboard();
                }
                return false;
            }
        });
    }

    @Override
    protected void responseData(Object response) {
    }

    public void initView(View view) {
        mEdtPass = (EditText) view.findViewById(R.id.edt_pass);
        mUnderline = (TextView) view.findViewById(R.id.underline);
        mEdtWord = (EditText) view.findViewById(R.id.edt_word);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mNumKeyboard = (CustomNumKeyBoard) view.findViewById(R.id.num_keyboard);
        mCharKeyboard = (CustomCharKeyBoard) view.findViewById(R.id.char_keyboard);

        String host = getArguments().getString(UserGlobal.Host.send_register_host);
        if (null != host && host.equals(UserGlobal.Host.code_pw_host)) {
            mBtnLogin.setText("重置密码");
        } else if (null != host && host.equals(UserGlobal.Host.code_register_host)) {
            mBtnLogin.setText("注   册");
        } else {
            mBtnLogin.setText("未知错误");
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            hitCustomKeyboard();
            verificationData();
            return;
        }

        Editable editable = null;
        int start = 0;
        if (mFocusablePos == 0) {
            editable = mEdtPass.getText();
            start = mEdtPass.getSelectionStart();

        } else if (mFocusablePos == 1) {
            editable = mEdtWord.getText();
            start = mEdtWord.getSelectionStart();
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
     * 数据验证
     */
    private void verificationData() {
        String pwd = getEdtPass();
        String pwd2 = getEdtWord();
        if (pwd.length() < 6 || pwd.length() > 20) {
            toastShort("密码位数不对");
            return;
        } else if (!RegexUtils.checkPwd(pwd)) {
            toastShort("密码存在非法字符");
            return;
        } else if (RegexUtils.checkNum(pwd)) {
            toastShort("密码不能为纯数字");
            return;
        } else if (RegexUtils.checkChar(pwd)) {
            toastShort("密码不能为纯字母");
            return;
        } else if (!pwd.equals(pwd2)) {
            toastShort("两次输入的密码不一致");
            return;
        }

        String host = getArguments().getString(UserGlobal.Host.send_register_host);
        if (null != host && host.equals(UserGlobal.Host.code_pw_host)) {
            if (mPresenter != null) mPresenter.v_u013_01();
        } else if (null != host && host.equals(UserGlobal.Host.code_register_host)) {
            if (mPresenter != null) mPresenter.v_u001_01();
        } else {
            toastShort("未知错误");
        }
    }

    public String getEdtPass() {
        return mEdtPass.getText().toString().trim();
    }

    public String getEdtWord() {
        return mEdtWord.getText().toString().trim();
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
            if (view.getId() == R.id.edt_pass)
                mEdtPass.setText("");
            else if (view.getId() == R.id.edt_word)
                mEdtWord.setText("");
        }
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (v.getId() == R.id.edt_pass) {//手机号码
                mFocusablePos = 0;
                mEdtPass.requestFocus();
                numCustomKeyboard();
            } else if (v.getId() == R.id.edt_word) {
                mFocusablePos = 1;
                mEdtWord.requestFocus();
                numCustomKeyboard();
            }
        }
        return true;
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

    @Override
    public void setPresenter(IRegisterContract.IRegisterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String getUserPhone() {
        return getArguments().getString(UserGlobal.putExtra.user_login_phone);
    }

    @Override
    public String getUserPassword() {
        return getEdtWord();
    }

    /**
     * 注册成功
     */
    @Override
    public void v_u001_01Error(String message) {
        toastShort(message);
    }

    @Override
    public void v_u001_01Succeed(LoginResponse response) {
        toastShort("注册成功");

        UserRouter.gotoLoginActivity(getContext(),
                getArguments().getString(UserGlobal.putExtra.user_login_phone),
                getEdtWord());
    }
}
