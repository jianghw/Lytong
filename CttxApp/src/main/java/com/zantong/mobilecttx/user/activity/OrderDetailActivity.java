package com.zantong.mobilecttx.user.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.common.Config;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.UserApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.user.bean.CheckOrderResult;
import com.zantong.mobilecttx.user.bean.OrderItem;
import com.zantong.mobilecttx.base.bean.Result;
import com.zantong.mobilecttx.user.dto.CheckOrderDTO;
import com.zantong.mobilecttx.user.dto.InsOrderDTO;
import com.zantong.mobilecttx.presenter.OrderPresenter;
import com.zantong.mobilecttx.utils.NetUtils;
import com.zantong.mobilecttx.utils.StringUtils;
import cn.qqtheme.framework.util.ToastUtils;
import com.zantong.mobilecttx.utils.jumptools.Act;
import com.zantong.mobilecttx.weizhang.activity.PayWebActivity;
import com.zantong.mobilecttx.interf.IOrderView;

import butterknife.Bind;

/**
 * 我的订单
 *
 * @author Sandy
 *         create at 16/6/6 上午11:35
 */
public class OrderDetailActivity extends BaseMvpActivity<IOrderView, OrderPresenter> {
    @Bind(R.id.order_detail_insname)
    TextView mInsName; //保险名称
    @Bind(R.id.order_detail_appnum)
    TextView mAppNum; //投保单号
    @Bind(R.id.order_detail_appuser)
    TextView mAppUser; //投保人
    @Bind(R.id.order_detail_insuser)
    TextView mInsUser; //被保险人
    @Bind(R.id.order_detail_instm)
    TextView mInsTm; //保险期间
    @Bind(R.id.order_detail_insdamt)
    TextView mInsDamt; //保额
    @Bind(R.id.order_detail_insprem)
    TextView mInsPrem; //保费
    @Bind(R.id.order_detail_state)
    TextView mState; //状态
    @Bind(R.id.order_detail_signstate_layout)
    View mSignLayout; //签单状态布局
    @Bind(R.id.order_detail_signstate)
    TextView mSignState; //签单状态
    @Bind(R.id.order_detail_commit)
    TextView mCommit; //提交

    public static final String ORDER_ITEM = "orderItem";
    public static final int CLIENT_TYPE_1 = 1;//工行iPhone客户端版
    public static final int CLIENT_TYPE_2 = 2;//工行Android客户端版
    public static final int CLIENT_TYPE_21 = 21;//工行移动生活版（iPhone）
    public static final int CLIENT_TYPE_22 = 22;//工行移动生活版（Android）
    public static final int CLIENT_TYPE_0 = 0;//HTML版

    int orderState;

    @Override
    public void initView() {
        setTitleText("订单详情");

    }

    private String mPolcyprignum;
    private String mCastinspolcycode;
    private String mOrigtranserlnum;
    OrderItem item;
    private boolean isFirst = true;
    @Override
    public void initData() {

        mCommit.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            item = (OrderItem) intent.getSerializableExtra(ORDER_ITEM);

            mPolcyprignum = item.getPolcyprignum();
            mCastinspolcycode = item.getCastinspolcycode();
            mOrigtranserlnum = item.getHostreqserno();

            mInsName.setText(item.getInsInfo().get(0).getInsnm());
            mAppNum.setText(item.getCastinspolcycode());
            mAppUser.setText(item.getAppsnm());
            mInsUser.setText(item.getNmofinsd());
            String startTm = item.getInsInfo().get(0).getStrtdt();
            String endTm = item.getInsInfo().get(0).getTmtdt();
            mInsTm.setText(startTm.substring(0, 4) + "-" + startTm.substring(4, 6) + "-" + startTm.substring(6, 8) + "至" +
                    endTm.substring(0, 4) + "-" + endTm.substring(4, 6) + "-" + endTm.substring(6,8));

            mInsPrem.setText(StringUtils.getPriceString(item.getInsInfo().get(0).getInsdamt())+"元");
            mInsDamt.setText(StringUtils.getPriceString(item.getInsInfo().get(0).getInsprem())+"元");

            orderState = item.getOrderste();
            switch (orderState) {
                case 0:
                    mState.setText("未核保");
                    mCommit.setVisibility(View.GONE);
                    break;
                case 1:
                    mState.setText("未支付");
                    mState.setTextColor(getResources().getColor(R.color.red));
                    mSignLayout.setVisibility(View.GONE);
                    mCommit.setText("继续支付");
                    break;
                case 2:
                    mState.setText("已支付");
                    mSignLayout.setVisibility(View.VISIBLE);
                    mSignState.setText("未签单");
                    mCommit.setText("去签单");
                    break;
                case 3:
                    mState.setText("已支付");
                    mSignLayout.setVisibility(View.VISIBLE);
                    mSignState.setText("已签单");
                    mCommit.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public OrderPresenter initPresenter() {
        return new OrderPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.mine_order_detail_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detail_commit:
                if (orderState == 1) {
//                    String url = "https://211.103.143.163:13143/ecip/payment_payForInsurance";
                    String url = "https://ctkapptest.icbc-axa.com/ecip/payment_payForInsurance";
                    StringBuilder sb = new StringBuilder();
                    sb.append(url).
                            append("?orderNo=").append(item.getPolcyprignum()).     //投保单号
                            append("&payAmount=").append(item.getTotinsprem()).     //总保费
                            append("&productCode=").append(item.getCastinspolcycode()).  //保单号
                            append("&userName=").append(PublicData.getInstance().userID).  //用户ID
                            append("&clientIP=").append(NetUtils.getPhontIP(this)).
                            append("&clientType=").append(CLIENT_TYPE_0);             //启动类型

                    PublicData.getInstance().mHashMap.put("PayWebActivity", sb.toString());
                    Act.getInstance().gotoIntent(this, PayWebActivity.class);
                } else {
                    commitInsOrder();
                }
                break;
        }
    }

    /**
     * 申请保险签单
     */
    private void commitInsOrder() {
        InsOrderDTO dto = new InsOrderDTO();
        dto.setUsrid(PublicData.getInstance().userID);
        dto.setCastinspolcycode(mCastinspolcycode);
        dto.setOrigtranserlnum(mOrigtranserlnum);
        dto.setPolcyprignum(mPolcyprignum);
        UserApiClient.commitInsOrder(this, dto, new CallBack<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {
                    mState.setText("已签单");
                    mCommit.setVisibility(View.GONE);
                } else {
                    ToastUtils.showShort(OrderDetailActivity.this, result.getSYS_HEAD().getReturnMessage());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst){
            CheckOrderDTO dto = new CheckOrderDTO();
            dto.setOrigtranserlnum(mOrigtranserlnum);
            dto.setPolcyprignum(mPolcyprignum);
            UserApiClient.checkOrder(this, dto, new CallBack<CheckOrderResult>() {
                @Override
                public void onSuccess(CheckOrderResult result) {
                    if (Config.OK.equals(result.getSYS_HEAD().getReturnCode())) {

                        switch (result.getRspInfo().getPymtste()){
//                            0 未支付
//                            1 支付成功
//                            2 未支付的已撤单
//                            3 支付中
//                            4 交易可疑
//                            5 支付失败
//                            6 支付成功且退款成功的已撤单
//                            7 退款中(退款接收成功，退款处理中，退款可疑)
                            case 0:
//                                mState.setText("未支付");
//                                mCommit.setText("继续支付");
                                showToast("订单尚未支付,请继续!");
                                break;
                            case 1:
//                                mState.setText("支付成功");
//                                mCommit.setText("去签单");
                                break;
                            case 2:
//                                mState.setText("支付中");
//                                mCommit.setVisibility(View.GONE);
                                showToast("该订单已被撤销!");
                                break;
                            case 3:
//                                mState.setText("未支付");
//                                mCommit.setText("继续支付");
                                showToast("该订单正在支付中,请稍后继续!");
                                break;
                            case 4:
//                                mState.setText("交易可疑");
//                                mCommit.setText("继续支付");
                                showToast("该订单为可疑订单,请和投保公司联系确认!");
                                break;
                            case 5:
//                                mState.setText("支付失败");
//                                mCommit.setText("继续支付");
                                showToast("该订单支付失败,请继续支付!");
                                break;
                            case 6:
//                                mState.setText("未支付");
//                                mCommit.setText("继续支付");
                                showToast("该订单已被撤销!");
                                break;
                            case 7:
//                                mState.setText("退款中");
//                                mCommit.setText("继续支付");
                                showToast("该订单正在退款中,请耐心等待!");
                                break;
                        }
                    } else {
                        ToastUtils.showShort(OrderDetailActivity.this, result.getSYS_HEAD().getReturnMessage());
                    }
                }
            });
        }
        isFirst = false;
    }
    private void showToast(String msg){
        Toast toast = Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
