
package com.zantong.mobilecttx.presenter.weizhang;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.contract.IViolationListFtyContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.List;

import cn.qqtheme.framework.bean.BaseResponse;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 违章查询列表模块
 * Update by:
 * Update day:
 */
public class ViolationListPresenter
        implements IViolationListFtyContract.IViolationListFtyPresenter {

    private final RepositoryManager mRepository;
    private final IViolationListFtyContract.IViolationListFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public ViolationListPresenter(@NonNull RepositoryManager repositoryManager,
                                  @NonNull IViolationListFtyContract.IViolationListFtyView view) {
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
        mAtyView.dismissLoadingDialog();
        mSubscriptions.clear();
    }

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    @Override
    public void searchViolation() {
        Subscription subscription = mRepository.searchViolation(initViolationDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ViolationResultParent>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.searchViolationError(e.getMessage());
                    }

                    @Override
                    public void doNext(ViolationResultParent result) {
                        if (result != null && "000000".equals(result.getSYS_HEAD().getReturnCode())) {
                            mAtyView.allPaymentData(result.getRspInfo());
                            List<ViolationBean> beanList = result.getRspInfo().getViolationInfo();
                            if (beanList != null && !beanList.isEmpty()) handleViolations(beanList);

                            dataDistribution(result, 0);
                            dataDistribution(result, 1);
                        } else {
                            mAtyView.searchViolationError(result != null
                                    ? result.getSYS_HEAD().getReturnMessage()
                                    : "未知错误(cip.cfc.v002.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 求情参数
     */
    @Override
    public String initViolationDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.v002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        ViolationDTO bean = mAtyView.getViolationDTO();

        dto.setReqInfo(bean);
        return new Gson().toJson(dto);
    }

    /**
     * 业务逻辑
     * 1、取 List<ViolationBean>
     * 2、过滤出 processste 0,1,2,3状态的值
     */
    private void dataDistribution(ViolationResultParent result, final int value) {
        Subscription subscription = Observable.just(result)
                .map(new Func1<ViolationResultParent, ViolationResult>() {
                    @Override
                    public ViolationResult call(ViolationResultParent resultParent) {
                        return resultParent.getRspInfo();
                    }
                })
                .filter(new Func1<ViolationResult, Boolean>() {
                    @Override
                    public Boolean call(ViolationResult violationResult) {
                        return null != violationResult;
                    }
                })
                .map(new Func1<ViolationResult, List<ViolationBean>>() {
                    @Override
                    public List<ViolationBean> call(ViolationResult violationResult) {
                        return violationResult.getViolationInfo();
                    }
                })
                .filter(new Func1<List<ViolationBean>, Boolean>() {
                    @Override
                    public Boolean call(List<ViolationBean> violationBeen) {
                        return null != violationBeen;
                    }
                })
                .flatMap(new Func1<List<ViolationBean>, Observable<ViolationBean>>() {
                    @Override
                    public Observable<ViolationBean> call(List<ViolationBean> violationBeen) {
                        return Observable.from(violationBeen);
                    }
                })
                .filter(new Func1<ViolationBean, Boolean>() {
                    @Override
                    public Boolean call(ViolationBean violationBean) {
                        return null != violationBean;
                    }
                })
                .filter(new Func1<ViolationBean, Boolean>() {
                    @Override
                    public Boolean call(ViolationBean violationBean) {
                        return violationBean.getProcessste() == value || violationBean.getProcessste() == value + 2;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<ViolationBean>>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dataDistributionError(e.getMessage(), value);
                    }

                    @Override
                    public void doNext(List<ViolationBean> beanList) {
                        if (value == 0) {
                            mAtyView.nonPaymentData(beanList);
                        } else if (value == 1) {
                            mAtyView.havePaymentData(beanList);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 处理违章信息 上传数据后台
     */
    @Override
    public void handleViolations(List<ViolationBean> beanList) {
        ViolationCarDTO violationCarDTO = mAtyView.getViolationCarDTO();
        List<ViolationBean> violationInfo = beanList;
        violationCarDTO.setViolationInfo(violationInfo);

        Subscription subscription = mRepository.handleViolations(violationCarDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseResponse>() {
                    @Override
                    public void doCompleted() {

                    }

                    @Override
                    public void doError(Throwable e) {

                    }

                    @Override
                    public void doNext(BaseResponse result) {

                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * cip.cfc.c002.01
     */
    @Override
    public void getPayCars() {
        Subscription subscription = mRepository.getPayCars(initHomeDataDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PayCarResult>() {
                    @Override
                    public void doCompleted() {
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getPayCarsError(e.getMessage());
                    }

                    @Override
                    public void doNext(PayCarResult result) {
                        if (result != null && "000000".equals(result.getSYS_HEAD().getReturnCode())) {
                            mAtyView.getPayCarsSucceed(result.getRspInfo());
                        } else {
                            mAtyView.getPayCarsError(result != null
                                    ? result.getSYS_HEAD().getReturnMessage()
                                    : "未知错误(cip.cfc.c002.01)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initHomeDataDTO() {
        RequestDTO dto = new RequestDTO();
        RequestHeadDTO requestHeadDTO = mRepository.initLicenseFileNumDTO("cip.cfc.c002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        LogoutDTO params = new LogoutDTO();
        params.setUsrid(mRepository.getRASUserID());

        dto.setReqInfo(params);
        return new Gson().toJson(dto);
    }
}
