package com.zantong.mobilecttx.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
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
        Context context = weakReference.get();
        BaseDTO dto = new BaseDTO();
        if (context != null) {
            dto.setUsrId(strByEncryption(PublicData.getInstance().userID, true));
        }
        return dto;
    }

    public MessageDetailDTO initMessageDetailDTO() {
        Context context = weakReference.get();
        MessageDetailDTO dto = new MessageDetailDTO();
        if (context != null) {
            dto.setUsrId(strByEncryption(PublicData.getInstance().userID, true));
        }
        return dto;
    }

    /**
     * 获取用户id值
     *
     * @return
     */
    public String DefaultUserID() {
        return PublicData.getInstance().userID;
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
        Context context = weakReference.get();
        if (context != null) {
            string = RSAUtils.strByEncryption(string, true);
        }
        return string;
    }

    /**
     * 安盛请求必须构建体
     *
     * @return
     */
    public RequestHeadDTO initRequestHeadDTO() {
        Context context = weakReference.get();
        RequestHeadDTO dto = new RequestHeadDTO();
        if (context != null) {
            dto.setConsumerId("04");
            dto.setRequestDate(DateUtils.getDate());
            dto.setRequestTime(DateUtils.getTime());
            dto.setConsumerSeqNo(StringUtils.getRandomStr());
            dto.setDvcToken(Tools.getIMEI());
        }
        return dto;
    }

    public void saveLicenseFileNumDTO(LicenseFileNumDTO licenseFileNumDTO) {
        Context context = weakReference.get();
        if (context != null) {
            SPUtils.getInstance().saveLicenseFileNumDTO(licenseFileNumDTO);
        }
    }

    /**
     * 获取登录信息
     *
     * @return LoginInfoBean.RspInfoBean
     */
    public LoginInfoBean.RspInfoBean readObjectLoginInfoBean() {
        Context context = weakReference.get();
        LoginInfoBean.RspInfoBean rspInfoBean = null;
        if (context != null) {
            rspInfoBean = (LoginInfoBean.RspInfoBean) UserInfoRememberCtrl.readObject(context);
        }
        return rspInfoBean;
    }

    /**
     * 初始化用户登录信息
     *
     * @param rspInfoBean
     */
    public void initGlobalLoginInfo(LoginInfoBean.RspInfoBean rspInfoBean) {
        if (rspInfoBean == null) return;
        PublicData.getInstance().mLoginInfoBean = rspInfoBean;
        PublicData.getInstance().userID = rspInfoBean.getUsrid();
        PublicData.getInstance().filenum = rspInfoBean.getFilenum();
        PublicData.getInstance().getdate = rspInfoBean.getGetdate();
        PublicData.getInstance().filenum = rspInfoBean.getFilenum();
        PublicData.getInstance().loginFlag = true;

        Context context = weakReference.get();
        if (context != null) {
            OpenQueryBean.RspInfoBean.UserCarsInfoBean mUserCarsInfoBean =
                    (OpenQueryBean.RspInfoBean.UserCarsInfoBean)
                            UserInfoRememberCtrl.readObject(
                                    context, PublicData.getInstance().DefaultCarLocalFlag);
            if (mUserCarsInfoBean != null) {
                PublicData.getInstance().defaultCar = true;
                PublicData.getInstance().defaultCarNumber = mUserCarsInfoBean.getCarnum();
            }
        }
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    public String readLoginPassword() {
        Context context = weakReference.get();
        String pwd = null;
        if (context != null) {
            pwd = (String) UserInfoRememberCtrl.readObject(context, UserInfoRememberCtrl.USERPD);
        }
        return pwd;
    }

    /**
     * 登录后重复保存数据
     *
     * @param result
     */
    public void saveLoginInfoRepeat(LoginInfoBean result) {
        Context context = weakReference.get();
        if (context != null) {
            initGlobalLoginInfo(result.getRspInfo());
            UserInfoRememberCtrl.saveObject(context, UserInfoRememberCtrl.USERDEVICE, PublicData.getInstance().imei);
            UserInfoRememberCtrl.saveObject(context, result.getRspInfo());
        }
    }
}
