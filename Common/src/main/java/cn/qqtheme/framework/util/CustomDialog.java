package cn.qqtheme.framework.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.R;
import cn.qqtheme.framework.adapter.PopupCarTypeAdapter;
import cn.qqtheme.framework.contract.bean.SparringGoodsBean;
import cn.qqtheme.framework.contract.custom.IAreaDialogListener;
import cn.qqtheme.framework.contract.custom.ISpeedDialogListener;
import cn.qqtheme.framework.contract.custom.ITimeDialogListener;
import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.picker.SparringAreaPicker;
import cn.qqtheme.framework.picker.SparringTimePicker;
import cn.qqtheme.framework.util.ui.DensityUtils;

/**
 * Created by jianghw on 2017/7/13.
 * Description:
 * Update by:
 * Update day:
 */

public class CustomDialog {

    /**
     * 车辆类型、变速箱 选择器
     */
    public static void popupBottomCarType(Context context, List<SparringGoodsBean> aresBeanList,
                                          ISpeedDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog_Popup);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.custom_dialog_popup_car_type, null);

        initCarTypeView(context, aresBeanList, dialog, inflate, listener);

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        dialog.setCanceledOnTouchOutside(true);

        initWinBottomDialogParams(context, dialog);
        //显示对话框
        dialog.show();
    }

    /**
     * 子控件初始化
     */
    protected static void initCarTypeView(Context context, final List<SparringGoodsBean> aresBeanList,
                                          final Dialog dialog, View inflate, final ISpeedDialogListener listener) {

        if (aresBeanList != null && aresBeanList.size() > 0) aresBeanList.get(0).setChoice(true);
        //初始化控件
        TextView tvClose = (TextView) inflate.findViewById(R.id.tv_close);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tvCommit = (TextView) inflate.findViewById(R.id.tv_commit);
        ListView lvType = (ListView) inflate.findViewById(R.id.lv_type);
        final PopupCarTypeAdapter typeAdapter = new PopupCarTypeAdapter(context, aresBeanList);
        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SparringGoodsBean bean : aresBeanList) {
                    bean.setChoice(false);
                }
                aresBeanList.get(position).setChoice(true);
                typeAdapter.notifyDataSetChanged();
            }
        });
        lvType.setAdapter(typeAdapter);

        ListView lvGearBox = (ListView) inflate.findViewById(R.id.lv_gearbox);
        final List<SparringGoodsBean> speedList = new ArrayList<>();
        speedList.add(new SparringGoodsBean("手动挡", true));
        speedList.add(new SparringGoodsBean("自动挡", false));
        final PopupCarTypeAdapter boxAdapter = new PopupCarTypeAdapter(context, speedList);
        lvGearBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SparringGoodsBean bean : speedList) {
                    bean.setChoice(false);
                }
                speedList.get(position).setChoice(true);
                boxAdapter.notifyDataSetChanged();
            }
        });
        lvGearBox.setAdapter(boxAdapter);

        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparringGoodsBean goodsBean = null;
                for (SparringGoodsBean bean : aresBeanList) {
                    if (bean.isChoice()) {
                        goodsBean = bean;
                        break;
                    }
                }
                SparringGoodsBean sparringGoodsBean = null;
                for (SparringGoodsBean bean : speedList) {
                    if (bean.isChoice()) {
                        sparringGoodsBean = bean;
                        break;
                    }
                }

                if (listener != null) listener.setCurPosition(goodsBean, sparringGoodsBean);
                dialog.dismiss();
            }
        });
    }

    private static void initWinBottomDialogParams(Context context, Dialog dialog) {
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.width = DensityUtils.getScreenWidth(context);
            dialogWindow.setAttributes(params);
        }
    }

    /**
     * 地区选择
     */
    public static void popupBottomArea(Activity context, ArrayList<String> firstList,
                                       ArrayList<ArrayList<String>> secondList,
                                       final IAreaDialogListener dialogListener) {

        SparringAreaPicker areaPicker = new SparringAreaPicker(context, firstList, secondList);
        areaPicker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                if (dialogListener != null) dialogListener.setCurPosition(second);
            }
        });
        areaPicker.setOffset(1);
        areaPicker.setHalfScreen(true);
        areaPicker.show();
    }

    /**
     * 时间段选择
     */
    public static void popupBottomTime(Activity context, ArrayList<String> firstList,
                                       ArrayList<ArrayList<String>> secondList,
                                       final ITimeDialogListener listener) {

        SparringTimePicker timePicker = new SparringTimePicker(context, firstList, secondList);
        timePicker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                if (listener != null) listener.setCurPosition(first, second, third);
            }
        });
        timePicker.setOffset(1);
        timePicker.setFirstLinkage(false);
        timePicker.setSecondLinkage(false);
        timePicker.setThirdLinkage(false);
        timePicker.setColumnWeight(0.5, 0.25, 0.25);
        timePicker.setTopLineVisible(false);
        timePicker.setHeight(ScreenUtils.heightPixels(context) * 3/ 4);
        timePicker.show();
    }

    /**
     * 默认提示信息
     */
    public static void createCustomDialogHint(Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.CustomImageDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog_image_content, null);
        ImageView imageClose = (ImageView) layout.findViewById(R.id.img_close);
        TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) layout.findViewById(R.id.tv_content);
        dialog.setView(layout);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 显示提示文本
     */
    public static void customContentDialog(Context context, String title, String content) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.CustomImageDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog_image_content, null);
        ImageView imageClose = (ImageView) layout.findViewById(R.id.img_close);
        TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) layout.findViewById(R.id.tv_content);
        dialog.setView(layout);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        tvTitle.setText(title);
        tvContent.setText(content);
    }
}
