package com.tzly.ctcyh.cargo.bean.response;

import com.tzly.ctcyh.cargo.bean.PHPResponse;

/**
 * Created by jianghw on 2017/6/1.
 * Description:57获取指定类型优惠券
 * Update by:
 * Update day:
 */

public class ScoreCaptchaResponse extends PHPResponse {

    private String cookie;
    private String captche;

    public String getCookie() {
        return cookie;
    }

    public String getCaptche() {
        return captche;
    }
}
