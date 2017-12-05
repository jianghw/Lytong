package com.zantong.mobilecttx.application;

import android.content.Context;

import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.card.bean.ProvinceModel;
import com.zantong.mobilecttx.map.bean.NetLocationBean;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app内存数据清单
 * Created by Administrator on 2016/5/9.
 */
public class LoginData {

    private static LoginData instance;

    private LoginData() {}

    public static synchronized LoginData getInstance() {
        if (instance == null) {
            instance = new LoginData();
        }
        return instance;
    }

    public String success = "000000";//交易返回成功标志
    public int smCtrlTime = 60;//验证码时间

    public boolean defaultCar = false;//默认车标志
    public String defaultCarNumber = "";//默认车牌号

    public String CarLocalFlag = "userCarInfo";//本地缓存车辆信息的key
    public String DefaultCarLocalFlag = "defaultCarInfo";//本地缓存默认车辆信息的key

    public boolean updateMsg = false;//设置-更新通知开启状态
    public final String NOTICE_STATE = "notice_state";//积分周期提醒key

    public String bitmap;
    public Class<?> className;
    public int mCarNum = 0;

    public RspInfoBean mLoginInfoBean;//用户Bean对象
    public NetLocationBean mNetLocationBean;
    public QueryHistoryBean mQueryHistoryBean;//查询历史缓存

    public HashMap<String, Object> mHashMap = new HashMap<>();//界面信息传递工具
    public List<UserCarInfoBean> mServerCars = new ArrayList<>();//我的车辆集合
    public List<CarInfoDTO> mLocalCars = new ArrayList<>();//我的车辆集合
    public List<ProvinceModel> provinceModel;//城市三级联动数据列表

    public int commonListType = 0;//1、驾照有效期限 2、准驾车型
    public String commonListData = "小型汽车";//

    public boolean isDelCar = false;//是否操作了删除车辆操作
    public boolean isSetPayCar = false;//是否操作了更改可缴费车辆

    public void clearData(Context mContext) {
        updateMsg = false;
        mNetLocationBean = null;

        success = "000000";//交易返回成功标志
        smCtrlTime = 60;//验证码时间

        defaultCar = false;//默认车标志
        defaultCarNumber = "";//默认车牌号

        updateMsg = false;//设置-更新通知开启状态
        bitmap = null;
        className = null;
        mCarNum = 0;

        mLoginInfoBean = null;//用户Bean对象
        mNetLocationBean = null;
        mQueryHistoryBean = null;//查询历史缓存

        if (mHashMap != null) mHashMap.clear();//界面信息传递工具
        if (mServerCars != null) mServerCars.clear();//我的车辆集合
        if (mLocalCars != null) mLocalCars.clear();//我的车辆集合
        if (provinceModel != null) provinceModel.clear();//城市三级联动数据列表

        commonListType = 0;//1、驾照有效期限 2、准驾车型
        commonListData = "小型汽车";//

        isDelCar = false;//是否操作了删除车辆操作
        isSetPayCar = false;//是否操作了更改可缴费车辆

        SPUtils.getInstance().setUserPwd("");
        UserInfoRememberCtrl.saveObject(null);
        UserInfoRememberCtrl.saveObject(CarLocalFlag, null);
        UserInfoRememberCtrl.saveObject(DefaultCarLocalFlag, null);
        UserInfoRememberCtrl.saveObject("jiayou", null);
        UserInfoRememberCtrl.saveObject("nianjian", null);

        AccountRememberCtrl.nosaveDefaultNumber(mContext);
        AccountRememberCtrl.nosaveLoginAD(mContext);
        AccountRememberCtrl.nosaveDefaultNumber(mContext);
        AccountRememberCtrl.nosavePersonal(mContext);
        AccountRememberCtrl.nosaveChangTongFlag(mContext);
    }
}
