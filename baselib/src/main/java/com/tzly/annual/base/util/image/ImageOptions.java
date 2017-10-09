package com.tzly.annual.base.util.image;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tzly.annual.base.R;

/**
 * 图片显示属性工具类
 */
public class ImageOptions {
    /**
     * 默认 banner图片加载
     */
    public static DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.default_330_160)
                .showImageOnFail(R.mipmap.default_330_160)
                .showImageOnLoading(R.mipmap.default_330_160)
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
                .showImageForEmptyUri(R.mipmap.ic_splash_default)
                .showImageOnFail(R.mipmap.ic_splash_default)
                .showImageOnLoading(R.mipmap.ic_splash_default)
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
     * 默认云键背景图片加载
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
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.portrait)
                .showImageOnLoading(R.mipmap.portrait)
                .showImageOnFail(R.mipmap.portrait)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .displayer(new RoundedBitmapDisplayer(200))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

}
