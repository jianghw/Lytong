package com.zantong.mobilecttx.common;

import android.content.Context;

import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.map.bean.NetLocationBean;
import com.zantong.mobilecttx.card.bean.ProvinceModel;
import com.zantong.mobilecttx.weizhang.bean.QueryHistoryBean;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.utils.AccountRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * app内存数据清单
 * Created by Administrator on 2016/5/9.
 */
public class PublicData {

    private static PublicData instance;

    private PublicData() {
    }

    public static synchronized PublicData getInstance() {
        if (instance == null) {
            instance = new PublicData();
        }
        return instance;
    }

    public String success = "000000";//交易返回成功标志
    public int smCtrlTime = 60;//验证码时间
    public String imei = "00000000";//手机IMEI
    public String deviceId = "";//阿里云DeviceId
    public boolean loginFlag = false;//登录状态标志
    public boolean isCheckLogin = false;//是否验证登录状态
    public String userID = "";//用户ID
    public boolean defaultCar = false;//默认车标志
    public String defaultCarNumber = "";//默认车牌号
    public String filenum = "";//档案号(驾照号)
    public String getdate = "";//初次领证号
    public String loginAD = "1";//登录是否弹出对话框
    public String NewDefaultCarNumber = "";//设置新的默认车辆号
    public String CarLocalFlag = "userCarInfo";//本地缓存车辆信息的key
    public String DefaultCarLocalFlag = "defaultCarInfo";//本地缓存默认车辆信息的key
    public String IllegalViolationFlag = "IllegalViolationcChcek";//本地缓存违章信息的key
    public String ImageFileUrl = "https://192.9.200.131";//服务器图片地址
    public String onlyflag = "";//短信验证码唯一标识
    public boolean TitleFlag = false;//短信验证码唯一标识
    public boolean updateMsg = false;//设置-更新通知开启状态
    public final String NOTICE_STATE = "notice_state";//积分周期提醒key
    public boolean DialogCarNotice = false;//查询车辆是否添加的标志
    public boolean isOpenCheckPush = true;//是否打开车辆年检提醒
    public String bitmap;
    public Class<?> className;

    public int mPayCarNum = 0;
    public int mNorCarNum = 0;
    public int isPayCar = 0;
    public int mCarNum = 0;
    public LoginInfoBean.RspInfoBean mLoginInfoBean;//用户Bean对象
    public NetLocationBean mNetLocationBean;
    public HashMap<String, Object> mHashMap = new HashMap<>();//界面信息传递工具
    public QueryHistoryBean mQueryHistoryBean;//查询历史缓存
    public List<UserCarInfoBean> payData = new ArrayList<>();//可缴费车辆集合
    public List<UserCarInfoBean> mServerCars = new ArrayList<>();//我的车辆集合
    public List<CarInfoDTO> mLocalCars = new ArrayList<>();//我的车辆集合
    public List<UserCarInfoBean> noPayData = new ArrayList<>();//不可缴费车辆集合
    public List<ProvinceModel> provinceModel;//城市三级联动数据列表

    public int mapType = 0;//地图类型  0 加油 , 1 洗车 , 2 年检
    public int commonListType = 0;//1、驾照有效期限 2、准驾车型
    public String commonListData = "小型汽车";//
    public String webviewUrl = "";//h5链接
    public String webviewTitle = "";//h5标题
    public boolean isDelCar = false;//是否操作了删除车辆操作
    public boolean isSetPayCar = false;//是否操作了更改可缴费车辆

    public int GUIDE_TYPE = 0;//引导页面

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
