package com.zantong.mobilecttx.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.utils.ImageOptions;

import cn.qqtheme.framework.widght.banner.CBPageAdapter;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<HomeAdvertisement> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, HomeAdvertisement data) {

        ImageLoader.getInstance().displayImage(
                data.getUrl(),
                imageView,
                ImageOptions.getDefaultOptions());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
                Toast.makeText(view.getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
