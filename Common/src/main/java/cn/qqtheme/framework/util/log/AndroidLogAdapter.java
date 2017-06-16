package cn.qqtheme.framework.util.log;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import com.orhanobut.logger.LogAdapter;

/**
 * Created by jianghw on 2017/4/25.
 * <p>
 * Describe:
 */

public class AndroidLogAdapter implements LogAdapter {

    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void w(String tag, String message) {
        Log.w(tag, message);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void v(String tag, String message) {
        Log.v(tag, message);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void wtf(String tag, String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            Log.wtf(tag, message);
        }
    }
}
