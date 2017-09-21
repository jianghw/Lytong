package com.zantong.mobilecttx.weizhang.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.contract.ItemClickListener;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.adapter.SectionedExpandableLayoutHelper;
import com.zantong.mobilecttx.weizhang.bean.ViolationCarInfo;
import com.zantong.mobilecttx.weizhang.bean.ViolationHistoryBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationItemBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationItemInfo;
import com.zantong.mobilecttx.weizhang.dto.ViolationSearchDTO;
import com.zantong.mobilecttx.widght.MyPopWindow;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ViolationHistoryAcitvity extends BaseMvpActivity<IBaseView,HelpPresenter> implements ItemClickListener{

    @Bind(R.id.activity_violation_history_num)
    TextView mNum;
    @Bind(R.id.activity_violation_history_amount)
    TextView mAmount;
    @Bind(R.id.activity_violation_history_xrecyclerview)
    XRecyclerView mRecyclerView;
    @Bind(R.id.activity_violation_history_layout)
    View mDownLayout;
    @Bind(R.id.activity_violation_history_text)
    TextView mDownTitle;

    private MyPopWindow mPW;
    private String mDate = "12";
    private List<ViolationCarInfo> violationCarList;

    SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;

    @Override
    protected int getContentResId() {
        return R.layout.activity_violation_history;
    }


    @Override
    public void initView() {
        setTitleText("违章处理记录");
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    public void initData() {
        sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(this,
                mRecyclerView, this, 3);
        getViolationHistory();
    }

    private void getViolationHistory(){
        showDialogLoading();
        ViolationSearchDTO dto = new ViolationSearchDTO();
        dto.setDate(mDate);
        dto.setUsernum(RSAUtils.strByEncryption(PublicData.getInstance().userID,true));
        CarApiClient.getViolationHistory(this, dto, new CallBack<ViolationHistoryBean>() {
            @Override
            public void onSuccess(ViolationHistoryBean result) {
                hideDialogLoading();
                mNum.setText("车辆数："+result.getData().getCarSize()+"辆");
                mAmount.setText(result.getData().getTotalPrice()+"元");
                violationCarList = result.getData().getData();
                if (violationCarList != null && violationCarList.size() > 1){
                    violationCarList.get(0).setExpanded(true);
                }
                sectionedExpandableLayoutHelper.removeAll();
                for (ViolationCarInfo info : violationCarList){
                    ViolationSearchDTO dto = new ViolationSearchDTO();
                    dto.setCarnum(info.getCarnum());
                    dto.setDate(mDate);
                    dto.setUsernum(RSAUtils.strByEncryption(PublicData.getInstance().userID,true));
                    getHistoryByCar(info,dto);

                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                hideDialogLoading();
            }
        });
    }
    private void getHistoryByCar(final ViolationCarInfo section,ViolationSearchDTO dto){
        CarApiClient.getViolationHistoryByCar(this, dto, new CallBack<ViolationItemBean>() {
            @Override
            public void onSuccess(ViolationItemBean result) {
                sectionedExpandableLayoutHelper.addSection(section, result.getData());
                sectionedExpandableLayoutHelper.notifyDataSetChanged();
            }
        });
    }
    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }


    @Override
    public void itemClicked(ViolationItemInfo item) {
        Act.getInstance().gotoIntent(this,ViolationDetails.class,item.getPeccancynum());
    }

    @OnClick({(R.id.activity_violation_history_layout)})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.activity_violation_history_layout:
                showPopwindow();
                break;
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindow_date, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        LogUtils.i("mDownLayout.getWidth()----"+mDownLayout.getWidth());
        final PopupWindow window = new PopupWindow(view, mDownLayout.getWidth(),
                LinearLayout.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 在底部显示
        window.showAsDropDown(mDownLayout,0,0);

        // 这里检验popWindow里的button是否可以点击
        Button first = (Button) view.findViewById(R.id.popwindow_first);
        Button second = (Button) view.findViewById(R.id.popwindow_second);
        Button third = (Button) view.findViewById(R.id.popwindow_third);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownTitle.setText("一年内");
                mDate = "12";
                window.dismiss();
                getViolationHistory();
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownTitle.setText("6个月");
                mDate = "6";
                window.dismiss();
                getViolationHistory();
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownTitle.setText("3个月");
                mDate = "3";
                window.dismiss();
                getViolationHistory();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                window.dismiss();
            }
        });

    }

    public List<ViolationCarInfo> getViolationCarList() {
        return violationCarList;
    }
}
