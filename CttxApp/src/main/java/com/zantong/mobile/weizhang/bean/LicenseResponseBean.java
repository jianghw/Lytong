package com.zantong.mobile.weizhang.bean;

import com.zantong.mobile.base.bean.APPHEADBean;
import com.zantong.mobile.base.bean.SYSHEADBean;

/**
 * Created by jianghw on 2017/5/5.
 * 驾驶证查分 响应结果
 */

public class LicenseResponseBean {


    /**
     * RspInfo : {"ViolationInfo":[]}
     * SYS_HEAD : {"TransServiceCode":"cip.cfc.v001.01","ReturnCode":"CIC019","ConsumerSeqNo":"936FA29E-259D-40F5-A99B-B120115DD4C31493950161100065","ConsumerId":"04","RequestDate":"20170505","ResponseTime":"135144","ReturnStatus":"F","ReturnMessage":"重复的服务请求","ConsumerIP":"127.0.0.1","RequestTime":"100921","ResponseDate":"20170502"}
     * APP_HEAD : {"AuthTellerInfo":[]}
     */

    private RspInfoBean RspInfo;
    private SYSHEADBean SYS_HEAD;
    private APPHEADBean APP_HEAD;

    public RspInfoBean getRspInfo() {
        return RspInfo;
    }

    public void setRspInfo(RspInfoBean RspInfo) {
        this.RspInfo = RspInfo;
    }

    public SYSHEADBean getSYS_HEAD() {
        return SYS_HEAD;
    }

    public void setSYS_HEAD(SYSHEADBean SYS_HEAD) {
        this.SYS_HEAD = SYS_HEAD;
    }

    public APPHEADBean getAPP_HEAD() {
        return APP_HEAD;
    }

    public void setAPP_HEAD(APPHEADBean APP_HEAD) {
        this.APP_HEAD = APP_HEAD;
    }

}
