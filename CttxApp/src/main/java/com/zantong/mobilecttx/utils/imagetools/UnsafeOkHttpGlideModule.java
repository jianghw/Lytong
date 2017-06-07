package com.zantong.mobilecttx.utils.imagetools;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * 作者：王海洋
 * 时间：2016/7/12 15:44
 */
public class UnsafeOkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        File cacheDir;
//        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"CTTXCACHE");
//        else
//            cacheDir=context.getCacheDir();
//        if(!cacheDir.exists())
//            cacheDir.mkdirs();
//        String downloadDirectoryPath = Environment
//                .getExternalStorageDirectory()+"/CTTXCACHE";
//        int cacheSize100MegaBytes = 104857600;
//        builder.setDiskCache(
//                new DiskLruCacheFactory( downloadDirectoryPath, cacheSize100MegaBytes)
//        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
