package com.zantong.mobilecttx.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.bean.BaseResult;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.BrowserActivity;
import com.zantong.mobilecttx.fahrschule.activity.FahrschuleActivity;
import com.zantong.mobilecttx.home.activity.CustomCordovaActivity;
import com.zantong.mobilecttx.home.bean.HomeAdvertisement;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;

import cn.qqtheme.framework.global.GlobalConfig;
import cn.qqtheme.framework.widght.banner.CBPageAdapter;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class MainBannerImgHolderView implements CBPageAdapter.Holder<HomeAdvertisement> {

    private ImageView imageView;
    private Context mContext;

    @Override
    public View createView(Context context) {
        mContext = context;
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
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
        PublicData.getInstance().webviewUrl = data.getAdvertisementSkipUrl();
        PublicData.getInstance().mHashMap.put("htmlUrl", PublicData.getInstance().webviewUrl);
        PublicData.getInstance().webviewTitle = "优惠";
        PublicData.getInstance().isCheckLogin = false;

        if (PublicData.getInstance().webviewUrl.contains("discount")
                || PublicData.getInstance().webviewUrl.contains("happysend")) {//保险
            Act.getInstance().gotoIntent(mContext, CustomCordovaActivity.class);
        } else if (PublicData.getInstance().webviewUrl.contains("localActivity")) {//百日无违章
            if (PublicData.getInstance().loginFlag) {
                GlobalConfig.getInstance().eventIdByUMeng(1);
                getSignStatus();
            } else {
                Act.getInstance().gotoIntent(mContext, LoginActivity.class);
            }
        } else if (PublicData.getInstance().webviewUrl.contains("fahrschule")) {//驾校报名
            Act.getInstance().gotoIntentLogin(mContext, FahrschuleActivity.class);
        } else {
            Act.getInstance().gotoIntent(mContext, BrowserActivity.class);

            CarApiClient.commitAdClick(mContext, data.getId(), new CallBack<BaseResult>() {
                @Override
                public void onSuccess(BaseResult result) {
                }
            });
        }
    }

    private void getSignStatus() {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(mContext, activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {
                if (result.getResponseCode() == 2000 && !TextUtils.isEmpty(result.getData().getPlateNo())) {
                    SPUtils.getInstance().setSignStatus(true);
                    SPUtils.getInstance().setSignCarPlate(result.getData().getPlateNo());
                    PublicData.getInstance().webviewUrl = Config.HUNDRED_PLAN_HOME;
                    PublicData.getInstance().webviewTitle = "百日无违章";
                    Act.getInstance().gotoIntentLogin(mContext, BrowserActivity.class);
                } else if (result.getResponseCode() == 4000) {
                    SPUtils.getInstance().setSignStatus(false);
                    PublicData.getInstance().webviewUrl = Config.HUNDRED_PLAN_DEADLINE;
                    PublicData.getInstance().webviewTitle = "百日无违章";
                    Act.getInstance().gotoIntentLogin(mContext, BrowserActivity.class);
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
            }
        });
    }

}
