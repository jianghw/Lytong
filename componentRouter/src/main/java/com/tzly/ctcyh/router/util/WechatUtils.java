package com.tzly.ctcyh.router.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.image.BitmapUtils;

import static com.tzly.ctcyh.router.util.ToastUtils.toastShort;

/**
 * 微信工具类
 */

public final class WechatUtils {
    public static final String APP_ID = "wxc3d27f07b30b3b66";// 微信开放平台申请到的app_id
    private static final int THUMB_SIZE = 150;

    private WechatUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 发送图片
     *
     * @param activity
     * @param bitmap
     * @param flag     0--个人  1--朋友圈
     */
    public static void sendReqBitmap(Activity activity, Bitmap bitmap, int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        api.registerApp(APP_ID);

        if (!api.isWXAppInstalled()) {
            toastShort("您还未安装微信客户端");
            return;
        }
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = BitmapUtils.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    private static String buildTransaction(String type) {
        return (TextUtils.isEmpty(type)) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static void sendReqText(boolean isLogin, String text, int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(Utils.getContext(), APP_ID, true);
        api.registerApp(APP_ID);

        if (!api.isWXAppInstalled()) {
            toastShort("您还未安装微信客户端");
            return;
        }

        WXTextObject textObject = new WXTextObject();
        WXMediaMessage msg = new WXMediaMessage();

        msg.mediaObject = textObject;
        msg.description = text;
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(Utils.getContext().getResources(), R.mipmap.ic_global_app);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    /**
     * 网页分享
     *
     * @param isLogin
     * @param url
     * @param title
     * @param flag
     */
    public static void sendReqPage(boolean isLogin, String url, String title, int flag) {
        IWXAPI api = WXAPIFactory.createWXAPI(Utils.getContext(), APP_ID, true);
        api.registerApp(APP_ID);

        if (!api.isWXAppInstalled()) {
            toastShort("您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if (isLogin) {
            webpage.webpageUrl = url;
        } else {
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = "畅通车友会,优惠推荐";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(Utils.getContext().getResources(), R.mipmap.ic_global_app);
        msg.thumbData = BitmapUtils.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
