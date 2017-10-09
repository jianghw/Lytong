package com.zantong.mobile.user.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.bean.request.RegisterDTO;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.CarApiClient;
import com.zantong.mobile.base.activity.BaseMvpActivity;
import com.zantong.mobile.base.interf.IBaseView;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.contract.ModelView;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.login_v.LoginUserSPreference;
import com.zantong.mobile.presenter.UpdateNickNamePresenter;
import com.zantong.mobile.utils.rsa.RSAUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

public class UpdateNickName extends BaseMvpActivity<IBaseView, UpdateNickNamePresenter>
        implements View.OnClickListener, IBaseView, ModelView {
    @Bind(R.id.nick_name_edit)
    EditText nickNameEdit;
    @Bind(R.id.image_delete)
    ImageView imageDelete;

    @Override
    public UpdateNickNamePresenter initPresenter() {
        return new UpdateNickNamePresenter(UpdateNickName.this);
    }

    @Override
    protected int getContentResId() {
        return R.layout.update_nick_name;
    }

    @Override
    public void initView() {
        setTitleText("修改昵称");
        setEnsureText("保存");
        nickNameEdit.setText(MemoryData.getInstance().mLoginInfoBean.getNickname());
    }

    @Override
    public void initData() {
        nickNameEdit.addTextChangedListener(new TextWatcher() {
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
            }
        });
    }

    public HashMap<String, String> mapData() {
        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("nickname", nickNameEdit.getText().toString());
        return mHashMap;
    }

    @Override
    protected void baseGoBack() {
        finish();
    }

    @Override
    protected void baseGoEnsure() {
        presenter.loadView(1);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @OnClick({R.id.nick_name_edit, R.id.image_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nick_name_edit:
                break;
            case R.id.image_delete:
                nickNameEdit.setText("");
                break;
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void updateView(Object object, int index) {
        switch (index) {
            case 1:
                if (MemoryData.getInstance().success.equals(((UpdateInfo) object).getSYS_HEAD().getReturnCode())) {
                    liyingreg();
                    MemoryData.getInstance().mLoginInfoBean.setNickname(nickNameEdit.getText().toString());
                    LoginUserSPreference.saveObject(MemoryData.getInstance().mLoginInfoBean);
                    finish();
                }
                break;
        }
    }

    private void liyingreg() {
        String phone = RSAUtils.strByEncryptionLiYing(MemoryData.getInstance().mLoginInfoBean.getPhoenum(), true);
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPhoenum(phone);
        registerDTO.setUsrid(RSAUtils.strByEncryptionLiYing(MemoryData.getInstance().userID, true));
        registerDTO.setNickname(nickNameEdit.getText().toString());
        CarApiClient.liYingReg(getApplicationContext(), registerDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
            }
        });
    }

    @Override
    public void hideProgress() {
    }
}
