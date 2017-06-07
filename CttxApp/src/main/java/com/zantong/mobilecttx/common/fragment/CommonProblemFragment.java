package com.zantong.mobilecttx.common.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.common.adapter.CommonProblemAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.common.bean.CommonProblem;
import com.zantong.mobilecttx.common.activity.CommonProblemDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujie on 2016/12/23.
 */

public class CommonProblemFragment extends BaseListFragment<CommonProblem> {

    @Override
    protected boolean isLoadMore() {
        return false;
    }

    @Override
    protected boolean isRefresh() {
        return false;
    }


    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        CommonProblem commonProblem = (CommonProblem) data;
        Intent intent = new Intent(getActivity(), CommonProblemDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commonproblem", commonProblem);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    protected void getData() {
        getListView().noMoreLoadings();
        List<CommonProblem> commonProblemList = new ArrayList<>();

        commonProblemList.add(
                new CommonProblem(1,
                        "畅通车友会",
                        "什么是畅通车友会App?",
                        "        畅通车友会是由中国工商银行上海分行协助打造的一款在线查缴违章App，旨在为牡丹畅通卡用户提供便捷的驾乘金融服务体验。\n" +
                                "功能覆盖了交通违章查询与缴费、驾乘人员保险保障、特色增值服务等多项快捷的在线服务。非牡丹畅通卡持卡用户还可以在线申请办卡。"));
        commonProblemList.add(
                new CommonProblem(2,
                        "绑卡",
                        "绑卡时显示\"预留手机号不符\"",
                        "        绑卡时显示”预留手机号不符“的原因是您办卡后，需在柜台激活预留手机。您在工商银行网点柜台对预留手机号进行激活，就可以正常使用了。因此，领卡时请一定在柜面(非终端)完成绑定预留手机号及开通e支付后再离开。"));

        commonProblemList.add(
                new CommonProblem(3,
                        "绑定车辆",
                        "怎样改/解绑车辆",
                        "        根据上海市交警总队通知要求，自2009年4月1日起，每张牡丹畅通卡可缴纳违法车辆调整为2辆（即绑定车辆）。如需车辆更换，可通过“个人中心- 车辆管理 – 设置”对新增车辆进行改绑操作，改绑操作12小时后即可对新绑定车辆进行违章处理。"));

        commonProblemList.add(
                new CommonProblem(4,
                        "短信验证码",
                        "无法收到短信验证码/提示验证码已经超过5次",
                        "        注册收不到注册码有可能以下几个原因:1.网络不畅，请确认是否给与app网络连接权限2.安装了一些手机管家、安全类的app,他们的默认设置可能影响了注册时往外送手机号码，导致不能成功获得注册码，或者将收到的验证码短信作为骚扰短信进行了拦截。"));

        commonProblemList.add(
                new CommonProblem(5,
                        "违章查询",
                        "为什么查询不到我的违章?",
                        "        请更新软件最新版本输入正确的发动机号后5位包括字母（注意大小写）。现场处理的违章要求绑定畅通卡后才能查询。外地车牌只能查询在上海本市内的违章记录。"));

        commonProblemList.add(
                new CommonProblem(6,
                        "牡丹畅通卡",
                        "如何申办牡丹畅通卡?",
                        "        凡持有上海市有效机动车驾驶执照的人员均可申请办理牡丹畅通卡。" +
                                "您可以通过首页“畅通卡”或“我的-我的畅通卡”点击“申办畅通卡”提交在线办卡申请，" +
                                "在线申请提交后大约5个工作日即可接到工商银行短信通知您的办卡受理结果。在线提交办卡需要您前往您自行指定的网点去" +
                                "领取卡片，请您留意，领卡时需确认您的预留手机号已激活并开通工银e支付功能才能在线缴纳罚款。"));

        commonProblemList.add
                (new CommonProblem(7,
                        "在线缴费",
                        "如何缴纳罚款/提示开通e支付?",
                        "        缴纳罚款需要首先绑定牡丹畅通卡，使用牡丹畅通卡进行违章处理，同时需要在银行柜面激活预留手机号并开通工银e支付。"));

        commonProblemList.add(
                new CommonProblem(8,
                        "缴费安全",
                        "违章缴费是否安全？与代缴的区别是什么?",
                        "        畅通车友会是由中国工商银行上海市分行支持打造的在线缴付违章平台，" +
                                "您的个人信息及畅通卡信息不会保存在银行系统之外，使用畅通车友会分秒之间缴纳罚款，" +
                                "免排队、付款后即时扣分，一步到位，安全方便。市面常见的各种代缴大多数则需要提供您的个人信息给第三方，" +
                                "由第三方为您代为缴付，且大部分不支持有扣分的违章缴纳。"));

        commonProblemList.add(
                new CommonProblem(9,
                        "违章缴费类型",
                        "畅通卡可以缴纳哪些罚款?",
                        "        交通违法罚款分为“电子警察”、“违法停车”、“现场执法”三种，使用牡丹畅通卡可自助缴纳以上类型的交通违法罚款。暂时无法受理异地车牌的“电子警察”、" +
                                "“违法停车”罚款自助缴纳。"));

        commonProblemList.add(
                new CommonProblem(9,
                        "违章申诉",
                        "对违章有异议该怎么办?",
                        "        您若对交通违法事实有异议，需要陈述或申辩的，需要到市交警相关服务窗口进行处理。"));

        commonProblemList.add(
                new CommonProblem(10,
                        "联系客服",
                        "如何联系客服?",
                        "        您可以在公众号留言描述您的问题，我们会在工作时间2小时内对您的问题进行答复"));
        setDataResult(commonProblemList);
    }

    @Override
    public BaseAdapter<CommonProblem> createAdapter() {
        return new CommonProblemAdapter();
    }

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }
}
