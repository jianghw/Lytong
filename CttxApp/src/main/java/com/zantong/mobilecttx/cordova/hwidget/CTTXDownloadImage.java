package com.zantong.mobilecttx.cordova.hwidget;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zantong.mobilecttx.contract.ModelView;
import com.zantong.mobilecttx.presenter.InsurancePayPresenterImp;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;
import cn.qqtheme.framework.util.image.ImageTools;
import com.zantong.mobilecttx.utils.NetUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CTTXDownloadImage extends CordovaPlugin implements ModelView {
    private String payUrl = "";
    private final int insurancePay = 1;
    private InsurancePayPresenterImp mInsurancePayPresenterImp;
    HashMap<String, String> mHashMap = new HashMap<>();
    private CallbackContext callbackContext;
    Bitmap myBitmap;

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if ("downloadImageWithUrl".equals(action)) {
            String merCustomIp = NetUtils.getPhontIP(cordova.getActivity());
            this.callbackContext = callbackContext;
            payUrl = args.getString(0);
            payUrl += "&merCustomIp=" + merCustomIp;

            ImageLoader.getInstance().loadImage(args.getString(0), new ImageSize(500, 500), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    myBitmap = bitmap;
                    if (myBitmap != null) {
                        String bitmapBase64 = ImageTools.bitmapToBase64(myBitmap);
                        echo(bitmapBase64, callbackContext);
                    } else {
                        echo("", callbackContext);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
            return true;
        }
        return super.execute(action, args, callbackContext);
    }


    private void echo(String message, CallbackContext callbackContext) {


        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("ERROR");
        }

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void updateView(Object object, int index) {
        CTTXInsurancePayBean mCTTXInsurancePayBean = (CTTXInsurancePayBean) object;
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Boolean shouldAllowRequest(String url) {
        return true;
    }

    @Override
    public Boolean shouldAllowBridgeAccess(String url) {
        return true;
    }
}
