package com.zantong.mobilecttx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.dto.LicenseFileNumDTO;

import java.io.IOException;

/**
 * SharedPreference 配置文件工具类
 *
 * @author Sandy
 *         create at 16/6/1 下午6:25
 */
public class SPUtils {
    /**
     * 清单文件名称
     */
    public static final String SP_NAME = "cttx";

    /* 车辆信息表 */
    public static final String CARSINFO = "carsinfo";
    /* 是否引导扫罚单 */
    public static final String GUIDE_SAOFADAN = "saofadan";
    /* 是否引导违章查询 */
    public static final String GUIDE_WEIZHANG = "weizhang";
    /* 是否引导行驶证扫描 */
    public static final String GUIDE_XINGSHIZHENG = "xingshizheng";
    /* 是否引导驾照扫描 */
    public static final String GUIDE_JIASHIZHEGN = "jiashizheng";
    /* 是否引导申办畅卡 */
    public static final String GUIDE_BANKA = "shenbanchangtongka";
    /* 是否开启违章通知 */
    public static final String WEIZHANG_PUSH = "weizhangpush";
    /* 是否开启记分周期更新通知 */
    public static final String JIFEN_PUSH = "jifenpush";
    /* 引导页 */
    public static final String IS_GUIDE = "isGuide";
    /* 用户密码 */
    public static final String UESR_PWD = "user_pwd";

    public static final String VIOLATION = "violation";

    public static final String LICENSE_FILE_NUM = "license_file_num";

    public static final String SIGN_STATUS = "signstatus";//活动报名状态

    public static final String SIGN_CAR_PLATE = "signcarpalte";//活动报名车牌号

    public static final String XINNENGYUAN_FLAG = "xinnengyuanflag";//选择新能源车型的提示

    public static final String OIL_CARD = "oilcard";//加油卡卡号

    public static final String LOGIN_INFO_BEAN = "login_info_bean";

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
