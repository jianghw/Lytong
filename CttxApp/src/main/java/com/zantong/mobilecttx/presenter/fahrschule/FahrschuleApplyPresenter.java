
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResult;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResult;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResult;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResult;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.interf.IFahrschuleApplyFtyContract;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 16/6/1.
 * Description: 驾校报名
 * Update by:
 * Update day:
 */
public class FahrschuleApplyPresenter
        implements IFahrschuleApplyFtyContract.IFahrschuleApplyFtyPresenter {

    private final RepositoryManager mRepository;
    private final IFahrschuleApplyFtyContract.IFahrschuleApplyFtyView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public FahrschuleApplyPresenter(@NonNull RepositoryManager repositoryManager,
                                    @NonNull IFahrschuleApplyFtyContract.IFahrschuleApplyFtyView view) {
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
     * 3.获取商户区域列表
     */
    @Override
    public void getMerchantArea() {
        Subscription subscription = mRepository.getMerchantArea()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MerchantAresResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getMerchantAreaError(e.getMessage());
                    }

                    @Override
                    public void doNext(MerchantAresResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getMerchantAreaSucceed(result);
                        } else {
                            mAtyView.getMerchantAreaError(result != null
                                    ? result.getResponseDesc() : "未知错误(N3)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 4.获取区域商品列表
     */
    @Override
    public void getAreaGoods() {
        Subscription subscription = mRepository.getAreaGoods(getAreaCode())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<AresGoodsResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getAreaGoodsError(e.getMessage());
                    }

                    @Override
                    public void doNext(AresGoodsResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getAreaGoodsSucceed(result);
                        } else {
                            mAtyView.getAreaGoodsError(result != null
                                    ? result.getResponseDesc() : "未知错误(N4)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 6.获取商品详情
     */
    @Override
    public void getGoodsDetail(String goodsId) {
        Subscription subscription = mRepository.getGoodsDetail(goodsId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GoodsDetailResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.getGoodsDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(GoodsDetailResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.getGoodsDetailSucceed(result);
                        } else {
                            mAtyView.getGoodsDetailError(result != null
                                    ? result.getResponseDesc() : "未知错误(N6)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public int getAreaCode() {
        return mAtyView.getAreaCode();
    }

    /**
     * 2.创建订单 N
     */
    @Override
    public void createOrder() {
        Subscription subscription = mRepository.createOrder(getCreateOrder())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CreateOrderResult>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(CreateOrderResult result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.createOrderSucceed(result);
                        } else {
                            mAtyView.createOrderError(result != null
                                    ? result.getResponseDesc() : "未知错误(54)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 1 加油充值，2 代驾，3 学车，4 科目强化，5 陪练
     */
    @Override
    public CreateOrderDTO getCreateOrder() {
        CreateOrderDTO orderDTO = new CreateOrderDTO();
        orderDTO.setType("3");
        orderDTO.setUserNum(mRepository.getDefaultRASUserID());
        orderDTO.setGoodsId(mAtyView.getGoodsId());
        orderDTO.setPrice(mAtyView.getPriceValue());
        orderDTO.setUserName(mAtyView.getEditName());
        orderDTO.setPhone(mAtyView.getEditPhone());
        orderDTO.setIdCard(mAtyView.getEditIdentityCard());
        return orderDTO;
    }
}
