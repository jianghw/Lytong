package com.zantong.mobilecttx.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description: 1x00--0
 * Update by:
 * Update day:
 */

public final class MainGlobal {

    public static final class requestCode {
        public static final int meg_detail_del = 1010;
        public static final int violation_query_camera = 1020;
        public static final int violation_query_bean = 1030;
        public static final int set_pay_car = 1040;

        public static final int order_detail_amend = 1070;
    }

    public static final class resultCode {
        public static final int meg_detail_del = 2010;
        public static final int order_express_back = 2020;
        public static final int ocr_camera_license = 2030;
        public static final int web_browser_back = 2040;
        public static final int violation_query_submit = 2050;
        public static final int violation_query_del = 2051;
        public static final int set_pay_car_succeed = 2060;

        public static final int amend_order_detail = 2070;
    }

    public static final class putExtra {
        public static final String home_position_extra = "home_position_extra";
        public static final String web_order_id_extra = "web_order_id_extra";
        public static final String ocr_camera_extra = "ocr_camera_extra";
        public static final String meg_title_extra = "meg_title_extra";
        public static final String meg_id_extra = "meg_id_extra";
        public static final String browser_title_extra = "browser_title_extra";
        public static final String browser_url_extra = "browser_url_extra";
        public static final String violation_num_extra = "violation_num_extra";
        public static final String car_engine_extra = "car_engine_extra";
        public static final String car_item_bean_extra = "car_item_bean_extra";
        public static final String license_bean_extra = "license_bean_extra";
        public static final String license_position_extra = "license_position_extra";
        public static final String home_rich_extra = "home_rich_extra";

        public static final String splash_type_extra = "splash_type_extra";
        public static final String splash_id_extra = "splash_id_extra";
        public static final String splash_url_extra = "splash_url_extra";

        public static final String map_type_extra = "map_type_extra";
    }

    public static final class Host {
        public static final String subject_host = "subject_host";
        public static final String fahrschule_host = "fahrschule_host";
        public static final String sparring_host = "sparring_host";
    }

    public static final class Response {
        public static final String bank_succeed = "000000";
        public static final int base_succeed = 2000;
    }

    public static final class MapType {
        //年检地图
        public static final int annual_inspection_map = -1;
        //加油地图
        public static final int annual_oil_map = -2;
        /**
         * 免检领标
         */
        public static final int annual_led_service = 2;
        /**
         * 年检站点
         */
        public static final int annual_site_service = 1;
        /**
         * 外牌代办点
         */
        public static final int annual_agent_service = 3;

        public static final int annual_0_service = 1;
        public static final int annual_92_service = 5;
        public static final int annual_95_service = 6;
    }

}
