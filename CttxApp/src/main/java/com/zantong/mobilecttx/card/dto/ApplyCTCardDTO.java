package com.zantong.mobilecttx.card.dto;

import java.io.Serializable;

/**
 * Created by zhoujie on 2017/1/3.
 */

public class ApplyCTCardDTO implements Serializable{
    private String usrid;//	 用户ID
    private String filenum;//	 档案号
    private String usrname;//	用户姓名
    private String issuoffic;//	发证机关
    private String ctftp;//	证件类型
    private String ctfnum;//	证件号码
    private String ctfvldprd;//	证件有效期
    private String enghnm;//	英文名
    private String gnd;//	性别 1男 2女
    private String dtofbrth;//	出生日期
    private String brthcty;//	出生城市
    private String marlst;//	婚姻状况
    private String edunlvl;//	教育程度
    private String resltp;//	住宅类型
    private String hmadrprov;//	住宅地址省份
    private String hmadrcity;//	住宅地址城市
    private String hmadrcnty;//	住宅地址县(区)
    private String hmadr;//	住宅地址
    private String hmadrzip;//	住宅邮编
    private String indate;//	入住日期
    private String hmareacode;//	家庭电话区号
    private String hmphoe;//	家庭电话
    private String hmphoeexn;//	家庭电话分机号
    private String phoenum;//	手机号
    private String conm;//	单位名称
    private String corpsecr;//	所在部门
    private String idycgy;//	行业类别
    private String ocp;//	职业
    private String cottl;//	职务
    private String coareacode;//	单位电话区号
    private String cophoe;//	单位电话
    private String cophoeexn;//	单位电话分机号
    private String coadrprov;//	单位地址省份
    private String coadrcity;//	单位地址城市
    private String coadrcnty;//	单位地址县(区)
    private String coadr;//	单位地址
    private String coadrzip;//	单位邮编
    private String corptp;//	单位性质
    private String joindate;//	入职日期
    private String anulincm;//	年收入
    private String ctcnm1;//	联系人1姓名
    private String ctcgnd1;//	联系人1性别
    private String ctc1;//	联系人1关系
    private String ctcconm1;//	联系人1单位名称
    private String ctccoareacode1;//	联系人1单位电话区号
    private String ctccophoe1;//	联系人1单位电话
    private String ctccophoeexn1;//	联系人1单位电话分机号
    private String ctcphoenum1;//	联系人1手机号
    private String ctchmadr1;//	联系人1住宅地址
    private String ctchmadrzip1;//	联系人1住宅邮编
    private String ctcnm2;//	联系人2姓名
    private String ctcgnd2;//	联系人2性别
    private String ctc2;//	联系人2关系
    private String ctcconm2;//	联系人2单位名称
    private String ctccoareacode2;//	联系人2单位电话区号
    private String ctccophoe2;//	联系人2单位电话
    private String ctccophoeexn2;//	联系人2单位电话分机号
    private String ctcphoenum2;//	联系人2手机号
    private String ctchmadr2;//	联系人2住宅地址
    private String ctchmadrzip2;//	联系人2住宅邮编
    private String autcrepymtmth;//	自动还款类型
    private String turnoutacnum;//	转出账号
    private String elecbillsign;//	电子对账单标识
    private String elecmail;//	电子邮箱
    private String actnotf;//	账户变动提醒
    private String getbrno;//	领卡网点
    private String dscode;
    private String dscodegs;

    public String getEdunlvl() {
        return edunlvl;
    }

    public void setEdunlvl(String edunlvl) {
        this.edunlvl = edunlvl;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getFilenum() {
        return filenum;
    }

    public void setFilenum(String filenum) {
        this.filenum = filenum;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getIssuoffic() {
        return issuoffic;
    }

    public void setIssuoffic(String issuoffic) {
        this.issuoffic = issuoffic;
    }

    public String getCtftp() {
        return ctftp;
    }

    public void setCtftp(String ctftp) {
        this.ctftp = ctftp;
    }

    public String getCtfnum() {
        return ctfnum;
    }

    public void setCtfnum(String ctfnum) {
        this.ctfnum = ctfnum;
    }

    public String getCtfvldprd() {
        return ctfvldprd;
    }

    public void setCtfvldprd(String ctfvldprd) {
        this.ctfvldprd = ctfvldprd;
    }

    public String getEnghnm() {
        return enghnm;
    }

    public void setEnghnm(String enghnm) {
        this.enghnm = enghnm;
    }

    public String getGnd() {
        return gnd;
    }

    public void setGnd(String gnd) {
        this.gnd = gnd;
    }

    public String getDtofbrth() {
        return dtofbrth;
    }

    public void setDtofbrth(String dtofbrth) {
        this.dtofbrth = dtofbrth;
    }

    public String getBrthcty() {
        return brthcty;
    }

    public void setBrthcty(String brthcty) {
        this.brthcty = brthcty;
    }

    public String getMarlst() {
        return marlst;
    }

    public void setMarlst(String marlst) {
        this.marlst = marlst;
    }

    public String getResltp() {
        return resltp;
    }

    public void setResltp(String resltp) {
        this.resltp = resltp;
    }

    public String getHmadrprov() {
        return hmadrprov;
    }

    public void setHmadrprov(String hmadrprov) {
        this.hmadrprov = hmadrprov;
    }

    public String getHmadrcity() {
        return hmadrcity;
    }

    public void setHmadrcity(String hmadrcity) {
        this.hmadrcity = hmadrcity;
    }

    public String getHmadrcnty() {
        return hmadrcnty;
    }

    public void setHmadrcnty(String hmadrcnty) {
        this.hmadrcnty = hmadrcnty;
    }

    public String getHmadr() {
        return hmadr;
    }

    public void setHmadr(String hmadr) {
        this.hmadr = hmadr;
    }

    public String getHmadrzip() {
        return hmadrzip;
    }

    public void setHmadrzip(String hmadrzip) {
        this.hmadrzip = hmadrzip;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getHmareacode() {
        return hmareacode;
    }

    public void setHmareacode(String hmareacode) {
        this.hmareacode = hmareacode;
    }

    public String getHmphoe() {
        return hmphoe;
    }

    public void setHmphoe(String hmphoe) {
        this.hmphoe = hmphoe;
    }

    public String getHmphoeexn() {
        return hmphoeexn;
    }

    public void setHmphoeexn(String hmphoeexn) {
        this.hmphoeexn = hmphoeexn;
    }

    public String getPhoenum() {
        return phoenum;
    }

    public void setPhoenum(String phoenum) {
        this.phoenum = phoenum;
    }

    public String getConm() {
        return conm;
    }

    public void setConm(String conm) {
        this.conm = conm;
    }

    public String getCorpsecr() {
        return corpsecr;
    }

    public void setCorpsecr(String corpsecr) {
        this.corpsecr = corpsecr;
    }

    public String getIdycgy() {
        return idycgy;
    }

    public void setIdycgy(String idycgy) {
        this.idycgy = idycgy;
    }

    public String getOcp() {
        return ocp;
    }

    public void setOcp(String ocp) {
        this.ocp = ocp;
    }

    public String getCottl() {
        return cottl;
    }

    public void setCottl(String cottl) {
        this.cottl = cottl;
    }

    public String getCoareacode() {
        return coareacode;
    }

    public void setCoareacode(String coareacode) {
        this.coareacode = coareacode;
    }

    public String getCophoe() {
        return cophoe;
    }

    public void setCophoe(String cophoe) {
        this.cophoe = cophoe;
    }

    public String getCophoeexn() {
        return cophoeexn;
    }

    public void setCophoeexn(String cophoeexn) {
        this.cophoeexn = cophoeexn;
    }

    public String getCoadrprov() {
        return coadrprov;
    }

    public void setCoadrprov(String coadrprov) {
        this.coadrprov = coadrprov;
    }

    public String getCoadrcity() {
        return coadrcity;
    }

    public void setCoadrcity(String coadrcity) {
        this.coadrcity = coadrcity;
    }

    public String getCoadrcnty() {
        return coadrcnty;
    }

    public void setCoadrcnty(String coadrcnty) {
        this.coadrcnty = coadrcnty;
    }

    public String getCoadr() {
        return coadr;
    }

    public void setCoadr(String coadr) {
        this.coadr = coadr;
    }

    public String getCoadrzip() {
        return coadrzip;
    }

    public void setCoadrzip(String coadrzip) {
        this.coadrzip = coadrzip;
    }

    public String getCorptp() {
        return corptp;
    }

    public void setCorptp(String corptp) {
        this.corptp = corptp;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public String getAnulincm() {
        return anulincm;
    }

    public void setAnulincm(String anulincm) {
        this.anulincm = anulincm;
    }

    public String getCtcnm1() {
        return ctcnm1;
    }

    public void setCtcnm1(String ctcnm1) {
        this.ctcnm1 = ctcnm1;
    }

    public String getCtcgnd1() {
        return ctcgnd1;
    }

    public void setCtcgnd1(String ctcgnd1) {
        this.ctcgnd1 = ctcgnd1;
    }

    public String getCtc1() {
        return ctc1;
    }

    public void setCtc1(String ctc1) {
        this.ctc1 = ctc1;
    }

    public String getCtcconm1() {
        return ctcconm1;
    }

    public void setCtcconm1(String ctcconm1) {
        this.ctcconm1 = ctcconm1;
    }

    public String getCtccoareacode1() {
        return ctccoareacode1;
    }

    public void setCtccoareacode1(String ctccoareacode1) {
        this.ctccoareacode1 = ctccoareacode1;
    }

    public String getCtccophoe1() {
        return ctccophoe1;
    }

    public void setCtccophoe1(String ctccophoe1) {
        this.ctccophoe1 = ctccophoe1;
    }

    public String getCtccophoeexn1() {
        return ctccophoeexn1;
    }

    public void setCtccophoeexn1(String ctccophoeexn1) {
        this.ctccophoeexn1 = ctccophoeexn1;
    }

    public String getCtcphoenum1() {
        return ctcphoenum1;
    }

    public void setCtcphoenum1(String ctcphoenum1) {
        this.ctcphoenum1 = ctcphoenum1;
    }

    public String getCtchmadr1() {
        return ctchmadr1;
    }

    public void setCtchmadr1(String ctchmadr1) {
        this.ctchmadr1 = ctchmadr1;
    }

    public String getCtchmadrzip1() {
        return ctchmadrzip1;
    }

    public void setCtchmadrzip1(String ctchmadrzip1) {
        this.ctchmadrzip1 = ctchmadrzip1;
    }

    public String getCtcnm2() {
        return ctcnm2;
    }

    public void setCtcnm2(String ctcnm2) {
        this.ctcnm2 = ctcnm2;
    }

    public String getCtcgnd2() {
        return ctcgnd2;
    }

    public void setCtcgnd2(String ctcgnd2) {
        this.ctcgnd2 = ctcgnd2;
    }

    public String getCtc2() {
        return ctc2;
    }

    public void setCtc2(String ctc2) {
        this.ctc2 = ctc2;
    }

    public String getCtcconm2() {
        return ctcconm2;
    }

    public void setCtcconm2(String ctcconm2) {
        this.ctcconm2 = ctcconm2;
    }

    public String getCtccoareacode2() {
        return ctccoareacode2;
    }

    public void setCtccoareacode2(String ctccoareacode2) {
        this.ctccoareacode2 = ctccoareacode2;
    }

    public String getCtccophoe2() {
        return ctccophoe2;
    }

    public void setCtccophoe2(String ctccophoe2) {
        this.ctccophoe2 = ctccophoe2;
    }

    public String getCtccophoeexn2() {
        return ctccophoeexn2;
    }

    public void setCtccophoeexn2(String ctccophoeexn2) {
        this.ctccophoeexn2 = ctccophoeexn2;
    }

    public String getCtcphoenum2() {
        return ctcphoenum2;
    }

    public void setCtcphoenum2(String ctcphoenum2) {
        this.ctcphoenum2 = ctcphoenum2;
    }

    public String getCtchmadr2() {
        return ctchmadr2;
    }

    public void setCtchmadr2(String ctchmadr2) {
        this.ctchmadr2 = ctchmadr2;
    }

    public String getCtchmadrzip2() {
        return ctchmadrzip2;
    }

    public void setCtchmadrzip2(String ctchmadrzip2) {
        this.ctchmadrzip2 = ctchmadrzip2;
    }

    public String getAutcrepymtmth() {
        return autcrepymtmth;
    }

    public void setAutcrepymtmth(String autcrepymtmth) {
        this.autcrepymtmth = autcrepymtmth;
    }

    public String getTurnoutacnum() {
        return turnoutacnum;
    }

    public void setTurnoutacnum(String turnoutacnum) {
        this.turnoutacnum = turnoutacnum;
    }

    public String getElecbillsign() {
        return elecbillsign;
    }

    public void setElecbillsign(String elecbillsign) {
        this.elecbillsign = elecbillsign;
    }

    public String getElecmail() {
        return elecmail;
    }

    public void setElecmail(String elecmail) {
        this.elecmail = elecmail;
    }

    public String getActnotf() {
        return actnotf;
    }

    public void setActnotf(String actnotf) {
        this.actnotf = actnotf;
    }

    public String getGetbrno() {
        return getbrno;
    }

    public void setGetbrno(String getbrno) {
        this.getbrno = getbrno;
    }

    public String getDscode() {
        return dscode;
    }

    public void setDscode(String dscode) {
        this.dscode = dscode;
    }

    public String getDscodegs() {
        return dscodegs;
    }

    public void setDscodegs(String dscodegs) {
        this.dscodegs = dscodegs;
    }
}
