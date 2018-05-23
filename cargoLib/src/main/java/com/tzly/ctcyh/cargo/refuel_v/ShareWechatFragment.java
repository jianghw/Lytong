package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IShareWechatContract;
import com.tzly.ctcyh.cargo.refuel_p.ShareWechatPresenter;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.tzly.ctcyh.router.util.WechatUtils;

/**
 * Fragment 微信分享
 */
public class ShareWechatFragment extends Fragment
        implements IShareWechatContract.IShareWechatView {

    private static final String IMAGE_URL = "image_url";
    private static final String CODE_URL = "code_url";
    private static final String WE_TYPE = "we_type";
    /**
     * 控制器
     */
    private IShareWechatContract.IShareWechatPresenter mPresenter;

    public static ShareWechatFragment newInstance(String imgUrl, String codeUrl, int type) {
        ShareWechatFragment f = new ShareWechatFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL, imgUrl);
        bundle.putString(CODE_URL, codeUrl);
        bundle.putInt(WE_TYPE, type);
        f.setArguments(bundle);
        return f;
    }

    /**
     * 当该fragment被添加到Activity时回调，该方法只会被调用一次
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * 创建fragment时被回调。该方法只会调用一次。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ShareWechatPresenter presenter = new ShareWechatPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);

    }

    /**
     * 每次创建、绘制该fragment的View组件时回调该方法，fragment将会显示该方法的View组件
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 当fragment所在的Activity被创建完成后调用该方法。
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 销毁该fragment所包含的View组件时调用。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    /**
     * 销毁fragment时被回调，该方法只会被调用一次。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当该fragment从Activity中被删除、被替换完成时回调该方法，
     * onDestory()方法后一定会回调onDetach()方法。该方法只会被调用一次。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Context getContext() {
        if (super.getContext() != null && super.getContext().getApplicationContext() != null) {
            return super.getContext().getApplicationContext();
        }
        return super.getContext();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void setPresenter(IShareWechatContract.IShareWechatPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 下载分享的图片
     */
    public void toShareBitmap() {
        String imgUrl = getArguments().getString(IMAGE_URL);
        if (TextUtils.isEmpty(imgUrl)) {
            ToastUtils.toastShort("分享图片地址失效");
            return;
        }

        ImageLoader.getInstance().loadImage(imgUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                ToastUtils.toastShort("加载图片失败,请重新试一试");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                bitmapFactory(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    private void bitmapFactory(Bitmap bitmap) {
        String codeUrl = getArguments().getString(CODE_URL);
        Bitmap logio = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_global_app);
        if (mPresenter != null) mPresenter.mergeBitmap(bitmap, codeUrl, logio);
    }

    @Override
    public void mergeSucceed(Bitmap bitmap) {
        int flag = getArguments().getInt(WE_TYPE);

//        byte[] image = BitmapUtils.bmpToByteArray(bitmap, true);
//        Bitmap bit = BitmapFactory.decodeByteArray(image, 0, image.length);
        WechatUtils.sendReqBitmap(getActivity(), bitmap, flag);
    }
}