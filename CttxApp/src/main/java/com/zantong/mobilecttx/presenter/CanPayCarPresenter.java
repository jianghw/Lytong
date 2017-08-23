package com.zantong.mobilecttx.presenter;

import com.zantong.mobilecttx.api.OnLoadServiceBackUI;
import com.zantong.mobilecttx.base.BasePresenter;
import com.zantong.mobilecttx.base.MessageFormat;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.car.bean.CanPayCarBean;
import com.zantong.mobilecttx.car.fragment.CanPayCarFragment;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.model.CarPayCarModelImp;
import com.zantong.mobilecttx.presenter.presenterinterface.SimplePresenter;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.qqtheme.framework.util.ToastUtils;

/**
 * Created by 王海洋 on 16/6/1.
 */
public class CanPayCarPresenter extends BasePresenter<IBaseView>
        implements SimplePresenter, OnLoadServiceBackUI {

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
        switch (index) {
            case 1:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c002.01");
                masp = new JSONObject();
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                MessageFormat.getInstance().setTransServiceCode("cip.cfc.c003.01");
                masp = new JSONObject();
                try {
                    masp.put("usrid", PublicData.getInstance().userID);
                    MessageFormat.getInstance().setMessageJSONObject(masp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        msg = MessageFormat.getInstance().getMessageFormat();
        mCarPayCarModelImp.loadUpdate(this, msg, index);
    }

    @Override
    public void onSuccess(Object clazz, int index) {
        mCanPayCarFragment.onShowContent();
        switch (index) {
            case 1:
                CanPayCarBean mCanPayCarBean = (CanPayCarBean) clazz;
                if (PublicData.getInstance().success.equals(mCanPayCarBean.getSYS_HEAD().getReturnCode())) {
                    loadView(2);
                } else {
                    mCanPayCarFragment.onShowFailed();
                    ToastUtils.toastShort(mCanPayCarBean.getSYS_HEAD().getReturnMessage());
                }
                break;
            case 2:

                mOpenQueryBean = (OpenQueryBean) clazz;
                if (!PublicData.getInstance().success.equals(mOpenQueryBean.getSYS_HEAD().getReturnCode())) {
                    mCanPayCarFragment.onShowFailed();
                    ToastUtils.toastShort(mOpenQueryBean.getSYS_HEAD().getReturnMessage());
                    return;
                }
                if (0 == mOpenQueryBean.getRspInfo().getUserCarsInfo().size()) {
                    mCanPayCarFragment.updateView(null, 1);
                    break;
                }
                UserInfoRememberCtrl.saveObject(PublicData.getInstance().CarLocalFlag, mOpenQueryBean.getRspInfo());
                mCanPayCarFragment.updateView(mOpenQueryBean, index);
                break;
        }
    }

    @Override
    public void onFailed() {
        mCanPayCarFragment.onShowFailed();
        ToastUtils.toastShort(Config.getErrMsg("1"));
    }
}
