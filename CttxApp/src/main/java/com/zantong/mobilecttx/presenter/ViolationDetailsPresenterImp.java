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
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;
import com.zantong.mobilecttx.weizhang.bean.ViolationDetailsBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ViolationDetailsPresenterImp implements SimplePresenter, OnLoadServiceBackUI {

    private ViolationDetails mViolationDetails;
    private ViolationDetailsModelImp mViolationDetailsModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private ViolationDetailsBean mViolationDetailsBean;
    private JSONObject masp = null;
//    private CTTXHttpQueryPOSTInterface cttxHttpQueryPOSTInterface;

    public ViolationDetailsPresenterImp(ViolationDetails mViolationDetails) {
        this.mViolationDetails = mViolationDetails;
        mViolationDetailsModelImp = new ViolationDetailsModelImp();
    }


    private void init() {
//        mLoginPhone.
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
                    if(TextUtils.isEmpty(PublicData.getInstance().imei)){
                        masp.put("token", RSAUtils.strByEncryption(mViolationDetails, "00000000", true));
                    }else{
                        masp.put("token", RSAUtils.strByEncryption(mViolationDetails,PublicData.getInstance().imei, true));
                    }
                    LogUtils.i(masp.get("token").toString());
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c001.01");
                masp = new JSONObject();
//
                try {
                    OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBeans = (OpenQueryBean.RspInfoBean.UserCarsInfoBean) PublicData.getInstance().mHashMap.get("ConsummateInfo");
                    masp.put("usrid", PublicData.getInstance().userID);
                    masp.put("opertype", 3);
//                    masp.put("carnumtype", mConsummateInfo.mapData().get("carnumtype"));
//                    masp.put("carnum", mConsummateInfo.mapData().get("carnum"));
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
//        Log.e("why",mslog);
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

//                        LogUtils.i("Processste--" + this.mViolationDetailsBean.getRspInfo().getProcessste());
//                        String title = "该条违章为现场处罚违章，请使用罚单进行缴费！";
//                        String option = "去缴费";
//                        if (!(PublicData.getInstance().mHashMap.get("Processste")).equals("0")) {
//                            option = "看详情";
//                            title = "该条违章为现场处罚违章,请使用罚单缴费功能查看详情!";
//                        }
                        DialogUtils.createDialog(mViolationDetails, "请使用处罚决定书编号进行缴纳", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViolationDetails.finish();
                            }
                        });

//                        new DialogMgr(mViolationDetails, "温馨提示", title, "取消", option,
//                                new View.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(View arg0) {
//                                        mViolationDetails.finish();
//                                    }
//                                }, new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View view) {
//                                PermissionGen.needPermission(mViolationDetails, 100,
//                                        new String[]{
//                                                Manifest.permission.CAMERA,
////                                Manifest.permission.RECEIVE_SMS,
////                                Manifest.permission.WRITE_CONTACTS
//                                        }
//                                );
//                            }
//                        }, "1");

                    } else {
                        ToastUtils.showShort(mViolationDetails, mViolationDetailsBean.getSYS_HEAD().getReturnMessage());
                    }
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        ToastUtils.showShort(mViolationDetails, Config.getErrMsg("1"));
        mViolationDetails.loadFaildProgress();
    }
}
