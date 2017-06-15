package com.zantong.mobilecttx.cordova.hwidget;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zantong.mobilecttx.api.APPHttpClient;
import com.zantong.mobilecttx.interf.ModelView;
import com.zantong.mobilecttx.presenter.InsurancePayPresenterImp;
import com.zantong.mobilecttx.user.bean.CTTXInsurancePayBean;
import com.zantong.mobilecttx.utils.ImageTools;
import com.zantong.mobilecttx.utils.Tools;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.engine.PermissionHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import cn.qqtheme.framework.util.LogUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 王海洋 on 2016/5/24.
 */
public class CTTXLifeLoopImage extends CordovaPlugin implements ModelView {
    private String payUrl = "";
    private final int insurancePay = 1;
    private InsurancePayPresenterImp mInsurancePayPresenterImp;
    HashMap<String, String> mHashMap = new HashMap<>();
    private CallbackContext callbackContext;
    private CordovaArgs args;
    public static final int TAKE_PIC_SEC = 0;
    public static final int PERMISSION_DENIED_ERROR = 20;
    protected final static String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.VIBRATE};
    // 关键代码
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Response response;
    OkHttpClient client = APPHttpClient.getInstance().getUnsafeOkHttpClient();


    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if ("queryLifeLoop".equals(action)) {
//            String merCustomIp = NetUtils.getPhontIP(cordova.getActivity());
            this.args = args;
            this.callbackContext = callbackContext;
            boolean locationPermission = PermissionHelper.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (!locationPermission) {
                locationPermission = true;
                try {
                    PackageManager packageManager = this.cordova.getActivity().getPackageManager();
                    String[] permissionsInPackage = packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
                    if (permissionsInPackage != null) {
                        for (String permission : permissionsInPackage) {
                            if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                locationPermission = false;
                                break;
                            }
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // We are requesting the info for our package, so this should
                    // never be caught
                }
            }


            if (locationPermission) {
//                startLocation();
                try {
                    imageLoad();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                PermissionHelper.requestPermissions(this, TAKE_PIC_SEC, permissions);
            }
            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    private void imageLoad() throws Exception {

        String url = args.getString(0);
        String jsonStr = null;
        try {
            jsonStr = post(url, args.getString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Tools.isStrEmpty(jsonStr)) {
            JSONObject json = new JSONObject(jsonStr);
            final JSONArray urlList = (JSONArray) json.get("RotationCfg");
            final JSONArray jsons = new JSONArray();

            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    try {
                        for (int i = 0; i < urlList.length(); i++) {
                            final String imageUrl = (String) ((JSONObject) urlList.get(i)).get("imgurl");
                            ImageLoader.getInstance().loadImage(imageUrl, new ImageLoadingListener() {
                                @Override
                                public void onLoadingStarted(String s, View view) {

                                }

                                @Override
                                public void onLoadingFailed(String s, View view, FailReason failReason) {

                                }

                                @Override
                                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                    Bitmap myBitmap;
                                    myBitmap = bitmap;
                                    String[] imageName = imageUrl.split("\\/");
                                    ImageTools.saveBitmap(myBitmap, cordova.getActivity(), imageName[imageName.length - 1]);
                                    String urlImage = Environment
                                            .getExternalStorageDirectory() + "/CTTXCACHE/" + imageName[imageName.length - 1];
    //                    PublicData.getInstance().bitmap = bitmapBase64;

                                    File file = new File(urlImage);
                                    Uri uri = Uri.fromFile(file);
                                    LogUtils.e("why", uri.toString());
    //                    String demo = "file://"+file.getAbsolutePath();
                                    jsons.put(uri);
                                    if (jsons.length() == 3) {
                                        CTTXLifeLoopImage.this.echo(jsons, callbackContext);
                                    }
                                }

                                @Override
                                public void onLoadingCancelled(String s, View view) {

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                    private void echo(JSONArray jsons, CallbackContext callbackContext) {
//                    }
            });
        } else {
            this.echoerror("", callbackContext);
        }


    }

    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, PERMISSION_DENIED_ERROR));
                return;
            }
        }
        switch (requestCode) {
            case TAKE_PIC_SEC:
                try {
                    imageLoad();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void echo(JSONArray message, CallbackContext callbackContext) {

        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }

    }

    private void echoerror(String message, CallbackContext callbackContext) {

        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("ERROR");
        }

    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void updateView(Object object, int index) {
        CTTXInsurancePayBean mCTTXInsurancePayBean = (CTTXInsurancePayBean) object;
//        switchJump(mCTTXInsurancePayBean.getRspInfo().getPymtste());
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
