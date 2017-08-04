package cn.qqtheme.framework.picker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.R;
import cn.qqtheme.framework.adapter.PopupCarTypeAdapter;
import cn.qqtheme.framework.contract.bean.SparringGoodsBean;
import cn.qqtheme.framework.util.ContextUtils;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 时间选择器
 */
public class SparringTimePicker extends LinkagePicker {


    public SparringTimePicker(Activity activity) {
        super(activity);
    }

    public SparringTimePicker(Activity activity, ArrayList<String> firstList,
                              ArrayList<ArrayList<String>> secondList) {

        super(activity);

        ArrayList<ArrayList<ArrayList<String>>> thirdList = getThirdArrayLists("09:00");

        initAllListData(firstList, secondList, thirdList);
    }

    @NonNull
    protected ArrayList<ArrayList<ArrayList<String>>> getThirdArrayLists(String time) {
        ArrayList<ArrayList<ArrayList<String>>> thirdList = new ArrayList<>();
        ArrayList<ArrayList<String>> second = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        first.add(time);
        second.add(first);
        thirdList.add(second);
        return thirdList;
    }

    public SparringTimePicker(Activity activity, ArrayList<String> firstList,
                              ArrayList<ArrayList<String>> secondList,
                              ArrayList<ArrayList<ArrayList<String>>> thirdList) {
        super(activity, firstList, secondList, thirdList);
    }

    /**
     * 覆盖父类  头部状态
     */
    @Nullable
    protected View makeHeaderView() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog_time_picker, null);
        TextView tvClose = (TextView) layout.findViewById(R.id.tv_close);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ListView listView = (ListView) layout.findViewById(R.id.lv_type);
        final List<SparringGoodsBean> speedList = new ArrayList<>();
        speedList.add(new SparringGoodsBean("2小时", true));
        speedList.add(new SparringGoodsBean("3小时", false));
        final PopupCarTypeAdapter boxAdapter = new PopupCarTypeAdapter(activity, speedList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SparringGoodsBean bean : speedList) {
                    bean.setChoice(false);
                }
                speedList.get(position).setChoice(true);
                boxAdapter.notifyDataSetChanged();

                //TODO 时间切换时改值 位置==0--》07：00
                int secondPositon = getSelectedSecondIndex() + 7 + (position == 0 ? 2 : 3);
                String thirdTime = (secondPositon < 10 ? "0" + secondPositon : secondPositon) + ":00";
                updateSelThirdItem(getThirdArrayLists(thirdTime));
            }
        });
        listView.setAdapter(boxAdapter);
//二级栏滑动监听
        setWheelViewListener(new WheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {

                int secondPositon = selectedIndex + 7 + (speedList.get(0).isChoice() ? 2 : 3);
                String thirdTime = (secondPositon < 10 ? "0" + secondPositon : secondPositon) + ":00";
                updateSelThirdItem(getThirdArrayLists(thirdTime));
            }
        });

        return layout;
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
        textView.setBackgroundColor(ContextUtils.getContext().getResources().getColor(R.color.colorTvBlue_59b));
        textView.setTextColor(ContextUtils.getContext().getResources().getColor(R.color.colorWhite));

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
