package com.zantong.mobile.presenter;

import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.base.BasePresenter;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.base.interf.IBaseView;
import com.zantong.mobile.car.fragment.SetPayCarFragment;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.home.bean.UpdateInfo;
import com.zantong.mobile.model.SetPayCarFragmentModelImp;
import com.zantong.mobile.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobile.utils.rsa.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.tzly.annual.base.util.ToastUtils;

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
                    masp.put("usrid", MemoryData.getInstance().userID);
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
                if (MemoryData.getInstance().success.equals(mUpdateInfo.getSYS_HEAD().getReturnCode())) {
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