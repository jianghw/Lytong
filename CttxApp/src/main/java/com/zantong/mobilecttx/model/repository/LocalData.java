package com.zantong.mobilecttx.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zantong.mobilecttx.application.MemoryData;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.lang.ref.WeakReference;

import static com.zantong.mobilecttx.utils.rsa.RSAUtils.strByEncryption;

/**
 * Created by jianghw on 2017/4/26.
 * 本地数据处理
 */

public class LocalData {
    @Nullable
    private static LocalData INSTANCE = null;
    private final WeakReference<Context> weakReference;

    public static LocalData getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalData(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private LocalData(Context context) {
        weakReference = new WeakReference<>(context);
    }

    BaseDTO initBaseDTO() {
        BaseDTO dto = new BaseDTO();
        dto.setUsrId(strByEncryption(MemoryData.getInstance().userID, true));
        return dto;
    }

    public MessageDetailDTO initMessageDetailDTO() {
        MessageDetailDTO dto = new MessageDetailDTO();
        dto.setUsrId(strByEncryption(MemoryData.getInstance().userID, true));
        return dto;
    }

    /**
     * 获取用户id值
     *
     * @return
     */
    public String DefaultUserID() {
        return MemoryData.getInstance().userID;
    }

    /**
     * 用户id值，加密后
     *
     * @return
     */
    public String getRASUserID() {
        return getStrByEncryption(DefaultUserID());
    }

    /**
     * 数据加密处理
     *
     * @param string
     * @return
     */
    public String getStrByEncryption(String string) {
        return RSAUtils.strByEncryption(string, true);
    }

    /**
     * 登陆用户信息
     */
    public RspInfoBean getDefaultUser() {
        return MemoryData.getInstance().mLoginInfoBean;
    }

    /**
     * 安盛请求必须构建体
     *
     * @return
     */
    public RequestHeadDTO initRequestHeadDTO() {

        RequestHeadDTO dto = new RequestHeadDTO();

        dto.setConsumerId("04");
        dto.setRequestDate(DateUtils.getDate());
        dto.setRequestTime(DateUtils.getTime());
        dto.setConsumerSeqNo(StringUtils.getRandomStr());
        dto.setDvcToken(Tools.getIMEI());
        return dto;
    }

    public void saveLicenseFileNumDTO(LicenseFileNumDTO licenseFileNumDTO) {
        SPUtils.getInstance().saveLicenseFileNumDTO(licenseFileNumDTO);
    }

    /**
     * 获取登录信息
     *
     */
    public RspInfoBean readObjectLoginInfoBean() {
        RspInfoBean rspInfoBean;
        rspInfoBean = (RspInfoBean) UserInfoRememberCtrl.readObject();
        return rspInfoBean;
    }

    /**
     * 初始化用户登录信息
     *
     * @param rspInfoBean
     */
    public void initGlobalLoginInfo(RspInfoBean rspInfoBean) {
        if (rspInfoBean == null) return;
        MemoryData.getInstance().mLoginInfoBean = rspInfoBean;
        MemoryData.getInstance().userID = rspInfoBean.getUsrid();
        MemoryData.getInstance().filenum = rspInfoBean.getFilenum();
        MemoryData.getInstance().getdate = rspInfoBean.getGetdate();
        MemoryData.getInstance().loginFlag = true;

        OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBean =
                (OpenQueryBean.RspInfoBean.UserCarsInfoBean)
                        UserInfoRememberCtrl.readObject(MemoryData.getInstance().DefaultCarLocalFlag);
        if (mUserCarsInfoBean != null) {
            MemoryData.getInstance().defaultCar = true;
            MemoryData.getInstance().defaultCarNumber = mUserCarsInfoBean.getCarnum();
        }
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    public String readLoginPassword() {
        String pwd;
        pwd = (String) UserInfoRememberCtrl.readObject(UserInfoRememberCtrl.USERPD);
        return pwd;
    }

    /**
     * 登录后重复保存数据
     *
     * @param result
     */
    public void saveLoginInfoRepeat(LoginInfoBean result) {
        initGlobalLoginInfo(result.getRspInfo());
        UserInfoRememberCtrl.saveObject(UserInfoRememberCtrl.USERDEVICE, MemoryData.getInstance().imei);
        UserInfoRememberCtrl.saveObject(result.getRspInfo());
    }
}
