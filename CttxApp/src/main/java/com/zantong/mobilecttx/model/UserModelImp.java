package com.zantong.mobilecttx.model;

/**
 * Created by Administrator on 2016/4/22.
 */
public class UserModelImp {


//    @Override
//    public void loadUser(final OnLoadServiceBackUI listener) {

//        Call call = (Call) APPHttpClient.getCttxHttpInterface().listUers();
//        Call call = (Call) APPHttpClient.getCttxHttpInterface().listUers("data/cityinfo/101010100.html");
//        APPHttpClient.getCttxHttpInterface().listUsers("data/cityinfo/101010100.html")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BeanModel>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e("why","到这儿了！！！！");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(BeanModel beanModel) {
////                        Log.e("why",weather.getWeatherinfo().getCity()+"");
////                        listener.onSucess();
//                    }
//
//                    });
//        call.enqueue(new Callback() {
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                Log.e("why",((Weather) response.body()).getWeatherinfo().getCity());
////                listener.onSucess((User) response.body());
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Log.e("why",t.getCause()+"");
//                listener.onFailed();
//            }
//        });

//    }

//    public interface  OnLoadServiceBackUI{
////        void onSucess(User user);
////        void onFailed();
//    }

}
