package com.zantong.mobilecttx.weizhang.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zantong.mobilecttx.BuildConfig;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.utils.AmountUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.NetUtils;
import com.zantong.mobilecttx.utils.SPUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.weizhang.activity.PayWebActivity;
import com.zantong.mobilecttx.weizhang.activity.ViolationDetails;
import com.zantong.mobilecttx.weizhang.bean.ViolationResultParent;
import com.zantong.mobilecttx.weizhang.dto.ViolationDTO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ToastUtils;
import cn.qqtheme.framework.util.log.LogUtils;

public class SurePayFragment extends Fragment{

    @Bind(R.id.pay_type_choose_rl)
    RelativeLayout payTypeChooseRl;
    @Bind(R.id.next_btn)
    Button nextBtn;
    @Bind(R.id.close_sure_pay)
    ImageView close_sure_pay;
    @Bind(R.id.pay_type_choose)
    TextView pay_type_choose;
    @Bind(R.id.pay_money_text)
    TextView pay_money_text;  //本金
    @Bind(R.id.pay_late_fee_money_text)
    TextView mLateFeeMoney;   //滞纳金
    @Bind(R.id.pay_all_money_text)
    TextView mAllMoney;       //总计
    @Bind(R.id.order_number_text)
    TextView order_number_text;
    private Context mContext;
    private View mView;
    private FragmentTransaction transaction;
    private ViolationDetails mViolationDetails;
    private String  remark = "3|";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mViolationDetails = (ViolationDetails) mContext;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initData(){
        try {
            mAllMoney.setText(AmountUtils.changeF2Y(mViolationDetails.mapData().get("violationamt")) + "元");
            pay_money_text.setText(AmountUtils.changeF2Y(mViolationDetails.mapData().get("benjin")) + "元");
            mLateFeeMoney.setText(AmountUtils.changeF2Y(mViolationDetails.mapData().get("zhinajin")) + "元");
        } catch (Exception e) {
            e.printStackTrace();
        }
        order_number_text.setText(mViolationDetails.mapData().get("violationnum"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sure_pay_popupwindows, container, false);

        ButterKnife.bind(this, mView);
        transaction = ((ViolationDetails) mContext).getSurePayFragmentManager().beginTransaction();
        String bitNumber = mViolationDetails.mapData().get("violationnum").substring(6,7);
        if("1".equals(bitNumber) || "2".equals(bitNumber)){
            payTypeChooseRl.setClickable(true);
            payTypeChooseRl.setFocusable(true);
        }else{
            payTypeChooseRl.setClickable(false);
            payTypeChooseRl.setFocusable(false);
        }
        initData();
        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        switch (((ViolationDetails) mContext).getPayType()){
            case 1:
                remark = "3|";
                pay_type_choose.setText("使用畅通卡缴费");
                break;
            case 2:
                remark = "4|";
                pay_type_choose.setText("使用其他工行银行卡缴费");
                break;
        }
    }

    private void closeFragment(){
        ((ViolationDetails) mContext).setIndexFlag(0);
        transaction.remove(this).commit();
        ((ViolationDetails) mContext).setTitleColor();
    }

    @OnClick({R.id.pay_type_choose_rl, R.id.next_btn, R.id.close_sure_pay, R.id.pay_doubt_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_type_choose_rl:
                ((ViolationDetails) mContext).setIndexFlag(2);
                transaction.setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
                        .replace(R.id.sure_pay_frame, ((ViolationDetails) mContext).getPayTypeFragment()).commit();
                break;
            case R.id.next_btn:
                seachViolation();
                break;
            case R.id.close_sure_pay:
                closeFragment();
//                transaction.setCustomAnimations(R.anim.right_to_left, R.anim.left_to_right)
//                        .replace(R.id.sure_pay_frame, null).commit();
                break;
            case R.id.pay_doubt_img:
                lateFeeDialog();
                break;
        }
    }

    /**
     * 跳转到缴费页面
     */
    private void gotoPay(){
        String merCustomIp = NetUtils.getPhontIP(this.getActivity());

        String violationnum = mViolationDetails.mapData().get("violationnum");
        String violationamt = mViolationDetails.mapData().get("violationamt");
        String merCustomId = PublicData.getInstance().filenum;//畅通卡档案编号
        String payUrl = BuildConfig.APP_URL+"payment_payForViolation?orderid="+violationnum+"&amount="+violationamt+
                "&merCustomIp="+merCustomIp+"&merCustomId="+merCustomId+"&remark="+remark;
        LogUtils.i("payUrl---"+payUrl);
        PublicData.getInstance().mHashMap.put("PayWebActivity", payUrl);
        Act.getInstance().lauchIntent(getContext(), PayWebActivity.class);
        closeFragment();
    }

    /**
     * 查询列表
     */
    private void seachViolation(){
        final Dialog showLoading = DialogUtils.showLoading(getActivity());

        ViolationDTO violationDTO = SPUtils.getInstance().getViolation();
        violationDTO.setCarnum(RSAUtils.strByEncryption(violationDTO.getCarnum(), true));
        violationDTO.setEnginenum(RSAUtils.strByEncryption(violationDTO.getEnginenum(), true));
        violationDTO.setCarnumtype(violationDTO.getCarnumtype());
        UserApiClient.searchViolation(getActivity(), violationDTO, new CallBack<ViolationResultParent>() {
            @Override
            public void onSuccess(ViolationResultParent result) {
                if(showLoading!=null&&showLoading.isShowing())  showLoading.dismiss();
                if(result.getSYS_HEAD().getReturnCode().equals("000000")){
                    gotoPay();
                }else{
                    ToastUtils.showShort(getActivity(), "请求失败,请再次点击...");
                }
            }

            @Override
            public void onError(String errorCode, String msg) {
                if(showLoading!=null&&showLoading.isShowing())  showLoading.dismiss();
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
