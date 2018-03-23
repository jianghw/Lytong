package com.zantong.mobilecttx.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.custom.image.ImageTools;
import com.tzly.ctcyh.router.util.DensityUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Config;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.chongzhi.bean.RechargeCouponBean;
import com.zantong.mobilecttx.common.adapter.CommonSelectAdapter;
import com.zantong.mobilecttx.common.bean.CommonTwoLevelMenuBean;
import com.zantong.mobilecttx.huodong.activity.HundredPlanActivity;
import com.zantong.mobilecttx.huodong.adapter.HundredPlanCarsAdapter;
import com.zantong.mobilecttx.model.AppInfo;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.bean.UserCarInfoBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 弹窗工具类
 *
 * @author Sandy
 *         create at 16/6/1 下午5:40
 */
public class DialogUtils {
    /**
     * 加载中遮罩层
     *
     * @param context
     * @return
     */
    public static Dialog showLoading(Context context, String messge) {
        Dialog dialog = null;
        View mView = LayoutInflater.from(context).inflate(R.layout.base_loading, null);
        mView.setVisibility(View.VISIBLE);
        mView.setPadding(30, 30, 30, 30);
        TextView tv = (TextView) mView.findViewById(R.id.base_loading_msg);
        tv.setTextColor(context.getResources().getColor(R.color.white));
        if (!TextUtils.isEmpty(messge)) {
            tv.setText(messge);
        }
        dialog = new Dialog(context, R.style.CommonLoadingShadeDialog);
        dialog.setContentView(mView);
        dialog.show();
        return dialog;
    }

    public static Dialog showLoading(Context context) {
        return showLoading(context, "加载中...");
    }

    /**
     * 确认对话框
     *
     * @param context
     * @param message
     */
    public static void confirm(final Context context, String message) {
        AlertDialog.Builder bui = new AlertDialog.Builder(context);
        bui.setTitle("提示");
        bui.setMessage(message);
        bui.setNegativeButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog mExitDialog = bui.create();
        mExitDialog.show();
    }

    /**
     * 确认对话框
     *
     * @param context
     * @param message
     */
    public static void createDialog(final Context context, String message) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_one_btn, null);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_one_btn_content);
        Button mCommit = (Button) view.findViewById(R.id.dialog_one_btn_commit);
        mContent.setText(message);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 滞纳金
     *
     * @param context
     * @param message
     */
    public static void createLateFeeDialog(final Context context, String title, String message) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(
                R.layout.dialog_latefee_one_btn, null);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_one_btn_content);
        Button mCommit = (Button) view.findViewById(R.id.dialog_one_btn_commit);
        mTitle.setText(title);
        mContent.setText(message);
        mCommit.setTextColor(context.getResources().getColor(R.color.res_color_red_f3));
        mCommit.setText("我知道了");
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 5 / 6;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 版本更新
     */
    public static void updateDialog(Context context, String title, String msg,
                                    final View.OnClickListener leftListener,
                                    final View.OnClickListener rightListener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_dialog_update, null);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn1 = (Button) view.findViewById(R.id.dialog_btn1);
        Button btn2 = (Button) view.findViewById(R.id.dialog_btn2);
        mTitle.setText(title);
        mContent.setText(msg);

        btn1.setText("直接下载更新");
        btn1.setTextColor(context.getResources().getColor(R.color.gray_99));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListener != null) leftListener.onClick(v);
                dialog.dismiss();
            }
        });

        btn2.setText("应用市场更新");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightListener != null) rightListener.onClick(v);
                dialog.dismiss();
            }
        });

        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 创建选择框
     *
     * @param context
     */
    public static void createSelectDialog(Context context, String title,
                                          List<CommonTwoLevelMenuBean> list,
                                          final DialogOnClickBack onClickBack) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_select, null);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_select_title);
        mTitle.setText(title);

        XRecyclerView mRecyclerView = (XRecyclerView) view.findViewById(R.id.dialog_select_recyclerview);
        LinearLayoutManager mgr = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mgr);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.noMoreLoadings();

        CommonSelectAdapter mAdapter = new CommonSelectAdapter();
        mAdapter.append(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (onClickBack != null) onClickBack.onRechargeProviderClick(view, data);
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        });
        dialog.show();

        initWindowDialogParams(context, dialog, view);
    }

    /**
     * 活动页面
     */
    public static void createActionDialog(final Context context, int count,
                                          String imageUrl, final String url, ActionADOnClick onClickBack) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_action_ad, null);
        ImageView mImage = (ImageView) view.findViewById(R.id.img_url);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(url))
                    MainRouter.gotoWebHtmlActivity(context, "优惠活动", url);
            }
        });
        ImageLoadUtils.loadThreeRectangle(imageUrl, mImage);

        TextView mCount = (TextView) view.findViewById(R.id.tv_count);
        TextView mClose = (TextView) view.findViewById(R.id.tv_close);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) dialog.dismiss();
            }
        });

        if (onClickBack != null) onClickBack.textCount(mCount, mClose);

        mCount.setVisibility(count <= 0 ? View.GONE : View.VISIBLE);
        mClose.setVisibility(count <= 0 ? View.VISIBLE : View.GONE);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Window window = dialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        params.height = DensityUtils.getScreenHeight(context) * 2 / 3;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
    }


    /**
     * 加油充值优惠模块
     */
    public static void createCouponSelectDialog(Context context, String title,
                                                List<RechargeCouponBean> list,
                                                final DialogOnClickBack onClickBack,
                                                final ActivityOnClick onActivityClick) {
        List<CommonTwoLevelMenuBean> mList = new ArrayList<>();
        for (RechargeCouponBean bean : list) {
            CommonTwoLevelMenuBean twoLevelMenuBean = new CommonTwoLevelMenuBean();
            //TODO 协议-1为优惠券
            twoLevelMenuBean.setId(bean.isChoice() ? -1 : -2);
            twoLevelMenuBean.setImgId(bean.isChoice() ? R.mipmap.icon_coupon : R.mipmap.icon_nocoupon);
            twoLevelMenuBean.setContext(bean.getCouponContent());
            mList.add(twoLevelMenuBean);
        }

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_select, null);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_select_title);
        mTitle.setText(title);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.ray_tv);
        relativeLayout.setVisibility(View.VISIBLE);
        TextView mCancel = (TextView) view.findViewById(R.id.tv_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        });
        TextView mDetail = (TextView) view.findViewById(R.id.tv_detail);
        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//优惠劵详情
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                if (onActivityClick != null) onActivityClick.onActivityCouponClick(v);
            }
        });

        XRecyclerView mRecyclerView = (XRecyclerView) view.findViewById(R.id.dialog_select_recyclerview);
        LinearLayoutManager mgr = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mgr);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.noMoreLoadings();

        CommonSelectAdapter mAdapter = new CommonSelectAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.append(mList);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (onClickBack != null) onClickBack.onRechargeProviderClick(view, data);
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        });
        dialog.show();

        initWindowDialogParams(context, dialog, view);
    }

    private static void initWindowDialogParams(Context context, AlertDialog dialog, View view) {
        Window window = dialog.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
    }

    /**
     * 监听回调接口
     */
    public interface DialogOnClickBack {
        void onRechargeProviderClick(View view, Object data);
    }

    public interface ActivityOnClick {
        void onActivityCouponClick(View view);
    }

    public interface ActionADOnClick {
        void textCount(TextView mCount, TextView mClose);
    }

    /**
     * 车辆删除
     *
     * @param context
     * @param message
     */
    public static void delDialog(final Context context, String title, String message, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_dialog_update, null);

        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn1 = (Button) view.findViewById(R.id.dialog_btn1);
        Button btn2 = (Button) view.findViewById(R.id.dialog_btn2);
        mTitle.setText(title);
        mContent.setText(message);
        btn1.setText("删  除");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 车辆删除
     *
     * @param context
     * @param message
     */
    public static void telDialog(final Context context, String title, String message, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_dialog_update, null);

        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn1 = (Button) view.findViewById(R.id.dialog_btn1);
        Button btn2 = (Button) view.findViewById(R.id.dialog_btn2);

        mTitle.setText(title);
        mContent.setText(message);
        btn1.setText("确  定");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 确认对话框
     * <p>
     * 必须确认
     */
    public static void createDialog(final Context context, String message, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_one_btn, null);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_one_btn_content);
        Button mCommit = (Button) view.findViewById(R.id.dialog_one_btn_commit);
        mContent.setText(message);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
    }

    public static void createDialogNoDismiss(final Context context, String message, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_one_btn, null);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_one_btn_content);
        Button mCommit = (Button) view.findViewById(R.id.dialog_one_btn_commit);
        mContent.setText(message);
        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 大图显示框
     *
     * @param context
     * @param url
     */
    public static void showBigImg(final Context context, String url, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(
                R.layout.dialog_bigimg, null);
        ImageView mImg = (ImageView) view.findViewById(R.id.dialog_imageview);
        if (!"".equals(url)) {
            ImageTools.displayFromSDCard(url, mImg);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context);
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 确认对话框
     *
     * @param context
     * @param message
     * @param listener
     */
    public static void confirm(final Context context, String message,
                               OnClickListener listener) {
        AlertDialog.Builder bui = new AlertDialog.Builder(context);
        bui.setTitle("温馨提示");
        bui.setMessage(message);
        bui.setPositiveButton("是", listener);
        bui.setNegativeButton("否", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog mExitDialog = bui.create();
        mExitDialog.show();
    }

    public static void confirm(final Context context, String message, String pStr, OnClickListener pListener, String nStr,
                               OnClickListener nListener) {
        AlertDialog.Builder bui = new AlertDialog.Builder(context);
        bui.setTitle("温馨提示");
        bui.setMessage(message);
        bui.setPositiveButton(pStr, pListener);
        bui.setNegativeButton(nStr, nListener);
        AlertDialog mExitDialog = bui.create();
        mExitDialog.show();
    }

    public static void confirm(final Context context, String title, String message, String pStr, OnClickListener pListener, String nStr,
                               OnClickListener nListener) {
        AlertDialog.Builder bui = new AlertDialog.Builder(context);
        bui.setTitle(title);
        bui.setMessage(message);
        bui.setPositiveButton(pStr, pListener);
        bui.setNegativeButton(nStr, nListener);
        AlertDialog mExitDialog = bui.create();
        mExitDialog.setCanceledOnTouchOutside(false);
        mExitDialog.setCancelable(false);
        mExitDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        mExitDialog.show();
    }


    /**
     * 确认对话框
     *
     * @param context
     * @param message
     * @param listener
     */
    public static void createDialog(final Context context, String title, String message, String btnText,
                                    OnClickListener listener) {
        AlertDialog.Builder bui = new AlertDialog.Builder(context);
        bui.setTitle(title);
        bui.setMessage(message);
        bui.setPositiveButton(btnText, listener);
        AlertDialog mExitDialog = bui.create();
        mExitDialog.show();
    }

    /**
     * 确认对话框
     *
     * @param context
     * @param message
     * @param listener
     */
    public static void createDialog(final Context context, String message, OnClickListener listener) {
        createDialog(context, "温馨提示", message, "确定", listener);
    }


    /**
     * 确认对话框
     *
     * @param context
     */
    public static void createDialog(final Context context, List<AppInfo> appInfos) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_map_nav, null);


        TextView mCancel = (TextView) view.findViewById(R.id.dialog_map_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) DensityUtils.getScreenWidth(context) / 2; // 高度
        p.width = (int) p.height / 2; // 宽度
        dialog.getWindow().setAttributes(p);

        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 创建油卡支付订单
     *
     * @author zyb
     * <p>
     * <p>
     * *  *   *  *
     * *      *      *
     * *             *
     * *           *
     * *     *
     * *
     * <p>
     * <p>
     * create at 17/1/16 下午3:12
     */
    public static void createRechargeDialog(final Context context, String ordId, String amount, String payType, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_recharge, null);

        View mCancel = view.findViewById(R.id.dialog_recharge_close);
        View mPayTypeLayout = view.findViewById(R.id.dialog_recharge_paytype_layout);
        TextView mPayTypeText = (TextView) view.findViewById(R.id.dialog_recharge_paytype);
        TextView mSn = (TextView) view.findViewById(R.id.dialog_recharge_sn);
        TextView mAmount = (TextView) view.findViewById(R.id.dialog_recharge_amount);
        Button mCommit = (Button) view.findViewById(R.id.dialog_one_btn_commit);
        mSn.setText(ordId);
        mAmount.setText(amount);
        mPayTypeText.setText(payType.equals("0") ? "畅通卡支付" : "其它银行卡支付");

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                if (listener != null) listener.onClick(v);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
            }
        });
        dialog.show();
        initWinBottomDialogParams(context, dialog, view);
    }

    private static void initWinBottomDialogParams(Context context, AlertDialog dialog, View view) {
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        if (window == null) return;
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context);
        window.setAttributes(params);

        window.setBackgroundDrawableResource(R.color.trans);
        window.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 选择支付类型
     *
     * @author zyb
     * <p>
     * <p>
     * *  *   *  *
     * *      *      *
     * *             *
     * *           *
     * *     *
     * *
     * <p>
     * <p>
     * create at 17/1/16 下午3:12
     */
    public static void selRechargeTypeDialog(final Context context, final TextView tv) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(
                R.layout.dialog_recharge_paytype, null);
        View mCancel = view.findViewById(R.id.dialog_recharge_paytype_close);
        final TextView mPayType1 = (TextView) view.findViewById(R.id.dialog_recharge_paytype_changtongcard);
        final TextView mPayType2 = (TextView) view.findViewById(R.id.dialog_recharge_paytype_othercard);

        mPayType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(mPayType1.getText().toString());
                Config.PAY_TYPE = 0;
                dialog.dismiss();
            }
        });
        mPayType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(mPayType2.getText().toString());
                Config.PAY_TYPE = 1;
                dialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context);
        window.setAttributes(params);

        window.setBackgroundDrawableResource(R.color.trans);
        window.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 百日活动 车辆列表
     *
     * @author zyb
     * <p>
     * <p>
     * *  *   *  *
     * *      *      *
     * *             *
     * *           *
     * *     *
     * *
     * <p>
     * <p>
     * create at 17/1/16 下午3:12
     */
    public static void createCarsDialog(final Context context, final TextView textview) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(
                R.layout.dialog_cars, null);
        XRecyclerView mRecyclerView = (XRecyclerView) view.findViewById(R.id.dialog_cars_recyclerview);
        HundredPlanCarsAdapter mAdapter = new HundredPlanCarsAdapter();
        mAdapter.append(LoginData.getInstance().mServerCars);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.loading);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                UserCarInfoBean bean = (UserCarInfoBean) data;
                HundredPlanActivity.isSelCar = true;
                textview.setText(bean.getCarnum());
                dialog.dismiss();
            }
        });
        dialog.show();
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context);
        window.setAttributes(params);

        window.setBackgroundDrawableResource(R.color.trans);
        window.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 定位失败提醒  提示title 提示内容 两个自定义按钮内容及事件
     *
     * @param context
     * @param message
     * @param listener1
     */
    public static void remindDialog(final Context context, String title, String message,
                                    String leftBtn, String rightBtn,
                                    final View.OnClickListener listener1, final View.OnClickListener listener2) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_dialog_update, null);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn1 = (Button) view.findViewById(R.id.dialog_btn1);
        Button btn2 = (Button) view.findViewById(R.id.dialog_btn2);
        mTitle.setText(title);
        mContent.setText(message);
        btn1.setText(leftBtn);
        btn1.setTextColor(context.getResources().getColor(R.color.gray_99));
        btn2.setText(rightBtn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                if (listener1 != null) listener1.onClick(v);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                if (listener2 != null) listener2.onClick(v);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 车辆删除
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void previewImg(final Context context, Bitmap bmp, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_dialog_update, null);
        LinearLayout mLayoutView = (LinearLayout) view.findViewById(R.id.line_content);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn1 = (Button) view.findViewById(R.id.dialog_btn1);
        Button btn2 = (Button) view.findViewById(R.id.dialog_btn2);
        mTitle.setVisibility(View.GONE);
        mContent.setVisibility(View.GONE);
        Drawable drawable = new BitmapDrawable(bmp);
        mLayoutView.setBackground(drawable);
        btn1.setText("确  定");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 7 / 8;
        params.width = DensityUtils.getScreenHeight(context) * 7 / 8;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * @param context
     * @param title
     * @param content
     * @param leftMenu
     * @param rightMenu
     * @param listener1
     * @param listener2
     */
    public static void createDialog(final Context context, String title, String content,
                                    String leftMenu, String rightMenu,
                                    final View.OnClickListener listener1, final View.OnClickListener listener2) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.custom_dialog_update, null);
        TextView mTitle = (TextView) view.findViewById(R.id.dialog_title);
        TextView mContent = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn1 = (Button) view.findViewById(R.id.dialog_btn1);
        Button btn2 = (Button) view.findViewById(R.id.dialog_btn2);
        mTitle.setText(title);
        mContent.setText(content);
        btn1.setText(leftMenu);
        btn1.setTextColor(context.getResources().getColor(R.color.gray_99));
        btn2.setText(rightMenu);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                dialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                dialog.dismiss();
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context) * 3 / 4;
        dialog.getWindow().setAttributes(params);

        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 创建选择框
     *
     * @param context
     */
    public static void createSelCarDialog(Context context, String car1, String car2, final View.OnClickListener listener1, final View.OnClickListener listener2) {


        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_set_paycar, null);
        TextView mCarNum1 = (TextView) view.findViewById(R.id.dialog_paycar_tv1);
        TextView mCarNum2 = (TextView) view.findViewById(R.id.dialog_paycar_tv2);
        View view1 = view.findViewById(R.id.dialog_paycar_layout1);
        View view2 = view.findViewById(R.id.dialog_paycar_layout2);
        Button mCancel = (Button) view.findViewById(R.id.dialog_paycar_cancel);
        mCarNum1.setText(car1);
        mCarNum2.setText(car2);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                dialog.dismiss();
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                dialog.dismiss();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        SystemBarTintManager tintManager = new SystemBarTintManager((Activity) context);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(context);
        //		params.height = DensityUtils.getScreenHeight(context) - tintManager.getConfig().getStatusBarHeight();
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(view);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
    }
}
