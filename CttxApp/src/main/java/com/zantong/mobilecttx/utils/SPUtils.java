package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

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

    private Editor editor;
    private SharedPreferences sp;
    private static SPUtils sInstance;

    private SPUtils(Context mContext) {

        this.sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        this.editor = this.sp.edit();
    }

    public static SPUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SPUtils(context);
        }
        return sInstance;
    }

    /**
     * 清除所有配置
     */
    public void clear() {
        this.editor.clear();
        this.editor.commit();
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
        this.editor.putString(CARSINFO, str);
        this.editor.commit();
    }

    /**
     * 获取车辆信息
     *
     * @return
     */
    public List<CarInfoDTO> getCarsInfo() {
        if (carsinfo == null) {
            carsinfo = new ArrayList<CarInfoDTO>();
            // 获取序列化的数据
            String str = this.sp.getString(CARSINFO, "");

            try {
                Object obj = SerializableUtils.str2Obj(str);
                if (obj != null) {
                    carsinfo = (List<CarInfoDTO>) obj;
                }

            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        this.editor.putString(VIOLATION, str);
        this.editor.commit();
    }

    /**
     * 获取车辆信息
     *
     * @return
     */
    public ViolationDTO getViolation() {
        ViolationDTO violationDTO = null;
        // 获取序列化的数据
        String str = this.sp.getString(VIOLATION, "");
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
        this.editor.putBoolean(GUIDE_SAOFADAN, isGuide);
        this.editor.commit();
    }

    public boolean getGuideSaoFaDan() {
        return this.sp.getBoolean(GUIDE_SAOFADAN, false);
    }

    public void setUserPwd(String userPwd) {
        this.editor.putString(UESR_PWD, userPwd);
        this.editor.commit();
    }

    public String getUserPwd() {
        return this.sp.getString(UESR_PWD, "111111a");
    }

    public void setGuideWeiZhang(boolean isGuide) {
        this.editor.putBoolean(GUIDE_WEIZHANG, isGuide);
        this.editor.commit();
    }

    public boolean getGuideWeiZhang() {
        return this.sp.getBoolean(GUIDE_WEIZHANG, false);
    }


    public void setGuideXingShiZheng(boolean isGuide) {
        this.editor.putBoolean(GUIDE_XINGSHIZHENG, isGuide);
        this.editor.commit();
    }

    public boolean getGuideXingShiZheng() {
        return this.sp.getBoolean(GUIDE_XINGSHIZHENG, false);
    }


    public void setGuideJiaShiZheng(boolean isGuide) {
        this.editor.putBoolean(GUIDE_JIASHIZHEGN, isGuide);
        this.editor.commit();
    }

    public boolean getGuideJiaShiZheng() {
        return this.sp.getBoolean(GUIDE_JIASHIZHEGN, false);
    }

    public void setGuideBanKa(boolean isGuide) {
        this.editor.putBoolean(GUIDE_BANKA, isGuide);
        this.editor.commit();
    }

    public boolean getGuideBanKa() {
        return this.sp.getBoolean(GUIDE_BANKA, false);
    }

    public void setWeizhangPush(boolean isPush) {
        this.editor.putBoolean(WEIZHANG_PUSH, isPush);
        this.editor.commit();
    }

    public boolean getWeizhangPush() {
        return this.sp.getBoolean(WEIZHANG_PUSH, true);
    }

    public void setJifenPush(boolean isPush) {
        this.editor.putBoolean(JIFEN_PUSH, isPush);
        this.editor.commit();
    }

    public boolean getJifenPush() {
        return this.sp.getBoolean(JIFEN_PUSH, true);
    }

    public void setIsGuide(String isPush) {
        this.editor.putString(IS_GUIDE, isPush);
        this.editor.commit();
    }

    public String getIsGuide() {
        return this.sp.getString(IS_GUIDE, "1.0.0");
    }

    public void setSignStatus(boolean isSign) {
        this.editor.putBoolean(SIGN_STATUS, isSign);
        this.editor.commit();
    }

    public boolean getSignStatus() {
        return this.sp.getBoolean(SIGN_STATUS, false);
    }


    public void setXinnengyuanFlag(boolean isSign) {
        this.editor.putBoolean(XINNENGYUAN_FLAG, isSign);
        this.editor.commit();
    }

    public boolean getXinnengyuanFlag() {
        return this.sp.getBoolean(XINNENGYUAN_FLAG, true);
    }

    public void setSignCarPlate(String str) {
        this.editor.putString(SIGN_CAR_PLATE, str);
        this.editor.commit();
    }

    public String getSignCarPlate() {
        return this.sp.getString(SIGN_CAR_PLATE, "");
    }

    public void setOilCard(String cardNum) {
        this.editor.putString(OIL_CARD, cardNum);
        this.editor.commit();
    }

    public String getOilCard() {
        return this.sp.getString(OIL_CARD, "");
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
        this.editor.putString(LICENSE_FILE_NUM, str);
        this.editor.commit();
    }

    public LicenseFileNumDTO getLicenseFileNumDTO() {
        LicenseFileNumDTO dto = null;
        // 获取序列化的数据
        String str = this.sp.getString(LICENSE_FILE_NUM, "");
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
    public void setLoginInfoBean(LoginInfoBean.RspInfoBean mLoginInfoBean) {
        String str = "";
        try {
            str = SerializableUtils.obj2Str(mLoginInfoBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.editor.putString(LOGIN_INFO_BEAN, str);
        this.editor.commit();
    }

    public LoginInfoBean.RspInfoBean getLoginInfoBean(){

        LoginInfoBean.RspInfoBean mLoginInfoBean = null;
        // 获取序列化的数据
        String str = this.sp.getString(LOGIN_INFO_BEAN, "");
        try {
            Object obj = SerializableUtils.str2Obj(str);
            if (obj != null) mLoginInfoBean = (LoginInfoBean.RspInfoBean) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mLoginInfoBean;
    }
}
