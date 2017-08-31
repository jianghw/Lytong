package com.zantong.mobilecttx.presenter;

import android.text.TextUtils;
import android.view.View;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.ViolationDetailsModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

import org.json.JSONException;
import org.json.JSONObject;

import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ViolationDetailsPresenterImp implements SimplePresenter, OnLoadServiceBackUI {

    private ViolationDetails mViolationDetails;
    private ViolationDetailsModelImp mViolationDetailsModelImp;
    private String msg = "";
    private ViolationDetailsBean mViolationDetailsBean;
    private JSONObject masp = null;

    public ViolationDetailsPresenterImp(ViolationDetails mViolationDetails) {
        this.mViolationDetails = mViolationDetails;
        mViolationDetailsModelImp = new ViolationDetailsModelImp();
    }

    @Override
    public void loadView(int index) {
        mViolationDetails.showProgress();
        switch (index) {
            case 1:
                LogUtils.i("调用v003接口");
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.v003.01");
                masp = new JSONObject();
                try {
                    masp.put("violationnum", mViolationDetails.mapData().get("violationnum"));
                    if (TextUtils.isEmpty(PublicData.getInstance().imei)) {
                        masp.put("token", RSAUtils.strByEncryption("00000000", true));
                    } else {
                        masp.put("token", RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
                    }
                    LogUtils.i(masp.get("token").toString());
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c001.01");
                masp = new JSONObject();
                try {
                    OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBeans = (OpenQueryBean.RspInfoBean.UserCarsInfoBean)
                            PublicData.getInstance().mHashMap.get("ConsummateInfo");
                    masp.put("usrid", PublicData.getInstance().userID);
                    masp.put("opertype", 3);
                    masp.put("ispaycar", mUserCarsInfoBeans.getIspaycar());
                    masp.put("defaultflag", mUserCarsInfoBeans.getDefaultflag());
                    masp.put("inspectflag", "0");
                    masp.put("violationflag", "0");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mViolationDetailsModelImp.loadUpdate(this, msg, index);
    }


    @Override
    public void onSuccess(Object mLoginInfoBean, int index) {

        switch (index) {
            case 1:
                this.mViolationDetailsBean = (ViolationDetailsBean) mLoginInfoBean;
                if (PublicData.getInstance().success.equals(mViolationDetailsBean.getSYS_HEAD().getReturnCode())) {
                    mViolationDetails.hideProgress();
                    mViolationDetails.updateView(this.mViolationDetailsBean, index);
                } else {
                    mViolationDetails.loadFaildProgress();
                    if (mViolationDetailsBean.getSYS_HEAD().getReturnMessage().contains("处罚决定书")) {
                        DialogUtils.createDialog(mViolationDetails,
                                "请使用处罚决定书编号进行缴纳",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mViolationDetails.finish();
                                    }
                                });
                    } else {
                        ToastUtils.toastShort(mViolationDetailsBean.getSYS_HEAD().getReturnMessage());
                    }
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        ToastUtils.toastShort(Config.getErrMsg("1"));
        mViolationDetails.loadFaildProgress();
    }
}
