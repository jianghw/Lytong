package com.zantong.mobilecttx.card.bean;

import com.tzly.ctcyh.java.response.BankResponse;

/**
 * Created by zhengyingbing on 16/9/13.
 */
public class BindCardResult extends BankResponse {

    private BindCard RspInfo;

    public void setRspInfo(BindCard rspInfo) {
        RspInfo = rspInfo;
    }

    public BindCard getRspInfo() {
        return RspInfo;
    }

    public class BindCard {
        int cardflag; // 0 没有 1畅通卡不一致 2畅通卡一致
        int mobileflag;//0 预留手机号不正确  1正确
        int custcodeflag;// 0 证件号不一致 1一致

        public int getCardflag() {
            return cardflag;
        }

        public void setCardflag(int cardflag) {
            this.cardflag = cardflag;
        }

        public int getMobileflag() {
            return mobileflag;
        }

        public void setMobileflag(int mobileflag) {
            this.mobileflag = mobileflag;
        }

        public int getCustcodeflag() {
            return custcodeflag;
        }

        public void setCustcodeflag(int custcodeflag) {
            this.custcodeflag = custcodeflag;
        }
    }


}
