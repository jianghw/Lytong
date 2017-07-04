package com.zantong.mobilecttx.presenter;

import android.text.TextUtils;
import android.view.View;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.IllegalQueryModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;
import com.zantong.mobilecttx.weizhang.fragment.QueryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class IllegalQueryPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    QueryFragment mQueryFragment;
    IllegalQueryModelImp mIllegalQueryModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    public IllegalQueryPresenter(QueryFragment mQueryFragment) {
        this.mQueryFragment = mQueryFragment;
        mIllegalQueryModelImp = new IllegalQueryModelImp();


    }


    @Override
    public void loadView(final int index) {
        if (SPUtils.getInstance().getXinnengyuanFlag() && mQueryFragment.mapData().get("carnum").length() >= 8 && (!mQueryFragment.mapData().get("carnumtype").equals("51") ||
                    !mQueryFragment.mapData().get("carnumtype").equals("52"))){
                SPUtils.getInstance().setXinnengyuanFlag(false);
                DialogUtils.createDialog(mQueryFragment.getActivity(), "温馨提示", "如果您的汽车为新能源汽车,车牌类型请选择新能源汽车", "取消", "继续查询", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mQueryFragment.showProgress();
                        switch (index) {
                            case 1:
                                MessageFormat.getInstance().setTransServiceCode("cip.cfc.v002.01");
                                masp = new JSONObject();
                                try {
                                    if (mQueryFragment.mapData().get("carnum").length() <= 6) {
                                        ToastUtils.showShort(mQueryFragment.getActivity(), "请输入车牌号");
                                        mQueryFragment.hideProgress();
                                        return;
                                    }
                                    if (Tools.isStrEmpty(mQueryFragment.mapData().get("enginenum"))) {
                                        ToastUtils.showShort(mQueryFragment.getActivity(), "请输入发动机号");
                                        mQueryFragment.hideProgress();
                                        return;
                                    }
                                    if (mQueryFragment.mapData().get("enginenum").length() != 5) {
                                        ToastUtils.showShort(mQueryFragment.getActivity(), "发动机号格式不正确");
                                        mQueryFragment.hideProgress();
                                        return;
                                    }
                                    if (TextUtils.isEmpty(PublicData.getInstance().imei)) {
                                        masp.put("token", RSAUtils.strByEncryption("00000000", true));
                                    } else {
                                        masp.put("token", RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
                                    }
                                    LogUtils.i(masp.get("token").toString());
                                    masp.put("carnumtype", mQueryFragment.mapData().get("carnumtype"));
                                    masp.put("carnum", RSAUtils.strByEncryption(mQueryFragment.mapData().get("carnum"), true));
                                    masp.put("enginenum", RSAUtils.strByEncryption(mQueryFragment.mapData().get("enginenum"), true));
                                    MessageFormat.getInstance().setMessageJSONObject(masp);
                                    msg = MessageFormat.getInstance().getMessageFormat();
                                    mIllegalQueryModelImp.loadUpdate(IllegalQueryPresenter.this, msg, index);
                                }catch (JSONException e){

                                }
                                break;
                        }
                    }
                });

        }else {
            mQueryFragment.showProgress();
            switch (index) {
                case 1:
                    MessageFormat.getInstance().setTransServiceCode("cip.cfc.v002.01");
                    masp = new JSONObject();
                    try {
                        if (mQueryFragment.mapData().get("carnum").length() <= 6) {
                            ToastUtils.showShort(mQueryFragment.getActivity(), "请输入车牌号");
                            mQueryFragment.hideProgress();
                            return;
                        }
                        if (Tools.isStrEmpty(mQueryFragment.mapData().get("enginenum"))) {
                            ToastUtils.showShort(mQueryFragment.getActivity(), "请输入发动机号");
                            mQueryFragment.hideProgress();
                            return;
                        }
                        if (mQueryFragment.mapData().get("enginenum").length() != 5) {
                            ToastUtils.showShort(mQueryFragment.getActivity(), "发动机号格式不正确");
                            mQueryFragment.hideProgress();
                            return;
                        }
                        if (TextUtils.isEmpty(PublicData.getInstance().imei)) {
                            masp.put("token", RSAUtils.strByEncryption("00000000", true));
                        } else {
                            masp.put("token", RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
                        }
                        LogUtils.i(masp.get("token").toString());
                        masp.put("carnumtype", mQueryFragment.mapData().get("carnumtype"));
                        masp.put("carnum", RSAUtils.strByEncryption(mQueryFragment.mapData().get("carnum"), true));
                        masp.put("enginenum", RSAUtils.strByEncryption(mQueryFragment.mapData().get("enginenum"), true));
                        MessageFormat.getInstance().setMessageJSONObject(masp);
                        msg = MessageFormat.getInstance().getMessageFormat();
                        mIllegalQueryModelImp.loadUpdate(IllegalQueryPresenter.this, msg, index);
                    }catch (JSONException e){

                    }
                    break;
            }
        }

    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mQueryFragment.hideProgress();
        switch (index){
            case 1:
                AddVehicleBean mAddVehicleBean = (AddVehicleBean) clazz;
                if(PublicData.getInstance().success.equals(mAddVehicleBean.getSYS_HEAD().getReturnCode())){
                    LogUtils.i("1");
                    mQueryFragment.updateView(clazz, index);
                }else{
                    LogUtils.i("2");
                    ToastUtils.showShort(mQueryFragment.getActivity(), mAddVehicleBean.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mQueryFragment.hideProgress();
        LogUtils.i("3");
//        ToastUtils.showShort(mQueryFragment.getActivity(), Config.getErrMsg("1"));
    }

}
