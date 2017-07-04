package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.QueryHistoryModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;
import com.zantong.mobilecttx.weizhang.fragment.QueryHistory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.log.LogUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class QueryHistoryPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    QueryHistory mQueryHistory;
    QueryHistoryModelImp mQueryHistoryModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    public QueryHistoryPresenter(QueryHistory mQueryHistory) {
        this.mQueryHistory = mQueryHistory;
        mQueryHistoryModelImp = new QueryHistoryModelImp();


    }


    @Override
    public void loadView(int index) {
        mQueryHistory.showProgress();
        switch (index){
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.v002.01");
                masp = new JSONObject() ;
//
                try {
                    masp.put("token", RSAUtils.strByEncryption(PublicData.getInstance().imei, true));
                    masp.put("carnumtype", PublicData.getInstance().mHashMap.get("carnumtype"));
                    masp.put("carnum", RSAUtils.strByEncryption(PublicData.getInstance().mHashMap.get("carnum").toString(),true));
                    masp.put("enginenum",  RSAUtils.strByEncryption(PublicData.getInstance().mHashMap.get("enginenum").toString(),true));
                    LogUtils.i(masp.get("carnumtype").toString()+ masp.get("carnum")+masp.get("enginenum"));
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
//        Log.e("why",msg);
        mQueryHistoryModelImp.loadUpdate(this, msg, index);

    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mQueryHistory.hideProgress();
        switch (index){
            case 1:
                AddVehicleBean mAddVehicleBean = (AddVehicleBean) clazz;
                if(PublicData.getInstance().success.equals(mAddVehicleBean.getSYS_HEAD().getReturnCode())){
                    mQueryHistory.updateView(clazz, index);
                }else{
                    ToastUtils.showShort(mQueryHistory.getContext(), mAddVehicleBean.getSYS_HEAD().getReturnMessage());
                }
                break;
        }
    }

    @Override
    public void onFailed() {
        mQueryHistory.hideProgress();
        ToastUtils.showShort(mQueryHistory.getContext(), Config.getErrMsg("1"));
    }
}
