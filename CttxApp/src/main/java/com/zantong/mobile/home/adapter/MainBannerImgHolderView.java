package com.zantong.mobile.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tzly.annual.base.bean.BaseResult;
import com.tzly.annual.base.custom.banner.CBPageAdapter;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.CarApiClient;
import com.zantong.mobile.browser.AdvBrowserActivity;
import com.zantong.mobile.common.Config;
import com.zantong.mobile.common.PublicData;
import com.zantong.mobile.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobile.home.bean.HomeAdvertisement;
import com.zantong.mobile.huodong.bean.ActivityCarResult;
import com.zantong.mobile.huodong.dto.ActivityCarDTO;
import com.zantong.mobile.utils.SPUtils;
import com.zantong.mobile.utils.jumptools.Act;

import com.tzly.annual.base.global.JxGlobal;
import com.tzly.annual.base.util.ContextUtils;
import com.tzly.annual.base.util.image.ImageOptions;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class MainBannerImgHolderView implements CBPageAdapter.Holder<HomeAdvertisement> {

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
        PublicData.getInstance().mHashMap.put("htmlUrl", url);

        if (url.contains("fahrschule")) {//驾校报名
            Act.getInstance().gotoIntentLogin(mAdapterContext, FahrschuleActivity.class);
        } else {
            Intent intent = new Intent();
            intent.putExtra(JxGlobal.putExtra.browser_title_extra, "优惠");
            intent.putExtra(JxGlobal.putExtra.browser_url_extra, url);
            Act.getInstance().gotoLoginByIntent(mAdapterContext, AdvBrowserActivity.class, intent);

            CarApiClient.commitAdClick(ContextUtils.getContext(), data.getId(), new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {
                }
            });
        }
    }

    private void getSignStatus() {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(ContextUtils.getContext(), activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {
                Intent intent = new Intent();

                if (result.getResponseCode() == 2000 && !TextUtils.isEmpty(result.getData().getPlateNo())) {
                    SPUtils.getInstance().setSignStatus(true);
                    SPUtils.getInstance().setSignCarPlate(result.getData().getPlateNo());
                    intent.putExtra(JxGlobal.putExtra.browser_url_extra, Config.HUNDRED_PLAN_HOME);
                } else if (result.getResponseCode() == 4000) {
                    SPUtils.getInstance().setSignStatus(false);
                    intent.putExtra(JxGlobal.putExtra.browser_url_extra, Config.HUNDRED_PLAN_DEADLINE);
                }
                intent.putExtra(JxGlobal.putExtra.browser_title_extra, "百日无违章");
                Act.getInstance().gotoLoginByIntent(mAdapterContext, AdvBrowserActivity.class, intent);
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }

}
