package com.tzly.ctcyh.router.custom.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tzly.ctcyh.router.R;

/**
 * 微信分享
 */

public class WeiXinDialogFragment extends DialogFragment {

    public void showDialog(WeiXinDialogFragment fragment) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("wechat_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        if (fragment != null) fragment.show(ft, "wechat_dialog");
    }

    public static WeiXinDialogFragment newInstance() {
        return new WeiXinDialogFragment();
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

            }
        });
        View friend = rootView.findViewById(R.id.tv_friend);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
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
