package com.tzly.annual.base.util;

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

import com.tzly.annual.base.R;
import com.tzly.annual.base.adapter.PopupCarTypeAdapter;
import com.tzly.annual.base.contract.bean.SubjectGoodsBean;
import com.tzly.annual.base.contract.bean.SubjectGoodsData;
import com.tzly.annual.base.contract.custom.IAreaDialogListener;
import com.tzly.annual.base.contract.custom.ISpeedDialogListener;
import com.tzly.annual.base.contract.custom.ITimeDialogListener;
import com.tzly.annual.base.picker.LinkagePicker;
import com.tzly.annual.base.picker.SparringAreaPicker;
import com.tzly.annual.base.picker.SparringTimePicker;
import com.tzly.annual.base.util.ui.DensityUtils;

import java.util.ArrayList;
import java.util.List;

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
    public static void popupBottomCarType(Context context, SubjectGoodsData goodsData,
                                          ISpeedDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.CustomDialog_Popup);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.custom_dialog_popup_car_type, null);

        initCarTypeView(context, goodsData, dialog, inflate, listener);

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
    protected static void initCarTypeView(Context context, SubjectGoodsData goodsData,
                                          final Dialog dialog, View inflate, final ISpeedDialogListener listener) {
        final List<SubjectGoodsBean> beanList = goodsData.getGoodsList();
        final List<SubjectGoodsBean> speedList = new ArrayList<>();

        updateStopData(beanList.get(0), speedList);
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
        final PopupCarTypeAdapter typeAdapter = new PopupCarTypeAdapter(context, beanList);

        ListView lvGearBox = (ListView) inflate.findViewById(R.id.lv_gearbox);
        final PopupCarTypeAdapter boxAdapter = new PopupCarTypeAdapter(context, speedList);

        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SubjectGoodsBean bean : beanList) {
                    bean.setChoice(false);
                }

                updateStopData(beanList.get(position), speedList);
                typeAdapter.notifyDataSetChanged();
                boxAdapter.notifyDataSetChanged();
            }
        });
        lvType.setAdapter(typeAdapter);

        lvGearBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SubjectGoodsBean bean : speedList) {
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
                SubjectGoodsBean goodsBean = null;
                for (SubjectGoodsBean bean : beanList) {
                    if (bean.isChoice()) {
                        goodsBean = bean;
                        break;
                    }
                }
                SubjectGoodsBean sparringGoodsBean = null;
                for (SubjectGoodsBean bean : speedList) {
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

    private static void updateStopData(SubjectGoodsBean subjectGoodsBean, List<SubjectGoodsBean> speedList) {
        subjectGoodsBean.setChoice(true);
        if (!speedList.isEmpty()) speedList.clear();

        for (String type : subjectGoodsBean.getDetails()) {
            SubjectGoodsBean.GoodsBean bean = new SubjectGoodsBean.GoodsBean(type.equals("01") ? "手动挡" : "自动挡");
            speedList.add(new SubjectGoodsBean(bean, type.equals("01")));
        }
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

    public static void popupBottomAllArea(Activity context, ArrayList<String> firstList,
                                          ArrayList<ArrayList<String>> secondList,
                                          ArrayList<ArrayList<ArrayList<String>>> thirdList,
                                          final IAreaDialogListener dialogListener) {

        final SparringAreaPicker areaPicker =
                new SparringAreaPicker(context, firstList, secondList, thirdList);
        areaPicker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String first, String second, String third) {
                if (dialogListener != null)
                    dialogListener.setCurPosition(areaPicker.getSelectedFirstIndex() +
                            "/" + areaPicker.getSelectedSecondIndex() +
                            "/" + areaPicker.getSelectedThirdIndex());
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
        timePicker.setHeight(ScreenUtils.heightPixels(context) * 3 / 4);
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
