package com.zantong.mobilecttx.user.bean;

import java.util.List;

/**
 * 作者：王海洋
 * 时间：2016/6/7 16:21
 */
public class SmsBean {


    /**
     * onlyflag : 000000000000000000000000000000001022
     * rgstste : 0
     */

    private RspInfoBean RspInfo;
    /**
     * TransServiceCode : cip.cfc.u014.01
     * ReturnCode : 000000
     * ConsumerSeqNo : 86760102560606920165319055331046
     * ConsumerId : 04
     * RequestDate : 20165319
     * ResponseTime : 175311
     * ReturnStatus : S
     * ReturnMessage : 成功
     * ConsumerIP : 107.6.61.71
     * RequestTime : 055331
     * ResponseDate : 20160731
     */

    private SYSHEADBean SYS_HEAD;
    /**
     * AuthTellerInfo : []
     * TranTellerNo : 000000
     * TranBranchId : 000000
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
        private String onlyflag;
        private String rgstste;

        public String getOnlyflag() {
            return onlyflag;
        }

        public void setOnlyflag(String onlyflag) {
            this.onlyflag = onlyflag;
        }

        public String getRgstste() {
            return rgstste;
        }

        public void setRgstste(String rgstste) {
            this.rgstste = rgstste;
        }
    }

    public static class SYSHEADBean {
        private String TransServiceCode;
        private String ReturnCode;
        private String ConsumerSeqNo;
        private String ConsumerId;
        private String RequestDate;
        private String ResponseTime;
        private String ReturnStatus;
        private String ReturnMessage;
        private String ConsumerIP;
        private String RequestTime;
        private String ResponseDate;

        public String getTransServiceCode() {
            return TransServiceCode;
        }

        public void setTransServiceCode(String TransServiceCode) {
            this.TransServiceCode = TransServiceCode;
        }

        public String getReturnCode() {
            return ReturnCode;
        }

        public void setReturnCode(String ReturnCode) {
            this.ReturnCode = ReturnCode;
        }

        public String getConsumerSeqNo() {
            return ConsumerSeqNo;
        }

        public void setConsumerSeqNo(String ConsumerSeqNo) {
            this.ConsumerSeqNo = ConsumerSeqNo;
        }

        public String getConsumerId() {
            return ConsumerId;
        }

        public void setConsumerId(String ConsumerId) {
            this.ConsumerId = ConsumerId;
        }

        public String getRequestDate() {
            return RequestDate;
        }

        public void setRequestDate(String RequestDate) {
            this.RequestDate = RequestDate;
        }

        public String getResponseTime() {
            return ResponseTime;
        }

        public void setResponseTime(String ResponseTime) {
            this.ResponseTime = ResponseTime;
        }

        public String getReturnStatus() {
            return ReturnStatus;
        }

        public void setReturnStatus(String ReturnStatus) {
            this.ReturnStatus = ReturnStatus;
        }

        public String getReturnMessage() {
            return ReturnMessage;
        }

        public void setReturnMessage(String ReturnMessage) {
            this.ReturnMessage = ReturnMessage;
        }

        public String getConsumerIP() {
            return ConsumerIP;
        }

        public void setConsumerIP(String ConsumerIP) {
            this.ConsumerIP = ConsumerIP;
        }

        public String getRequestTime() {
            return RequestTime;
        }

        public void setRequestTime(String RequestTime) {
            this.RequestTime = RequestTime;
        }

        public String getResponseDate() {
            return ResponseDate;
        }

        public void setResponseDate(String ResponseDate) {
            this.ResponseDate = ResponseDate;
        }
    }

    public static class APPHEADBean {
        private String TranTellerNo;
        private String TranBranchId;
        private List<?> AuthTellerInfo;

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

        public List<?> getAuthTellerInfo() {
            return AuthTellerInfo;
        }

        public void setAuthTellerInfo(List<?> AuthTellerInfo) {
            this.AuthTellerInfo = AuthTellerInfo;
        }
    }
}
