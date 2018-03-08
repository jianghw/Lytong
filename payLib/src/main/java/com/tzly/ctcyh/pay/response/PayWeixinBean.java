package com.tzly.ctcyh.pay.response;

/**
 * Created by jianghw on 2017/10/26.
 * Description:
 * Update by:
 * Update day:
 */
public class PayWeixinBean {

    /**
     * appid : wx6f090722facc7bf1
     * sign : 4A4FB86B4D2A9996419A1B49CC340C02
     * partnerid : 1486769222
     * mweburl : https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx20171215165541d9cf59fe120197582749&package=3304411462
     * noncestr : H5ADLF6M2J5994Q05IAVH03G3K4C3R6I
     * timestamp : 1513328142
     */

    private String appid;
    private String sign;
    private String partnerid;
    private String mweburl;
    private String noncestr;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getMweburl() {
        return mweburl;
    }

    public void setMweburl(String mweburl) {
        this.mweburl = mweburl;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
