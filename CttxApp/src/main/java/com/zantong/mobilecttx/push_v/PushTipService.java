package com.zantong.mobilecttx.push_v;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.alibaba.sdk.android.push.notification.BasicCustomPushNotification;
import com.tzly.ctcyh.router.util.BadgeUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.data_m.BaseSubscriber;
import com.zantong.mobilecttx.data_m.RepositoryManager;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.MessageCountBean;
import com.zantong.mobilecttx.user.bean.MessageCountResponse;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jianghw on 2017/10/29.
 * Description:
 * Update by:
 * Update day:
 */

public class PushTipService extends Service {

    private RepositoryManager mRepositoryManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mRepositoryManager == null)
            mRepositoryManager = Injection.provideRepository(Utils.getContext());
    }

    /**
     * 刷新提示小红点
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!MainRouter.isUserLogin()) return super.onStartCommand(intent, flags, startId);

        Subscription subscription = mRepositoryManager
                .countMessageDetail(initBaseDTO())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MessageCountResponse>() {
                    @Override
                    public void doCompleted() {}

                    @Override
                    public void doError(Throwable e) {}

                    @Override
                    public void doNext(MessageCountResponse result) {
                        if (result != null && result.getResponseCode() == 2000) {
                            MessageCountBean countBean = result.getData();

                            BadgeUtils.setBadgeCount(Utils.getContext(),
                                    countBean.getCount(), R.mipmap.ic_global_app);
                            //广播通讯
                            Intent i = new Intent();
                            i.setAction(PushBroadcastReceiver.PUSH_TIP_ACTION);
                            i.putExtra(PushBroadcastReceiver.PUSH_TIP_ACTION, countBean.getCount());
                            sendBroadcast(i);
                        }
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }

    private void xiaoMiCountTip() {
        try {
            BasicCustomPushNotification basicCustomPushNotification = new BasicCustomPushNotification();
            Field field = basicCustomPushNotification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(basicCustomPushNotification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, new Object[]{Integer.valueOf(10)});
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public BaseDTO initBaseDTO() {
        BaseDTO baseDTO = new BaseDTO();
        baseDTO.setUsrId(mRepositoryManager.getRASUserID());
        return baseDTO;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRepositoryManager = null;
    }
}
