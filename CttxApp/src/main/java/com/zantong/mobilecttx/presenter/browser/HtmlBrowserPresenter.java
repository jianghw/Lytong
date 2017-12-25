
package com.zantong.mobilecttx.presenter.browser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zantong.mobilecttx.base.bean.PayWeixinResponse;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.contract.browser.IHtmlBrowserContract;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.weizhang.bean.PayOrderResponse;

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
 * Description:
 * Update by:
 * Update day:
 */
public class HtmlBrowserPresenter
        implements IHtmlBrowserContract.IHtmlBrowserPresenter {

    private final RepositoryManager mRepository;
    private final IHtmlBrowserContract.IHtmlBrowserView mContractView;
    private final CompositeSubscription mSubscriptions;

    public HtmlBrowserPresenter(@NonNull RepositoryManager repositoryManager,
                                @NonNull IHtmlBrowserContract.IHtmlBrowserView view) {
        mRepository = repositoryManager;
        mContractView = view;
        mSubscriptions = new CompositeSubscription();
        mContractView.setPresenter(this);
    }

    @Override
    public void onSubscribe() {
        //TODO 缓存操作 暂时先就这样
    }

    @Override
    public void unSubscribe() {
        mContractView.dismissLoading();
        mSubscriptions.clear();
    }

    @Override
    public void uploadDrivingImg() {
        Subscription subscription = Observable.just(getImageFile())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Func1<File, MultipartBody.Part>() {
                    @Override
                    public MultipartBody.Part call(File file) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        return MultipartBody.Part.createFormData("file", "ocr_img.jpg", requestBody);
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
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.uploadDrivingImgError(e.getMessage());
                    }

                    @Override
                    public void doNext(DrivingOcrResult result) {
                        if ("OK".equals(result.getStatus())) {
                            mContractView.uploadDrivingImgSucceed(result);
                        } else {
                            mContractView.uploadDrivingImgError("行驶证图片解析失败(55)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public File getImageFile() {
        return OcrCameraActivity.file;
    }

    /**
     * N 5.获取工行支付页面
     */
    @Override
    public void getBankPayHtml(String coupon, final String orderId, String orderPrice) {
        Subscription subscription = mRepository
                .getBankPayHtml(orderId, orderPrice, TextUtils.isEmpty(coupon) ? 0 : Integer.valueOf(coupon))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.getBankPayHtmlError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayOrderResponse result) {
                        if (result != null && result.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.getBankPayHtmlSucceed(result, orderId);
                        } else {
                            mContractView.getBankPayHtmlError(result != null
                                    ? result.getResponseDesc() : "未知错误(N5)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void weChatPay(String couponUserId, final String orderId, String amount) {
        Subscription subscription = mRepository
                .weChatPay(orderId, amount, TextUtils.isEmpty(couponUserId) ? 0 : Integer.valueOf(couponUserId))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mContractView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayWeixinResponse>() {
                    @Override
                    public void doCompleted() {
                        mContractView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mContractView.dismissLoading();
                        mContractView.weChatPayError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayWeixinResponse response) {
                        if (response != null && response.getResponseCode()
                                == MainGlobal.Response.base_succeed) {
                            mContractView.weChatPaySucceed(response,orderId);
                        } else {
                            mContractView.weChatPayError(response != null
                                    ? response.getResponseDesc() : "未知错误(weChatPay)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

}
