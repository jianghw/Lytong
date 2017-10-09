package com.zantong.mobile.utils;

import android.app.Activity;

import com.zantong.mobile.main_v.MainClubActivity;

import java.util.Stack;

public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public static ScreenManager getScreenManager() {
        if (null == instance) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public static void popActivity() {
        if (!activityStack.isEmpty()) {
            Activity activity = activityStack.lastElement();
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        if (activityStack.size() == 0) {
            return null;
        }
        return activityStack.lastElement();
    }

    // 将当前Activity推入栈中
    public static void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    // 退出栈中所有Activity
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            if (activityStack.size() == 0) {
                break;
            }
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    public void specialMethod() {
        while (true) {
            if (activityStack.size() == 0) {
                break;
            }
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if ( activity.getClass().equals(MainClubActivity.class)) {
                break;
            }
            popActivity(activity);
        }
    }

    // 退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }
}
