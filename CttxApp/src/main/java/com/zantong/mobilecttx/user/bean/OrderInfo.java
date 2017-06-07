package com.zantong.mobilecttx.user.bean;

/**
 * 订单列表实体
 * @author Sandy
 * create at 16/6/12 上午11:40
 */
public class OrderInfo {
    private String hostreqserno;//交易流水号
    private String polcyprignum;//投保单号(订单号)
    private String castinspolcycode;//保单号
    private String totinsprem;//总保费
    private String totinsdamt;//总保额
    private String areaencg;//地区代码
    private String inscocode;//承保公司编码
    private String insconm;//承保公司名称
    private String dtofentrintofore;//保险合同生效日期
    /* F-女 M-男 */
    private String appsnm;//投保人姓名
    private String appsgnd;//投保人性别
    private String polrdtofbrth;//投保人出生日期
    /* 0-身份证  1-护照 2-军官证 3-士兵证 4-回乡证 5-临时身份证 6-户口本 7-其它 9-警官证 */
    private int appsctftp;//投保人证件类型
    private String appsctfvldprd;//投保人证件号码有效期
    private String appsctfnum;//投保人证件号码
    private String appsmblphoe;//投保人手机
    private String appselecmail;//投保人电子邮箱
    /* 1-Supose 配偶关系
    2-Parent 父母关系
    3-Child 子女关系
    4-GrandParent 祖父或祖母关系
    5-GrandChild 孙子或孙女关系
    6-Sibling 兄弟姐妹关系
    7-Family 其他亲属关系
    8-Self本人关系
    9-Friend 朋友关系
    28-法定关系
    99-Other 其他*/
    private String rltnpbtwinsdandapps;//投保人与被保人关系
    private String nmofinsd;//被保人姓名
    private String insdgnd;//被保人性别
    private String insddtofbrth;//被保人出生日期
    private int insdctftp;//被保人证件类型
    private String insdctfvldprd;//承保公司编码
    private String insdctfnum;//被保人证件号码
    private String insdmblphoe;//被保人电话
    private String insdelecmail;//被保人电子邮箱
    private String benfinfo;//受益人信息
    private String insinfo;//保险信息
    /* 0-初始 1-核保成功 2-支付完成 3-签单成功  */
    private int orderste;//订单状态

    public String getInsinfo() {
        return insinfo;
    }

    public void setInsinfo(String insinfo) {
        this.insinfo = insinfo;
    }

    public String getPolcyprignum() {
        return polcyprignum;
    }

    public void setPolcyprignum(String polcyprignum) {
        this.polcyprignum = polcyprignum;
    }

    public String getCastinspolcycode() {
        return castinspolcycode;
    }

    public void setCastinspolcycode(String castinspolcycode) {
        this.castinspolcycode = castinspolcycode;
    }

    public String getTotinsprem() {
        return totinsprem;
    }

    public void setTotinsprem(String totinsprem) {
        this.totinsprem = totinsprem;
    }

    public String getTotinsdamt() {
        return totinsdamt;
    }

    public void setTotinsdamt(String totinsdamt) {
        this.totinsdamt = totinsdamt;
    }

    public String getAreaencg() {
        return areaencg;
    }

    public void setAreaencg(String areaencg) {
        this.areaencg = areaencg;
    }

    public String getInscocode() {
        return inscocode;
    }

    public void setInscocode(String inscocode) {
        this.inscocode = inscocode;
    }

    public String getInsconm() {
        return insconm;
    }

    public void setInsconm(String insconm) {
        this.insconm = insconm;
    }

    public String getDtofentrintofore() {
        return dtofentrintofore;
    }

    public void setDtofentrintofore(String dtofentrintofore) {
        this.dtofentrintofore = dtofentrintofore;
    }

    public String getAppsnm() {
        return appsnm;
    }

    public void setAppsnm(String appsnm) {
        this.appsnm = appsnm;
    }

    public String getAppsgnd() {
        return appsgnd;
    }

    public void setAppsgnd(String appsgnd) {
        this.appsgnd = appsgnd;
    }

    public String getPolrdtofbrth() {
        return polrdtofbrth;
    }

    public void setPolrdtofbrth(String polrdtofbrth) {
        this.polrdtofbrth = polrdtofbrth;
    }

    public String getAppsctfvldprd() {
        return appsctfvldprd;
    }

    public void setAppsctfvldprd(String appsctfvldprd) {
        this.appsctfvldprd = appsctfvldprd;
    }

    public String getAppsctfnum() {
        return appsctfnum;
    }

    public void setAppsctfnum(String appsctfnum) {
        this.appsctfnum = appsctfnum;
    }

    public String getAppsmblphoe() {
        return appsmblphoe;
    }

    public void setAppsmblphoe(String appsmblphoe) {
        this.appsmblphoe = appsmblphoe;
    }

    public String getAppselecmail() {
        return appselecmail;
    }

    public void setAppselecmail(String appselecmail) {
        this.appselecmail = appselecmail;
    }

    public String getRltnpbtwinsdandapps() {
        return rltnpbtwinsdandapps;
    }

    public void setRltnpbtwinsdandapps(String rltnpbtwinsdandapps) {
        this.rltnpbtwinsdandapps = rltnpbtwinsdandapps;
    }

    public String getNmofinsd() {
        return nmofinsd;
    }

    public void setNmofinsd(String nmofinsd) {
        this.nmofinsd = nmofinsd;
    }

    public String getInsdgnd() {
        return insdgnd;
    }

    public void setInsdgnd(String insdgnd) {
        this.insdgnd = insdgnd;
    }

    public String getInsddtofbrth() {
        return insddtofbrth;
    }

    public void setInsddtofbrth(String insddtofbrth) {
        this.insddtofbrth = insddtofbrth;
    }

    public String getInsdctfvldprd() {
        return insdctfvldprd;
    }

    public void setInsdctfvldprd(String insdctfvldprd) {
        this.insdctfvldprd = insdctfvldprd;
    }

    public String getInsdctfnum() {
        return insdctfnum;
    }

    public void setInsdctfnum(String insdctfnum) {
        this.insdctfnum = insdctfnum;
    }

    public String getInsdmblphoe() {
        return insdmblphoe;
    }

    public void setInsdmblphoe(String insdmblphoe) {
        this.insdmblphoe = insdmblphoe;
    }

    public String getInsdelecmail() {
        return insdelecmail;
    }

    public void setInsdelecmail(String insdelecmail) {
        this.insdelecmail = insdelecmail;
    }

    public String getBenfinfo() {
        return benfinfo;
    }

    public void setBenfinfo(String benfinfo) {
        this.benfinfo = benfinfo;
    }

    public String getHostreqserno() {
        return hostreqserno;
    }

    public void setHostreqserno(String hostreqserno) {
        this.hostreqserno = hostreqserno;
    }

    public int getAppsctftp() {
        return appsctftp;
    }

    public void setAppsctftp(int appsctftp) {
        this.appsctftp = appsctftp;
    }

    public int getInsdctftp() {
        return insdctftp;
    }

    public void setInsdctftp(int insdctftp) {
        this.insdctftp = insdctftp;
    }

    public int getOrderste() {
        return orderste;
    }

    public void setOrderste(int orderste) {
        this.orderste = orderste;
    }
}
