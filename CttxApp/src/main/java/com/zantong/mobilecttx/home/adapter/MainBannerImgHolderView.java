package com.zantong.mobilecttx.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.fahrschule_v.FahrschuleActivity;
import com.zantong.mobilecttx.home.activity.CustomCordovaActivity;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResponse;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import com.tzly.ctcyh.router.bean.BaseResponse;
import com.tzly.ctcyh.router.custom.banner.CBPageAdapter;
import com.tzly.ctcyh.router.util.MobUtils;
import com.tzly.ctcyh.router.custom.image.ImageOptions;

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
        CarApiClient.commitAdClick(Utils.getContext(), data.getId(), "1",
                new CallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse result) {
                    }
                });

        String url = data.getAdvertisementSkipUrl();
        LoginData.getInstance().mHashMap.put("htmlUrl", url);
        if (url.contains("discount")
                || url.contains("happysend")) {//保险
            Act.getInstance().gotoIntent(mAdapterContext, CustomCordovaActivity.class, url);
        } else if (url.contains("localActivity")) {//百日无违章
            if (MainRouter.isUserLogin()) {
                MobUtils.getInstance().eventIdByUMeng(1);
                getSignStatus();
            } else {
                MainRouter.gotoLoginActivity(mAdapterContext);
            }
        } else if (url.contains("fahrschule")) {//驾校报名
            Act.getInstance().gotoIntentLogin(mAdapterContext, FahrschuleActivity.class);
        } else {
            MainRouter.gotoHtmlActivity(mAdapterContext, "优惠", url);
        }
    }

    private void getSignStatus() {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(MainRouter.getUserID(false));
        CarApiClient.getActivityCar(Utils.getContext(), activityCarDTO, new CallBack<ActivityCarResponse>() {
            @Override
            public void onSuccess(ActivityCarResponse result) {
                String url = null;
                if (result.getResponseCode() == 2000 && !TextUtils.isEmpty(result.getData().getPlateNo())) {
                    SPUtils.getInstance().setSignStatus(true);
                    SPUtils.getInstance().setSignCarPlate(result.getData().getPlateNo());
                    url = Config.HUNDRED_PLAN_HOME;
                } else if (result.getResponseCode() == 4000) {
                    SPUtils.getInstance().setSignStatus(false);
                    url = Config.HUNDRED_PLAN_DEADLINE;
                }

                MainRouter.gotoHtmlActivity(mAdapterContext, "百日无违章", url);
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }

}
