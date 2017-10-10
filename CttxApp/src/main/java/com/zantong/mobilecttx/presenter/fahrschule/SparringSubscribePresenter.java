
package com.zantong.mobilecttx.presenter.fahrschule;

import android.support.annotation.NonNull;

import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponResponse;
import com.zantong.mobilecttx.contract.fahrschule.ISparringSubscribeContract;
import com.zantong.mobilecttx.fahrschule.bean.CreateOrderResponse;
import com.zantong.mobilecttx.fahrschule.bean.ServerTimeResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringAreaResponse;
import com.zantong.mobilecttx.fahrschule.bean.SparringGoodsResponse;
import com.zantong.mobilecttx.fahrschule.dto.CreateOrderDTO;
import com.zantong.mobilecttx.model.repository.BaseSubscriber;
import com.zantong.mobilecttx.model.repository.RepositoryManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class SparringSubscribePresenter
        implements ISparringSubscribeContract.ISparringSubscribePresenter {

    private final RepositoryManager mRepository;
    private final ISparringSubscribeContract.ISparringSubscribeView mAtyView;
    private final CompositeSubscription mSubscriptions;

    public SparringSubscribePresenter(@NonNull RepositoryManager repositoryManager,
                                      @NonNull ISparringSubscribeContract.ISparringSubscribeView view) {
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
     * 20.新手陪练获取服务地区
     */
    @Override
    public void getServiceArea() {
        Subscription subscription = mRepository.getServiceArea()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SparringAreaResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.serviceAreaError(e.getMessage());
                    }

                    @Override
                    public void doNext(SparringAreaResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.serviceAreaSucceed(result);
                        } else {
                            mAtyView.serviceAreaError(result != null
                                    ? result.getResponseDesc() : "未知错误(N20)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 22.获取商品
     */
    @Override
    public void getGoods() {
        Subscription subscription = mRepository.getGoodsFive("5")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SparringGoodsResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.goodsError(e.getMessage());
                    }

                    @Override
                    public void doNext(SparringGoodsResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.goodsSucceed(result);
                        } else {
                            mAtyView.goodsError(result != null
                                    ? result.getResponseDesc() : "未知错误(N22)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 21.获取服务器时间
     */
    @Override
    public void getServerTime() {
        Subscription subscription = mRepository.getServerTime()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ServerTimeResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.serverTimeError(e.getMessage());
                    }

                    @Override
                    public void doNext(ServerTimeResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            serverTimeSucceed(result);
                        } else {
                            mAtyView.serverTimeError(result != null
                                    ? result.getResponseDesc() : "未知错误(N21)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 57.获取指定类型优惠券
     * 优惠券业务：1 加油充值；2 代驾；3 洗车
     */
    @Override
    public void getCouponByType() {
        Subscription subscription = mRepository.getCouponByType(initUserId(), "5")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mAtyView.showLoadingDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<RechargeCouponResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.couponByTypeError(e.getMessage());
                    }

                    @Override
                    public void doNext(RechargeCouponResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.couponByTypeSucceed(result);
                        } else {
                            mAtyView.couponByTypeError(result != null
                                    ? result.getResponseDesc() : "未知错误(57)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public String initUserId() {
        return mRepository.getDefaultRASUserID();
    }

    /**
     * 日期处理类
     * 明天至年底的时间
     */
    public void serverTimeSucceed(ServerTimeResponse result) {
        String dateString = result.getData();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = startCalendar.getTime();

        Calendar endCalendar = Calendar.getInstance();
        int month = startCalendar.get(Calendar.MONTH);
        int day = startCalendar.get(Calendar.DAY_OF_MONTH);
        endCalendar.set(Calendar.YEAR, month != 11 && day != 31
                ? startCalendar.get(Calendar.YEAR) : startCalendar.get(Calendar.YEAR) + 1);
        endCalendar.set(Calendar.MONTH, Calendar.DECEMBER);
        endCalendar.set(Calendar.DATE, 31);
        Date endDate = endCalendar.getTime();

        List<Date> dateArrayList = new ArrayList<>();
        while (tomorrow.getTime() <= endDate.getTime()) {
            dateArrayList.add(tomorrow);
            startCalendar.add(Calendar.DAY_OF_YEAR, 1);
            tomorrow = startCalendar.getTime();
        }
//        dateArrayList.add(endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 E", Locale.SIMPLIFIED_CHINESE);
        ArrayList<String> dateList = new ArrayList<>();
        for (Date da : dateArrayList) {
            dateList.add(sdf.format(da));
        }
        mAtyView.serverTimeSucceed(dateList, date);
    }

    /**
     * 2.创建订单
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
                .subscribe(new BaseSubscriber<CreateOrderResponse>() {
                    @Override
                    public void doCompleted() {
                        mAtyView.dismissLoadingDialog();
                    }

                    @Override
                    public void doError(Throwable e) {
                        mAtyView.createOrderError(e.getMessage());
                    }

                    @Override
                    public void doNext(CreateOrderResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            mAtyView.createOrderSucceed(result);
                        } else {
                            mAtyView.createOrderError(result != null
                                    ? result.getResponseDesc() : "未知错误(N2)");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public CreateOrderDTO getCreateOrder() {
        CreateOrderDTO orderDTO = mAtyView.getCreateOrderDTO();
        orderDTO.setType("5");
        orderDTO.setPayType("1");
        orderDTO.setUserNum(mRepository.getDefaultRASUserID());

        if (mAtyView.getUseCoupon())
            orderDTO.setCouponId(mAtyView.getCouponId());
        return orderDTO;
    }
}
