package com.zantong.mobilecttx.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.R;

import com.tzly.ctcyh.router.util.DensityUtils;


/**
 * @version 创建时间：2011-11-1 下午05:34:54
 *          类说明
 * @author王海洋
 */
public class DialogMgr {
    static AlertDialog alg;

    public DialogMgr(Context ctx, int res) {
        popImageDlg(ctx, res);
    }

    public DialogMgr(Context ctx, final OnClickListener listener1,
                     final OnClickListener listener2) {
        shareDialog(ctx, listener1, listener2);
    }

    /**
     * 功能：提示对话框
     *
     * @param ctx
     * @param title
     * @param msg
     * @param
     * @return
     */
    public DialogMgr(Context ctx, String title, String msg, String btnName) {
        popDlg(ctx, title, msg, btnName);
    }

    public DialogMgr(Context ctx, String title, String msg) {
        popBMIDlg(ctx, title, msg, "我知道了");
    }

    public AlertDialog popDlg(final Context ctx, String title, String msg, String btnName) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(R.layout.dialog_one, null);
        TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
        ImageView dialog_iv = (ImageView) view.findViewById(R.id.dialog_icon);
        TextView msg_tv = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn = (Button) view.findViewById(R.id.dialog_ok);
        if (!Tools.isStrEmpty(btnName)) {
            btn.setText(btnName);
        }
        dialog_iv.setBackgroundResource(R.mipmap.dialog_icon2);
//        switch (type) {
//        case 2://警告对话框
//            break;
//        case 1://提示对话框
//            dialog_iv.setBackgroundResource(R.drawable.dialog_icon1);
//            break;
//        }
        if (Tools.isStrEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setVisibility(View.VISIBLE);
            title_tv.setText(title);
        }
        msg_tv.setText(msg);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//            	alg.dismiss();
                onclick(v);
            }
        });
        alg.show();
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
//        alg.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        alg.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_HOME) {
                    alg.dismiss();
                    return true;
                }
                return false;
            }
        });
        return alg;
    }

    public AlertDialog popImageDlg(final Context ctx, int res) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(R.layout.dialog_image, null);
        ImageView image_title = (ImageView) view.findViewById(R.id.image_title);
        ImageView image_close = (ImageView) view.findViewById(R.id.image_close);
        if (0 != res) {
            image_title.setImageResource(res);
//			image_title.setImageDrawable(ctx.getResources().getDrawable(res));
        }
        image_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alg.dismiss();
                onclick(v);
            }
        });
        alg.show();
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
        alg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        alg.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        alg.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_HOME) {
                    alg.dismiss();
                    return true;
                }
                return false;
            }
        });
        return alg;
    }

    public AlertDialog popBMIDlg(final Context ctx, String title, String msg, String btnName) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(R.layout.dialog_bmi, null);
        TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
        ImageView dialog_iv = (ImageView) view.findViewById(R.id.dialog_icon);
        TextView msg_tv = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn = (Button) view.findViewById(R.id.dialog_ok);
        if (!Tools.isStrEmpty(btnName)) {
            btn.setText(btnName);
        }
        dialog_iv.setBackgroundResource(R.mipmap.dialog_icon2);
//        switch (type) {
//        case 2://警告对话框
//            break;
//        case 1://提示对话框
//            dialog_iv.setBackgroundResource(R.drawable.dialog_icon1);
//            break;
//        }
        if (Tools.isStrEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setVisibility(View.VISIBLE);
            title_tv.setText(title);
        }
        msg_tv.setText(msg);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(v);
            }
        });
        alg.show();
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
        alg.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_HOME) {
                    alg.dismiss();
                    return true;
                }
                return false;
            }
        });
        return alg;
    }

    public static void selectDlg(Context ctx, final String[] items, DialogInterface.OnClickListener listener2) {
        selectBaseDlg(ctx, items, listener2);
    }

    public static void selectBaseDlg(Context ctx, final String[] items, final DialogInterface.OnClickListener listener2) {
        new AlertDialog.Builder(ctx)
                .setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        listener2.onClick(dialog, which);
                    }
                }).show();
    }


    /**
     * 两个按扭的对话框（右面是重点）
     *
     * @param ctx
     * @param title
     * @param msg
     */
    public DialogMgr(Context ctx, String title, String futitle, String msg, String btn1,
                     String btn2, OnClickListener listener1, OnClickListener listener2) {
        popDlgUpdate(ctx, title, futitle, msg, btn1, btn2, listener1, listener2);
    }

    /**
     * 左面是显示重点
     *
     * @param ctx
     * @param title
     * @param msg
     * @param btn1
     * @param btn2
     * @param listener1
     * @param listener2
     */
    public DialogMgr(Context ctx, String title, String msg, String btn1,
                     String btn2, OnClickListener listener1, OnClickListener listener2, OnClickListener listener3) {
        popDlg2(ctx, title, msg, btn1, btn2, listener1, listener2, listener3);
    }

    /**
     * 右面是显示重点
     *
     * @param ctx
     * @param title
     * @param msg
     * @param btn1
     * @param btn2
     * @param listener1
     * @param listener2
     */
    public DialogMgr(Context ctx, String title, String msg, String btn1,
                     String btn2, OnClickListener listener1, OnClickListener listener2, String flag) {
        if ("1".equals(flag)) {
            popDlgUpdate(ctx, title, "", msg, btn1, btn2, listener1, listener2);
        } else {
            popDlg(ctx, title, msg, btn1, btn2, listener1, listener2);
        }
    }


    public AlertDialog popDlg(final Context ctx, String title, String msg,
                              String btn1, String btn2, final OnClickListener listener1,
                              final OnClickListener listener2) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(
                R.layout.dialog_three, null);
        TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
        ImageView dialog_iv = (ImageView) view.findViewById(R.id.dialog_icon);
        TextView msg_tv = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn_ok = (Button) view.findViewById(R.id.dialog_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.dialog_cancel);
        dialog_iv.setBackgroundResource(R.mipmap.dialog_icon2);

        if (Tools.isStrEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setText(title);
        }
        msg_tv.setText(msg);
        btn_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                oncancel(v);
            }
        });
        btn_ok.setText(btn1);
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                oncancel(v);
            }
        });
        btn_cancel.setText(btn2);
        alg.show();
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
        return alg;
    }

    public AlertDialog popDlgUpdate(final Context ctx, String title, String futitle, String msg,
                                    String btn1, String btn2, final OnClickListener listener1,
                                    final OnClickListener listener2) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(
                R.layout.dialog_update, null);
        TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
        ImageView dialog_iv = (ImageView) view.findViewById(R.id.dialog_icon);
        TextView msg_tv = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn_ok = (Button) view.findViewById(R.id.dialog_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.dialog_cancel);
        dialog_iv.setBackgroundResource(R.mipmap.dialog_icon2);
        if (Tools.isStrEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setText(title);
        }
        msg_tv.setText(msg);
        btn_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                oncancel(v);
            }
        });
        btn_ok.setText(btn1);
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                oncancel(v);
            }
        });
        btn_cancel.setText(btn2);
        alg.show();
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
        alg.setCancelable(false);
        alg.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        WindowManager.LayoutParams params = alg.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(ctx) * 3 / 4;
//		params.height = 1000 ;
        alg.getWindow().setAttributes(params);
        return alg;
    }

    public AlertDialog popDlg2(final Context ctx, String title, String msg,
                               String btn1, String btn2, final OnClickListener listener1,
                               final OnClickListener listener2, final OnClickListener listener3) {
        alg = new AlertDialog.Builder(ctx)
                .setOnKeyListener(keylistener)
                .create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(
                R.layout.dialog_two, null);
        TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
        ImageView dialog_iv = (ImageView) view.findViewById(R.id.dialog_icon);
        TextView msg_tv = (TextView) view.findViewById(R.id.dialog_msg);
        Button add_open_card = (Button) view.findViewById(R.id.add_open_card);
        Button btn_next = (Button) view.findViewById(R.id.btn_next);
        RelativeLayout login_dialog_notice_close_rl = (RelativeLayout) view.findViewById(R.id.login_dialog_notice_close_rl);
        dialog_iv.setBackgroundResource(R.mipmap.dialog_icon2);

        if (Tools.isStrEmpty(title)) {
            title_tv.setVisibility(View.GONE);
        } else {
            title_tv.setText(title);
        }
        msg_tv.setText(msg);
        add_open_card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                oncancel(v);
            }
        });
        add_open_card.setText(btn1);
        btn_next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                oncancel(v);
            }
        });
        btn_next.setText(btn2);
        login_dialog_notice_close_rl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener3.onClick(v);
                oncancel(v);
            }
        });
        alg.show();
        WindowManager.LayoutParams params = alg.getWindow().getAttributes();
        params.width = DensityUtils.getScreenWidth(ctx) * 4 / 5;
        alg.getWindow().setAttributes(params);
        alg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
        return alg;
    }

    OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    public AlertDialog popDlg3(final Context ctx, String title1, String title2, String msg,
                               String btn1, String btn2, final OnClickListener listener1,
                               final OnClickListener listener2) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(
                R.layout.dialog_two, null);
        TextView title_tv = (TextView) view.findViewById(R.id.dialog_title);
        ImageView dialog_iv = (ImageView) view.findViewById(R.id.dialog_icon);
        TextView msg_tv = (TextView) view.findViewById(R.id.dialog_msg);
        Button btn_ok = (Button) view.findViewById(R.id.dialog_ok);
        Button btn_cancel = (Button) view.findViewById(R.id.dialog_cancel);
        dialog_iv.setBackgroundResource(R.mipmap.dialog_icon2);

        title_tv.setText(title1);
        msg_tv.setText(msg);
        btn_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                oncancel(v);
            }
        });
        btn_ok.setText(btn1);
        btn_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                oncancel(v);
            }
        });
        btn_cancel.setText(btn2);
        alg.show();
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
        return alg;
    }

    public void shareDialog(final Context ctx, final OnClickListener listener1,
                            final OnClickListener listener2) {
        alg = new AlertDialog.Builder(ctx).create();
        View view = ((Activity) ctx).getLayoutInflater().inflate(R.layout.activity_wx_share, null);
        LinearLayout haoYouLayout = (LinearLayout) view.findViewById(R.id.share_wx_haoyou);
        LinearLayout penYouQuanLayout = (LinearLayout) view.findViewById(R.id.share_wx_penyouquan);

        TextView titleCancel = (TextView) view.findViewById(R.id.share_cancel);
        haoYouLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onClick(v);
                oncancel(v);
            }
        });
        penYouQuanLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onClick(v);
                oncancel(v);
            }
        });

        titleCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                oncancel(v);
            }
        });
        alg.show();
        alg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alg.getWindow().setContentView(view);
        alg.setCanceledOnTouchOutside(false);
    }

    protected void onclick(View v) {
        alg.dismiss();
    }

    protected void oncancel(View v) {
        alg.dismiss();
    }

    protected void onDismissButtonClick(View v) {
        alg.dismiss();
    }

    public void dismiss() {
        alg.dismiss();
    }
}
