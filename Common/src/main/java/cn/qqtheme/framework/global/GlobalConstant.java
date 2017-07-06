package cn.qqtheme.framework.global;

/**
 * Created by jianghw on 2017/7/5.
 * Description:
 * Update by:
 * Update day:
 */

public final class GlobalConstant {

    public static final class requestCode {
        public static final int violation_query_plate = 1000;
        public static final int add_car_aty = 1001;
        public static final int violation_query_camera = 1002;
    }

    public static final class resultCode {
        public static final int common_list_fty = 2000;
        public static final int ocr_camera_license = 2001;
    }

    public static final class putExtra {
        public static final String common_list_extra = "common_list_extra";
        public static final String ocr_camera_extra = "ocr_resource_extra";
    }

    public static final class cameraType {
        public static final int common_list_fty = 0;
    }
}
