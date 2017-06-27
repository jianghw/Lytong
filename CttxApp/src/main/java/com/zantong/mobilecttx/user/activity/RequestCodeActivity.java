package com.zantong.mobilecttx.user.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.interf.ILoginView;
import com.zantong.mobilecttx.presenter.LoginPresenter;
import com.zantong.mobilecttx.user.dto.PersonInfoDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.ValidateUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import butterknife.Bind;
import cn.qqtheme.framework.util.log.LogUtils;

/**
 * 我的邀请码
 * @author Sandy
 * create at 16/6/13 下午6:05
 */
public class RequestCodeActivity extends BaseMvpActivity<ILoginView,LoginPresenter>  {

    @Bind(R.id.request_commit)
    Button mCommit;
    @Bind(R.id.request_edittext)
    EditText mEditText;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        setTitleText("输入邀请码");
        mCommit.setOnClickListener(this);
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_requestcode_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.request_commit:
                commit();
                break;
        }
    }

    private void commit(){
        final String requestCode = mEditText.getText().toString();
        if (Tools.isStrEmpty(requestCode)){
            ToastUtils.showShort(this, "请先输入邀请码");
        }else if(!ValidateUtils.isMobile(requestCode)){
            ToastUtils.showShort(this, "您输入的格式不正确");
        }else {
            showDialogLoading();
            PersonInfoDTO dto = new PersonInfoDTO();
            dto.setRecdphoe(RSAUtils.strByEncryption(this, requestCode, true));
            dto.setPhoenum(RSAUtils.strByEncryption(this, PublicData.getInstance().mLoginInfoBean.getPhoenum(), true));
            UserApiClient.commitPersonInfo(this, dto, new CallBack<Result>() {
                @Override
                public void onSuccess(Result result) {
                    hideDialogLoading();
                    if ("000000".equals(result.getSYS_HEAD().getReturnCode())) {
                        PublicData.getInstance().mLoginInfoBean.setRecdphoe(requestCode);
                        PublicData.getInstance().mLoginInfoBean.setRecddt(DateUtils.getDate());
                        LogUtils.i(DateUtils.getYearMonthDay());
                        UserInfoRememberCtrl.saveObject(RequestCodeActivity.this, PublicData.getInstance().mLoginInfoBean);
                        Intent intent = new Intent(RequestCodeActivity.this, RequestSuccActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onError(String errorCode, String msg) {
                    super.onError(errorCode, msg);
                    hideDialogLoading();
                }
            });
        }
    }
}
