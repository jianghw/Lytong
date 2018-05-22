package com.tzly.ctcyh.cargo.refuel_v;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.BidOilBean;
import com.tzly.ctcyh.cargo.bean.response.BidOilData;
import com.tzly.ctcyh.cargo.bean.response.BidOilResponse;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderBean;
import com.tzly.ctcyh.cargo.bean.response.RefuelOrderResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.BidOilAdapter;
import com.tzly.ctcyh.cargo.refuel_p.BidOilPresenter;
import com.tzly.ctcyh.cargo.refuel_p.IBidOilContract;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.java.response.oil.OilRemainderResponse;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.dialog.DialogMgr;
import com.tzly.ctcyh.router.custom.image.ImageLoadUtils;
import com.tzly.ctcyh.router.custom.popup.CustomDialog;
import com.tzly.ctcyh.router.imple.IAreaDialogListener;
import com.tzly.ctcyh.router.util.RudenessScreenHelper;
import com.tzly.ctcyh.router.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 加油申办
 */
public class BidOilFragment extends RefreshFragment
        implements IBidOilContract.IBidOilView, View.OnClickListener {

    private IBidOilContract.IBidOilPresenter mPresenter;

    private ImageView mImgBanner;
    private TextView mTvNotice;
    private RecyclerView recyclerView;
    /**
     * 购卡金额（包含油费）
     */
    private TextView mTvBid;
    /**
     * 请输入姓名
     */
    private EditText mEdtName;
    /**
     * 请输入手机号
     */
    private EditText mEditPhone;
    private ImageView mImgBrand;
    /**
     * 请选择地区
     */
    private TextView mTvArea;
    private RelativeLayout mLayArea;
    /**
     * 请输入详细地址
     */
    private EditText mEditDetailedAddress;
    /**
     * 申请办卡
     */
    private Button mBtnCommit;
    private BidOilAdapter mAdapter;
    /**
     * 选择项目
     */
    private BidOilBean infoBean;

    public static BidOilFragment newInstance() {
        return new BidOilFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_bid_oil;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);

        BidOilPresenter presenter = new BidOilPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);

        GridLayoutManager manager = new GridLayoutManager(Utils.getContext(), 2);
//        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        mAdapter = new BidOilAdapter();

        mAdapter.setOnItemClickListener(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                if (!(data instanceof BidOilBean)) return;

                BidOilBean bidOilBean = (BidOilBean) data;
                for (BidOilBean oilBean : mAdapter.getAll()) {
                    boolean isMe = bidOilBean.getId().equals(oilBean.getId());
                    oilBean.setSelect(isMe);
                    if (isMe) infoBean = oilBean;
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.handleOilCard();
    }

    @Override
    public void setPresenter(IBidOilContract.IBidOilPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) mPresenter.unSubscribe();
    }


    /**
     * 数据加载成功
     */
    @Override
    protected void responseData(Object response) {
        if (response instanceof BidOilResponse) {
            BidOilResponse oilResponse = (BidOilResponse) response;
            BidOilData bidOilData = oilResponse.getData();

            String url = bidOilData.getImg();
            if (!url.contains("http")) {
                url = (BuildConfig.isDeta ? BuildConfig.beta_base_url : BuildConfig.release_base_url) + url;
            }
            ImageLoadUtils.loadTwoRectangle(url, mImgBanner);

            List<BidOilBean> lis = bidOilData.getGoods();
            //手动标记默认选择项目
            if (!lis.isEmpty()) {//默认第一个
                for (int i = 0; i < lis.size(); i++) {
                    lis.get(i).setSelect(i == 0);
                    if (i == 0) infoBean = lis.get(i);
                }
            }
            setSimpleDataResult(lis);
        } else
            responseError();
    }

    private void setSimpleDataResult(List<BidOilBean> data) {
        mAdapter.cleanListData();
        if (data == null || data.isEmpty()) {
            showStateEmpty();
        } else {
            //动态设置高度
            int line = data.size() % 2 == 0 ? data.size() / 2 : data.size() / 2 + 1;
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.height = RudenessScreenHelper.ptInpx(130) * line;
            recyclerView.setLayoutParams(layoutParams);

            mAdapter.append(data);
        }
    }

    @Override
    public void onClick(View view) {
        int viD = view.getId();
        if (viD == R.id.img_banner) {//图片

        } else if (viD == R.id.tv_bid) {//去办畅通卡
            CargoRouter.gotoHtmlActivity(getActivity(),
                    "申办工行卡",
                    "http://icbccard.una-campaign.com/?cid=283");
        } else if (viD == R.id.lay_area) {//城市
            if (mPresenter != null) mPresenter.getAllAreas();
        } else if (viD == R.id.btn_commit) {//提交
            verificationSubmitData();
        }
    }

    /**
     * 数据验证
     */
    private void verificationSubmitData() {
        String name = getStrEdtName();
        if (TextUtils.isEmpty(name)) {
            toastShort("请填写真实的姓名");
            return;
        }
        String phone = getStrEditPhone();
        if (TextUtils.isEmpty(phone)) {
            toastShort("请填写真实的手机");
            return;
        }
        String area = getTvArea();
        if (TextUtils.isEmpty(area)) {
            toastShort("请填写真实的城市");
            return;
        }
        String address = getEditDetailedAddress();
        if (TextUtils.isEmpty(address)) {
            toastShort("请填写真实的地址详情");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("姓名:").append(name).append("\n");
        sb.append("手机:").append(phone).append("\n");
        sb.append("城市:").append(area).append("\n");
        sb.append("地址:").append(address);
        makeSureUser(sb.toString());
    }

    public void makeSureUser(String response) {
        new DialogMgr(getActivity(), "信息确认", "",
                response, "修改", "确认",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mPresenter != null) mPresenter.getRemainder();
                    }
                });
    }

    public void initView(View view) {
        mImgBanner = (ImageView) view.findViewById(R.id.img_banner);
        mImgBanner.setOnClickListener(this);
        mTvNotice = (TextView) view.findViewById(R.id.tv_notice);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mTvBid = (TextView) view.findViewById(R.id.tv_bid);
        mTvBid.setOnClickListener(this);
        mEdtName = (EditText) view.findViewById(R.id.edt_name);
        mEditPhone = (EditText) view.findViewById(R.id.edit_phone);
        mImgBrand = (ImageView) view.findViewById(R.id.img_brand);
        mTvArea = (TextView) view.findViewById(R.id.tv_area);
        mLayArea = (RelativeLayout) view.findViewById(R.id.lay_area);
        mLayArea.setOnClickListener(this);
        mEditDetailedAddress = (EditText) view.findViewById(R.id.edit_detailed_address);
        mBtnCommit = (Button) view.findViewById(R.id.btn_commit);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    public String getStrEdtName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public String getStrEditPhone() {
        return mEditPhone.getText().toString().trim();
    }

    @Override
    public String getTvArea() {
        return mTvArea.getText().toString().trim();
    }

    @Override
    public String getEditDetailedAddress() {
        return mEditDetailedAddress.getText().toString().trim();
    }

    @Override
    public BidOilBean getSubmitBean() {
        return infoBean != null ? infoBean : new BidOilBean();
    }

    /**
     * 创建订单
     */
    @Override
    public void createOrderError(String message) {
        toastShort(message);
    }

    @Override
    public void createOrderSucceed(RefuelOrderResponse response) {
        toastShort(response.getResponseDesc());

        RefuelOrderBean bean = response.getData();
        if (bean != null)
            CargoRouter.gotoPayTypeActivity(getActivity(), bean.getOrderId());
        else
            toastShort("数据出错,创建订单未知错误");
    }

    @Override
    public void allAreasError(String message) {
        toastShort(message);
    }

    @Override
    public void allAreasSucceed(Object[] result) {
        if (result.length < 6) return;
        final ArrayList<String> first = (ArrayList<String>) result[3];
        final ArrayList<ArrayList<String>> second = (ArrayList<ArrayList<String>>) result[4];
        final ArrayList<ArrayList<ArrayList<String>>> third = (ArrayList<ArrayList<ArrayList<String>>>) result[5];

        final ArrayList<String> firstList = new ArrayList<>();
        firstList.addAll((ArrayList<String>) result[0]);
        final ArrayList<ArrayList<String>> secondList = new ArrayList<>();
        secondList.addAll((ArrayList<ArrayList<String>>) result[1]);
        final ArrayList<ArrayList<ArrayList<String>>> thirdList = new ArrayList<>();
        thirdList.addAll((ArrayList<ArrayList<ArrayList<String>>>) result[2]);

        CustomDialog.popupBottomAllArea(getActivity(), firstList, secondList, thirdList, new IAreaDialogListener() {
            @Override
            public void setCurPosition(String position) {
                String[] postions = position.split("/");
                int f = Integer.valueOf(postions[0]);
                int s = Integer.valueOf(postions[1]);
                int t = Integer.valueOf(postions[2]);
                mTvArea.setText(firstList.get(f) + "/" + secondList.get(f).get(s) + "/" + thirdList.get(f).get(s).get(t));
            }
        });
    }

    /**
     * 提示框
     */
    @Override
    public void isNeedCreate(OilRemainderResponse response) {
        new DialogMgr(getActivity(), "温馨提示", "",
                response.getResponseDesc(), "取消", "继续",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mPresenter != null) mPresenter.createOrder();
                    }
                });
    }
}