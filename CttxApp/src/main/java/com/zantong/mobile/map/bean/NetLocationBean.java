package com.zantong.mobile.map.bean;

import java.util.List;

/**
 * 作者：王海洋
 * 时间：2016/7/1 18:12
 */
public class NetLocationBean {
    private List<NetLocationElement> netLocationlist;

    public List<NetLocationElement> getNetLocationlist() {
        return netLocationlist;
    }

    public void setNetLocationlist(List<NetLocationElement> netLocationlist) {
        this.netLocationlist = netLocationlist;
    }

    public static class NetLocationElement{

        private String netLocationQu;

        public String getNetLocationQu() {
            return netLocationQu;
        }

        public void setNetLocationQu(String netLocationQu) {
            this.netLocationQu = netLocationQu;
        }

        private List<NetQuBean> listNet;

        public List<NetQuBean> getListNet() {
            return listNet;
        }

        public void setListNet(List<NetQuBean> listNet) {
            this.listNet = listNet;
        }

        public static class NetQuBean{
            private String netLocationName;
            private String netLocationCode;
            private String netLocationXiang;
            private String netLocationNumber;

            public String getNetLocationNumber() {
                return netLocationNumber;
            }

            public void setNetLocationNumber(String netLocationNumber) {
                this.netLocationNumber = netLocationNumber;
            }

            public String getNetLocationName() {
                return netLocationName;
            }

            public void setNetLocationName(String netLocationName) {
                this.netLocationName = netLocationName;
            }

            public String getNetLocationCode() {
                return netLocationCode;
            }

            public void setNetLocationCode(String netLocationCode) {
                this.netLocationCode = netLocationCode;
            }
            public String getNetLocationXiang() {
                return netLocationXiang;
            }

            public void setNetLocationXiang(String netLocationXiang) {
                this.netLocationXiang = netLocationXiang;
            }
        }

    }
}
