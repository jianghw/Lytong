package cn.qqtheme.framework.util.primission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具类封装
 */
final public class PermissionUtils {

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 判断有哪些权限是被禁止的
     *
     * @param activity
     * @param permission
     * @return
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    public static List<Method> findAnnotationMethods(Class clazz, Class<? extends Annotation> clazz1) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(clazz1)) {
                methods.add(method);
            }
        }
        return methods;
    }

    public static <A extends Annotation> Method findMethodPermissionFailWithRequestCode(Class clazz,
                                                                                        Class<A> permissionFailClass, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(permissionFailClass)) {
                if (requestCode == method.getAnnotation(PermissionFail.class).requestCode()) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 根据请求code找办法
     *
     * @param clazz
     * @param annotation
     * @param requestCode
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Method findMethodWithRequestCode(
            Class clazz, Class<A> annotation, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                if (isEqualRequestCodeFromAnnotation(method, annotation, requestCode)) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 判断根据请求code 执行响应方法
     *
     * @param m
     * @param clazz
     * @param requestCode
     * @return
     */
    public static boolean isEqualRequestCodeFromAnnotation(Method m, Class clazz, int requestCode) {
        if (clazz.equals(PermissionFail.class)) {
            return requestCode == m.getAnnotation(PermissionFail.class).requestCode();
        } else if (clazz.equals(PermissionSuccess.class)) {
            return requestCode == m.getAnnotation(PermissionSuccess.class).requestCode();
        } else {
            return false;
        }
    }

    public static <A extends Annotation> Method findMethodPermissionSuccessWithRequestCode(
            Class clazz, Class<A> permissionFailClass, int requestCode) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(permissionFailClass)) {
                if (requestCode == method.getAnnotation(PermissionSuccess.class).requestCode()) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 返回对象类型
     *
     * @param object
     * @return
     */
    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }
}
