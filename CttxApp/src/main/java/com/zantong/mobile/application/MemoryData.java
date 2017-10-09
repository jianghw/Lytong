package com.zantong.mobile.application;

import android.content.Context;

import com.zantong.mobile.login_v.LoginUserSPreference;
import com.zantong.mobile.map.bean.NetLocationBean;
import com.zantong.mobile.user.bean.RspInfoBean;
import com.zantong.mobile.user.bean.UserCarInfoBean;
import com.zantong.mobile.utils.AccountRememberCtrl;
import com.zantong.mobile.utils.SPUtils;
import com.zantong.mobile.weizhang.bean.QueryHistoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app内存数据清单
 * Created by Administrator on 2016/5/9.
 */
public class MemoryData {

    private static MemoryData instance;

    public static synchronized MemoryData getInstance() {
        if (instance == null) {
            instance = new MemoryData();
        }
        return instance;
    }

    public RspInfoBean mLoginInfoBean; //用户Bean对象
    public String userID = "";//用户ID
    public String filenum = "";//档案号(驾照号)
    public String getdate = "";//初次领证号
    public boolean loginFlag = false;//登录状态标志

    public String success = "000000";//交易返回成功标志
    public int smCtrlTime = 60;//验证码时间
    public String imei = "00000000";//手机IMEI
    public String deviceId = "";//阿里云DeviceId

    public boolean updateMsg = false;//设置-更新通知开启状态
    public final String NOTICE_STATE = "notice_state";//积分周期提醒key
    public String bitmap;

    public Class<?> className;

    public int mCarNum = 0;
    public NetLocationBean mNetLocationBean;
    public HashMap<String, Object> mHashMap = new HashMap<>();//界面信息传递工具

    public QueryHistoryBean mQueryHistoryBean;//查询历史缓存
    public List<UserCarInfoBean> payData = new ArrayList<>();//可缴费车辆集合
    public List<UserCarInfoBean> mServerCars = new ArrayList<>();//我的车辆集合

    public int commonListType = 0;//1、驾照有效期限 2、准驾车型
    public String commonListData = "小型汽车";//

    public boolean isSetPayCar = false;//是否操作了更改可缴费车辆

    public void clearData(Context mContext) {
        mLoginInfoBean = null;
        userID = "";
        filenum = "";
        loginFlag = false;

        updateMsg = false;
        mNetLocationBean = null;

        SPUtils.getInstance().setUserPwd("");
        LoginUserSPreference.saveObject(null);
        LoginUserSPreference.saveObject("jiayou", null);
        LoginUserSPreference.saveObject("nianjian", null);
        LoginUserSPreference.saveObject(MemoryData.getInstance().userID, null);

        AccountRememberCtrl.nosaveDefaultNumber(mContext);
        AccountRememberCtrl.nosaveLoginAD(mContext);
        AccountRememberCtrl.nosaveDefaultNumber(mContext);
        AccountRememberCtrl.nosavePersonal(mContext);
        AccountRememberCtrl.nosaveChangTongFlag(mContext);
    }
}
