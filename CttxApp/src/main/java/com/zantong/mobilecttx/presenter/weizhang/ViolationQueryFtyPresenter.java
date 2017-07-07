
package com.zantong.mobilecttx.presenter.weizhang;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.interf.IViolationQueryFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 违章查询 p
 * Update by:
 * Update day:
 */
public class ViolationQueryFtyPresenter implements IViolationQueryFtyContract.IViolationQueryFtyPresenter {

    private final RepositoryManager mRepository;
    private final IViolationQueryFtyContract.IViolationQueryFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public ViolationQueryFtyPresenter(@NonNull RepositoryManager repositoryManager,
                                      @NonNull IViolationQueryFtyContract.IViolationQueryFtyView view) {
        mRepository = repositoryManager;
        mAtyView = view;
        mSubscriptions = new CompositeSubscription();
        mAtyView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mAtyView.hideLoadingProgress();
        mSubscriptions.clear();
    }


    @Override
    public void uploadDrivingImg() {
        Subscription subscription = Observable.just(getImageFile())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.loadingProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Func1<File, MultipartBody.Part>() {
                    @Override
                    public MultipartBody.Part call(File file) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "ocr_img.jpg", requestBody);
                        return part;
                    }
                })
                .flatMap(new Func1<MultipartBody.Part, Observable<DrivingOcrResult>>() {
                    @Override
                    public Observable<DrivingOcrResult> call(MultipartBody.Part part) {
                        return mRepository.uploadDrivingImg(part);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<DrivingOcrResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.hideLoadingProgress();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.uploadDrivingImgError(e.getMessage());
                    }

                    @Override
                    public void doNext(DrivingOcrResult result) {
                        if ("OK".equals(result.getStatus())) {
                            mAtyView.uploadDrivingImgSucceed(result);
                        } else {
                            mAtyView.uploadDrivingImgError("行驶证图片解析失败(55)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public File getImageFile() {
        return OcrCameraActivity.file;
    }
}
