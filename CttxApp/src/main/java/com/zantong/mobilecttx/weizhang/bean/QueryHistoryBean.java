package com.zantong.mobilecttx.weizhang.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者：王海洋
 * 时间：2016/6/13 15:26
 */
public class QueryHistoryBean implements Serializable{
    private LinkedList<QueryCarBean> queryCar;
    public LinkedList<QueryCarBean> getQueryCar() {
        return queryCar;
    }

    public void setQueryCar(LinkedList<QueryCarBean> queryCar) {
        this.queryCar = queryCar;
    }


    public static class QueryCarBean implements Serializable{
        private String carNumber;
        private String engineNumber;
        private String queryTime;
        private String carnumtype;

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getEngineNumber() {
            return engineNumber;
        }

        public void setEngineNumber(String engineNumber) {
            this.engineNumber = engineNumber;
        }

        public String getQueryTime() {
            return queryTime;
        }

        public void setQueryTime(String queryTime) {
            this.queryTime = queryTime;
        }
        public String getCarnumtype() {
            return carnumtype;
        }

        public void setCarnumtype(String carnumtype) {
            this.carnumtype = carnumtype;
        }


    }
}
