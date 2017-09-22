package com.zantong.mobile.home.bean;

import java.util.List;

/**
 * Created by jianghw on 2017/5/5.
 */
public class RspInfoBean {

    /**
     * totcent : 2
     * totcount : 99999
     * totamt : 75000
     * ViolationInfo : [{"violationcent":"2","processste":"1","paydate":"20170220","carnum":"XOBDdy2mbXcpO84qlNQbsg==","violationdate":"20170215","violationamt":"10000","violationnum":"3101011830480748"},{"violationcent":"0","processste":"1","paydate":"20170116","carnum":"foM4u2dTInhrAd4JPZNTYQ==","violationdate":"20161111","violationamt":"20000","violationnum":"3101017020895105"},{"violationcent":"0","processste":"1","paydate":"20161230","carnum":"huMFVtMmTJDyUZzyfy6fqw==","violationdate":"20161111","violationamt":"5000","violationnum":"3101061818052367"},{"violationcent":"0","processste":"1","paydate":"20170428","carnum":"UZDjW5GA0biHO6Y5yZvvwg==","violationdate":"20160803","violationamt":"20000","violationnum":"3101107016227413"},{"violationcent":"0","processste":"1","paydate":"20161221","carnum":"4F+/AbDLLGJJ3q+Cwr1hLg==","violationdate":"20161126","violationamt":"20000","violationnum":"3101130806985382"}]
     * totcarcount : 5
     */

    private int totcent;
    private String totcount;
    private int totamt;
    private String totcarcount;
    private List<ViolationInfoBean> ViolationInfo;

    public int getTotcent() {
        return totcent;
    }

    public void setTotcent(int totcent) {
        this.totcent = totcent;
    }

    public String getTotcount() {
        return totcount;
    }

    public void setTotcount(String totcount) {
        this.totcount = totcount;
    }

    public int getTotamt() {
        return totamt;
    }

    public void setTotamt(int totamt) {
        this.totamt = totamt;
    }

    public String getTotcarcount() {
        return totcarcount;
    }

    public void setTotcarcount(String totcarcount) {
        this.totcarcount = totcarcount;
    }

    public List<ViolationInfoBean> getViolationInfo() {
        return ViolationInfo;
    }

    public void setViolationInfo(List<ViolationInfoBean> ViolationInfo) {
        this.ViolationInfo = ViolationInfo;
    }

    public static class ViolationInfoBean {
        /**
         * violationcent : 2
         * processste : 1
         * paydate : 20170220
         * carnum : XOBDdy2mbXcpO84qlNQbsg==
         * violationdate : 20170215
         * violationamt : 10000
         * violationnum : 3101011830480748
         */

        private String violationcent;
        private String processste;
        private String paydate;
        private String carnum;
        private String violationdate;
        private String violationamt;
        private String violationnum;

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

        public String getCarnum() {
            return carnum;
        }

        public void setCarnum(String carnum) {
            this.carnum = carnum;
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
