package com.tzly.ctcyh.cargo.refuel_v;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.router.CargoRouter;
import com.tzly.ctcyh.router.base.AbstractBaseFragment;

/**
 * 加油充值
 */
public class FoldRefuelOilFragment extends AbstractBaseFragment implements View.OnClickListener {

    private ImageView mImgBanner;
    private LinearLayout mImgOilCard;
    private LinearLayout mImgBuyCard;

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected int contentView() {
        return R.layout.cargo_fragment_fold_refuel;
    }

    @Override
    protected void bindContent(View contentView) {
        initView(contentView);
    }

    @Override
    protected void loadingFirstData() {

    }

    @Override
    protected void clickRefreshData() {

    }

    @Override
    protected void responseData(Object response) {

    }

    public static FoldRefuelOilFragment newInstance() {
        return new FoldRefuelOilFragment();
    }

    public void initView(View view) {
        mImgBanner = (ImageView) view.findViewById(R.id.img_banner);
        mImgOilCard = (LinearLayout) view.findViewById(R.id.img_oil_card);
        mImgOilCard.setOnClickListener(this);
        mImgBuyCard = (LinearLayout) view.findViewById(R.id.img_buy_card);
        mImgBuyCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_oil_card) {
           CargoRouter.gotoDiscountOilActivity(getContext());
        } else if (v.getId() == R.id.img_buy_card) {
            CargoRouter.gotoBidOilActivity(getContext());
        }
    }
}