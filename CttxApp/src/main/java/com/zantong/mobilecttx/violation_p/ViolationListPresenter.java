
package com.zantong.mobilecttx.violation_p;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tzly.ctcyh.router.bean.BaseResponse;
import com.zantong.mobilecttx.base.dto.RequestDTO;
import com.zantong.mobilecttx.base.dto.RequestHeadDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResult;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationCarDTO;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.List;

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
        implements IViolationListContract.IViolationListPresenter {

    private final RepositoryManager mRepository;
    private final IViolationListContract.IViolationListView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public ViolationListPresenter(@NonNull RepositoryManager repositoryManager,
                                  @NonNull IViolationListContract.IViolationListView view) {
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
        mAtyView.dismissLoading();
        mSubscriptions.clear();
    }

    /**
     * 车辆违章查询
     * cip.cfc.v002.01
     */
    @Override
    public void searchViolation() {
        Subscription subscription = mRepository
                .searchViolation(initViolationDTO())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ViolationResultParent>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
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
        RequestHeadDTO requestHeadDTO = mRepository.initServiceCodeDTO("cip.cfc.v002.01");
        dto.setSYS_HEAD(requestHeadDTO);

        ViolationDTO violationDTO = mAtyView.getViolationDTO();
        if (violationDTO != null) {
            ViolationDTO bean = new ViolationDTO();
            bean.setProcessste("2");
            bean.setToken(mRepository.getRASByStr(MainRouter.getPhoneDeviceId()));
            bean.setCarnum(mRepository.getRASByStr(violationDTO.getCarnum()));
            bean.setEnginenum(mRepository.getRASByStr(violationDTO.getEnginenum()));
            bean.setCarnumtype(violationDTO.getCarnumtype());
            dto.setReqInfo(bean);
        }
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
        violationCarDTO.setViolationInfo(beanList);

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

}
