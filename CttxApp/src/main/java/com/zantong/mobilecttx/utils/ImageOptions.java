package com.zantong.mobilecttx.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zantong.mobilecttx.R;

/**
 * 图片显示属性工具类
 * @author Sandy
 * create at 16/9/18 下午5:58
 */
public class ImageOptions {
	/**
	 * 默认图片加载
	 * @return
	 */
	public static DisplayImageOptions getDefaultOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true)
				.showImageForEmptyUri(R.mipmap.ic_default_loading)
				.showImageOnFail(R.mipmap.ic_default_loading)
				.showImageOnLoading(R.mipmap.ic_default_loading)
				.cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}
	/**
	 * 默认图片加载
	 * @return
	 */
	public static DisplayImageOptions getAdOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true)
				.cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}
	/**
	 * 默认云键背景图片加载
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
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}

	/**
	 * 获取头像
	 * @return
	 */
	public static DisplayImageOptions getAvatarOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheInMemory(true)
				.showImageForEmptyUri(R.mipmap.icon_portrai)
				.showImageOnFail(R.mipmap.icon_portrai)
				.showImageOnLoading(R.mipmap.icon_portrai)
				.cacheOnDisk(false).displayer(new RoundedBitmapDisplayer(200))
				.cacheInMemory(false)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		return options;
	}
}
