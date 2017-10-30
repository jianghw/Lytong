package com.zantong.mobilecttx.presenter;

import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.fragment.SetPayCarFragment;
import com.zantong.mobilecttx.home.bean.UpdateInfo;
import com.zantong.mobilecttx.model.SetPayCarFragmentModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class SetPayCarFragmentPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    SetPayCarFragment mSetPayCarFragment;
    SetPayCarFragmentModelImp mSetPayCarFragmentModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;

    public SetPayCarFragmentPresenter(SetPayCarFragment mSetPayCarFragment) {
        this.mSetPayCarFragment = mSetPayCarFragment;
        mSetPayCarFragmentModelImp = new SetPayCarFragmentModelImp();
    }


    @Override
    public void loadView(int index) {
        mSetPayCarFragment.showProgress();
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c004.01");
                masp = new JSONObject();

                try {
                    masp.put("usrid", LoginData.getInstance().userID);
                    masp.put("delcarnum", RSAUtils.strByEncryption(
                            mSetPayCarFragment.mapData().get("delcarnum"), true));
                    masp.put("addcarnum", RSAUtils.strByEncryption(
                            mSetPayCarFragment.mapData().get("addcarnum"), true));
                    masp.put("carnumtype", mSetPayCarFragment.mapData().get("carnumtype"));
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();

        mSetPayCarFragmentModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mSetPayCarFragment.hideProgress();
        switch (index) {
            case 1:
                UpdateInfo mUpdateInfo = (UpdateInfo) clazz;
                if (LoginData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())) {
                    mSetPayCarFragment.updateView(clazz, index);
                } else {
                    ToastUtils.toastShort( mUpdateInfo.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mSetPayCarFragment.hideProgress();
        ToastUtils.toastShort( Config.getErrMsg("1"));
    }
}
