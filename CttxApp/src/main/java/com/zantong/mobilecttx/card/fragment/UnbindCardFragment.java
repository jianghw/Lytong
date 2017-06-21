package com.zantong.mobilecttx.card.fragment;

import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.fragment.BaseFragment;
import com.zantong.mobilecttx.card.activity.ApplyCardCheckActivity;
import com.zantong.mobilecttx.card.activity.BindJiaZhaoActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.presenter.UnbindCardPresenter;
import com.zantong.mobilecttx.utils.ReadFfile;
import com.zantong.mobilecttx.utils.jumptools.Act;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.util.FileUtils;
import cn.qqtheme.framework.util.log.LogUtils;
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

    /**
     * 下载文件txt
     */
    public void downloadTxt() {
        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        downloadFile(subscriber);
                    }
                })
                .subscribeOn(Schedulers.io())
//                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        PublicData.getInstance().mNetLocationBean
                                = ReadFfile.readNetLocationFile(getActivity().getApplicationContext());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                    }
                });
    }

    private void downloadFile(Subscriber<? super String> subscriber) {
        FileDownloadApi api = new Retrofit2Utils().getRetrofitHttps(BuildConfig.APP_URL).create(FileDownloadApi.class);
        Response<ResponseBody> response = null;
        try {
            response = api.downloadFileWithFixedUrl("download/icbcorg.txt").execute();
        } catch (IOException e) {
            subscriber.onError(e);
        }

        if (response != null && response.isSuccessful()) {
            InputStream inputStream = response.body().byteStream();
            String filePath = FileUtils.icbTxtFilePath(getActivity().getApplicationContext(), FileUtils.DOWNLOAD_DIR);
            File txtFile = new File(filePath);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(txtFile);
                int count;
                byte[] buffer = new byte[1024 * 8];
                while ((count = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.flush();
                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(e);
            } finally {
                try {
                    inputStream.close();
                    if (fileOutputStream != null) fileOutputStream.close();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        } else {
            subscriber.onError(new Exception("银行网点接口请求异常,请退出页面稍后重试"));
        }
    }

}
