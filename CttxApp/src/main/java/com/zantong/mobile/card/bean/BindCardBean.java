package com.zantong.mobile.card.bean;

import java.util.List;

/**
 * 作者：王海洋
 * 时间：2016/6/23 13:31
 */
public class BindCardBean {

    /**
     * totcount : 3
     * totamt : 2000
     * ViolationInfo : [{"violationcent":"00","processste":"1","carnum":"沪QV533W","paydate":"20141130","violationdate":"20140521","violationnum":"3103020126916942","violationamt":"1000"},{"violationcent":"00","processste":"1","carnum":"京QV533W","paydate":"","violationdate":"20140521","violationnum":"3103020126916943","violationamt":"1000"},{"violationcent":"01","processste":"0","carnum":"沪CJD168","paydate":"20141130","violationdate":"20131126","violationnum":"3103020727919166","violationamt":"1000"}]
     * totcarcount : 2
     */

    private RspInfoBean RspInfo;
    /**
     * Reserve :
     * TransServiceCode : cip.cfc.v001.01
     * ConsumerId : 04
     * ReturnCode : 000000
     * RequestDate : 20162823
     * ReturnStatus : S
     * ResponseTime : 131948
     * TransServiceScene :
     * ServerSeqNo :
     * ReturnMessage : 成功
     * RequestTime : 012827
     * ConsumerIP : 192.9.200.131
     * ResponseDate : 20160623
     * ServerIP :
     * ConsumerSeqNo : 86760102560606920162823012827737
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
        private String totcount;
        private String totamt;
        private String totcarcount;
        private String totcent;
        /**
         * violationcent : 00
         * processste : 1
         * carnum : 沪QV533W
         * paydate : 20141130
         * violationdate : 20140521
         * violationnum : 3103020126916942
         * violationamt : 1000
         */

        private List<ViolationInfoBean> ViolationInfo;

        public String getTotcount() {
            return totcount;
        }

        public void setTotcount(String totcount) {
            this.totcount = totcount;
        }

        public String getTotamt() {
            return totamt;
        }

        public void setTotamt(String totamt) {
            this.totamt = totamt;
        }

        public String getTotcarcount() {
            return totcarcount;
        }

        public void setTotcarcount(String totcarcount) {
            this.totcarcount = totcarcount;
        }
        public String getTotcent() {
            return totcent;
        }

        public void setTotcent(String totcent) {
            this.totcent = totcent;
        }

        public List<ViolationInfoBean> getViolationInfo() {
            return ViolationInfo;
        }

        public void setViolationInfo(List<ViolationInfoBean> ViolationInfo) {
            this.ViolationInfo = ViolationInfo;
        }

        public static class ViolationInfoBean {
            private String violationcent;
            private String processste;
            private String carnum;
            private String paydate;
            private String violationdate;
            private String violationnum;
            private String violationamt;

            public String getViolationcent() {
                return violationcent;
            }

            public void setViolationcent(String violationcent) {
                this.violationcent = violationcent;
            }

            public String getProcessste() {
                return processste;
            }

            public void setProcessste(String processste) {
                this.processste = processste;
            }

            public String getCarnum() {
                return carnum;
            }

            public void setCarnum(String carnum) {
                this.carnum = carnum;
            }

            public String getPaydate() {
                return paydate;
            }

            public void setPaydate(String paydate) {
                this.paydate = paydate;
            }

            public String getViolationdate() {
                return violationdate;
            }

            public void setViolationdate(String violationdate) {
                this.violationdate = violationdate;
            }

            public String getViolationnum() {
                return violationnum;
            }

            public void setViolationnum(String violationnum) {
                this.violationnum = violationnum;
            }

            public String getViolationamt() {
                return violationamt;
            }

            public void setViolationamt(String violationamt) {
                this.violationamt = violationamt;
            }
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
