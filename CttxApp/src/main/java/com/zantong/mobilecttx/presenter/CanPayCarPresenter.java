package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.car.bean.CanPayCarBean;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.model.CarPayCarModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.car.fragment.CanPayCarFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class CanPayCarPresenter extends BasePresenter<IBaseView> implements SimplePresenter, OnLoadServiceBackUI {

    CanPayCarFragment mCanPayCarFragment;
    CarPayCarModelImp mCarPayCarModelImp;
    private String msg = "";
    private HashMap<String, Object> oMap = new HashMap<>();
    private JSONObject masp = null;
    private String carnumtype;
    private String carnum;
    private String enginenum;
    private OpenQueryBean mOpenQueryBean;
    public CanPayCarPresenter(CanPayCarFragment mCanPayCarFragment) {
        this.mCanPayCarFragment = mCanPayCarFragment;
        mCarPayCarModelImp = new CarPayCarModelImp();


    }


    @Override
    public void loadView(int index) {
        mCanPayCarFragment.onShowLoading();
        switch (index){
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c002.01");
                masp = new JSONObject() ;
//
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c003.01");
                masp = new JSONObject() ;
//
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
//                    masp.put("smsscene", "001");
//            masp.put("usrid","000160180 6199 2851");
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
//        Log.e("why",msg);
        mCarPayCarModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mCanPayCarFragment.onShowContent();
        switch (index){
            case 1:
                CanPayCarBean mCanPayCarBean = (CanPayCarBean) clazz;
                if(PublicData.getInstance().success.equals(mCanPayCarBean.getSYS_HEAD().getReturnCode())){
//                    int size = mCanPayCarBean.getRspInfo().getUserCarsInfo().size();
//                    for (int i = 0; i < size; i ++){
//                        carnum = mCanPayCarBean.getRspInfo().getUserCarsInfo().get(i).getCarnum();
//                        carnumtype = mCanPayCarBean.getRspInfo().getUserCarsInfo().get(i).getCarnumtype();
//                        enginenum = mCanPayCarBean.getRspInfo().getUserCarsInfo().get(i).getEnginenum();
                        loadView(2);
//                    }
//                    mCanPayCarFragment.updateView(clazz, index);
                }else{
                    mCanPayCarFragment.onShowFailed();
//                    EventBus.getDefault().post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, null, mCanPayCarFragment.getActivity()));
                    ToastUtils.showShort(mCanPayCarFragment.getActivity(), mCanPayCarBean.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 2:

                mOpenQueryBean = (OpenQueryBean) clazz;
                if(!PublicData.getInstance().success.equals(mOpenQueryBean.getSYS_HEAD().getReturnCode())){
//                    EventBus.getDefault().post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, null, mCanPayCarFragment.getActivity()));
                    mCanPayCarFragment.onShowFailed();
                    ToastUtils.showShort(mCanPayCarFragment.getActivity(), mOpenQueryBean.getSYS_HEAD().getReturnMessage());
                    return;
                }
                if(0 == mOpenQueryBean.getRspInfo().getUserCarsInfo().size()){
                    mCanPayCarFragment.updateView(null, 1);
                    break;
                }
                UserInfoRememberCtrl.saveObject(PublicData.getInstance().CarLocalFlag, mOpenQueryBean.getRspInfo());
//                int size = mOpenQueryBean.getRspInfo().getUserCarsInfo().size();
//                for (int i = 0; i < size; i ++){
//                    String flag = mOpenQueryBean.getRspInfo().getUserCarsInfo().get(i).getDefaultflag();
//                    if("1".equals(flag)){
//                        mUserCarsInfoBean = mOpenQueryBean.getRspInfo().getUserCarsInfo().get(i);
////                        mOpen.setUserCarsInfoBean(mUserCarsInfoBean);
//                        UserInfoRememberCtrl.saveObject(mOpen.getActivity(), PublicData.getInstance().DefaultCarLocaelFlag, mUserCarsInfoBean);
//                        PublicData.getInstance().defaultCar = true;
//                    }
//                }

                mCanPayCarFragment.updateView(mOpenQueryBean, index);

                break;
        }
    }

    @Override
    public void onFailed() {
//        mCanPayCarFragment.hideDialogLoading();
//        EventBus.getDefault().post(new ErrorEvent(Config.ERROR_IO, Config.ERROR_IO_MSG, null, mCanPayCarFragment.getActivity()));
        mCanPayCarFragment.onShowFailed();
        ToastUtils.showShort(mCanPayCarFragment.getActivity(), Config.getErrMsg("1"));
    }
}
