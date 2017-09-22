package com.zantong.mobile.user.bean;

import java.io.Serializable;

/**
 * Created by zhengyingbing on 16/6/23.
 */
public class BenfInfoItem implements Serializable {

    private String BenfGnd;
    private String benfctfnum;
    private String benfctfvldprd;
    private String bnftrto;
    private String seqcode;
    private String benfdtofbrth;
    private String benfctftp;
    private String rltnpbtwinsdandapps;
    private String bensnm;

    public String getBenfGnd() {
        return BenfGnd;
    }

    public void setBenfGnd(String benfGnd) {
        BenfGnd = benfGnd;
    }

    public String getBenfctfnum() {
        return benfctfnum;
    }

    public void setBenfctfnum(String benfctfnum) {
        this.benfctfnum = benfctfnum;
    }

    public String getBenfctfvldprd() {
        return benfctfvldprd;
    }

    public void setBenfctfvldprd(String benfctfvldprd) {
        this.benfctfvldprd = benfctfvldprd;
    }

    public String getBnftrto() {
        return bnftrto;
    }

    public void setBnftrto(String bnftrto) {
        this.bnftrto = bnftrto;
    }

    public String getSeqcode() {
        return seqcode;
    }

    public void setSeqcode(String seqcode) {
        this.seqcode = seqcode;
    }

    public String getBenfdtofbrth() {
        return benfdtofbrth;
    }

    public void setBenfdtofbrth(String benfdtofbrth) {
        this.benfdtofbrth = benfdtofbrth;
    }

    public String getBenfctftp() {
        return benfctftp;
    }

    public void setBenfctftp(String benfctftp) {
        this.benfctftp = benfctftp;
    }

    public String getRltnpbtwinsdandapps() {
        return rltnpbtwinsdandapps;
    }

    public void setRltnpbtwinsdandapps(String rltnpbtwinsdandapps) {
        this.rltnpbtwinsdandapps = rltnpbtwinsdandapps;
    }

    public String getBensnm() {
        return bensnm;
    }

    public void setBensnm(String bensnm) {
        this.bensnm = bensnm;
    }
}
