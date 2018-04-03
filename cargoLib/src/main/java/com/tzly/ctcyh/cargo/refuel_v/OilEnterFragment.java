package com.tzly.ctcyh.cargo.refuel_v;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.router.base.RefreshFragment;

/**
 * 加油进入
 */
public class OilEnterFragment extends RefreshFragment implements View.OnClickListener {

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

    protected boolean isRefresh() {
        return false;
    }

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

    }

    @Override
    protected void loadingFirstData() {

    }

    @Override
    protected void responseData(Object response) {

    }

    public static OilEnterFragment newInstance() {
        return new OilEnterFragment();
    }


    public void initView(View view) {
        mTvOil997 = (TextView) view.findViewById(R.id.tv_oil_997);
        mTv997Chongzhi = (TextView) view.findViewById(R.id.tv_997_chongzhi);
        mLayOil997 = (LinearLayout) view.findViewById(R.id.lay_oil_997);
        mTvOil97 = (TextView) view.findViewById(R.id.tv_oil_97);
        mImgOil97 = (ImageView) view.findViewById(R.id.img_oil_97);
        mTv97Gou = (TextView) view.findViewById(R.id.tv_97_gou);
        mTv97Zhe = (TextView) view.findViewById(R.id.tv_97_zhe);
        mTv97Gouka = (TextView) view.findViewById(R.id.tv_97_gouka);
        mLayOil97 = (LinearLayout) view.findViewById(R.id.lay_oil_97);
        mTvOilBank = (TextView) view.findViewById(R.id.tv_oil_bank);
        mImgOilBank = (ImageView) view.findViewById(R.id.img_oil_bank);
        mTvBankGou = (TextView) view.findViewById(R.id.tv_bank_gou);
        mTvBankZhe = (TextView) view.findViewById(R.id.tv_bank_zhe);
        mTvBankGouka = (TextView) view.findViewById(R.id.tv_bank_gouka);
        mTvBankGouka.setOnClickListener(this);
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
        if (vId == R.id.tv_bank_gouka) {

        } else if (vId == R.id.tv_map) {
        } else if (vId == R.id.lay_map) {
        } else {
        }
    }
}