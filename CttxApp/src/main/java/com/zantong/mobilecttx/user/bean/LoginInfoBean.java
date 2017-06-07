package com.zantong.mobilecttx.user.bean;

import com.zantong.mobilecttx.base.bean.BeanModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/5.
 */
public class LoginInfoBean extends BeanModel {

    /**
     * lastlogindate : 20160525
     * remindflag : 0
     * getdate :
     * nickname :
     * relatedcard :
     * ctftp :
     * portrait :
     * lastlogintime : 144434
     * upddate : 20160505
     * regdate : 20160505
     * filenum :
     * ctfnum :
     * usrid : 00010013521396353
     * phoenum : 13521396353
     */

    private RspInfoBean RspInfo ;
    /**
     * Reserve :
     * TransServiceCode : cip.cfc.u001.01
     * ConsumerId : 04
     * ReturnCode : 000000
     * RequestDate : 20165225
     * ReturnStatus : S
     * ResponseTime : 144434
     * TransServiceScene :
     * ServerSeqNo :
     * ReturnMessage : 成功
     * RequestTime : 025230
     * ConsumerIP : 192.9.200.131
     * ResponseDate : 20160525
     * ServerIP :
     * ConsumerSeqNo : 20165225025230
     * TranMode :
     */

    private SYSHEADBean SYS_HEAD;
    /**
     * AuthTellerInfo : [{"AuthTellerLevel":"","AuthTellerPassword":"","AuthBranchId":"","AuthTellerType":"","AuthTellerNo":""}]
     * TranTellerType :
     * TranTellerNo :
     * TranBranchId : 000000
     * TranTellerPassword :
     * TranTellerLevel :
     */

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

    public static class RspInfoBean implements Serializable{
        private String lastlogindate;
        private String remindflag;
        private String getdate;
        private String nickname;
        private String relatedcard;
        private String ctftp;
        private String portrait;
        private String lastlogintime;
        private String upddate;
        private String regdate;
        private String filenum;
        private String ctfnum;
        private String usrid;
        private String phoenum;
        private String recdphoe;
        private String recddt;
        private String pswderrnum;

        public String getPswderrnum() {
            return pswderrnum;
        }

        public void setPswderrnum(String pswderrnum) {
            this.pswderrnum = pswderrnum;
        }



        public String getRecdphoe() {
            return recdphoe;
        }

        public void setRecdphoe(String recdphoe) {
            this.recdphoe = recdphoe;
        }

        public String getRecddt() {
            return recddt;
        }

        public void setRecddt(String recddt) {
            this.recddt = recddt;
        }

        public String getLastlogindate() {
            return lastlogindate;
        }

        public void setLastlogindate(String lastlogindate) {
            this.lastlogindate = lastlogindate;
        }

        public String getRemindflag() {
            return remindflag;
        }

        public void setRemindflag(String remindflag) {
            this.remindflag = remindflag;
        }

        public String getGetdate() {
            return getdate;
        }

        public void setGetdate(String getdate) {
            this.getdate = getdate;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRelatedcard() {
            return relatedcard;
        }

        public void setRelatedcard(String relatedcard) {
            this.relatedcard = relatedcard;
        }

        public String getCtftp() {
            return ctftp;
        }

        public void setCtftp(String ctftp) {
            this.ctftp = ctftp;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getLastlogintime() {
            return lastlogintime;
        }

        public void setLastlogintime(String lastlogintime) {
            this.lastlogintime = lastlogintime;
        }

        public String getUpddate() {
            return upddate;
        }

        public void setUpddate(String upddate) {
            this.upddate = upddate;
        }

        public String getRegdate() {
            return regdate;
        }

        public void setRegdate(String regdate) {
            this.regdate = regdate;
        }

        public String getFilenum() {
            return filenum;
        }

        public void setFilenum(String filenum) {
            this.filenum = filenum;
        }

        public String getCtfnum() {
            return ctfnum;
        }

        public void setCtfnum(String ctfnum) {
            this.ctfnum = ctfnum;
        }

        public String getUsrid() {
            return usrid;
        }

        public void setUsrid(String usrid) {
            this.usrid = usrid;
        }

        public String getPhoenum() {
            return phoenum;
        }

        public void setPhoenum(String phoenum) {
            this.phoenum = phoenum;
        }
    }

    public static class SYSHEADBean {
        private String Reserve;
        private String TransServiceCode;
        private String ConsumerId;
        private String ReturnCode;
        private String RequestDate;
        private String ReturnStatus;
        private String ResponseTime;
        private String TransServiceScene;
        private String ServerSeqNo;
        private String ReturnMessage;
        private String RequestTime;
        private String ConsumerIP;
        private String ResponseDate;
        private String ServerIP;
        private String ConsumerSeqNo;
        private String TranMode;

        public String getReserve() {
            return Reserve;
        }

        public void setReserve(String Reserve) {
            this.Reserve = Reserve;
        }

        public String getTransServiceCode() {
            return TransServiceCode;
        }

        public void setTransServiceCode(String TransServiceCode) {
            this.TransServiceCode = TransServiceCode;
        }

        public String getConsumerId() {
            return ConsumerId;
        }

        public void setConsumerId(String ConsumerId) {
            this.ConsumerId = ConsumerId;
        }

        public String getReturnCode() {
            return ReturnCode;
        }

        public void setReturnCode(String ReturnCode) {
            this.ReturnCode = ReturnCode;
        }

        public String getRequestDate() {
            return RequestDate;
        }

        public void setRequestDate(String RequestDate) {
            this.RequestDate = RequestDate;
        }

        public String getReturnStatus() {
            return ReturnStatus;
        }

        public void setReturnStatus(String ReturnStatus) {
            this.ReturnStatus = ReturnStatus;
        }

        public String getResponseTime() {
            return ResponseTime;
        }

        public void setResponseTime(String ResponseTime) {
            this.ResponseTime = ResponseTime;
        }

        public String getTransServiceScene() {
            return TransServiceScene;
        }

        public void setTransServiceScene(String TransServiceScene) {
            this.TransServiceScene = TransServiceScene;
        }

        public String getServerSeqNo() {
            return ServerSeqNo;
        }

        public void setServerSeqNo(String ServerSeqNo) {
            this.ServerSeqNo = ServerSeqNo;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public void setReturnMessage(String ReturnMessage) {
            this.ReturnMessage = ReturnMessage;
        }

        public String getRequestTime() {
            return RequestTime;
        }

        public void setRequestTime(String RequestTime) {
            this.RequestTime = RequestTime;
        }

        public String getConsumerIP() {
            return ConsumerIP;
        }

        public void setConsumerIP(String ConsumerIP) {
            this.ConsumerIP = ConsumerIP;
        }

        public String getResponseDate() {
            return ResponseDate;
        }

        public void setResponseDate(String ResponseDate) {
            this.ResponseDate = ResponseDate;
        }

        public String getServerIP() {
            return ServerIP;
        }

        public void setServerIP(String ServerIP) {
            this.ServerIP = ServerIP;
        }

        public String getConsumerSeqNo() {
            return ConsumerSeqNo;
        }

        public void setConsumerSeqNo(String ConsumerSeqNo) {
            this.ConsumerSeqNo = ConsumerSeqNo;
        }

        public String getTranMode() {
            return TranMode;
        }

        public void setTranMode(String TranMode) {
            this.TranMode = TranMode;
        }
    }

    public static class APPHEADBean {
        private String TranTellerType;
        private String TranTellerNo;
        private String TranBranchId;
        private String TranTellerPassword;
        private String TranTellerLevel;
        /**
         * AuthTellerLevel :
         * AuthTellerPassword :
         * AuthBranchId :
         * AuthTellerType :
         * AuthTellerNo :
         */

        private List<AuthTellerInfoBean> AuthTellerInfo;

        public String getTranTellerType() {
            return TranTellerType;
        }

        public void setTranTellerType(String TranTellerType) {
            this.TranTellerType = TranTellerType;
        }

        public String getTranTellerNo() {
            return TranTellerNo;
        }

        public void setTranTellerNo(String TranTellerNo) {
            this.TranTellerNo = TranTellerNo;
        }

        public String getTranBranchId() {
            return TranBranchId;
        }

        public void setTranBranchId(String TranBranchId) {
            this.TranBranchId = TranBranchId;
        }

        public String getTranTellerPassword() {
            return TranTellerPassword;
        }

        public void setTranTellerPassword(String TranTellerPassword) {
            this.TranTellerPassword = TranTellerPassword;
        }

        public String getTranTellerLevel() {
            return TranTellerLevel;
        }

        public void setTranTellerLevel(String TranTellerLevel) {
            this.TranTellerLevel = TranTellerLevel;
        }

        public List<AuthTellerInfoBean> getAuthTellerInfo() {
            return AuthTellerInfo;
        }

        public void setAuthTellerInfo(List<AuthTellerInfoBean> AuthTellerInfo) {
            this.AuthTellerInfo = AuthTellerInfo;
        }

        public static class AuthTellerInfoBean {
            private String AuthTellerLevel;
            private String AuthTellerPassword;
            private String AuthBranchId;
            private String AuthTellerType;
            private String AuthTellerNo;

            public String getAuthTellerLevel() {
                return AuthTellerLevel;
            }

            public void setAuthTellerLevel(String AuthTellerLevel) {
                this.AuthTellerLevel = AuthTellerLevel;
            }

            public String getAuthTellerPassword() {
                return AuthTellerPassword;
            }

            public void setAuthTellerPassword(String AuthTellerPassword) {
                this.AuthTellerPassword = AuthTellerPassword;
            }

            public String getAuthBranchId() {
                return AuthBranchId;
            }

            public void setAuthBranchId(String AuthBranchId) {
                this.AuthBranchId = AuthBranchId;
            }

            public String getAuthTellerType() {
                return AuthTellerType;
            }

            public void setAuthTellerType(String AuthTellerType) {
                this.AuthTellerType = AuthTellerType;
            }

            public String getAuthTellerNo() {
                return AuthTellerNo;
            }

            public void setAuthTellerNo(String AuthTellerNo) {
                this.AuthTellerNo = AuthTellerNo;
            }
        }
    }
}
