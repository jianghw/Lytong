package com.zantong.mobile.presenter;

import android.text.TextUtils;
import android.view.View;

import com.zantong.mobile.api.OnLoadServiceBackUI;
import com.zantong.mobile.base.MessageFormat;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.model.ViolationDetailsModelImp;
import com.zantong.mobile.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobile.utils.DialogUtils;
import com.zantong.mobile.utils.rsa.RSAUtils;
import com.zantong.mobile.weizhang.activity.ViolationDetails;
import com.zantong.mobile.weizhang.bean.ViolationDetailsBean;

import org.json.JSONException;
import org.json.JSONObject;

import com.tzly.annual.base.util.ToastUtils;

/**
 * Created by Administrator on 2016/5/5.
 * Description:
 * Update by:
 * Update day:
 */
public class ViolationDetailsPresenterImp implements SimplePresenter, OnLoadServiceBackUI {

    private ViolationDetails mViolationDetailsView;
    private ViolationDetailsModelImp mViolationDetailsModelImp;
    private String msg = "";

    private JSONObject masp = null;

    public ViolationDetailsPresenterImp(ViolationDetails mViolationDetails) {
        this.mViolationDetailsView = mViolationDetails;
        mViolationDetailsModelImp = new ViolationDetailsModelImp();
    }

    @Override
    public void loadView(int number) {
    }

    public void loadView(String number) {
        mViolationDetailsView.showProgress();
        MessageFormat.getInstance().setTransServiceCode("cip.cfc.v003.01");
        masp = new JSONObject();
        try {
            masp.put("violationnum", number);
            if (TextUtils.isEmpty(MemoryData.getInstance().imei)) {
                masp.put("token", RSAUtils.strByEncryption("00000000", true));
            } else {
                masp.put("token", RSAUtils.strByEncryption(MemoryData.getInstance().imei, true));
            }

            MessageFormat.getInstance().setMessageJSONObject(masp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mViolationDetailsModelImp.loadUpdate(this, msg, 1);
    }

    @Override
    public void onSuccess(Object obj, int index) {
        switch (index) {
            case 1:
                ViolationDetailsBean detailsBean = (ViolationDetailsBean) obj;
                if (MemoryData.getInstance().success.equals(detailsBean.getSYS_HEAD().getReturnCode())) {
                    mViolationDetailsView.hideProgress();
                    mViolationDetailsView.updateView(detailsBean, index);

                } else {
                    mViolationDetailsView.loadFaildProgress();
                    if (detailsBean.getSYS_HEAD().getReturnMessage().contains("处罚决定书")) {
                        DialogUtils.createDialog(mViolationDetailsView,
                                "请使用处罚决定书编号进行缴纳",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mViolationDetailsView.finish();
                                    }
                                });
                    } else {
                        ToastUtils.toastShort(detailsBean.getSYS_HEAD().getReturnMessage());
                    }
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        ToastUtils.toastShort(Config.getErrMsg("1"));
        mViolationDetailsView.loadFaildProgress();
    }
}
