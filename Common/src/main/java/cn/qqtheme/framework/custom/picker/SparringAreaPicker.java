package cn.qqtheme.framework.custom.picker;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.qqtheme.framework.R;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 地区选择器
 */
public class SparringAreaPicker extends LinkagePicker {

    public SparringAreaPicker(Activity activity) {
        super(activity);
    }

    public SparringAreaPicker(Activity activity, ArrayList<String> firstList,
                              ArrayList<ArrayList<String>> secondList) {
        super(activity, firstList, secondList);
    }

    public SparringAreaPicker(Activity activity, ArrayList<String> firstList,
                              ArrayList<ArrayList<String>> secondList,
                              ArrayList<ArrayList<ArrayList<String>>> thirdList) {
        super(activity, firstList, secondList, thirdList);
    }

    /**
     * 覆盖父类  头部状态
     */
    @Nullable
    protected View makeHeaderView() {
        LinearLayout linearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.toPx(45f));
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(ConvertUtils.toPx(15f), 0, ConvertUtils.toPx(15f), 0);
        linearLayout.setClipToPadding(false);

        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setText("请选择地区");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(mApplicationContext.getResources().getColor(R.color.colorTvBlue_59b));

        Drawable drawableRight = mApplicationContext.getResources().getDrawable(R.mipmap.ic_tv_close);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onCancel();
            }
        });
        linearLayout.addView(textView);

        return linearLayout;
    }

    /**
     * 尾布局
     */
    @Nullable
    protected View makeFooterView() {
        TextView textView = new TextView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.toPx(60f));
        textView.setLayoutParams(params);
        textView.setText("确  认");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(ConvertUtils.toSp(54f));
        textView.setBackgroundColor(mApplicationContext.getResources().getColor(R.color.colorTvBlue_59b));
        textView.setTextColor(mApplicationContext.getResources().getColor(R.color.colorWhite));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmit();
            }
        });
        return textView;
    }

}
