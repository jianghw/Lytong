package com.zantong.mobilecttx.common.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.common.activity.CommonProblemDetailActivity;
import com.zantong.mobilecttx.common.adapter.CommonProblemAdapter;
import com.zantong.mobilecttx.common.bean.CommonProblem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemFragment extends RecyclerListFragment<CommonProblem> {

    public static CommonProblemFragment newInstance() {
        return new CommonProblemFragment();
    }

    @Override
    protected boolean isRefresh() {
        return false;
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (data instanceof CommonProblem) {
            CommonProblem commonProblem = (CommonProblem) data;
            Intent intent = new Intent(getActivity(), CommonProblemDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("commonproblem", commonProblem);
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public BaseAdapter<CommonProblem> createAdapter() {
        return new CommonProblemAdapter();
    }


    @Override
    protected void loadingFirstData() {
        String app_name = getResources().getString(R.string.main_app_name);

        List<CommonProblem> commonProblemList = new ArrayList<>();

        commonProblemList.add(
                new CommonProblem(1,
                        app_name,
                        "什么是" + app_name + "App?",
                        "        " + app_name + "是由中国工商银行上海分行协助打造的一款在线查缴违章App，旨在为牡丹畅通卡用户提供便捷的驾乘金融服务体验。\n" +
                                "功能覆盖了交通违章查询与缴费、驾乘人员保险保障、特色增值服务等多项快捷的在线服务。非牡丹畅通卡持卡用户还可以在线申请办卡。"));
        commonProblemList.add(
                new CommonProblem(2,
                        "牡丹畅通卡",
                        "如何申办牡丹畅通卡?",
                        "        凡持有上海市有效机动车驾驶执照的人员均可申请办理牡丹畅通卡。您可以通过首页“畅通卡”或“个人中心-我的畅通卡”点击“申办畅通卡”提交在线办卡申请，在线申请提交后大约7个工作日即可接到工商银行短信通知您的办卡受理结果。在线办卡需要您前往您自行指定的网点去领取卡片。请留意，领卡时麻烦您在柜台激活您的预留手机号（建议当场就在App上绑定畅通卡），并开通工银e支付功能，才能在线处理违章扣分，并查询到驾照记分情况。"));
        commonProblemList.add(
                new CommonProblem(3,
                        "绑卡问题",
                        "绑卡时显示\"预留手机号不符\"",
                        "        绑卡时显示”预留手机号不符“的话，原因是您办卡后未在柜台激活预留手机号或银行内留存的手机号不正确导致。需要您在工商银行网点柜台对预留手机号进行激活或变更。请一定在柜面(非终端)完成绑定预留手机号及开通e支付后再离开。"));

        commonProblemList.add(
                new CommonProblem(4,
                        "在线办卡",
                        "我的畅通卡没有开通工银e支付该怎么办?",
                        "        需要您在工商银行网点柜台开通e支付，手机目前不支持开通e支付通道。"));
        commonProblemList.add(
                new CommonProblem(5,
                        "违章缴费类型",
                        app_name + "App可以处理哪些违章?",
                        "        交通违法分为“电子警察”、“违法停车”、“现场执法”三种，使用" + app_name + "App可自助处理全部类型的交通违法，并同步完成缴纳罚款的动作。是唯一一款查扣缴一体化的App。"));

        commonProblemList.add(
                new CommonProblem(6,
                        "联系客服",
                        "如何联系客服?",
                        "        您可以在公众号留言描述您的问题，我们会在工作时间2小时内对您的问题进行答复。"));

        commonProblemList.add(
                new CommonProblem(7,
                        "缴费安全",
                        "违章缴费是否安全？与代缴的区别是什么?",
                        "        " + app_name + "App是由中国工商银行上海市分行打造的违章查扣缴一体化App平台，您的个人敏感信息及畅通卡信息不会保存在银行系统之外。使用" + app_name + "App是用您个人的畅通卡（绑定了驾照）进行违章处理和罚款缴纳，免费且安全。市面常见的各种代缴则需要提供您的个人信息给第三方，由第三方为您代为处理，不安全且要收费。"));

        commonProblemList.add(
                new CommonProblem(8,
                        "绑定车辆",
                        "怎样改/解绑车辆",
                        "        根据上海市交警总队通知要求，自2009年4月1日起，每张牡丹畅通卡可处理违法车辆调整为2辆（即可缴费车辆）。当有第三辆车的时候 可通过“个人中心 - 车辆管理 – 设置”对新增车辆进行改绑操作，改绑操作12小时后即可对新绑定车辆进行违章处理。"));

        commonProblemList.add(
                new CommonProblem(9,
                        "短信服务",
                        "无法收到短信验证码/提示验证码已经超过5次",
                        "        收不到注册码有可能以下几个原因: 1.网络不畅，请确认是否给予App移动网络的连接权限2.华为P9/P10/Mate8/Mate9等机型，安装了一些手机管家、安全类的App，这些应用的策略可能将收到的验证码短信作为骚扰短信进行了拦截。请卸载这些APP后重启再试。"));

        commonProblemList.add(
                new CommonProblem(10,
                        "违章查询",
                        "为什么查询不到我的违章?",
                        "        请更新软件最新版本，输入正确的发动机号后5位，包括字母（注意大小写）。现场处理的违法行为是驾照违章，要求绑定畅通卡后才能在App上按驾照号查到。外地车牌只能查询在上海本市内的违章记录。"));

        commonProblemList.add
                (new CommonProblem(11,
                        "违章滞纳金",
                        "滞纳金”是什么?",
                        "        根据《中华人民共和国道路交通安全法》第七章第109条规定：交通违法者自领到交通违法凭证15日内须缴纳罚款，逾期不接受处理的，从第16日起每日按罚金的3%收取滞纳金。滞纳金总额不会超过罚款本金的100%。没有去开罚单的“电子警察”记录不会产生滞纳金。"));

        commonProblemList.add(
                new CommonProblem(12,
                        "违章申诉",
                        "对违章有异议该怎么办?",
                        "        您若对交通违法事实有异议，需要陈述或申辩的，需要到市交警相关服务窗口进行处理。"));

        commonProblemList.add(
                new CommonProblem(13,
                        "在线缴费",
                        "如何缴纳罚款?",
                        "        用" + app_name + "App扫描处罚决定书，即可按照系统提示按步骤操作。支持工商银行所有银行卡。请确认该卡已开通“工银e支付”功能。"));

        setSimpleDataResult(commonProblemList);
    }

    @Override
    protected void initPresenter() {

    }
}
