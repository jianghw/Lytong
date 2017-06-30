package com.zantong.mobilecttx.user.activity;

import android.content.Context;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.user.dto.ChangePwdDTO;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.presenter.OrderPresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.TextWatchUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.xmlparser.SHATools;
import com.zantong.mobilecttx.interf.IOrderView;
import com.zantong.mobilecttx.widght.CustomCharKeyBoard;
import com.zantong.mobilecttx.widght.CustomNumKeyBoard;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import butterknife.Bind;
public class ChangePwdActivity extends BaseMvpActivity<IOrderView, OrderPresenter> implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {

    @Bind(R.id.register_next_btn)
    Button mCommit;
    @Bind(R.id.changepwd_old_pwd)
    EditText mOldPwd;
    @Bind(R.id.changepwd_new_pwd)
    EditText mNewPwd;
    @Bind(R.id.changepwd_new_repwd)
    EditText mNewRePwd;
    @Bind(R.id.mine_changepwd_numkeyboard)
    CustomNumKeyBoard mNumKeyBoard;
    @Bind(R.id.mine_changepwd_charkeyboard)
    CustomCharKeyBoard mCharKeyBoard;

    ArrayList<TextView> mListNum;
    ArrayList<TextView> mListChar;
    boolean isDaxie;
    int mFoucsPos = 1;//0 ：第一个输入框获取焦点 1： 第二个输入框获取焦点 2: 第三个获取焦点

    @Override
    public void initView() {
        setTitleText("修改密码");

        mListNum = mNumKeyBoard.getRandomList();
        mListChar = mCharKeyBoard.getRandomList();
        for (int i = 0; i < mListNum.size(); i++) {
            mListNum.get(i).setOnClickListener(this);
        }
        for (int i = 0; i < mListChar.size(); i++) {
            mListChar.get(i).setOnClickListener(this);
        }


        mOldPwd.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框
        mNewPwd.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框
        mNewRePwd.setOnTouchListener(this);// 对输入框增加触摸事件，让其显示自定义输入框

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

    }

    @Override
    public void initData() {
        mCommit.setOnClickListener(this);
        mOldPwd.addTextChangedListener(new TextWatchUtils(mCommit));
        mNewPwd.addTextChangedListener(new TextWatchUtils(mCommit));
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_changepwd;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Editable editable;
        int start = 0;
        if (mFoucsPos == 0) {
            editable = mOldPwd.getText();
            start = mOldPwd.getSelectionStart();
        } else if (mFoucsPos == 1) {
            editable = mNewPwd.getText();
            start = mNewPwd.getSelectionStart();
        } else {
            editable = mNewRePwd.getText();
            start = mNewRePwd.getSelectionStart();
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
            case R.id.register_next_btn:
                commitPwd();
                break;
        }
    }

    /**
     * 修改密码
     */
    private void commitPwd() {
        String oldPwd = mOldPwd.getText().toString();
        String newPwd = mNewPwd.getText().toString();
        String newRePwd = mNewRePwd.getText().toString();
        if (!ValidateUtils.checkPwd(newPwd)) {
            ToastUtils.showShort(this, "密码位数不对或存在非法字符");
            return;
        } else if (ValidateUtils.checkNum(newPwd)) {
            ToastUtils.showShort(this, "密码不能为纯数字");
            return;
        } else if (ValidateUtils.checkChar(newPwd)) {
            ToastUtils.showShort(this, "密码不能为纯字母");
            return;
        } else if (!newPwd.equals(newRePwd)) {
            ToastUtils.showShort(this, "两次输入的密码不一致");
            return;
        }
        ChangePwdDTO dto = new ChangePwdDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        try {
            SHATools sha = new SHATools();
            String newPassword = SHATools.hexString(sha.eccryptSHA1(newPwd));
            final String newPwdLi = newPassword;
            String oldPassword = SHATools.hexString(sha.eccryptSHA1(oldPwd));
            newPassword = RSAUtils.strByEncryption(this, newPassword, true);
            oldPassword = RSAUtils.strByEncryption(this, oldPassword, true);
            dto.setNewpswd(newPassword);
            dto.setOldpswd(oldPassword);
            showDialogLoading();
            UserApiClient.changePwd(this, dto, new CallBack<Result>() {
                @Override
                public void onSuccess(Result result) {
                    hideDialogLoading();
                    if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                        liyingreg(newPwdLi);
                        PublicData.getInstance().clearData(ChangePwdActivity.this);
                        finish();
                    } else {
                        ToastUtils.showShort(ChangePwdActivity.this, result.getSYS_HEAD().getReturnMessage());
                    }
                }

                @Override
                public void onError(String errorCode, String msg) {
                    super.onError(errorCode, msg);
                    hideDialogLoading();
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void liyingreg(String pwd) {
        try {
            String phone = RSAUtils.strByEncryptionLiYing(this, PublicData.getInstance().mLoginInfoBean.getPhoenum(), true);
            SHATools sha = new SHATools();
            String newPassword = RSAUtils.strByEncryptionLiYing(this,SHATools.hexString(sha.eccryptSHA1(pwd)), true);
            LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
            liYingRegDTO.setPhoenum(phone);
            liYingRegDTO.setPswd(newPassword);
            liYingRegDTO.setUsrid(RSAUtils.strByEncryption(this, PublicData.getInstance().userID, true));
            CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mNumKeyBoard.getNumDelView() || view == mCharKeyBoard.getCharDelView()) {
            if (mFoucsPos == 0) {
                mOldPwd.setText("");
            } else if (mFoucsPos == 1) {
                mNewPwd.setText("");
            } else {
                mNewRePwd.setText("");
            }
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (v.getId() == R.id.changepwd_old_pwd && mFoucsPos != 0) {
                    mFoucsPos = 0;
                    hideInputType();
                    mCharKeyBoard.setVisibility(View.GONE);
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    mOldPwd.requestFocus();
                    mOldPwd.setCursorVisible(true);
                } else if (v.getId() == R.id.changepwd_new_pwd && mFoucsPos != 1) {
                    mFoucsPos = 1;
                    hideInputType();
                    mCharKeyBoard.setVisibility(View.GONE);
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    mNewPwd.requestFocus();
                    mNewPwd.setCursorVisible(true);
                } else if (v.getId() == R.id.changepwd_new_repwd && mFoucsPos != 2) {
                    mFoucsPos = 2;
                    hideInputType();
                    mCharKeyBoard.setVisibility(View.GONE);
                    mNumKeyBoard.setVisibility(View.VISIBLE);
                    mNewRePwd.requestFocus();
                    mNewRePwd.setCursorVisible(true);
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
            imm.hideSoftInputFromWindow(mOldPwd.getWindowToken(), 0); //强制隐藏键盘
        } else if (mFoucsPos == 1) {
            imm.hideSoftInputFromWindow(mNewPwd.getWindowToken(), 0); //强制隐藏键盘
        } else if (mFoucsPos == 2) {
            imm.hideSoftInputFromWindow(mNewRePwd.getWindowToken(), 0); //强制隐藏键盘
        }
    }

}