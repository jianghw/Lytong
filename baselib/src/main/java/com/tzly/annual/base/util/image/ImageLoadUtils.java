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
}
