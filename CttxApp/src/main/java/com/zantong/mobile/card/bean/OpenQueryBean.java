package com.zantong.mobile.card.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class OpenQueryBean implements Serializable{
    private RspInfoBean RspInfo;
    /**
     * Reserve :
     * TransServiceCode : cip.cfc.c003.01
     * ConsumerId : 04
     * ReturnCode : 000000
     * RequestDate : 20164313
     * ReturnStatus : S
     * ResponseTime : 113456
     * TransServiceScene :
     * ServerSeqNo :
     * ReturnMessage : 成功
     * RequestTime : 114330
     * ConsumerIP : 192.9.200.131
     * ResponseDate : 20160613
     * ServerIP :
     * ConsumerSeqNo : 86760102560606920164313114330097
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

    public static class RspInfoBean  implements Serializable{
        /**
         * mileage :
         * carnumtype : 01
         * inspectdate :
         * ispaycar : 0
         * carmodel :
         * maintaindate :
         * defaultflag : 1
         * ViolationInfo : [{"updtime":"113456","violationtime":"2341243","carnumtype":null,"upddate":"20160613","violationcent":"10","processste":"0","paydate":"2015-01-01","carnum":null,"violationdate":"20140521","violationamt":"1000","violationnum":"3103020126916943"}]
         * carcolour :
         * maintaininterval :
         * carframenum :
         * inspectflag : 0
         * totcent : 10
         * carimage :
         * totcount : 1
         * enginenum : 66494
         * buydate :
         * carnum : 沪J64994
         * violationflag : 0
         * totamt : 1000
         */

        private List<UserCarsInfoBean> UserCarsInfo;

        public List<UserCarsInfoBean> getUserCarsInfo() {
            return UserCarsInfo;
        }

        public void setUserCarsInfo(List<UserCarsInfoBean> UserCarsInfo) {
            this.UserCarsInfo = UserCarsInfo;
        }

        public static class UserCarsInfoBean implements Serializable{
            private String mileage;
            private String carnumtype;
            private String inspectdate;
            private String ispaycar;
            private String carmodel;
            private String maintaindate;
            private String defaultflag;
            private String carcolour;
            private String maintaininterval;
            private String carframenum;
            private String inspectflag;
            private String totcent;
            private String carimage;
            private String totcount;
            private String enginenum;
            private String buydate;
            private String carnum;
            private String violationflag;
            private String totamt;
            private String untreatcount;
            private String untreatcent;
            private String untreatamt;
            /**
             * updtime : 113456
             * violationtime : 2341243
             * carnumtype : null
             * upddate : 20160613
             * violationcent : 10
             * processste : 0
             * paydate : 2015-01-01
             * carnum : null
             * violationdate : 20140521
             * violationamt : 1000
             * violationnum : 3103020126916943
             */

            private List<ViolationInfoBean> ViolationInfo;

            public String getMileage() {
                return mileage;
            }

            public void setMileage(String mileage) {
                this.mileage = mileage;
            }

            public String getCarnumtype() {
                return carnumtype;
            }

            public void setCarnumtype(String carnumtype) {
                this.carnumtype = carnumtype;
            }

            public String getInspectdate() {
                return inspectdate;
            }

            public void setInspectdate(String inspectdate) {
                this.inspectdate = inspectdate;
            }

            public String getIspaycar() {
                return ispaycar;
            }

            public void setIspaycar(String ispaycar) {
                this.ispaycar = ispaycar;
            }

            public String getCarmodel() {
                return carmodel;
            }

            public void setCarmodel(String carmodel) {
                this.carmodel = carmodel;
            }

            public String getMaintaindate() {
                return maintaindate;
            }

            public void setMaintaindate(String maintaindate) {
                this.maintaindate = maintaindate;
            }

            public String getDefaultflag() {
                return defaultflag;
            }

            public void setDefaultflag(String defaultflag) {
                this.defaultflag = defaultflag;
            }

            public String getCarcolour() {
                return carcolour;
            }

            public void setCarcolour(String carcolour) {
                this.carcolour = carcolour;
            }

            public String getMaintaininterval() {
                return maintaininterval;
            }

            public void setMaintaininterval(String maintaininterval) {
                this.maintaininterval = maintaininterval;
            }

            public String getCarframenum() {
                return carframenum;
            }

            public void setCarframenum(String carframenum) {
                this.carframenum = carframenum;
            }

            public String getInspectflag() {
                return inspectflag;
            }

            public void setInspectflag(String inspectflag) {
                this.inspectflag = inspectflag;
            }

            public String getTotcent() {
                return totcent;
            }

            public void setTotcent(String totcent) {
                this.totcent = totcent;
            }

            public String getCarimage() {
                return carimage;
            }

            public void setCarimage(String carimage) {
                this.carimage = carimage;
            }

            public String getTotcount() {
                return totcount;
            }

            public void setTotcount(String totcount) {
                this.totcount = totcount;
            }

            public String getEnginenum() {
                return enginenum;
            }

            public void setEnginenum(String enginenum) {
                this.enginenum = enginenum;
            }

            public String getBuydate() {
                return buydate;
            }

            public void setBuydate(String buydate) {
                this.buydate = buydate;
            }

            public String getCarnum() {
                return carnum;
            }

            public void setCarnum(String carnum) {
                this.carnum = carnum;
            }

            public String getViolationflag() {
                return violationflag;
            }

            public void setViolationflag(String violationflag) {
                this.violationflag = violationflag;
            }

            public String getTotamt() {
                return totamt;
            }

            public void setTotamt(String totamt) {
                this.totamt = totamt;
            }

            public String getUntreatcount() {
                return untreatcount;
            }

            public void setUntreatcount(String untreatcount) {
                this.untreatcount = untreatcount;
            }

            public String getUntreatcent() {
                return untreatcent;
            }

            public void setUntreatcent(String untreatcent) {
                this.untreatcent = untreatcent;
            }

            public String getUntreatamt() {
                return untreatamt;
            }

            public void setUntreatamt(String untreatamt) {
                this.untreatamt = untreatamt;
            }

            public List<ViolationInfoBean> getViolationInfo() {
                return ViolationInfo;
            }

            public void setViolationInfo(List<ViolationInfoBean> ViolationInfo) {
                this.ViolationInfo = ViolationInfo;
            }

            public static class ViolationInfoBean implements Serializable{
                private String updtime;
                private String violationtime;
                private Object carnumtype;
                private String upddate;
                private String violationcent;
                private String processste;
                private String paydate;
                private Object carnum;
                private String violationdate;
                private String violationamt;
                private String violationnum;
                private String check;

                public String getUpdtime() {
                    return updtime;
                }

                public void setUpdtime(String updtime) {
                    this.updtime = updtime;
                }

                public String getViolationtime() {
                    return violationtime;
                }

                public void setViolationtime(String violationtime) {
                    this.violationtime = violationtime;
                }

                public Object getCarnumtype() {
                    return carnumtype;
                }

                public void setCarnumtype(Object carnumtype) {
                    this.carnumtype = carnumtype;
                }

                public String getUpddate() {
                    return upddate;
                }

                public void setUpddate(String upddate) {
                    this.upddate = upddate;
                }

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

                public String getPaydate() {
                    return paydate;
                }

                public void setPaydate(String paydate) {
                    this.paydate = paydate;
                }

                public Object getCarnum() {
                    return carnum;
                }

                public void setCarnum(Object carnum) {
                    this.carnum = carnum;
                }

                public String getCheck() {
                    return check;
                }

                public void setCheck(String check) {
                    this.check = check;
                }

                public String getViolationdate() {
                    return violationdate;
                }

                public void setViolationdate(String violationdate) {
                    this.violationdate = violationdate;
                }

                public String getViolationamt() {
                    return violationamt;
                }

                public void setViolationamt(String violationamt) {
                    this.violationamt = violationamt;
                }

                public String getViolationnum() {
                    return violationnum;
                }

                public void setViolationnum(String violationnum) {
                    this.violationnum = violationnum;
                }
            }
        }
    }

    public static class SYSHEADBean implements Serializable{
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

    public static class APPHEADBean implements Serializable{
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
