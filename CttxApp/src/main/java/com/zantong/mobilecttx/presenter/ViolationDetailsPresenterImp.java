package com.zantong.mobilecttx.presenter;

import android.text.TextUtils;
import android.view.View;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.MessageFormat;
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
            if (TextUtils.isEmpty(PublicData.getInstance().imei)) {
                masp.put("token", RSAUtils.strByEncryption("00000000", true));
            } else {
                masp.put("token", RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
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
                if (PublicData.getInstance().success.equals(detailsBean.getSYS_HEAD().getReturnCode())) {
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
