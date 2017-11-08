
package com.zantong.mobilecttx.fahrschule_p;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.fahrschule.bean.AresGoodsResponse;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.GoodsDetailResponse;
import com.zantong.mobilecttx.fahrschule.bean.MerchantAresResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.contract.IFahrschuleApplyFtyContract;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;

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
        mAtyView.dismissLoading();
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
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MerchantAresResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.getMerchantAreaError(e.getMessage());
                    }

                    @Override
                    public void doNext(MerchantAresResponse result) {
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
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<AresGoodsResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.getAreaGoodsError(e.getMessage());
                    }

                    @Override
                    public void doNext(AresGoodsResponse result) {
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
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GoodsDetailResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.getGoodsDetailError(e.getMessage());
                    }

                    @Override
                    public void doNext(GoodsDetailResponse result) {
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
                        mAtyView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CreateOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoading();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.dismissLoading();
                        mAtyView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(CreateOrderResponse result) {
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
        orderDTO.setUserNum(mRepository.getRASUserID());
        orderDTO.setGoodsId(mAtyView.getGoodsId());
        orderDTO.setPrice(mAtyView.getPriceValue());
        orderDTO.setUserName(mAtyView.getEditName());
        orderDTO.setPhone(mAtyView.getEditPhone());
        orderDTO.setIdCard(mAtyView.getEditIdentityCard());
        orderDTO.setPayType("1");
        return orderDTO;
    }
}
