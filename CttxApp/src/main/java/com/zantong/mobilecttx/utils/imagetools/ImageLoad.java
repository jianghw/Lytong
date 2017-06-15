package com.zantong.mobilecttx.utils.imagetools;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.utils.ImageOptions;

/**
 * Created by 王海洋 on 2016/6/2.
 */
public class ImageLoad {

    public static void load(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl,
                imageView,
                ImageOptions.getCarOptions()
        );
    }

    public static void loadHead(String imageUrl, ImageView imageView) {
        ImageLoader.getInstance().displayImage(
                imageUrl,
                imageView,
                ImageOptions.getAvatarOptions()
        );
    }
}
