
package com.zantong.mobilecttx.presenter.weizhang;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.dto.CarInfoDTO;
import com.zantong.mobilecttx.card.dto.BindCarDTO;
import com.zantong.mobilecttx.common.activity.OcrCameraActivity;
import com.zantong.mobilecttx.contract.IViolationQueryFtyContract;
import com.zantong.mobilecttx.daijia.bean.DrivingOcrResult;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

import java.io.File;

import cn.qqtheme.framework.bean.BankResponse;
import cn.qqtheme.framework.bean.BaseResponse;
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
public class ViolationQueryFtyPresenter
        implements IViolationQueryFtyContract.IViolationQueryFtyPresenter {

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

    /**
     * 添加车俩
     */
    @Override
    public void commitCarInfoToOldServer() {
        Subscription subscription = mRepository.commitCarInfoToOldServer(initCarInfoDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BankResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.commitCarInfoToOldServerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BankResponse responseBean) {
                        if (responseBean != null
                                && responseBean.getSYS_HEAD().getReturnCode().equals("000000")) {
                            mAtyView.commitCarInfoToOldServerSucceed(responseBean);
                        } else {
                            mAtyView.commitCarInfoToOldServerError(responseBean != null
                                    ? responseBean.getSYS_HEAD().getReturnMessage() : "未知错误(cip.cfc.u005.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void commitCarInfoToNewServer() {
        Subscription subscription = mRepository.commitCarInfoToNewServer(initBindCarDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.commitCarInfoToNewServerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse responseBean) {
                        if (responseBean != null && responseBean.getResponseCode() == 2000) {

                        } else {
                            mAtyView.commitCarInfoToNewServerError(responseBean != null
                                    ? responseBean.getResponseDesc() : "未知错误(48)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 16.新增车辆
     */
    @Override
    public void addVehicleLicense() {
        Subscription subscription = mRepository.addVehicleLicense(initBindCarDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.commitCarInfoToNewServerError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse responseBean) {
                        if (responseBean != null && responseBean.getResponseCode() == 2000) {

                        } else {
                            mAtyView.commitCarInfoToNewServerError(responseBean != null
                                    ? responseBean.getResponseDesc() : "未知错误(48)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 18.删除车辆
     */
    @Override
    public void removeVehicleLicense() {
        Subscription subscription = mRepository.removeVehicleLicense(initBindCarDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.loadingProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.hideLoadingProgress();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.removeVehicleLicenseError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse responseBean) {
                        if (responseBean != null && responseBean.getResponseCode() == 2000) {
                            mAtyView.removeVehicleLicenseSucceed(responseBean);
                        } else {
                            mAtyView.removeVehicleLicenseError(responseBean != null
                                    ? responseBean.getResponseDesc() : "未知错误(N18)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 17.编辑车辆
     */
    @Override
    public void updateVehicleLicense() {
        Subscription subscription = mRepository.updateVehicleLicense(initBindCarDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.loadingProgress();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.hideLoadingProgress();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.removeVehicleLicenseError(e.getMessage());
                    }

                    @Override
                    public void doNext(BaseResponse responseBean) {
                        if (responseBean != null && responseBean.getResponseCode() == 2000) {
                            mAtyView.removeVehicleLicenseSucceed(responseBean);
                        } else {
                            mAtyView.removeVehicleLicenseError(responseBean != null
                                    ? responseBean.getResponseDesc() : "未知错误(N17)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public BindCarDTO initBindCarDTO() {
        BindCarDTO bindCarDTO = mAtyView.getBindCarDTO();

        BindCarDTO carDTO = new BindCarDTO();
        carDTO.setPlateNo(RSAUtils.strByEncryption(bindCarDTO.getPlateNo(), true));
        carDTO.setEngineNo(RSAUtils.strByEncryption(bindCarDTO.getEngineNo(), true));
        carDTO.setVehicleType(bindCarDTO.getVehicleType());
        carDTO.setUsrnum(RSAUtils.strByEncryption(LoginData.getInstance().userID, true));
        carDTO.setIssueDate(bindCarDTO.getIssueDate());
        carDTO.setIsPay(bindCarDTO.getIsPay());

        carDTO.setBrandId(bindCarDTO.getBrandId());
        carDTO.setSeriesId(bindCarDTO.getSeriesId());
        carDTO.setCarModelId(bindCarDTO.getCarModelId());
        return carDTO;
    }

    /**
     * 构建请求bean
     */
    @Override
    public String initCarInfoDTO() {
        RequestDTO dto = new RequestDTO();

        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.u005.01");
        dto.setSYS_HEAD(requestHeadDTO);

        CarInfoDTO bean = mAtyView.getCarInfoDTO();

        CarInfoDTO rsaBean = new CarInfoDTO(bean.getCarnumtype(), bean.getInspectdate(),
                bean.getCarmodel(), bean.getUsrid(), bean.getIspaycar(), bean.getDefaultflag(),
                bean.getInspectflag(), bean.getViolationflag(), bean.getCarimage(),
                bean.getOpertype(), bean.getActivityCar());

        rsaBean.setCarnum(RSAUtils.strByEncryption(bean.getCarnum(), true));
        rsaBean.setEnginenum(RSAUtils.strByEncryption(bean.getEnginenum(), true));

        dto.setReqInfo(rsaBean);
        return new Gson().toJson(dto);
    }
}
