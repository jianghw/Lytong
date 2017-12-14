package com.tzly.ctcyh.user.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description: 1x00--1
 * Update by:
 * Update day:
 */

public final class UserGlobal {

    public static final class requestCode {
    }

    public static final class resultCode {
    }

    public static final class putExtra {
        public static final String user_login_phone = "user_login_phone";
        public static final String user_login_pw = "user_login_pw";
    }

    public static final class Response {
        public static final String bank_succeed = "000000";
    }

    public static final class Host {
        //登录页面用
        public static final String login_code_host = "login_code_host";
        public static final String code_pw_host = "code_pw_host";
        public static final String code_register_host = "code_register_host";
        public static final String send_register_host = "send_register_host";
    }
}
