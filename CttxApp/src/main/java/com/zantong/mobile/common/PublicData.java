package com.zantong.mobile.common;

import android.content.Context;

import com.zantong.mobile.map.bean.NetLocationBean;
import com.zantong.mobile.card.bean.ProvinceModel;
import com.zantong.mobile.user.bean.RspInfoBean;
import com.zantong.mobile.weizhang.bean.QueryHistoryBean;
import com.zantong.mobile.user.bean.UserCarInfoBean;
import com.zantong.mobile.car.dto.CarInfoDTO;
import com.zantong.mobile.utils.AccountRememberCtrl;
import com.zantong.mobile.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobile.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app内存数据清单
 * Created by Administrator on 2016/5/9.
 */
public class PublicData {

    private static PublicData instance;

    public static synchronized PublicData getInstance() {
        if (instance == null) {
            instance = new PublicData();
        }
        return instance;
    }

    public String userID = "";//用户ID
    public RspInfoBean mLoginInfoBean; //用户Bean对象
    public String filenum = "";//档案号(驾照号)
    public String getdate = "";//初次领证号
    public boolean loginFlag = false;//登录状态标志

    public String success = "000000";//交易返回成功标志
    public int smCtrlTime = 60;//验证码时间
    public String imei = "00000000";//手机IMEI
    public String deviceId = "";//阿里云DeviceId
    public boolean defaultCar = false;//默认车标志

    public String defaultCarNumber = "";//默认车牌号
    public String CarLocalFlag = "userCarInfo";//本地缓存车辆信息的key

    public String DefaultCarLocalFlag = "defaultCarInfo";//本地缓存默认车辆信息的key
    public String onlyflag = "";//短信验证码唯一标识
    public boolean TitleFlag = false;//短信验证码唯一标识
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
    public List<CarInfoDTO> mLocalCars = new ArrayList<>();//我的车辆集合
    public List<ProvinceModel> provinceModel;//城市三级联动数据列表

    public int commonListType = 0;//1、驾照有效期限 2、准驾车型
    public String commonListData = "小型汽车";//

    public boolean isDelCar = false;//是否操作了删除车辆操作
    public boolean isSetPayCar = false;//是否操作了更改可缴费车辆

    public void clearData(Context mContext) {
        userID = "";
        loginFlag = false;
        updateMsg = false;
        filenum = "";
        mNetLocationBean = null;
        SPUtils.getInstance().setUserPwd("");

        UserInfoRememberCtrl.saveObject(null);
        UserInfoRememberCtrl.saveObject(CarLocalFlag, null);
        UserInfoRememberCtrl.saveObject(DefaultCarLocalFlag, null);
        UserInfoRememberCtrl.saveObject("jiayou", null);
        UserInfoRememberCtrl.saveObject("nianjian", null);
        UserInfoRememberCtrl.saveObject(PublicData.getInstance().userID, null);

        AccountRememberCtrl.nosaveDefaultNumber(mContext);
        AccountRememberCtrl.nosaveLoginAD(mContext);
        AccountRememberCtrl.nosaveDefaultNumber(mContext);
        AccountRememberCtrl.nosavePersonal(mContext);
        AccountRememberCtrl.nosaveChangTongFlag(mContext);
    }
}
