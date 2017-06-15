package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.card.bean.BindCardBean;
import com.zantong.mobilecttx.card.fragment.MyCardFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.BindCardModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.LogUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class BindCardPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    MyCardFragment mBindCardFragment;
    BindCardModelImp mBindCardModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;

    public BindCardPresenter(MyCardFragment mBindCardFragment) {
        this.mBindCardFragment = mBindCardFragment;
        mBindCardModelImp = new BindCardModelImp();


    }

    private String getStartTime() {
        String startTime = "";
        String nowMonth = Tools.getYearDate().substring(4, 6);
        LogUtils.i("本地日期：" + Tools.getYearDate());
        if (PublicData.getInstance().mLoginInfoBean.getGetdate() != null && PublicData.getInstance().mLoginInfoBean.getGetdate().length() >= 6) {

            String oldMonth = PublicData.getInstance().mLoginInfoBean.getGetdate().substring(4, 6);
            LogUtils.i("--登录返回日期：" + PublicData.getInstance().mLoginInfoBean.getGetdate());

            if (Integer.parseInt(nowMonth) > Integer.parseInt(oldMonth)) {
                startTime = Tools.getYearDate().substring(0, 4) + PublicData.getInstance().mLoginInfoBean.getGetdate().substring(4);
            } else {
                startTime = PublicData.getInstance().mLoginInfoBean.getGetdate();
            }
        }
        return startTime;
    }

    @Override
    public void loadView(int index) {
        mBindCardFragment.showProgress();
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.v001.01");
                masp = new JSONObject();
//
                try {
                    masp.put("filenum", RSAUtils.strByEncryption(mBindCardFragment.getActivity(), PublicData.getInstance().mLoginInfoBean.getFilenum(), true));
                    masp.put("strtdt", getStartTime());
                    masp.put("enddt", Tools.getYearDate());
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.u001.01");
                masp = new JSONObject();
//
                try {
//                    masp.put("phoenum", mLoginPhone.mapData().get("phoenum"));
//                    masp.put("captcha", mLoginPhone.mapData().get("captcha"));
//                    masp.put("onlyflag", onlyflag);
                    masp.put("devicetoken", PublicData.getInstance().imei);
                    masp.put("pushswitch", "0");
                    masp.put("pushmode", "1");
                    masp.put("chkflg", "1");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

        msg = MessageFormat.getInstance().getMessageFormat();
        mBindCardModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mBindCardFragment.hideProgress();
        switch (index) {
            case 1:
                if (!PublicData.getInstance().success.equals(((BindCardBean) clazz).getSYS_HEAD().getReturnCode())) {
                    ToastUtils.showShort(mBindCardFragment.getActivity(), ((BindCardBean) clazz).getSYS_HEAD().getReturnMessage());
                    return;
                }
                mBindCardFragment.updateView(clazz, index);
                break;
        }
    }

    @Override
    public void onFailed() {
        mBindCardFragment.faildProgress();
    }
}
