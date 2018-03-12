package com.tzly.ctcyh.router.custom.dialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.image.BitmapUtils;
import com.tzly.ctcyh.router.util.WechatUtils;

/**
 * 微信分享
 */

public class WeiXinDialogFragment extends DialogFragment {

    private static final String BITMAP_BYTE_ARRAY = "BITMAP_BYTE_ARRAY";
    private static final String S_URL = "S_URL";

    public static WeiXinDialogFragment newInstance(Bitmap bitmap) {
        WeiXinDialogFragment f = new WeiXinDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putByteArray(BITMAP_BYTE_ARRAY, BitmapUtils.bmpToByteArray(bitmap, true));
        f.setArguments(bundle);
        return f;
    }

    public static WeiXinDialogFragment newInstance(String url) {
        WeiXinDialogFragment f = new WeiXinDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(S_URL, url);
        f.setArguments(bundle);
        return f;
    }

    /**
     * 设置主题需要在 onCreate() 方法中调用 setStyle() 方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog_Popup);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.res_color_trans);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        View rootView = inflater.inflate(R.layout.custom_dialog_weixin, container, false);
        View close = rootView.findViewById(R.id.img_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        View wechat = rootView.findViewById(R.id.tv_wechat);
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWeChat(0);
                dismiss();
            }
        });
        View friend = rootView.findViewById(R.id.tv_friend);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWeChat(1);
                dismiss();
            }
        });
        return rootView;
    }

    public void sendWeChat(int flag) {
        String url = getArguments().getString(S_URL);
        if (!TextUtils.isEmpty(url)) {
            WechatUtils.sendReqPage(true, url, "畅通车友会", flag);
        } else {
            byte[] bytes = getArguments().getByteArray(BITMAP_BYTE_ARRAY);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            WechatUtils.sendReqBitmap(getActivity(), bitmap, flag);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

        //        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //        LayoutInflater inflater = getActivity().getLayoutInflater();
        //        View view = inflater.inflate(R.layout.custom_dialog_loading, null);
        //        builder.setView(view);
        //
        //        builder.setTitle("注意：")
        //                .setMessage("是否退出应用？")
        //                .setPositiveButton("确定", null)
        //                .setNegativeButton("取消", null)
        //                .setCancelable(false);
        //        return builder.create();
    }
}
