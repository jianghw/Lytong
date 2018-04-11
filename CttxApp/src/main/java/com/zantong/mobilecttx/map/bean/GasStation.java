package com.zantong.mobilecttx.map.bean;

/**
 * Created by zhoujie on 2016/9/19.
 */
public class GasStation {
    private int id;	                //加油站ID
    private String name;	        //加油站名称
    private double lng;	            //经度
    private double lat;	            //纬度
    private String zeroNum;	        //0号(折扣价格)
    private String minusTenNum;	    //-10号柴油
    private String eightyNineNum;	//89（90）号价格
    private String ninetyTwoNum;	//92（93）号价格
    private String ninetyFiveNum;	//95（97）号价格
    private String ninetyEightNum;	//98号价格
    private String preferentialPeriod; //优惠字段
    private String ninetyTwoStandard;  //92 优惠字段
    private String ninetyFiveStandard;  //95 优惠字段
    private String distance;            //距离

    private boolean hasNinetyFour;

    public boolean isHasNinetyFour() {
        return hasNinetyFour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getZeroNum() {
        return zeroNum;
    }

    public void setZeroNum(String zeroNum) {
        this.zeroNum = zeroNum;
    }

    public String getMinusTenNum() {
        return minusTenNum;
    }

    public void setMinusTenNum(String minusTenNum) {
        this.minusTenNum = minusTenNum;
    }

    public String getEightyNineNum() {
        return eightyNineNum;
    }

    public void setEightyNineNum(String eightyNineNum) {
        this.eightyNineNum = eightyNineNum;
    }

    public String getNinetyTwoNum() {
        return ninetyTwoNum;
    }

    public void setNinetyTwoNum(String ninetyTwoNum) {
        this.ninetyTwoNum = ninetyTwoNum;
    }

    public String getNinetyFiveNum() {
        return ninetyFiveNum;
    }

    public void setNinetyFiveNum(String ninetyFiveNum) {
        this.ninetyFiveNum = ninetyFiveNum;
    }

    public String getNinetyEightNum() {
        return ninetyEightNum;
    }

    public void setNinetyEightNum(String ninetyEightNum) {
        this.ninetyEightNum = ninetyEightNum;
    }

    public String getPreferentialPeriod() {
        return preferentialPeriod;
    }

    public void setPreferentialPeriod(String preferentialPeriod) {
        this.preferentialPeriod = preferentialPeriod;
    }

    public String getNinetyTwoStandard() {
        return ninetyTwoStandard;
    }

    public void setNinetyTwoStandard(String ninetyTwoStandard) {
        this.ninetyTwoStandard = ninetyTwoStandard;
    }

    public String getNinetyFiveStandard() {
        return ninetyFiveStandard;
    }

    public void setNinetyFiveStandard(String ninetyFiveStandard) {
        this.ninetyFiveStandard = ninetyFiveStandard;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
