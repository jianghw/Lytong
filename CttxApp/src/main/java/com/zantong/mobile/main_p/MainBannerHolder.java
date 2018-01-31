package com.zantong.mobile.main_p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.custom.banner.CBPageAdapter;
import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.ToastUtils;
import com.tzly.annual.base.util.image.ImageOptions;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.CarApiClient;
import com.zantong.mobile.browser.BrowserHtmlActivity;
import com.zantong.mobile.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobile.home.bean.HomeAdvertisement;
import com.zantong.mobile.utils.DialogUtils;
import com.zantong.mobile.utils.jumptools.Act;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class MainBannerHolder implements CBPageAdapter.Holder<HomeAdvertisement> {

    private ImageView imageView;
    private Context mAdapterContext;

    @Override
    public View createView(Context context) {
        mAdapterContext = context;
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(mAdapterContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, final int position, final HomeAdvertisement data) {

        ImageLoader.getInstance().displayImage(
                data.getUrl(),
                imageView,
                ImageOptions.getDefaultOptions());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webProcessingService(data);
            }
        });
    }

    protected void webProcessingService(HomeAdvertisement data) {
        String url = data.getAdvertisementSkipUrl();

        if (url.contains("localActivity")) {//百日无违章
            extendedFunction();
        } else if (url.contains("fahrschule")) {//驾校报名
            Act.getInstance().gotoIntentLogin(mAdapterContext, FahrschuleActivity.class);
        } else {
            //url.contains("discount")|| url.contains("happysend")
            Intent intent = new Intent();
            intent.putExtra(JxGlobal.putExtra.browser_title_extra, "优惠");
            intent.putExtra(JxGlobal.putExtra.browser_url_extra, url);
            Act.getInstance().gotoLoginByIntent(mAdapterContext, BrowserHtmlActivity.class, intent);

            CarApiClient.commitAdClick(ContextUtils.getContext(), data.getId(), new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {
                }
            });
        }
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
