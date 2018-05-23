package com.tzly.ctcyh.router.custom.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tzly.ctcyh.router.R;
import com.tzly.ctcyh.router.custom.image.EncodingUtils;
import com.tzly.ctcyh.router.util.RudenessScreenHelper;


/**
 * Created by jianghw on 2017/12/6.
 * Description: 二维码显示器
 * Update by:
 * Update day:
 */

public class BitmapDialogFragment extends DialogFragment {

    private static final String STATUS_CODE = "STATUS_CODE";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 设置主题需要在 onCreate() 方法中调用 setStyle() 方法
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_dialog_bitmap, container, true);

        ImageView imageView = (ImageView) view.findViewById(R.id.tv_msg);
        String codeUrl = getArguments().getString(STATUS_CODE);
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(
                codeUrl, RudenessScreenHelper.ptInpx(520), RudenessScreenHelper.ptInpx(520),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_global_app));
        imageView.setImageBitmap(qrCodeBitmap);

        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
        return view;
    }

    public static BitmapDialogFragment newInstance(String couponName) {
        BitmapDialogFragment fragment = new BitmapDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STATUS_CODE, couponName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

}
