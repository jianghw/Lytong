package com.tzly.ctcyh.router.custom.primission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 安卓M 权限管理工程类
 */
public class PermissionGen {
    private String[] mPermissions;
    private int mRequestCode;
    private Object object;

    public static final int PER_REQUEST_CODE = 1000;

    private PermissionGen(Object object) {
        this.object = object;
    }

    public static PermissionGen with(Activity activity) {
        return new PermissionGen(activity);
    }

    public static PermissionGen with(Fragment fragment) {
        return new PermissionGen(fragment);
    }

    public PermissionGen permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionGen addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public void request() {
        requestPermissions(object, mRequestCode, mPermissions);
    }

    /**
     * Activity发起权限请求
     *
     * @param activity
     * @param requestCode
     * @param permission
     */
    public static void needPermission(Activity activity, int requestCode, String permission) {
        needPermission(activity, requestCode, new String[]{permission});
    }

    public static void needPermission(Activity activity, int requestCode, String[] permissions) {
//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
//
//                Toast.makeText(activity.getApplicationContext(), "相应权限已被拒绝，请设置中开启", Toast.LENGTH_SHORT).show();
//            } else {
        requestPermissions(activity, requestCode, permissions);
//            }
//        } else {
//            doExecuteSuccess(activity, requestCode);
//        }
    }

    /**
     * Fragment发起权限请求
     *
     * @param fragment
     * @param requestCode
     * @param permission
     */

    public static void needPermission(Fragment fragment, int requestCode, String permission) {
        needPermission(fragment, requestCode, new String[]{permission});
    }

    public static void needPermission(Fragment fragment, int requestCode, String[] permissions) {
        requestPermissions(fragment, requestCode, permissions);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    private static void requestPermissions(Object object, int requestCode, String[] permissions) {
        if (!PermissionUtils.isOverMarshmallow()) {
            doExecuteSuccess(object, requestCode);
            return;
        }
        List<String> deniedPermissions =
                PermissionUtils.findDeniedPermissions(PermissionUtils.getActivity(object), permissions);

        if (deniedPermissions.size() > 0) {
            if (object instanceof Activity) {
                ((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment) {
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }
        } else {
            doExecuteSuccess(object, requestCode);
        }
    }

    /**
     * 执行成功回调
     *
     * @param activity
     * @param requestCode
     */
    private static void doExecuteSuccess(Object activity, int requestCode) {
        Method executeMethod = PermissionUtils.findMethodWithRequestCode(
                activity.getClass(),
                PermissionSuccess.class,
                requestCode);

        executeMethod(activity, executeMethod);
    }

    /**
     * 反射 执行方法
     *
     * @param activity
     * @param executeMethod
     */
    private static void executeMethod(Object activity, Method executeMethod) {
        if (executeMethod != null) {
            try {
                if (!executeMethod.isAccessible()) executeMethod.setAccessible(true);
                executeMethod.invoke(activity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void doExecuteFail(Object activity, int requestCode) {
        Method executeMethod = PermissionUtils.findMethodWithRequestCode(activity.getClass(),
                PermissionFail.class, requestCode);

        executeMethod(activity, executeMethod);

        if (activity instanceof Activity) {
            Toast.makeText((Activity) activity, "相应权限已被拒绝，请设置中手动开启", Toast.LENGTH_SHORT).show();
        } else if (activity instanceof Fragment) {
            Toast.makeText(((Fragment) activity).getActivity(), "相应权限已被拒绝，请设置中手动开启", Toast.LENGTH_SHORT).show();
        }
    }

    public static void onRequestPermissionsResult(
            Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(
            Fragment fragment, int requestCode, String[] permissions, int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    private static void requestResult(Object obj, int requestCode, String[] permissions, int[] grantResults) {
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }

        if (deniedPermissions.size() > 0) {
            doExecuteFail(obj, requestCode);
        } else {
            doExecuteSuccess(obj, requestCode);
        }
    }
}
