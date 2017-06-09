package com.zantong.mobilecttx.weizhang.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.fragment.BaseExtraFragment;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.NetUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.PayWebActivity;
import com.zantong.mobilecttx.weizhang.activity.ViolationResultAcitvity;
import com.zantong.mobilecttx.weizhang.bean.ViolationBean;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import butterknife.Bind;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class ViolationPayFragment extends BaseExtraFragment{

    @Bind(R.id.fragment_violation_paytype_layout)
    View mPayTypeLayout;
    @Bind(R.id.fragment_violation_paytype_text)
    TextView mPayTypeText;
    @Bind(R.id.fragment_violation_paytype_amount)
    TextView mAmount;       //总计
    @Bind(R.id.fragment_violation_num)
    TextView mVioNum;
    private String  remark = "3|";
    private Dialog mLoadingDialog;

    private FragmentTransaction mTransaction;
    ViolationBean data;
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_violation_pay;
    }

    public ViolationPayFragment (ViolationBean data){
        this.data = data;
    }
    @Override
    public void initView(View view) {
        mTransaction = ((ViolationResultAcitvity)this.getActivity()).getSurePayFragmentManager().beginTransaction();
        try {
            mAmount.setText(AmountUtils.changeF2Y(data.getViolationamt()) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVioNum.setText(data.getViolationnum());
    }

    @Override
    public void initData() {
        String bitNumber = data.getViolationnum().substring(6,7);
//        if("1".equals(bitNumber) || "2".equals(bitNumber)){
//            mPayTypeLayout.setClickable(true);
//            mPayTypeLayout.setFocusable(true);
//        }else{
//            mPayTypeLayout.setClickable(false);
//            mPayTypeLayout.setFocusable(false);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (((ViolationResultAcitvity) getActivity()).getPayType()){
            case 1:
                remark = "3|";
                mPayTypeText.setText("使用畅通卡缴费");
                break;
            case 2:
                remark = "4|";
                mPayTypeText.setText("使用其他工行银行卡缴费");
                break;
        }
    }

    @OnClick({R.id.fragment_violation_paytype_layout, R.id.fragment_violation_commit, R.id.fragment_violation_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_violation_paytype_layout:
                mTransaction.setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
                        .replace(((ViolationResultAcitvity)getActivity()).getPayLayoutId(), (((ViolationResultAcitvity) getActivity()).getPayTypeFragment())).commit();
                break;
            case R.id.fragment_violation_commit:
                seachViolation();
                break;
            case R.id.fragment_violation_close:
                mTransaction.remove(this).commit();
                break;
//            case R.id.pay_doubt_img:
//                lateFeeDialog();
//                break;
        }
    }
    private void closeFragment(){
//        mTransaction.remove(this).commit();
//        ((ViolationResultAcitvity) getActivity()).setIndexFlag(0);
//        ((ViolationDetails) mContext).setTitleColor();
    }
    /**
     * 跳转到缴费页面
     */
    private void gotoPay(){
        String merCustomIp = NetUtils.getPhontIP(this.getActivity());

        String violationnum = data.getViolationnum();
        String violationamt = data.getViolationamt();
        String merCustomId = PublicData.getInstance().filenum;//畅通卡档案编号
        String payUrl = BuildConfig.APP_URL+"payment_payForViolation?orderid="+violationnum+"&amount="+violationamt+
                "&merCustomIp="+merCustomIp+"&merCustomId="+merCustomId+"&remark="+remark;
        LogUtils.i("payUrl---"+payUrl);
        PublicData.getInstance().mHashMap.put("PayWebActivity", payUrl);
        Act.getInstance().lauchIntent(getContext(), PayWebActivity.class);
    }

    /**
     * 查询列表
     */
    private void seachViolation(){
        mLoadingDialog = DialogUtils.showLoading(getActivity());
        ViolationDTO violationDTO = SPUtils.getInstance(getActivity()).getViolation();
        violationDTO.setCarnum(RSAUtils.strByEncryption(getActivity(),violationDTO.getCarnum(), true));
        violationDTO.setEnginenum(RSAUtils.strByEncryption(getActivity(), violationDTO.getEnginenum(), true));
        violationDTO.setCarnumtype(violationDTO.getCarnumtype());
        UserApiClient.searchViolation(getActivity(), violationDTO, new CallBack<ViolationResultParent>() {
            @Override
            public void onSuccess(ViolationResultParent result) {
                mLoadingDialog.dismiss();
                if(result.getSYS_HEAD().getReturnCode().equals("000000")){
                    gotoPay();
                }else{
                    ToastUtils.showShort(getActivity(), "请求失败,请再次点击...");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                super.onError(errorCode, msg);
                mLoadingDialog.dismiss();
                ToastUtils.showShort(getActivity(), "请求失败,请再次点击...");
            }
        });
    }

    /**
     * 滞纳金dialog
     */
    private void lateFeeDialog(){
         DialogUtils.createLateFeeDialog(getActivity(), "滞纳金说明", "根据《中华人民共和国道" +
                 "路交通安全法》108条:当事人应当自收到行政处罚决定书" +
                 "之日起15日内，到指定的银行缴纳罚款。\n" +
                 "109条：到期不缴纳罚款的，每日按罚款数额的3%加处罚款；\n" +
                 "\n" +
                 "*滞纳金总额不会超过罚款本金的100%。\n" +
                 "*没有去开罚单的“电子警察”记录不会产生滞纳金。");
    }
}
