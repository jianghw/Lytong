package com.zantong.mobile.main_p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.tzly.annual.base.custom.banner.CBPageAdapter;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.utils.DialogUtils;

/**
 * Created by Sai on 15/8/4.
 * 本地图片Holder例子
 */
public class LocalImageHolder implements CBPageAdapter.Holder<Integer> {
    private ImageView imageView;
    private Context mAdapterContext;

    @Override
    public View createView(Context context) {
        mAdapterContext = context;
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, Integer data) {
        imageView.setImageResource(data);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extendedFunction();
            }
        });
    }

    private void extendedFunction() {
        DialogUtils.updateDialog(mAdapterContext,
                "扩展功能", "此功能为中国工商银行产品(畅通车友会App)专属,请下载体验",
                "不用 谢谢", "去应用市场下载",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareAppShop(ContextUtils.getContext().getPackageName());
                    }
                });
    }

    public void shareAppShop(String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + "com.zantong.mobilecttx");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mAdapterContext.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.toastShort("您没有安装应用市场,请点击立即更新");
        }
    }

}
