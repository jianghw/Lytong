package com.zantong.mobile.user.bean;

import java.util.List;

/**
 * 作者：王海洋
 * 时间：2016/7/7 14:44
 */
public class CTTXInsurancePayBean {

    /**
     * pymtste : 1
     */

    private RspInfoBean RspInfo;
    /**
     * Reserve : null
     * TransServiceCode : cip.cfc.i006.01
     * ConsumerId : 04
     * ReturnCode : 000000
     * RequestDate : 20164307
     * ReturnStatus : S
     * ResponseTime : 143444
     * TransServiceScene : null
     * ServerSeqNo : null
     * ReturnMessage : 成功
     * RequestTime : 024338
     * ConsumerIP : 192.9.200.131
     * ResponseDate : 20160707
     * ServerIP : null
     * ConsumerSeqNo : 86760102560606920164307024338462
     * TranMode : null
     */

    private SYSHEADBean SYS_HEAD;
    /**
     * AuthTellerInfo : []
     * TranTellerType : null
     * TranTellerNo : 000000
     * TranBranchId : 000000
     * TranTellerPassword : null
     * TranTellerLevel : null
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

    public static class RspInfoBean {
        private String pymtste;

        public String getPymtste() {
            return pymtste;
        }

        public void setPymtste(String pymtste) {
            this.pymtste = pymtste;
        }
    }

    public static class SYSHEADBean {
        private Object Reserve;
        private String TransServiceCode;
        private String ConsumerId;
        private String ReturnCode;
        private String RequestDate;
        private String ReturnStatus;
        private String ResponseTime;
        private Object TransServiceScene;
        private Object ServerSeqNo;
        private String ReturnMessage;
        private String RequestTime;
        private String ConsumerIP;
        private String ResponseDate;
        private Object ServerIP;
        private String ConsumerSeqNo;
        private Object TranMode;

        public Object getReserve() {
            return Reserve;
        }

        public void setReserve(Object Reserve) {
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

        public Object getTransServiceScene() {
            return TransServiceScene;
        }

        public void setTransServiceScene(Object TransServiceScene) {
            this.TransServiceScene = TransServiceScene;
        }

        public Object getServerSeqNo() {
            return ServerSeqNo;
        }

        public void setServerSeqNo(Object ServerSeqNo) {
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

        public Object getServerIP() {
            return ServerIP;
        }

        public void setServerIP(Object ServerIP) {
            this.ServerIP = ServerIP;
        }

        public String getConsumerSeqNo() {
            return ConsumerSeqNo;
        }

        public void setConsumerSeqNo(String ConsumerSeqNo) {
            this.ConsumerSeqNo = ConsumerSeqNo;
        }

        public Object getTranMode() {
            return TranMode;
        }

        public void setTranMode(Object TranMode) {
            this.TranMode = TranMode;
        }
    }

    public static class APPHEADBean {
        private Object TranTellerType;
        private String TranTellerNo;
        private String TranBranchId;
        private Object TranTellerPassword;
        private Object TranTellerLevel;
        private List<?> AuthTellerInfo;

        public Object getTranTellerType() {
            return TranTellerType;
        }

        public void setTranTellerType(Object TranTellerType) {
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

        public Object getTranTellerPassword() {
            return TranTellerPassword;
        }

        public void setTranTellerPassword(Object TranTellerPassword) {
            this.TranTellerPassword = TranTellerPassword;
        }

        public Object getTranTellerLevel() {
            return TranTellerLevel;
        }

        public void setTranTellerLevel(Object TranTellerLevel) {
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
