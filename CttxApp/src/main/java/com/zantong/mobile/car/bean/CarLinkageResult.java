package com.zantong.mobile.car.bean;

import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * Created by zhoujie on 2016/12/9.
 */

public class CarLinkageResult extends BaseResult {
    public CarInfo data;

    public CarInfo getData() {
        return data;
    }

    public void setData(CarInfo data) {
        this.data = data;
    }

    public class CarInfo{
        public List<CarXiBean> series;
        public List<CarStyleInfoBean> carModels;

        public List<CarXiBean> getSeries() {
            return series;
        }

        public void setSeries(List<CarXiBean> series) {
            this.series = series;
        }

        public List<CarStyleInfoBean> getCarModels() {
            return carModels;
        }

        public void setCarModels(List<CarStyleInfoBean> carModels) {
            this.carModels = carModels;
        }
    }
}
