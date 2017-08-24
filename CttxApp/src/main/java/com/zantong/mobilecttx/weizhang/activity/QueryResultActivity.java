package com.zantong.mobilecttx.weizhang.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.card.activity.MyCardActivity;
import com.zantong.mobilecttx.card.bean.OpenQueryBean;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.contract.ModelView;
import com.zantong.mobilecttx.presenter.IllegalViolationPresenter;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.widght.refresh.PullToRefreshLayout;
import com.zantong.mobilecttx.utils.PullRefreshableRecyclerView;
import com.zantong.mobilecttx.utils.RefreshNewTools.BaseRecyclerAdapter;
import com.zantong.mobilecttx.utils.RefreshNewTools.UserInfoRememberCtrl;
import com.zantong.mobilecttx.utils.RefreshNewTools.WrapAdapter;
import com.zantong.mobilecttx.utils.RefreshNewTools.WrapRecyclerView;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ScreenManager;
import com.zantong.mobilecttx.utils.StateBarSetting;
import com.zantong.mobilecttx.utils.TitleSetting;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.adapter.IllegalViolationAdapter;
import com.zantong.mobilecttx.weizhang.bean.AddVehicleBean;
import com.zantong.mobilecttx.weizhang.bean.IllegalQueryBean;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.util.log.LogUtils;

public class QueryResultActivity extends AppCompatActivity
        implements BaseRecyclerAdapter.OnRecyclerViewListener, ModelView, View.OnClickListener {

    @Bind(R.id.open_view_recyclerview)
    PullRefreshableRecyclerView openViewRecyclerview;
    @Bind(R.id.open_view_pullrefresh)
    PullToRefreshLayout openViewPullrefresh;
    @Bind(R.id.add_vechiclies_text)
    TextView add_vechiclies_text;
    @Bind(R.id.refreshing_title_notice)
    TextView refreshing_title_notice;
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.loading_empty_text)
    TextView empty_text;
    @Bind(R.id.loading_empty_content)
    RelativeLayout loading_empty_content;
    @Bind(R.id.loading_content)
    RelativeLayout mLoadingContent;
    @Bind(R.id.loading_image)
    ImageView mLoadingImg;

    private RotateAnimation refreshingAnimation;

    private WrapRecyclerView openViewWrapRecyclerview;
    private WrapAdapter mWrapAdapter;
    private LinkedList<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean> mLinkedList = new LinkedList<>();

    private IllegalQueryBean mIllegalQueryBean;
    private AddVehicleBean mAddVehicleBean;
    private String title;
    private List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean> mViolationInfoBean = new LinkedList<>();
    private Map<String, List<String>> mIllegalViolations;
    private List<String> mIllegalViolation;
    private IllegalViolationAdapter mIllegalViolationAdapter;
    private View headView;
    private int index = -1;
    private IllegalViolationPresenter mIllegalViolationPresenter;
    private OpenQueryBean.RspInfoBean mRspInfoBean;
    private boolean JumpFlag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_refresh_view);

        ButterKnife.bind(this);
        StateBarSetting.settingBar(this);
        mIllegalViolationPresenter = new IllegalViolationPresenter(this);

        title = (String) PublicData.getInstance().mHashMap.get("IllegalViolationName");
//
        TitleSetting.getInstance().initTitle(QueryResultActivity.this, title, 0, "取消", null, "缴费须知");

        mIllegalViolations = (Map<String, List<String>>) UserInfoRememberCtrl.readObject(PublicData.getInstance().IllegalViolationFlag);
        mRspInfoBean = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(PublicData.getInstance().CarLocalFlag);

        if (mIllegalViolations != null) {
            if (mIllegalViolations.containsKey(title)) {
                mIllegalViolation = mIllegalViolations.get(title);
            }

        }
        mIllegalViolationPresenter.loadView(1);
        openViewPullrefresh.setPullUpEnable(false);
        openViewPullrefresh.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                mIllegalViolationPresenter.loadView(1);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });

//        if (mLinkedList == null || mLinkedList.size() == 0) {
//            loading_empty_content.setVisibility(View.VISIBLE);
//        } else {
//            loading_empty_content.setVisibility(View.GONE);
//        }
        mIllegalViolationAdapter = new IllegalViolationAdapter(this, mLinkedList, mIllegalViolation);
        mIllegalViolationAdapter.setOnRecyclerViewListener(this);
        openViewWrapRecyclerview = (WrapRecyclerView) openViewPullrefresh.getPullableView();


        mWrapAdapter = new WrapAdapter(mIllegalViolationAdapter);
        openViewWrapRecyclerview.setAdapter(mWrapAdapter);
        openViewWrapRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        headView = getLayoutInflater().inflate(R.layout.illegal_violation_head, null);

//        headView.setVisibility(View.GONE);
//        openViewWrapRecyclerview.addHeaderView(headView);
//        openViewWrapRecyclerview.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        if (PublicData.getInstance().loginFlag && Tools.isStrEmpty(PublicData.getInstance().filenum)) {
            Toast.makeText(this, "当前未绑定畅通卡，需要在线缴费的请进行绑卡操作。", Toast.LENGTH_LONG).show();
            add_vechiclies_text.setVisibility(View.VISIBLE);
        } else {
            add_vechiclies_text.setVisibility(View.GONE);
        }
        add_vechiclies_text.setOnClickListener(this);
        empty_text.setText("无违章车辆或查询结果为空");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViolationInfoBean = (List<OpenQueryBean.RspInfoBean.UserCarsInfoBean.ViolationInfoBean>) PublicData.getInstance().mHashMap.get("IllegalViolation");
        if (mViolationInfoBean != null && 0 != mViolationInfoBean.size()) {
//        openViewPullrefresh.autoRefresh();
            mLinkedList = new LinkedList<>(mViolationInfoBean);
//            if (mLinkedList.size() == 0) {
//                loading_empty_content.setVisibility(View.VISIBLE);
//            } else {
//                loading_empty_content.setVisibility(View.GONE);
//            }
            mIllegalViolationAdapter.setItemLists(mLinkedList);
            if (mIllegalViolationAdapter.getNewFlag()) {
//            refreshing_title_notice.setVisibility(View.VISIBLE);
//            openViewWrapRecyclerview.addHeaderView(headView);

            } else {
//            openViewWrapRecyclerview.addHeaderView(headView);
                refreshing_title_notice.setVisibility(View.GONE);
            }
        }
        mRspInfoBean = (OpenQueryBean.RspInfoBean) UserInfoRememberCtrl.readObject(PublicData.getInstance().CarLocalFlag);
        if (mRspInfoBean != null && mRspInfoBean.getUserCarsInfo().size() != 0) {
            for (int i = 0; i < mRspInfoBean.getUserCarsInfo().size(); i++) {
                if (mRspInfoBean.getUserCarsInfo().get(i).getCarnum().equals(title)) {
                    JumpFlag = false;
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PublicData.getInstance().mHashMap.put("IllegalViolation", null);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (null == mIllegalViolation) {
            mIllegalViolation = new ArrayList<>();
        }
        if (!mIllegalViolation.contains(mLinkedList.get(position).getViolationnum())) {
            mIllegalViolation.add(mLinkedList.get(position).getViolationnum());
            if (null == mIllegalViolations) {
//                Map<String, List<String>> data = new HashMap<>();
                mIllegalViolations = new HashMap<>();
                mIllegalViolations.put(title, mIllegalViolation);
            } else {
                mIllegalViolations.put(title, mIllegalViolation);
            }
        }
        UserInfoRememberCtrl.saveObject(PublicData.getInstance().IllegalViolationFlag, mIllegalViolations);

        ViolationDTO violationDTO = new ViolationDTO();
        violationDTO.setCarnumtype(PublicData.getInstance().mHashMap.get("carnumtype").toString());
        violationDTO.setCarnum(PublicData.getInstance().mHashMap.get("carnum").toString());
        violationDTO.setEnginenum(PublicData.getInstance().mHashMap.get("enginenum").toString());
        SPUtils.getInstance().setViolation(violationDTO);

        PublicData.getInstance().mHashMap.put("ViolationDetailsStr", mLinkedList.get(position).getViolationnum());
        PublicData.getInstance().mHashMap.put("Processste", mLinkedList.get(position).getProcessste());
        PublicData.getInstance().mHashMap.put("mRes", "0");
        Act.getInstance().lauchIntent(QueryResultActivity.this, ViolationDetails.class);
    }

    @Override
    public boolean onItemLongClick(int position) {
        return true;
    }


    public HashMap<String, String> mapData() {
        HashMap<String, String> mHashMap = new HashMap<>();
        mHashMap.put("carnum", PublicData.getInstance().mHashMap.get("carnum").toString());
        mHashMap.put("enginenum", PublicData.getInstance().mHashMap.get("enginenum").toString());
        mHashMap.put("carnumtype", PublicData.getInstance().mHashMap.get("carnumtype").toString());
        return mHashMap;
    }

    @Override
    public void showProgress() {
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                this, R.anim.rotating_anim);
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        loading_empty_content.setVisibility(View.GONE);
        mLoadingImg.startAnimation(refreshingAnimation);
        if (mLinkedList == null || mLinkedList.size() == 0) {
            mLoadingContent.setVisibility(View.VISIBLE);
        } else {
            mLoadingContent.setVisibility(View.GONE);
        }

    }

    @Override
    public void updateView(Object object, int index) {
        switch (index) {
            case 1:

                AddVehicleBean mAddVehicleBean = (AddVehicleBean) object;
                LogUtils.i("returnCode:" + mAddVehicleBean.getSYS_HEAD().getReturnCode());
                mLinkedList = new LinkedList<>(mAddVehicleBean.getRspInfo().getViolationInfo());
                if (mLinkedList == null || mLinkedList.size() == 0) {
                    loading_empty_content.setVisibility(View.VISIBLE);
                } else {
                    loading_empty_content.setVisibility(View.GONE);
                }
                mIllegalViolationAdapter.setItemLists(mLinkedList);
                openViewPullrefresh.refreshFinish(PullToRefreshLayout.SUCCEED);
                for (int i = 0; i < mRspInfoBean.getUserCarsInfo().size(); i++) {
                    if (title.equals(mRspInfoBean.getUserCarsInfo().get(i).getCarnum())) {
                        mRspInfoBean.getUserCarsInfo().get(i).setViolationInfo(mAddVehicleBean.getRspInfo().getViolationInfo());
                        UserInfoRememberCtrl.saveObject(PublicData.getInstance().CarLocalFlag, mRspInfoBean);
                    }
                }

                break;
            case 2:
                ScreenManager.popActivity();
                break;
        }


    }

    @Override
    public void hideProgress() {
        mLoadingContent.setVisibility(View.GONE);
        mLoadingImg.clearAnimation();
    }

    @OnClick({R.id.add_vechiclies_text, R.id.text_right, R.id.tv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_vechiclies_text:
                Act.getInstance().lauchIntent(QueryResultActivity.this, MyCardActivity.class);
                break;
            case R.id.text_right:
                Act.getInstance().gotoIntent(this, PaymentActivity.class);
                break;
            case R.id.tv_back:
                if (PublicData.getInstance().loginFlag && PublicData.getInstance().DialogCarNotice && JumpFlag && PublicData.getInstance().mServerCars.size() < 3) {
                    DialogUtils.confirm(this, "您的资料未保存，是否保存后再退出", "不保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PublicData.getInstance().DialogCarNotice = false;
                            ScreenManager.popActivity();
                        }
                    }, "保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mIllegalViolationPresenter.loadView(2);
                        }
                    });
                } else {
                    finish();
                }
                break;
        }
    }
}
