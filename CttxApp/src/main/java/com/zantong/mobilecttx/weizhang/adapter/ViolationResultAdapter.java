package com.zantong.mobilecttx.weizhang.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zantong.mobilecttx.R;
import com.jcodecraeer.xrecyclerview.BaseRecyclerViewHolder;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.car.bean.PayCar;
import com.zantong.mobilecttx.car.bean.PayCarResult;
import com.zantong.mobilecttx.card.activity.CardHomeActivity;
import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.eventbus.UpdateCarInfoEvent;
import com.zantong.mobilecttx.user.activity.LoginActivity;
import com.zantong.mobilecttx.user.dto.LogoutDTO;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.ViolationResultAcitvity;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViolationResultAdapter extends BaseAdapter<ViolationBean> {

    private Context mContext;
    private FragmentManager mFragmentManager;

    @SuppressLint("SetTextI18n")
    @Override
    public void showData(BaseRecyclerViewHolder viewHolder, int position, final ViolationBean data) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (data != null) {
            String violationdate = data.getViolationdate();
            String violationtime = data.getViolationtime();

            if (!TextUtils.isEmpty(violationdate) && violationdate.length() >= 8
                    && !TextUtils.isEmpty(violationdate) && violationtime.length() >= 4) {
                String date = violationdate.substring(0, 4)
                        + "-" +
                        violationdate.substring(4, 6)
                        + "-" +
                        violationdate.substring(6, 8);

                String time = violationtime.substring(0, 2) + ":" + violationtime.substring(2, 4);
                holder.mTm.setText(date + " " + time);
            }
            int processte = data.getProcessste();

            String violationamt = data.getViolationamt();
            String violationcent = data.getViolationcent();

            String violationplace = data.getViolationplace();
            String violationinfo = data.getViolationinfo();

            holder.mAddr.setVisibility(processte == 2 || processte == 3 ? View.VISIBLE : View.GONE);
            holder.mReason.setVisibility(processte == 2 || processte == 3 ? View.VISIBLE : View.GONE);
            holder.mAddr.setText(violationplace);
            holder.mReason.setText(violationinfo);

            holder.mAmount.setText(StringUtils.getPriceString(violationamt) + "元");
            holder.mCount.setText(violationcent + "分");

            if (processte == 0 || processte == 2) {
                holder.mPay.setVisibility(View.VISIBLE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_weichuli);
            } else if (processte == 1 || processte == 3) {
                holder.mPay.setVisibility(View.GONE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_yichuli);
            } else {
                holder.mPay.setVisibility(View.GONE);
                holder.mFlagImg.setBackgroundResource(R.mipmap.icon_yichuli);
            }

            holder.mPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPayClick(data);
                }
            });
        }
    }

    private void onPayClick(final ViolationBean data) {
        //第一步，判断是否登录；第二步，判断是否是处罚决定书；第三步，判断是否绑卡；第四步，获取绑定车辆数量，判断是否为2辆；
        // 第五步，判断是否为绑定车厢；第六步，判断交易代码8400接口是否成功
        String bitNumber = data.getViolationnum();
        int processte = data.getProcessste();

        if (!PublicData.getInstance().loginFlag) {
            Act.getInstance().gotoIntent(mContext, LoginActivity.class);
        } else if (processte == 2 || processte == 3) {
            ((ViolationResultAcitvity) mContext).showDialogToCodequery();
        } else {
            ((ViolationResultAcitvity) mContext).setPayFragment(data);
            if ("1".equals(bitNumber) || "2".equals(bitNumber)) {//是否处罚决定书

                mFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.down_to_up, R.anim.left_to_right)
                        .replace(((ViolationResultAcitvity) mContext).getPayLayoutId(), ((ViolationResultAcitvity) mContext).getPayFragment()).commit();
                //直接去缴费
            } else {
                if (Tools.isStrEmpty(PublicData.getInstance().filenum)) {//未綁卡
                    byCardHome();
                } else {
                    hasCarCard(data);
                }
            }
        }
    }

    /**
     * 已经绑定卡后的操作
     */
    private void hasCarCard(final ViolationBean data) {
        LogoutDTO dto = new LogoutDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        UserApiClient.getPayCars(mContext, dto, new CallBack<PayCarResult>() {
            @Override
            public void onSuccess(PayCarResult result) {
                EventBus.getDefault().post(new UpdateCarInfoEvent(true));
                List<PayCar> list = result.getRspInfo().getUserCarsInfo();
                if (list.size() <= 2) {
                    mFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.down_to_up, R.anim.left_to_right)
                            .replace(((ViolationResultAcitvity) mContext).getPayLayoutId(), ((ViolationResultAcitvity) mContext).getPayFragment())
                            .commit();
                } else if (list.size() == 2) {
                    for (PayCar car : list) {
                        if (car.getCarnum().equals(data.getCarnum())) {
                            mFragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.down_to_up, R.anim.left_to_right)
                                    .replace(((ViolationResultAcitvity) mContext).getPayLayoutId(), ((ViolationResultAcitvity) mContext).getPayFragment())
                                    .commit();
                            break;
                        } else {
                            ToastUtils.showShort(mContext, "当前车辆为非绑定车辆，可去车辆管理进行改绑");
                        }
                    }
                }
            }
        });
    }

    /**
     * 去绑卡页面
     */
    private void byCardHome() {
        MobclickAgent.onEvent(mContext, Config.getUMengID(11));
        DialogUtils.remindDialog(mContext, "温馨提示", "您还未绑卡，暂时无法进行缴费", "取消", "立即绑卡",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Act.getInstance().lauchIntentToLogin(mContext, CardHomeActivity.class);
                    }
                });
    }

    @Override
    public View createView(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_violation_result, viewGroup, false);
        mFragmentManager = ((ViolationResultAcitvity) mContext).getSupportFragmentManager();
        return view;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(View view, int itemType) {
        return new ViewHolder(view);
    }

    /**
     * 自定义的ViewHolder，持有每个Item的的所有界面元素
     */
    public static class ViewHolder extends BaseRecyclerViewHolder {
        @Bind(R.id.item_violation_result_tm)
        TextView mTm;//违章时间
        @Bind(R.id.item_violation_result_addr)
        TextView mAddr;//地点
        @Bind(R.id.item_violation_result_reason)
        TextView mReason;//行为
        @Bind(R.id.item_violation_result_amount)
        TextView mAmount;//金额
        @Bind(R.id.item_violation_result_count)
        TextView mCount;//扣分数
        @Bind(R.id.item_violation_result_flag)
        ImageView mFlagImg;//标签
        @Bind(R.id.item_violation_result_pay)
        TextView mPay;//去处理

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 交易代码8400
     */
    private void setJiaoYiDaiMa() {
        if (!TextUtils.isEmpty(PublicData.getInstance().filenum)) {
            UserApiClient.setJiaoYiDaiMa(mContext, PublicData.getInstance().filenum, new CallBack<Result>() {
                @Override
                public void onSuccess(Result result) {
                    if (result.getSYS_HEAD().getReturnCode().equals("000000")) {

                    }
                }
            });
        }
    }

}
