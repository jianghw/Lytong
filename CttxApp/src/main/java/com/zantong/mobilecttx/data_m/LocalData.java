package com.zantong.mobilecttx.data_m;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tzly.ctcyh.router.custom.rea.RSAUtils;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.tzly.ctcyh.java.request.RequestHeadDTO;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.RspInfoBean;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;
import com.zantong.mobilecttx.utils.DateUtils;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.lang.ref.WeakReference;


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
        dto.setUsrId(MainRouter.getRASUserID());
        return dto;
    }

    public MessageDetailDTO initMessageDetailDTO() {
        MessageDetailDTO dto = new MessageDetailDTO();
        dto.setUsrId(MainRouter.getRASUserID());
        return dto;
    }

    /**
     * 获取用户id值 空值会调起登录页面
     */
    public String DefaultUserID() {
        return MainRouter.getUserID();
    }

    /**
     * 用户id值，加密后
     *
     * @return
     */
    public String getRASUserID() {
        return getRASByEncryption(DefaultUserID());
    }

    /**
     * 数据加密处理
     *
     * @param string
     * @return
     */
    public String getRASByEncryption(String string) {
        return RSAUtils.strByEncryption(string, true);
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
     */
    public RspInfoBean readObjectLoginInfoBean() {
        RspInfoBean rspInfoBean;
        rspInfoBean = (RspInfoBean) UserInfoRememberCtrl.readObject();
        return rspInfoBean;
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

}
