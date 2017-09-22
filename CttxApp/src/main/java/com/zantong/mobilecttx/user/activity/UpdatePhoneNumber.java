package com.zantong.mobilecttx.user.activity;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.presenter.UpdatePhoneNumberPresenter;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.contract.ModelView;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

public class UpdatePhoneNumber extends BaseMvpActivity<IBaseView, UpdatePhoneNumberPresenter> implements View.OnClickListener, IBaseView, ModelView {

    @Bind(R.id.btn_number)
    Button btnNumber;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.edit_phone_number)
    EditText edit_phone_number;
    @Bind(R.id.edit_old_phone)
    EditText edit_old_phone;
    @Bind(R.id.image_delete)
    ImageView imageDelete;
    @Bind(R.id.edit_code_number)
    EditText edit_code_number;


    private boolean codeFlag = false;
    private boolean phoneFlag = false;
    private Timer timer;
    private int iTime = -1;

    @Override
    public UpdatePhoneNumberPresenter initPresenter() {
        return new UpdatePhoneNumberPresenter(UpdatePhoneNumber.this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.update_phone_number;
    }

    @Override
    public void initView() {
        setTitleText("改绑手机");
        init();
    }

    @Override
    public void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public HashMap<String, String> mapData(){
        HashMap<String, String>  mHashMap = new HashMap<>();
        mHashMap.put("phoenum", edit_phone_number.getText().toString());
        mHashMap.put("captcha", edit_code_number.getText().toString());
        return mHashMap;
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
                if (s.length() > 0) {
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


    @OnClick({R.id.btn_number, R.id.login_btn, R.id.image_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_number:
                presenter.loadView(1);

                break;
            case R.id.login_btn:
                String oldPhone = edit_old_phone.getText().toString();
                if(ValidateUtils.isMobile(oldPhone) &&
                        !PublicData.getInstance().mLoginInfoBean.getPhoenum().equals(oldPhone)){
                    ToastUtils.showShort(this,"您输入的原手机号不正确");
                }else if(ValidateUtils.isMobile(edit_phone_number.getText().toString())){
                    //presenter.loadView(2);
                    presenter.loadView(3);
                }else{
                    ToastUtils.showShort(this,"您输入的手机号格式不正确");
                }

                break;
            case R.id.image_delete:
                edit_phone_number.setText("");
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void updateView(Object object, int index) {
        switch (index){
            case 1:
                iTime = PublicData.getInstance().smCtrlTime;
                if(iTime > 0){
//                    Toast.makeText(LoginPhone.this,"验证码发送成功，请注意查收", Toast.LENGTH_SHORT).show();
                    btnNumber.setEnabled(false);
                    btnNumber.setText("60s");
                }
                break;
            case 2:
                if(PublicData.getInstance().success.equals(((UpdateInfo) object).getSYS_HEAD().getReturnCode())){
                    PublicData.getInstance().mLoginInfoBean.setPhoenum(edit_phone_number.getText().toString());
                    UserInfoRememberCtrl.saveObject(PublicData.getInstance().mLoginInfoBean);
                    finish();
                }
                break;
            case 3:
                break;
        }


    }

    @Override
    public void hideProgress() {

    }
}
