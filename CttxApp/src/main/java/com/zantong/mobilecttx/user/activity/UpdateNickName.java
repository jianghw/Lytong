package com.zantong.mobilecttx.user.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;
import com.zantong.mobilecttx.presenter.UpdateNickNamePresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.interf.ModelView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

public class UpdateNickName extends BaseMvpActivity<IBaseView, UpdateNickNamePresenter> implements View.OnClickListener, IBaseView, ModelView {
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
        nickNameEdit.setText(PublicData.getInstance().mLoginInfoBean.getNickname());
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
//        super.baseGoBack();
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

    private void init() {

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
                if (PublicData.getInstance().success.equals(((UpdateInfo) object).getSYS_HEAD().getReturnCode())) {
                    liyingreg();
                    PublicData.getInstance().mLoginInfoBean.setNickname(nickNameEdit.getText().toString());
                    UserInfoRememberCtrl.saveObject(UpdateNickName.this, PublicData.getInstance().mLoginInfoBean);
                    finish();
                }

                break;
        }
    }

    private void liyingreg() {
        String phone = RSAUtils.strByEncryptionLiYing(PublicData.getInstance().mLoginInfoBean.getPhoenum(), true);
        LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
        liYingRegDTO.setPhoenum(phone);
        liYingRegDTO.setUsrid(RSAUtils.strByEncryptionLiYing(PublicData.getInstance().userID, true));
        liYingRegDTO.setNickname(nickNameEdit.getText().toString());
        CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResult>() {
            @Override
            public void onSuccess(BaseResult result) {
            }
        });
    }

    @Override
    public void hideProgress() {

    }
}
