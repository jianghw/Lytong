package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.ContextUtils;

public class SPUtils {
    /**
     * 清单文件名称
     */
    public static final String SP_NAME = "cttx";

    public static final String CARSINFO = "carsinfo";

    public static final String GUIDE_SAOFADAN = "saofadan";

    public static final String GUIDE_WEIZHANG = "weizhang";

    public static final String GUIDE_XINGSHIZHENG = "xingshizheng";

    public static final String GUIDE_JIASHIZHEGN = "jiashizheng";

    public static final String GUIDE_BANKA = "shenbanchangtongka";

    public static final String WEIZHANG_PUSH = "weizhangpush";

    public static final String JIFEN_PUSH = "jifenpush";

    public static final String IS_GUIDE = "isGuide";

    public static final String UESR_PWD = "user_pwd";

    public static final String VIOLATION = "violation";

    public static final String LICENSE_FILE_NUM = "license_file_num";

    public static final String SIGN_STATUS = "signstatus";//活动报名状态

    public static final String SIGN_CAR_PLATE = "signcarpalte";//活动报名车牌号

    public static final String XINNENGYUAN_FLAG = "xinnengyuanflag";//选择新能源车型的提示

    public static final String OIL_CARD = "oilcard";//加油卡卡号

    public static final String LOGIN_INFO_BEAN = "login_info_bean";

    private static List<CarInfoDTO> carsinfo = null;

    private Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private static SPUtils sInstance;

    private SPUtils() {
        mSharedPreferences = ContextUtils.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SPUtils getInstance() {
        if (sInstance == null) {
            sInstance = new SPUtils();
        }
        return sInstance;
    }

    /**
     * 清除所有配置
     */
    public void clear() {
        this.mEditor.clear();
        this.mEditor.commit();
    }


    /**
     * 保存车辆信息
     *
     * @param carsInfo
     */
    public void setCarInfo(List<CarInfoDTO> carsInfo) {
        String str = "";
        try {
            str = SerializableUtils.obj2Str(carsInfo);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        this.mEditor.putString(CARSINFO, str);
        this.mEditor.commit();
    }

    /**
     * 获取车辆信息
     */
    public List<CarInfoDTO> getCarsInfo() {
        if (carsinfo == null) carsinfo = new ArrayList<>();
        // 获取序列化的数据
        String sharedPreferences = mSharedPreferences.getString(CARSINFO, "");
        try {
            Object obj = SerializableUtils.str2Obj(sharedPreferences);
            if (obj == null) return carsinfo;
            carsinfo = new Gson().fromJson(obj.toString(), new TypeToken<List<CarInfoDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carsinfo;
    }

    /**
     * 保存车辆信息
     *
     * @param carsInfo
     */
    public void setViolation(ViolationDTO carsInfo) {
        String str = "";
        try {
            str = SerializableUtils.obj2Str(carsInfo);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        this.mEditor.putString(VIOLATION, str);
        this.mEditor.commit();
    }

    /**
     * 获取车辆信息
     *
     * @return
     */
    public ViolationDTO getViolation() {
        ViolationDTO violationDTO = null;
        // 获取序列化的数据
        String str = this.mSharedPreferences.getString(VIOLATION, "");
        try {
            Object obj = SerializableUtils.str2Obj(str);
            if (obj != null) {
                violationDTO = (ViolationDTO) obj;
            }

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return violationDTO;
    }

    public void setGuideSaoFaDan(boolean isGuide) {
        this.mEditor.putBoolean(GUIDE_SAOFADAN, isGuide);
        this.mEditor.commit();
    }

    public boolean getGuideSaoFaDan() {
        return this.mSharedPreferences.getBoolean(GUIDE_SAOFADAN, false);
    }

    public void setUserPwd(String userPwd) {
        this.mEditor.putString(UESR_PWD, userPwd);
        this.mEditor.commit();
    }

    public String getUserPwd() {
        return this.mSharedPreferences.getString(UESR_PWD, "111111a");
    }

    public void setGuideWeiZhang(boolean isGuide) {
        this.mEditor.putBoolean(GUIDE_WEIZHANG, isGuide);
        this.mEditor.commit();
    }

    public boolean getGuideWeiZhang() {
        return this.mSharedPreferences.getBoolean(GUIDE_WEIZHANG, false);
    }


    public void setGuideXingShiZheng(boolean isGuide) {
        this.mEditor.putBoolean(GUIDE_XINGSHIZHENG, isGuide);
        this.mEditor.commit();
    }

    public boolean getGuideXingShiZheng() {
        return this.mSharedPreferences.getBoolean(GUIDE_XINGSHIZHENG, false);
    }


    public void setGuideJiaShiZheng(boolean isGuide) {
        this.mEditor.putBoolean(GUIDE_JIASHIZHEGN, isGuide);
        this.mEditor.commit();
    }

    public boolean getGuideJiaShiZheng() {
        return this.mSharedPreferences.getBoolean(GUIDE_JIASHIZHEGN, false);
    }

    public void setGuideBanKa(boolean isGuide) {
        this.mEditor.putBoolean(GUIDE_BANKA, isGuide);
        this.mEditor.commit();
    }

    public boolean getGuideBanKa() {
        return this.mSharedPreferences.getBoolean(GUIDE_BANKA, false);
    }

    public void setWeizhangPush(boolean isPush) {
        this.mEditor.putBoolean(WEIZHANG_PUSH, isPush);
        this.mEditor.commit();
    }

    public boolean getWeizhangPush() {
        return this.mSharedPreferences.getBoolean(WEIZHANG_PUSH, true);
    }

    public void setJifenPush(boolean isPush) {
        this.mEditor.putBoolean(JIFEN_PUSH, isPush);
        this.mEditor.commit();
    }

    public boolean getJifenPush() {
        return this.mSharedPreferences.getBoolean(JIFEN_PUSH, true);
    }

    public void setIsGuide(String isPush) {
        this.mEditor.putString(IS_GUIDE, isPush);
        this.mEditor.commit();
    }

    public String getIsGuide() {
        return this.mSharedPreferences.getString(IS_GUIDE, "1");
    }

    public void setSignStatus(boolean isSign) {
        this.mEditor.putBoolean(SIGN_STATUS, isSign);
        this.mEditor.commit();
    }

    public boolean getSignStatus() {
        return this.mSharedPreferences.getBoolean(SIGN_STATUS, false);
    }


    public void setXinnengyuanFlag(boolean isSign) {
        this.mEditor.putBoolean(XINNENGYUAN_FLAG, isSign);
        this.mEditor.commit();
    }

    public boolean getXinnengyuanFlag() {
        return this.mSharedPreferences.getBoolean(XINNENGYUAN_FLAG, true);
    }

    public void setSignCarPlate(String str) {
        this.mEditor.putString(SIGN_CAR_PLATE, str);
        this.mEditor.commit();
    }

    public String getSignCarPlate() {
        return this.mSharedPreferences.getString(SIGN_CAR_PLATE, "");
    }

    public void setOilCard(String cardNum) {
        this.mEditor.putString(OIL_CARD, cardNum);
        this.mEditor.commit();
    }

    public String getOilCard() {
        return this.mSharedPreferences.getString(OIL_CARD, "");
    }

    /**
     * 保存驾驶证查分请求数据
     *
     * @param licenseFileNumDTO
     */
    public void saveLicenseFileNumDTO(LicenseFileNumDTO licenseFileNumDTO) {
        String str = "";
        try {
            str = SerializableUtils.obj2Str(licenseFileNumDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mEditor.putString(LICENSE_FILE_NUM, str);
        this.mEditor.commit();
    }

    public LicenseFileNumDTO getLicenseFileNumDTO() {
        LicenseFileNumDTO dto = null;
        // 获取序列化的数据
        String str = this.mSharedPreferences.getString(LICENSE_FILE_NUM, "");
        try {
            Object obj = SerializableUtils.str2Obj(str);
            if (obj != null) dto = (LicenseFileNumDTO) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    /**
     * 保存登录用户数据
     *
     * @param mLoginInfoBean
     */
    public void setLoginInfoBean(RspInfoBean mLoginInfoBean) {
        String str = "";
        try {
            str = SerializableUtils.obj2Str(mLoginInfoBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mEditor.putString(LOGIN_INFO_BEAN, str);
        this.mEditor.commit();
    }

    public RspInfoBean getLoginInfoBean() {

        RspInfoBean mLoginInfoBean = null;
        // 获取序列化的数据
        String str = this.mSharedPreferences.getString(LOGIN_INFO_BEAN, "");
        try {
            Object obj = SerializableUtils.str2Obj(str);
            if (obj != null) mLoginInfoBean = (RspInfoBean) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mLoginInfoBean;
    }
}
