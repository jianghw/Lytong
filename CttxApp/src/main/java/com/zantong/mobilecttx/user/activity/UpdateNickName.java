package com.zantong.mobilecttx.user.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tzly.ctcyh.router.ServiceRouter;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.tzly.ctcyh.service.IUserService;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.contract.ModelView;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.presenter.UpdateNickNamePresenter;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.dto.LiYingRegDTO;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import com.tzly.ctcyh.java.response.BaseResponse;

import static com.zantong.mobilecttx.router.MainRouter.getUserPhoenum;

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
        nickNameEdit.setText(MainRouter.getUserNickname());
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
                if (LoginData.getInstance().success.equals(((UpdateInfo) object).getSYS_HEAD().getReturnCode())) {
                    liyingreg();
                    //更新别名
                    ServiceRouter serviceRouter = ServiceRouter.getInstance();
                    if (serviceRouter.getService(IUserService.class.getSimpleName()) != null) {
                        IUserService service = (IUserService) serviceRouter
                                .getService(IUserService.class.getSimpleName());
                        service.saveUserNickname(nickNameEdit.getText().toString().trim());
                    } else {
                        //注册机开始工作
                        ServiceRouter.registerComponent("com.tzly.ctcyh.user.like.UserAppLike");
                    }

                    finish();
                }
                break;
        }
    }

    private void liyingreg() {
        String phone = RSAUtils.strByEncryptionLiYing(getUserPhoenum(), true);
        LiYingRegDTO liYingRegDTO = new LiYingRegDTO();
        liYingRegDTO.setPhoenum(phone);
        liYingRegDTO.setUsrid(MainRouter.getRASUserID());
        liYingRegDTO.setNickname(nickNameEdit.getText().toString().trim());
        CarApiClient.liYingReg(getApplicationContext(), liYingRegDTO, new CallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse result) {
            }
        });
    }

    @Override
    public void hideProgress() {
    }
}
