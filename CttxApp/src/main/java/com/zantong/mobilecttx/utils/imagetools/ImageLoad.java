package com.zantong.mobilecttx.utils.imagetools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zantong.mobilecttx.R;

/**
 * Created by 王海洋 on 2016/6/2.
 */
public class ImageLoad {

    public static void load(Context context, String imageUrl, ImageView imageView){

//        Glide.with(context).load(imageUrl).asBitmap().dontTransform().into(imageView);

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.open_no_car_image)
                .error(R.mipmap.open_no_car_image)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
//                .onLoadFailed(null,context.getResources().getDrawable(R.mipmap.open_no_car_image));
    }
    public static void loadHead(Context context, String imageUrl, ImageView imageView){

//        Glide.with(context).load(imageUrl).asBitmap().dontTransform().into(imageView);

        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.mine_head_default)
                .error(R.mipmap.mine_head_default)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
//                .onLoadFailed(null,context.getResources().getDrawable(R.mipmap.open_no_car_image));
    }
}
