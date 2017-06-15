package com.zantong.mobilecttx.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zantong.mobilecttx.R;

/**
 * 图片显示属性工具类
 *
 * @author Sandy
 *         create at 16/9/18 下午5:58
 */
public class ImageOptions {
    /**
     * 默认图片加载
     *
     * @return
     */
    public static DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.ic_default_loading)
                .showImageOnFail(R.mipmap.ic_default_loading)
                .showImageOnLoading(R.mipmap.ic_default_loading)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;
    }

    /**
     * 引导页默认
     */
    public static DisplayImageOptions getSplashOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.mipmap.ic_splash_default)
                .showImageForEmptyUri(R.mipmap.ic_splash_default)
                .showImageOnFail(R.mipmap.ic_splash_default)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;
    }

    /**
     * 默认图片加载
     */
    public static DisplayImageOptions getMessageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.app_icon)
                .showImageOnFail(R.mipmap.app_icon)
                .showImageOnLoading(R.mipmap.app_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        return options;
    }

    /**
     * 默认车辆图片
     */
    public static DisplayImageOptions getCarOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.open_no_car_image)
                .showImageOnFail(R.mipmap.open_no_car_image)
                .showImageOnLoading(R.mipmap.open_no_car_image)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }

    /**
     * 默认云键背景图片加载
     *
     * @return
     */
    public static DisplayImageOptions getKeyDefaultOptions(int defiamge) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(defiamge)
                .showImageOnFail(defiamge)
                .showImageOnLoading(defiamge)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }

    /**
     * 获取头像
     */
    public static DisplayImageOptions getAvatarOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .showImageForEmptyUri(R.mipmap.icon_portrai)
                .showImageOnLoading(R.mipmap.icon_portrai)
                .showImageOnFail(R.mipmap.icon_portrai)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(200))
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return options;
    }
}
