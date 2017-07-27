package com.zantong.mobilecttx.presenter;

import android.text.TextUtils;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.home.activity.Codequery;
import com.zantong.mobilecttx.model.CodeQueryModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class CodeQueryPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    Codequery mCodequery;
    CodeQueryModelImp mCodeQueryModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;

    public CodeQueryPresenter(Codequery mCodequery) {
        this.mCodequery = mCodequery;
        mCodeQueryModelImp = new CodeQueryModelImp();
    }

    @Override
    public void loadView(int index) {
        mCodequery.showProgress();
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.v003.01");
                masp = new JSONObject();
                try {
                    masp.put("violationnum", mCodequery.getEditNumber());
                    if (TextUtils.isEmpty(PublicData.getInstance().imei)) {
                        masp.put("token", RSAUtils.strByEncryption("00000000", true));
                    } else {
                        masp.put("token", RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
                    }

                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mCodeQueryModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mCodequery.hideProgress();
        switch (index) {
            case 1:
                ViolationDetailsBean mViolationDetailsBean = (ViolationDetailsBean) clazz;
                if (PublicData.getInstance().success.equals(
                        mViolationDetailsBean.getSYS_HEAD().getReturnCode())) {
                    mCodequery.updateView(clazz, index);
                } else {
                    ToastUtils.toastShort(mViolationDetailsBean.getSYS_HEAD().getReturnMessage());
                }
                break;
        }

    }

    @Override
    public void onFailed() {
        mCodequery.hideProgress();
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }
}
