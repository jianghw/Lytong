package com.zantong.mobilecttx.user.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.api.FileDownloadApi;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.basehttprequest.Retrofit2Utils;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.common.AppManager;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.Injection;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonProblemActivity;
import com.zantong.mobilecttx.home.bean.VersionResult;
import com.zantong.mobilecttx.home.dto.VersionDTO;
import com.zantong.mobilecttx.model.repository.RepositoryManager;
import com.zantong.mobilecttx.user.activity.CouponActivity;
import com.zantong.mobilecttx.user.activity.GetBonusActivity;
import com.zantong.mobilecttx.user.activity.MegTypeActivity;
import com.zantong.mobilecttx.user.activity.OrderActivity;
import com.zantong.mobilecttx.user.activity.OrderRechargeActivity;
import com.zantong.mobilecttx.user.activity.ProblemFeedbackActivity;
import com.zantong.mobilecttx.user.bean.CouponFragmentResult;
import com.zantong.mobilecttx.user.bean.LoginInfoBean;
import com.zantong.mobilecttx.user.bean.MessageCountResult;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.ImageOptions;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zyb
 *         <p>
 *         <p>
 *         *  *   *  *
 *         *      *      *
 *         *             *
 *         *           *
 *         *     *
 *         *
 *         <p>
 *         <p>
 *         create at 17/2/28 下午5:24
 */
public class MineFragment extends Fragment {

    @Bind(R.id.user_head_image)
    ImageView userHeadImage;
    @Bind(R.id.user_name_text)
    TextView userNameText;
    @Bind(R.id.mine_changtong_notice_text)
    TextView mine_changtong_notice_text;
    @Bind(R.id.mine_manage_vechilse_notice)
    TextView mine_manage_vechilse_notice;
    @Bind(R.id.mine_share)
    View mShare;
    @Bind(R.id.mine_card_status)
    TextView mCardStatus;

    @Bind(R.id.mine_meg_count)
    TextView mMegCount;   //消息数量
    @Bind(R.id.mine_youhuijuan_tv)
    TextView mYouHui;  //优惠券

    @Bind(R.id.tv_update_title)
    TextView mTvUpdateTitle;
    @Bind(R.id.tv_update)
    TextView mTvUpdate;


    private LoginInfoBean.RspInfoBean mLoginInfoBean;

    InputStream is;
    FileOutputStream fos;
    private final String apkUrl = Environment.getExternalStorageDirectory().getPath() + File.separator + "app_newkey_release_1_3.apk";
    ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateVersion();
    }

    @SuppressLint("SetTextI18n")
    private void updateVersion() {
        VersionDTO params = new VersionDTO();
        params.setVersionType("0");
        UserApiClient.getCurrentVerson(getActivity(), params, new CallBack<VersionResult>() {
            @Override
            public void onSuccess(VersionResult result) {
                if (result.getData() != null) {
                    int versionFlag = Tools.compareVersion(Tools.getVerName(getActivity()), result.getData().getVersion());
                    if (versionFlag == -1) {//更新
                        mTvUpdate.setText("请更新最新版本v" + result.getData().getVersion());
                        Drawable nav_up = getResources().getDrawable(R.mipmap.icon_dot_sel);
                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                        mTvUpdateTitle.setCompoundDrawables(null, null, nav_up, null);
                    } else {
                        mTvUpdate.setText("当前已为最新版本");
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        if (PublicData.getInstance().loginFlag) {
            getCouponCount();
            getUnReadMsgCount();
        }
    }

    /**
     * 未读消息数量
     */
    private void getUnReadMsgCount() {
        BaseDTO dto = new BaseDTO();
        dto.setUsrId(RSAUtils.strByEncryption(this.getActivity(), PublicData.getInstance().userID, true));
        CarApiClient.getUnReadMsgCount(this.getActivity(), dto, new CallBack<MessageCountResult>() {
            @Override
            public void onSuccess(MessageCountResult result) {
                if (result.getResponseCode() == 2000) {
                    mMegCount.setText(String.valueOf(result.getData().getCount()));
                }
            }
        });
    }

    /**
     * 优惠券数量
     */
    private void getCouponCount() {
        final Dialog showLoading = DialogUtils.showLoading(getActivity());
        RepositoryManager repositoryManager = Injection.provideRepository(getActivity().getApplicationContext());
        Subscription subscription = repositoryManager.usrCouponInfo(PublicData.getInstance().userID, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CouponFragmentResult>() {
                    @Override
                    public void onCompleted() {
                        showLoading.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showLoading.dismiss();
                    }

                    @Override
                    public void onNext(CouponFragmentResult result) {
                        if (result.getResponseCode() == 2000) {
                            if (result.getData() != null) {
                                mYouHui.setText(String.valueOf(result.getData().getCouponList().size()));
                            } else {
                                ToastUtils.showShort(MineFragment.this.getActivity(), result.getResponseDesc());
                            }
                        }
                    }
                });
    }

    private void init() {
        if ("".equals(PublicData.getInstance().filenum)) {
            mCardStatus.setText("未绑卡");
        } else {
            mCardStatus.setText("已绑卡");
        }
        mine_manage_vechilse_notice.setText(PublicData.getInstance().mCarNum + " 辆车");
        if (PublicData.getInstance().loginFlag) {
            mLoginInfoBean = PublicData.getInstance().mLoginInfoBean;
            if (!Tools.isStrEmpty(mLoginInfoBean.getNickname())) {
                userNameText.setText(mLoginInfoBean.getNickname());
            } else {
                userNameText.setText(mLoginInfoBean.getPhoenum().substring(7));
            }
            if (!Tools.isStrEmpty(PublicData.getInstance().filenum)) {
//                String temp = PublicData.getInstance().cttxCardNumber.substring(0,8);
//                user_changtong_card.setText(temp+"***");
                mine_changtong_notice_text.setText("已绑定");
            } else {
                mine_changtong_notice_text.setText("未绑定");
            }

//            OpenQueryBean.RspInfoBean mRspInfoBean = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(context, PublicData.getInstance().CarLocaelFlag);
//            if (mRspInfoBean != null) {
//                mine_manage_vechilse_notice.setText(mRspInfoBean.getUserCarsInfo().size() + " 辆车");
//            } else {
//                mine_manage_vechilse_notice.setText("0 辆车");
//            }
//            ImageLoad.loadHead(context, PublicData.getInstance().mLoginInfoBean.getPortrait(), userHeadImage);
//            LogUtils.i("头像地址："+PublicData.getInstance().mLoginInfoBean.getPortrait());
            ImageLoader.getInstance().displayImage(PublicData.getInstance().mLoginInfoBean.getPortrait(), userHeadImage, ImageOptions.getAvatarOptions());

        } else {
            mine_changtong_notice_text.setText("未绑定");
            userNameText.setText("您还未登录");
            userHeadImage.setImageResource(R.mipmap.icon_portrai);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.common_problem, R.id.mine_order, R.id.mine_pay_order, R.id.mine_info_rl,
            R.id.mine_tools_part1, R.id.mine_manage_vechilse, R.id.mine_share, R.id.mine_manage_weizhang_history,
            R.id.invite_red_packet, R.id.problem_feedback, R.id.about_us, R.id.mine_meg_layout, R.id.mine_ctk_layout,
            R.id.mine_youhuijian_layout, R.id.about_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_info_rl:  //用户信息
                break;
            case R.id.mine_tools_part1:  //畅通卡
                break;
            case R.id.mine_manage_vechilse:  //车辆管理
                break;
            case R.id.mine_manage_weizhang_history:  //违章缴费记录
                break;
            case R.id.mine_order:  //保险订单
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(29));
                Act.getInstance().lauchIntentToLogin(getActivity(), OrderActivity.class);
                break;
            case R.id.mine_pay_order:  //充值支付订单
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(30));
                Act.getInstance().lauchIntentToLogin(getActivity(), OrderRechargeActivity.class);
//                Act.getInstance().gotoIntent(context, OrderRechargeActivity.class);
                break;
            case R.id.mine_share:  //分享

                break;
            case R.id.common_problem:  //常见问题
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(32));
                Act.getInstance().gotoIntent(getActivity(), CommonProblemActivity.class);
                break;
            case R.id.invite_red_packet:  //推荐领积分页面
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(31));
                Act.getInstance().lauchIntentToLogin(this.getActivity(), GetBonusActivity.class);
                break;
            case R.id.problem_feedback:  //问题反馈
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(33));
                Act.getInstance().gotoIntent(getActivity(), ProblemFeedbackActivity.class);
                break;
            case R.id.about_us:  //关于我们
                break;
            case R.id.mine_meg_layout:  //消息
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(24));
                Act.getInstance().lauchIntentToLogin(getActivity(), MegTypeActivity.class);
                break;
            case R.id.mine_ctk_layout:  //畅通卡

                break;
            case R.id.mine_youhuijian_layout:  //优惠券
                MobclickAgent.onEvent(this.getActivity(), Config.getUMengID(27));
                Act.getInstance().lauchIntentToLogin(getActivity(), CouponActivity.class);
                break;
            case R.id.about_update://版本更新
                getAppLatestVerson();
                break;
            default:
                break;
        }
    }

    /**
     * 版本更新
     */
    private void getAppLatestVerson() {
        VersionDTO params = new VersionDTO();
        params.setVersionType("0");
        UserApiClient.getCurrentVerson(getActivity(), params, new CallBack<VersionResult>() {
            @Override
            public void onSuccess(VersionResult result) {
                if (result.getData() != null) {
                    int versionFlag = Tools.compareVersion(Tools.getVerName(getActivity()), result.getData().getVersion());
                    if (versionFlag == -1) {
                        showUpdateDialog(result);
                    } else {
                        ToastUtils.showShort(getActivity(), "当前已为最新版本");
                    }
                }
            }

        });
    }

    private void showUpdateDialog(final VersionResult result) {
        DialogUtils.updateDialog(
                getActivity(),
                "版本升级",
                "请升级到最新版本(" + result.getData().getVersion() + ")，以免影响您的使用体验",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//暂不升级

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //检查权限
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //如果没有授权，则请求授权
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            downloadDemo(result.getData().getAddress());
                        }

                    }
                });
    }

    public void downloadDemo(final String url) {
        Retrofit2Utils retrofit2Utils = new Retrofit2Utils();
        final FileDownloadApi api = retrofit2Utils.getRetrofitHttps("http://192.9.210.176:8080/").create(FileDownloadApi.class);
        Observable.create(new Observable.OnSubscribe<Long[]>() {
            @Override
            public void call(Subscriber<? super Long[]> subscriber) {
                try {
//                    String urls = "http://7b1g8u.com1.z0.glb.clouddn.com/app_newkey_release_8_4.apk";
                    Response<ResponseBody> response = api.downloadFileWithFixedUrl(url).execute();
                    try {
                        if (response != null && response.isSuccessful()) {
                            //文件总长度
                            long fileSize = response.body().contentLength();
                            long fileSizeDownloaded = 0;
                            is = response.body().byteStream();
                            File file = new File(apkUrl);
                            if (file.exists()) {
                                file.delete();
                            } else {
                                file.createNewFile();
                            }
                            fos = new FileOutputStream(file);
                            int count = 0;
                            byte[] buffer = new byte[1024];
                            while ((count = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, count);
                                fileSizeDownloaded += count;
                                Long[] data = new Long[2];
                                data[0] = fileSizeDownloaded;
                                data[1] = fileSize;
                                subscriber.onNext(data);
                            }
                            fos.flush();
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new Exception("接口请求异常"));
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long[]>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "文件下载完成");
//                        PublicData.getInstance().mNetLocationBean = bean;apkUrl
                        Tools.installApk(MineFragment.this.getActivity(), apkUrl);
                        progressBar.dismiss();
                        AppManager.getAppManager().AppExit(MineFragment.this.getActivity(), false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(MineFragment.this.getActivity(), "网络错误，请重试");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long[] data) {
                        Log.d("MainActivity", data[0] + "");
                        progressBar.setCancelable(true);
                        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        int demo = (int) (data[0] * 100 / data[1]);
                        progressBar.setProgress(demo);
                        progressBar.setMax(100);
                        progressBar.setCanceledOnTouchOutside(false);
                        progressBar.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    return true;
                                }
                                return false;
                            }
                        });
                        progressBar.show();

                    }
                });
    }
}
