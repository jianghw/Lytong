package com.zantong.mobilecttx.home.bean;

import java.util.List;

/**
 * 作者：王海洋
 * 时间：2016/6/10 11:12
 */
public class UpdateInfo {

    /**
     * Reserve :
     * TransServiceCode : cip.cfc.u003.01
     * ConsumerId : 04
     * ReturnCode : 000000
     * RequestDate : 20161110
     * ReturnStatus : S
     * ResponseTime : 110235
     * TransServiceScene :
     * ServerSeqNo :
     * ReturnMessage : 成功
     * RequestTime : 111115
     * ConsumerIP : 192.9.200.131
     * ResponseDate : 20160610
     * ServerIP :
     * ConsumerSeqNo : 86760102560606920161110111115767
     * TranMode :
     */

    private SYSHEADBean SYS_HEAD;
    /**
     * AuthTellerInfo : []
     * TranTellerType :
     * TranTellerNo : 000000
     * TranBranchId : 000000
     * TranTellerPassword :
     * TranTellerLevel :
     */

    private APPHEADBean APP_HEAD;

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
        private List<?> AuthTellerInfo;

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

        public List<?> getAuthTellerInfo() {
            return AuthTellerInfo;
        }

        public void setAuthTellerInfo(List<?> AuthTellerInfo) {
            this.AuthTellerInfo = AuthTellerInfo;
        }
    }
}
