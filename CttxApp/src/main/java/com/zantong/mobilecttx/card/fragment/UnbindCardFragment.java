package com.zantong.mobilecttx.card.fragment;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseFragment;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.map.bean.NetLocationBean;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.presenter.UnbindCardPresenter;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.card.activity.ApplyCardCheckActivity;
import com.zantong.mobilecttx.card.activity.BindJiaZhaoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UnbindCardFragment extends BaseFragment {
    UnbindCardPresenter mUnbindCardPresenter;
    @Bind(R.id.apply_changtong_card)
    TextView apply_changtong_card;
    InputStream is;
    FileOutputStream fos;

    public UnbindCardFragment(){
        mUnbindCardPresenter = new UnbindCardPresenter(this);
    }
//    public static UnbindCardFragment getSingleInstance(){
//        if (mSingleFragment == null){
//            synchronized (UnbindCardFragment.class){
//                if (mSingleFragment == null){
//                    mSingleFragment = new UnbindCardFragment();
//                }
//            }
//        }
//        return mSingleFragment;
//    }


    @Override
    protected int getLayoutResId() {
        return R.layout.unbind_card;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        downloadTxt();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login_btn, R.id.apply_changtong_card})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(13));
                Act.getInstance().lauchIntent(getContext(), BindJiaZhaoActivity.class);
                break;
            case R.id.apply_changtong_card:
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(12));
//                downloadTxt();
                //PublicData.getInstance().mHashMap.put("htmlUrl", "file:///android_asset/www/card_apply.html");
                //Act.getInstance().lauchIntent(getContext(), Demo.class);
                Act.getInstance().lauchIntent(getActivity(), ApplyCardCheckActivity.class);
                this.getActivity().finish();
                break;
        }
    }

//    public void downloadDemo() {
//        Retrofit2Utils retrofit2Utils=new Retrofit2Utils();
//        final FileDownloadApi api = retrofit2Utils.getRetrofitHttps(BuildConfig.APP_URL).create(FileDownloadApi.class);
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                try {
//                    Response<ResponseBody> response=api.downloadFileWithFixedUrl("download/icbcorg.txt").execute();
//                    try {
//                        if (response != null && response.isSuccessful()) {
//                            //文件总长度
//                            long fileSize = response.body().contentLength();
//                            long fileSizeDownloaded = 0;
//                            is = response.body().byteStream();
//                            File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator +"networktable.txt");
//                            if (file.exists()) {
//                                file.delete();
//                            } else {
//                                file.createNewFile();
//                            }
//                            fos = new FileOutputStream(file);
//                            int count = 0;
//                            byte[] buffer = new byte[1024];
//                            while ((count = is.read(buffer)) != -1) {
//                                fos.write(buffer, 0, count);
//                                fileSizeDownloaded += count;
//                                subscriber.onNext("file download: " + fileSizeDownloaded + " of " + fileSize);
//                            }
//                            fos.flush();
//                            subscriber.onCompleted();
//                        } else {
//                            subscriber.onError(new Exception("接口请求异常"));
//                        }
//                    } catch (Exception e) {
//                        subscriber.onError(e);
//                    } finally {
//                        if (is != null) {
//                            try {
//                                is.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        if (fos != null) {
//                            try {
//                                fos.close();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                    Log.e("why", e.toString());
//                    e.printStackTrace();
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .sample(1, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d("MainActivity", "文件下载完成");
//                        NetLocationBean bean = ReadFfile.readNetLocationFile();
//                        PublicData.getInstance().mNetLocationBean = bean;
////                        Log.e("why", bean.getNetLocationlist().get(0).toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.d("MainActivity", s);
//
//                    }
//                });
//    }

    public void downloadTxt() {
        Retrofit2Utils retrofit2Utils = new Retrofit2Utils();
        final FileDownloadApi api = retrofit2Utils.getRetrofitHttps(BuildConfig.APP_URL).create(FileDownloadApi.class);
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Response<ResponseBody> response = api.downloadFileWithFixedUrl("download/icbcorg.txt").execute();
                    try {
                        if (response != null && response.isSuccessful()) {
                            //文件总长度
                            long fileSize = response.body().contentLength();
                            long fileSizeDownloaded = 0;
                            is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "networktable.txt");
                            if (file.exists()) {
                                file.delete();
                            } else {
                                file.createNewFile();
                            }
                            fos = new FileOutputStream(file);
                            int count = 0;
                            byte[] buffer = new byte[1024];
                            while ((count = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, count);
                                fileSizeDownloaded += count;
                                subscriber.onNext("file download: " + fileSizeDownloaded + " of " + fileSize);
                            }
                            fos.flush();
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new Exception("接口请求异常"));
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.e("why", e.toString());
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i("文件下载完成");
                        NetLocationBean bean = ReadFfile.readNetLocationFile();
                        PublicData.getInstance().mNetLocationBean = bean;
//                        Log.e("why", bean.getNetLocationlist().get(0).toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("MainActivity", s);

                    }
                });
    }

}
