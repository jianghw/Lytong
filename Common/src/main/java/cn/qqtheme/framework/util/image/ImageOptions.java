package cn.qqtheme.framework.util.image;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import cn.qqtheme.framework.R;

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
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.icon_portrai)
                .showImageOnLoading(R.mipmap.icon_portrai)
                .showImageOnFail(R.mipmap.icon_portrai)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .displayer(new RoundedBitmapDisplayer(200))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    /**
     * 优惠 本地模块显示
     */
    public static DisplayImageOptions getNativeCircleOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.icon_oil_favorable)
                .showImageForEmptyUri(R.mipmap.icon_oil_favorable)
                .showImageOnFail(R.mipmap.icon_oil_favorable)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    /**
     * 优惠 分享模块
     */
    public static DisplayImageOptions getShareRectangleOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_750_300)
                .showImageForEmptyUri(R.mipmap.default_750_300)
                .showImageOnFail(R.mipmap.default_750_300)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public static DisplayImageOptions getTwoRectangleOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_330_160)
                .showImageForEmptyUri(R.mipmap.default_330_160)
                .showImageOnFail(R.mipmap.default_330_160)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public static DisplayImageOptions getThreeRectangleOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_220_160)
                .showImageForEmptyUri(R.mipmap.default_220_160)
                .showImageOnFail(R.mipmap.default_220_160)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
}
