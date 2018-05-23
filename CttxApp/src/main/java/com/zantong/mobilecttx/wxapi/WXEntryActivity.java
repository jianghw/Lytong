package com.zantong.mobilecttx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzly.ctcyh.router.util.LogUtils;
import com.zantong.mobilecttx.R;


/**
 * @author xiong_it
 * @description 微信第三方登录，分享demo,更多移动开发内容请关注： http://blog.csdn.net/xiong_it
 * @charset UTF-8
 * @date 2015-9-9下午2:50:14
 */
/*
 * 微信登录，分享应用中必须有这个名字叫WXEntryActivity，并且必须在wxapi包名下，腾讯官方文档中有要求
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";

    public static final String APP_ID = "wxc3d27f07b30b3b66";// 微信开放平台申请到的app_id
    public static final String APP_SECRET = "0eb8d63342bfa52daef600366dce70a9";// 微信开放平台申请到的app_id对应的app_secret
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, APP_ID, false);

        try {
            api.handleIntent(getIntent(), this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    /**
     * 请求回调接口
     */
    @Override
    public void onReq(BaseReq req) {
        Log.i(TAG, "onReq");
    }

    /**
     * 请求响应回调接口
     */
    @Override
    public void onResp(BaseResp resp) {
        int result;
        LogUtils.e(resp.errCode);
        LogUtils.e(resp.errStr);

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK://0
                result = R.string.errcode_success;

                //广播通讯
                Intent i = new Intent();
                i.setAction("com.tzly.ctcyh.pay.pay_type_v.PaySucceedActivity");
                sendBroadcast(i);
                break;
            case BaseResp.ErrCode.ERR_COMM://-1
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://-2
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED://-3
                result = R.string.sent_failed;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://-4
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT://-5
            case BaseResp.ErrCode.ERR_BAN://-6
            default:
                result = R.string.errcode_unknown;
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }

}
