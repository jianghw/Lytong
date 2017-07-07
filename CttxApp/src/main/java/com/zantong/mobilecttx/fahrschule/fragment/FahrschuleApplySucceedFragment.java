package com.zantong.mobilecttx.fahrschule.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseRefreshJxFragment;

/**
 * 驾校支付成功页面
 */
public class FahrschuleApplySucceedFragment extends BaseRefreshJxFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView mImgGift;
    /**
     * 恭喜你获得一份大礼包
     */
    private TextView mTvPrompt;
    /**
     * 礼品请到个人--优惠劵中查看
     */
    private TextView mTvPromptMine;
    private TextView mTvPromptContact;
    /**
     * 分享活动给好友
     */
    private Button mBtnPay;

    public static FahrschuleApplySucceedFragment newInstance() {
        return new FahrschuleApplySucceedFragment();
    }

    public static FahrschuleApplySucceedFragment newInstance(String param1, String param2) {
        FahrschuleApplySucceedFragment fragment = new FahrschuleApplySucceedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected int getFragmentLayoutResId() {
        return R.layout.fragment_fahrschule_apply_succeed;
    }

    @Override
    protected void initFragmentView(View view) {
        initView(view);
    }

    @Override
    protected void onFirstDataVisible() {

    }

    @Override
    protected void DestroyViewAndThing() {

    }

    public void initView(View view) {
        mImgGift = (ImageView) view.findViewById(R.id.img_gift);
        mTvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        mTvPromptMine = (TextView) view.findViewById(R.id.tv_prompt_mine);
        mTvPromptContact = (TextView) view.findViewById(R.id.tv_prompt_contact);
        mBtnPay = (Button) view.findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                break;
            default:
                break;
        }
    }
}
