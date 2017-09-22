package com.tzly.annual.base.util.image;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 加载图片
 */
public class ImageLoadUtils {

    public static void loadHead(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl,
                imageView,
                ImageOptions.getAvatarOptions()
        );
    }

    public static void loadNativeCircle(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl, imageView,
                ImageOptions.getNativeCircleOptions()
        );
    }

    public static void loadShareRectangle(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl, imageView,
                ImageOptions.getShareRectangleOptions()
        );
    }

    public static void loadTwoRectangle(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl, imageView,
                ImageOptions.getTwoRectangleOptions()
        );
    }

    public static void loadThreeRectangle(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl, imageView,
                ImageOptions.getThreeRectangleOptions()
        );
    }
}
