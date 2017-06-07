package com.zantong.mobilecttx.home.bean;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class HomeWeather {
    private String weatherDesc;	            //天气详情
    private String temperature;	            //温度
    private int carWashingAssessment;	//洗车指数

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getCarWashingAssessment() {
        return carWashingAssessment;
    }

    public void setCarWashingAssessment(int carWashingAssessment) {
        this.carWashingAssessment = carWashingAssessment;
    }
}
