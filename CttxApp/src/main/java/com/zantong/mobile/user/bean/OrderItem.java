package com.zantong.mobile.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 订单列表实体
 *
 * @author Sandy
 *         create at 16/6/12 上午11:40
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -7620435178023928252L;

    private String insdgnd;
    private String castinspolcycode;
    private String appsnm;
    private String insdelecmail;
    private String insdctfnum;
    private String appsgnd;
    private String insddtofbrth;
    private String appselecmail;
    private String insdctfvldprd;
    private String areaencg;
    private int orderste;
    private String polcyprignum;
    private String totinsdamt;
    private String rltnpbtwinsdandapps;
    private String inscocode;
    private String totinsprem;

    private String hostreqserno;

    private String appsctfnum;
    private String dtofentrintofore;
    private String insconm;
    private String insdmblphoe;
    private String appsctfvldprd;
    private String polrdtofbrth;
    private String nmofinsd;
    private String appsmblphoe;
    private String insdctftp;
    private String appsctftp;
    private List<InsInfoItem> InsInfo;
    private List<BenfInfoItem> BenfInfo;

    public String getInsdgnd() {
        return insdgnd;
    }

    public void setInsdgnd(String insdgnd) {
        this.insdgnd = insdgnd;
    }

    public String getCastinspolcycode() {
        return castinspolcycode;
    }

    public void setCastinspolcycode(String castinspolcycode) {
        this.castinspolcycode = castinspolcycode;
    }

    public String getAppsnm() {
        return appsnm;
    }

    public void setAppsnm(String appsnm) {
        this.appsnm = appsnm;
    }

    public String getInsdelecmail() {
        return insdelecmail;
    }

    public void setInsdelecmail(String insdelecmail) {
        this.insdelecmail = insdelecmail;
    }

    public String getInsdctfnum() {
        return insdctfnum;
    }

    public void setInsdctfnum(String insdctfnum) {
        this.insdctfnum = insdctfnum;
    }

    public String getAppsgnd() {
        return appsgnd;
    }

    public void setAppsgnd(String appsgnd) {
        this.appsgnd = appsgnd;
    }

    public String getInsddtofbrth() {
        return insddtofbrth;
    }

    public void setInsddtofbrth(String insddtofbrth) {
        this.insddtofbrth = insddtofbrth;
    }

    public String getAppselecmail() {
        return appselecmail;
    }

    public void setAppselecmail(String appselecmail) {
        this.appselecmail = appselecmail;
    }

    public String getInsdctfvldprd() {
        return insdctfvldprd;
    }

    public void setInsdctfvldprd(String insdctfvldprd) {
        this.insdctfvldprd = insdctfvldprd;
    }

    public String getAreaencg() {
        return areaencg;
    }

    public void setAreaencg(String areaencg) {
        this.areaencg = areaencg;
    }

    public int getOrderste() {
        return orderste;
    }

    public void setOrderste(int orderste) {
        this.orderste = orderste;
    }

    public String getPolcyprignum() {
        return polcyprignum;
    }

    public void setPolcyprignum(String polcyprignum) {
        this.polcyprignum = polcyprignum;
    }

    public String getTotinsdamt() {
        return totinsdamt;
    }

    public void setTotinsdamt(String totinsdamt) {
        this.totinsdamt = totinsdamt;
    }

    public String getRltnpbtwinsdandapps() {
        return rltnpbtwinsdandapps;
    }

    public void setRltnpbtwinsdandapps(String rltnpbtwinsdandapps) {
        this.rltnpbtwinsdandapps = rltnpbtwinsdandapps;
    }

    public String getInscocode() {
        return inscocode;
    }

    public void setInscocode(String inscocode) {
        this.inscocode = inscocode;
    }

    public String getTotinsprem() {
        return totinsprem;
    }

    public void setTotinsprem(String totinsprem) {
        this.totinsprem = totinsprem;
    }

    public String getHostreqserno() {
        return hostreqserno;
    }

    public void setHostreqserno(String hostreqserno) {
        this.hostreqserno = hostreqserno;
    }

    public String getAppsctfnum() {
        return appsctfnum;
    }

    public void setAppsctfnum(String appsctfnum) {
        this.appsctfnum = appsctfnum;
    }

    public String getDtofentrintofore() {
        return dtofentrintofore;
    }

    public void setDtofentrintofore(String dtofentrintofore) {
        this.dtofentrintofore = dtofentrintofore;
    }

    public String getInsconm() {
        return insconm;
    }

    public void setInsconm(String insconm) {
        this.insconm = insconm;
    }

    public String getInsdmblphoe() {
        return insdmblphoe;
    }

    public void setInsdmblphoe(String insdmblphoe) {
        this.insdmblphoe = insdmblphoe;
    }

    public String getAppsctfvldprd() {
        return appsctfvldprd;
    }

    public void setAppsctfvldprd(String appsctfvldprd) {
        this.appsctfvldprd = appsctfvldprd;
    }

    public String getPolrdtofbrth() {
        return polrdtofbrth;
    }

    public void setPolrdtofbrth(String polrdtofbrth) {
        this.polrdtofbrth = polrdtofbrth;
    }

    public String getNmofinsd() {
        return nmofinsd;
    }

    public void setNmofinsd(String nmofinsd) {
        this.nmofinsd = nmofinsd;
    }

    public String getAppsmblphoe() {
        return appsmblphoe;
    }

    public void setAppsmblphoe(String appsmblphoe) {
        this.appsmblphoe = appsmblphoe;
    }

    public String getInsdctftp() {
        return insdctftp;
    }

    public void setInsdctftp(String insdctftp) {
        this.insdctftp = insdctftp;
    }

    public String getAppsctftp() {
        return appsctftp;
    }

    public void setAppsctftp(String appsctftp) {
        this.appsctftp = appsctftp;
    }

    public List<InsInfoItem> getInsInfo() {
        return InsInfo;
    }

    public void setInsInfo(List<InsInfoItem> insInfo) {
        InsInfo = insInfo;
    }

    public List<BenfInfoItem> getBenfInfo() {
        return BenfInfo;
    }

    public void setBenfInfo(List<BenfInfoItem> benfInfo) {
        BenfInfo = benfInfo;
    }
}
