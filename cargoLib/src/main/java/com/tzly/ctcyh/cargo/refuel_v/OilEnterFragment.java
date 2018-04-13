package com.tzly.ctcyh.cargo.refuel_v;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.refuel_p.IOilEnterContract;
import com.tzly.ctcyh.cargo.refuel_p.OilEnterPresenter;
import com.tzly.ctcyh.cargo.refuel_p.RefuelOilPresenter;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.java.response.oil.OilEnterResponse;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.primission.PermissionFail;
import com.tzly.ctcyh.router.custom.primission.PermissionGen;
import com.tzly.ctcyh.router.custom.primission.PermissionSuccess;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.Utils;

import static com.tzly.ctcyh.router.custom.primission.PermissionGen.PER_REQUEST_CODE;

/**
 * 加油进入
 */
public class OilEnterFragment extends RefreshFragment
        implements View.OnClickListener, IOilEnterContract.IOilEnterView {

    /**
     * 9.97
     */
    private TextView mTvOil997;
    /**
     * 去充值 >
     */
    private TextView mTv997Chongzhi;
    private LinearLayout mLayOil997;
    /**
     * 畅通97折油卡
     */
    private TextView mTvOil97;
    private ImageView mImgOil97;
    /**
     * 已购卡
     */
    private TextView mTv97Gou;
    /**
     * 折起
     */
    private TextView mTv97Zhe;
    /**
     * 购卡充油>
     */
    private TextView mTv97Gouka;
    private LinearLayout mLayOil97;
    /**
     * 工行爱车卡
     */
    private TextView mTvOilBank;
    private ImageView mImgOilBank;
    /**
     * 已购卡
     */
    private TextView mTvBankGou;
    /**
     * 折起
     */
    private TextView mTvBankZhe;
    /**
     * 办卡充油>
     */
    private TextView mTvBankGouka;
    /**
     * 9.96
     */
    private TextView mTvOilMap;
    /**
     * 查看地图 >
     */
    private TextView mTvMap;
    /**
     * 可查询: 97折油卡及爱车卡支持加油站点
     */
    private TextView mTvMapHint;
    private LinearLayout mLayMap;
    private LinearLayout mLayOilBank;

    private IOilEnterContract.IOilEnterPresenter mPresenter;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_oil_enter;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);


        OilEnterPresenter presenter = new OilEnterPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.getCounts();
    }

    public static OilEnterFragment newInstance() {
        return new OilEnterFragment();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mPresenter != null) mPresenter.unSubscribe();
    }

    public void initView(View view) {
        mTvOil997 = (TextView) view.findViewById(R.id.tv_oil_997);
        mTv997Chongzhi = (TextView) view.findViewById(R.id.tv_997_chongzhi);
        mTv997Chongzhi.setOnClickListener(this);
        mLayOil997 = (LinearLayout) view.findViewById(R.id.lay_oil_997);
        mLayOil997.setOnClickListener(this);

        mTvOil97 = (TextView) view.findViewById(R.id.tv_oil_97);
        mImgOil97 = (ImageView) view.findViewById(R.id.img_oil_97);
        mTv97Gou = (TextView) view.findViewById(R.id.tv_97_gou);
        mTv97Zhe = (TextView) view.findViewById(R.id.tv_97_zhe);
        mTv97Gouka = (TextView) view.findViewById(R.id.tv_97_gouka);
        mTv97Gouka.setOnClickListener(this);
        mLayOil97 = (LinearLayout) view.findViewById(R.id.lay_oil_97);
        mLayOil97.setOnClickListener(this);

        mTvOilBank = (TextView) view.findViewById(R.id.tv_oil_bank);
        mImgOilBank = (ImageView) view.findViewById(R.id.img_oil_bank);
        mTvBankGou = (TextView) view.findViewById(R.id.tv_bank_gou);
        mTvBankZhe = (TextView) view.findViewById(R.id.tv_bank_zhe);
        mTvBankGouka = (TextView) view.findViewById(R.id.tv_bank_gouka);
        mTvBankGouka.setOnClickListener(this);
        mLayOilBank = (LinearLayout) view.findViewById(R.id.lay_oil_bank);
        mLayOilBank.setOnClickListener(this);

        mTvOilMap = (TextView) view.findViewById(R.id.tv_oil_map);
        mTvMap = (TextView) view.findViewById(R.id.tv_map);
        mTvMap.setOnClickListener(this);
        mTvMapHint = (TextView) view.findViewById(R.id.tv_map_hint);
        mLayMap = (LinearLayout) view.findViewById(R.id.lay_map);
        mLayMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        if (vId == R.id.tv_997_chongzhi || vId == R.id.lay_oil_997) {//9.97
            CargoRouter.gotoRechargeActivity(getActivity());
        } else if (vId == R.id.tv_97_gouka || vId == R.id.lay_oil_97) {//8.8
            CargoRouter.gotoFoldOilActivity(getActivity());
        } else if (vId == R.id.tv_bank_gouka || vId == R.id.lay_oil_bank) {//9
            CargoRouter.gotoBidOilActivity(getActivity());
        } else if (vId == R.id.tv_map || vId == R.id.lay_map) {//9.96
            showContacts();
        }
    }

    @Override
    public void setPresenter(IOilEnterContract.IOilEnterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void countError(String message) {
        ToastUtils.toastShort(message);
    }

    @Override
    public void countSucceed(OilEnterResponse response) {
        OilEnterResponse.DataBean data = response.getData();
        int count = data.getCount();
        mTv97Gou.setText(count + "人 " + "已购卡");
        mTvBankGou.setText(count + "人 " + "已购卡");
    }

    public void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            PermissionGen.needPermission(this, PER_REQUEST_CODE, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE});
        } else {
            gotoOilMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 申请拍照运行时权限
     */
    @PermissionSuccess(requestCode = PER_REQUEST_CODE)
    public void doPermissionSuccess() {
        gotoOilMap();
    }

    private void gotoOilMap() {
        CargoRouter.gotoOilMapActivity(getContext());
    }

    @PermissionFail(requestCode = PER_REQUEST_CODE)
    public void doPermissionFail() {
        ToastUtils.toastShort("此功能需要打开相关的地图权限");
    }
}